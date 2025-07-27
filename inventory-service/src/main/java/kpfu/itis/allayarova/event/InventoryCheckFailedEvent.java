package kpfu.itis.allayarova.event;

import lombok.Value;

import java.util.Set;

@Value
public class InventoryCheckFailedEvent {
    Long orderId;
    Set<Long> productsId;
    String reason;
}

