package kpfu.itis.allayarova.event;

import lombok.Value;

import java.util.List;

@Value
public class InventoryCheckedEvent {
    Long orderId;
    List<Long> productsId;
}
