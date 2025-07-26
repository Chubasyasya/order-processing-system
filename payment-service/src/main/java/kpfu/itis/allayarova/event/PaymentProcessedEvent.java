package kpfu.itis.allayarova.event;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class PaymentProcessedEvent {
    Long orderId;
    BigDecimal sum;
}
