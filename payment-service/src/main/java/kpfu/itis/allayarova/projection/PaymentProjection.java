package kpfu.itis.allayarova.projection;

import kpfu.itis.allayarova.event.PaymentProcessedEvent;
import kpfu.itis.allayarova.service.PaymentReceiptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentProjection {
    @EventHandler
    public void on(PaymentProcessedEvent event){
        Long orderId = event.getOrderId();
        log.info("Payment for order %d was fixed".formatted(orderId));
    }
}
