package kpfu.itis.allayarova.aggregate;

import kpfu.itis.allayarova.command.CompleteOrderCommand;
import kpfu.itis.allayarova.command.CreateOrderCommand;
import kpfu.itis.allayarova.data.model.OrderItemEntity;
import kpfu.itis.allayarova.data.model.OrderStatus;
import kpfu.itis.allayarova.event.OrderCompletedEvent;
import kpfu.itis.allayarova.event.OrderCreatedEvent;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Aggregate
@NoArgsConstructor
public class OrderAggregate {
    @TargetAggregateIdentifier
    Long id;
    Long customerId;
    LocalDateTime orderDate;
    Set<OrderItemEntity> items;
    OrderStatus status;
    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        AggregateLifecycle.apply(new OrderCreatedEvent(
                command.getId(),
                command.getCustomerId(),
                LocalDateTime.now(),
                command.getItems()
        ));
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        this.id = event.getId();
        this.customerId = event.getCustomerId();
        this.orderDate = event.getOrderDate();
        this.items = event.getItems();
    }

    @CommandHandler
    public void handle(CompleteOrderCommand command) {
        AggregateLifecycle.apply(new OrderCompletedEvent(command.getId()));
    }

    @EventSourcingHandler
    public void on(OrderCompletedEvent event) {
        this.status = OrderStatus.PROCESSING;
    }

}
