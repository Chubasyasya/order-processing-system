package kpfu.itis.allayarova.command;

import kpfu.itis.allayarova.data.model.OrderItemEntity;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Set;

@Value
public class CreateOrderCommand {
    @TargetAggregateIdentifier
    Long id;
    Long customerId;
    Set<OrderItemEntity> items;
}