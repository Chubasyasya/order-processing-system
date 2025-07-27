package kpfu.itis.allayarova.event;

import lombok.Value;

@Value
public class PaymentProcessFailedEvent {
    Long orderId;
}
