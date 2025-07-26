package kpfu.itis.allayarova.command;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class ProcessPaymentCommand {
    Long orderId;
    BigDecimal sum;
}
