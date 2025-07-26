package kpfu.itis.allayarova.command;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

@Value
public class CheckInventoryCommand {
    @TargetAggregateIdentifier
    Long orderId;
    List<Long> productsId;
}
