package kpfu.itis.allayarova.minio;

import io.minio.*;
import io.minio.http.Method;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

@Component
public class MinioClientWrapper {

    private final MinioClient minioClient;

    public MinioClientWrapper(MinioClient minioClient) {
        this.minioClient = minioClient;
    }
    public void uploadPdf(String bucketName, String objectName, InputStream pdfStream, long size, String contentType) throws Exception {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .stream(pdfStream, size, -1)
                        .contentType(contentType)
                        .build()
        );
    }

    public String getPresignedUrl(String bucketName, String objectName) throws Exception {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucketName)
                        .object(objectName)
                        .expiry(1, TimeUnit.HOURS)
                        .build()
        );
    }
}
