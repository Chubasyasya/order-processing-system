package kpfu.itis.allayarova.aggregate;

import kpfu.itis.allayarova.command.CancelReservationCommand;
import kpfu.itis.allayarova.command.CheckInventoryCommand;
import kpfu.itis.allayarova.event.InventoryCheckFailedEvent;
import kpfu.itis.allayarova.event.InventoryCheckedEvent;
import kpfu.itis.allayarova.event.ReservationCancelledEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Set;
import java.util.stream.Collectors;

@Aggregate
@NoArgsConstructor
@Slf4j
public class InventoryAggregate {
    @AggregateIdentifier
    private Long orderId;

    @CommandHandler
    public InventoryAggregate(CheckInventoryCommand command){
        boolean allProductsAvailable = false;
        log.info("Inventory aggregate check inventory command");

        // Здесь нужно сделать проверку наличия товаров в базе данных и уменьшить их количество на quantity
        // в данный момент это эмуляция проверки наличия товаров
        if (allProductsAvailable) {
            AggregateLifecycle.apply(new InventoryCheckedEvent(
                    command.getOrderId(), command.getProductsId(), null
            ));
        } else {
            AggregateLifecycle.apply(new InventoryCheckFailedEvent(
                    command.getOrderId(), command.getProductsId().stream().collect(Collectors.toSet()), "Some products are out of stock"
            ));
        }

    }

    @EventSourcingHandler
    public void on(InventoryCheckedEvent event) {
        this.orderId=event.getOrderId();
    }
    @EventSourcingHandler
    public void on(InventoryCheckFailedEvent event) {
        this.orderId = event.getOrderId();
    }

    @CommandHandler
    public void handle(CancelReservationCommand command) {
        AggregateLifecycle.apply(new ReservationCancelledEvent(command.getOrderId()));
    }

    @EventSourcingHandler
    public void on(ReservationCancelledEvent event) {
        this.orderId = event.getOrderId();
    }
}
