package kpfu.itis.allayarova.command;

import kpfu.itis.allayarova.dto.request.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.util.Set;

@Value
public class CreateOrderCommand {
    @TargetAggregateIdentifier
    Long id;
    Long customerId;
    Set<OrderItem> items;
}