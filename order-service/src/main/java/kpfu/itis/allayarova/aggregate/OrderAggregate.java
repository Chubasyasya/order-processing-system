package kpfu.itis.allayarova.aggregate;

import kpfu.itis.allayarova.command.CancelOrderCommand;
import kpfu.itis.allayarova.command.CompleteOrderCommand;
import kpfu.itis.allayarova.command.CreateOrderCommand;
import kpfu.itis.allayarova.data.model.OrderItemEntity;
import kpfu.itis.allayarova.data.model.OrderStatus;
import kpfu.itis.allayarova.event.OrderCancelledEvent;
import kpfu.itis.allayarova.event.OrderCompletedEvent;
import kpfu.itis.allayarova.event.OrderCreatedEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.LocalDateTime;
import java.util.Set;

@Aggregate
@NoArgsConstructor
@Slf4j
public class OrderAggregate {
    @AggregateIdentifier
    Long id;
    Long customerId;
    LocalDateTime orderDate;
    Set<OrderItemEntity> items;
    OrderStatus status;
    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        log.info("Order aggregate create order command");
        AggregateLifecycle.apply(new OrderCreatedEvent(
                command.getId(),
                command.getCustomerId(),
                LocalDateTime.now(),
                command.getItems()
        ));
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        log.info("Order aggregate order created event");

        this.id = event.getId();
        this.customerId = event.getCustomerId();
        this.orderDate = event.getOrderDate();
        this.items = event.getItems();
    }

    @CommandHandler
    public void handle(CompleteOrderCommand command) {
        log.info("Order aggregate complete order command");

        AggregateLifecycle.apply(new OrderCompletedEvent(command.getOrderId()));
    }

    @EventSourcingHandler
    public void on(OrderCompletedEvent event) {
        log.info("Order aggregate order completed event");

        this.status = OrderStatus.PROCESSING;
    }

    @CommandHandler
    public void handle(CancelOrderCommand command) {
        AggregateLifecycle.apply(new OrderCancelledEvent(command.getOrderId()));
    }

    @EventSourcingHandler
    public void on(OrderCancelledEvent event) {
        this.id = event.getOrderId();
        this.status = OrderStatus.CANCELLED;
    }
}
