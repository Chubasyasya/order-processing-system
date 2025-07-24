package kpfu.itis.allayarova.command;

import lombok.Value;

import java.util.List;

@Value
public class CheckInventoryCommand {
    Long orderId;
    List<Long> productsId;
}
