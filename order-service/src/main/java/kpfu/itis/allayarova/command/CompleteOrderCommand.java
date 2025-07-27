package kpfu.itis.allayarova.command;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class CompleteOrderCommand {
    @TargetAggregateIdentifier
    Long orderId;;
}
