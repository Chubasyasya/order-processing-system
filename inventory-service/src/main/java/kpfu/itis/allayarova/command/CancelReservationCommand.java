package kpfu.itis.allayarova.command;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class CancelReservationCommand {
    @TargetAggregateIdentifier
    Long orderId;
}
