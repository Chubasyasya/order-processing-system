package kpfu.itis.allayarova.aggregate;

import kpfu.itis.allayarova.command.ProcessPaymentCommand;
import kpfu.itis.allayarova.event.PaymentProcessFailedEvent;
import kpfu.itis.allayarova.event.PaymentProcessedEvent;
import kpfu.itis.allayarova.service.PaymentReceiptService;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
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

        // Тут должна быть проверка оплаты заказа, что оплата прошла успешно
        boolean paymentAvailable = true;
        if(paymentAvailable) {
            AggregateLifecycle.apply(new PaymentProcessedEvent(
                    command.getOrderId(), command.getSum()));
        }else {
            AggregateLifecycle.apply(new PaymentProcessFailedEvent(
                    command.getOrderId()));
        }
    }

    @EventSourcingHandler
    public void on(PaymentProcessedEvent event) {
        this.orderId = event.getOrderId();
        this.sum=event.getSum();
    }

    @EventSourcingHandler
    public void on(PaymentProcessFailedEvent event) {
        this.orderId = event.getOrderId();
    }
}
