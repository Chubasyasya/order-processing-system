package kpfu.itis.allayarova.aggregate;

import kpfu.itis.allayarova.command.CheckInventoryCommand;
import kpfu.itis.allayarova.event.InventoryCheckedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class InventoryAggregate {
    @TargetAggregateIdentifier
    private Long orderId;

    @CommandHandler
    public InventoryAggregate(CheckInventoryCommand command){
        AggregateLifecycle.apply(new InventoryCheckedEvent(command.getOrderId(), command.getProductsId(), null));
    }

    @EventSourcingHandler
    public void on(InventoryCheckedEvent event) {
        this.orderId=event.getOrderId();
    }
}
