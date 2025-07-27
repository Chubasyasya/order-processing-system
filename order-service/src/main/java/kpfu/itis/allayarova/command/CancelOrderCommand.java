package kpfu.itis.allayarova.command;

import lombok.Value;

@Value
public class CancelOrderCommand {
    Long orderId;
}
