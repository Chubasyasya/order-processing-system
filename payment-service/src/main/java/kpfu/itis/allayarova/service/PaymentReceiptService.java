package kpfu.itis.allayarova.service;

import kpfu.itis.allayarova.minio.MinioClientWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class PaymentReceiptService {

    private final MinioClientWrapper minioClientWrapper;
    private final String bucketName = "payment-receipts";

    public String processPayment(Long orderId, byte[] pdfBytes) throws Exception {
        String objectName = "receipt_" + orderId + ".pdf";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(pdfBytes);
        minioClientWrapper.uploadPdf(bucketName, objectName, inputStream, pdfBytes.length, "application/pdf");

        return minioClientWrapper.getPresignedUrl(bucketName, objectName);
    }

    public byte[] generatePdfReceipt(Long orderId) {
        String fakePdfContent = "Receipt for order #" + orderId;
        return fakePdfContent.getBytes(StandardCharsets.UTF_8);
    }
}