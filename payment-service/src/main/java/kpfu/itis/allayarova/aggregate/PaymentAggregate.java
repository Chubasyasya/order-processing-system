package kpfu.itis.allayarova.aggregate;

import kpfu.itis.allayarova.command.ProcessPaymentCommand;
import kpfu.itis.allayarova.event.PaymentProcessedEvent;
import kpfu.itis.allayarova.service.PaymentReceiptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@Aggregate
@Slf4j
public class PaymentAggregate {
    @TargetAggregateIdentifier
    private Long orderId;
    private BigDecimal sum;
    @Autowired
    private PaymentReceiptService paymentService;

    @CommandHandler
    public PaymentAggregate(ProcessPaymentCommand command){
        try {
            byte[] pdf = paymentService.generatePdfReceipt(command.getOrderId());
            String url = paymentService.processPayment(command.getOrderId(), pdf);
            log.info("PDF чек сохранен. Ссылка: {}", url);
        } catch (Exception e) {
            log.error("Ошибка при сохранении PDF в MinIO", e);
        }

        AggregateLifecycle.apply(new PaymentProcessedEvent(
                command.getOrderId(),command.getSum()));
    }

    @EventSourcingHandler
    public void on(PaymentProcessedEvent event) {
        this.orderId = event.getOrderId();
        this.sum=event.getSum();
    }
}
