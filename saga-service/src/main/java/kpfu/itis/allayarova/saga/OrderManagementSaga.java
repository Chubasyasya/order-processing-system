package kpfu.itis.allayarova.saga;

import kpfu.itis.allayarova.command.*;
import kpfu.itis.allayarova.data.model.OrderItemEntity;
import kpfu.itis.allayarova.event.*;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Saga
@Slf4j
public class OrderManagementSaga {
    @Autowired
    private transient CommandGateway commandGateway;

    private Long orderId;

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    public void on(OrderCreatedEvent event){
        this.orderId=event.getId();
        log.info("Сага началась для заказа {}", orderId);

        List<Long> productsId = event.getItems().stream().map(OrderItemEntity::getProductId).collect(Collectors.toList());

        commandGateway.send(new CheckInventoryCommand(this.orderId, productsId));
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(InventoryCheckedEvent event) {
        log.info("Наличие товаров подтверждено для заказа {}", orderId);
        BigDecimal fakeSum = new BigDecimal(10000);

        commandGateway.send(new ProcessPaymentCommand(orderId, fakeSum));
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(InventoryCheckFailedEvent event) {
        log.info("Наличие товаров не подтверждено для заказа {}", orderId);
        CancelOrderCommand command = new CancelOrderCommand(event.getOrderId());
        commandGateway.send(command);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(PaymentProcessedEvent event) {
        log.info("Оплата прошла успешно для заказа {}", orderId);

        commandGateway.send(new CompleteOrderCommand(orderId));
    }

    // Оплата не прошла
    // Отменяем резервацию товара
    @SagaEventHandler(associationProperty = "orderId")
    public void on(PaymentProcessFailedEvent event) {
        log.info("Оплата не прошла, отменяем резерв для заказа {}", event.getOrderId());
        commandGateway.send(new CancelReservationCommand(event.getOrderId()));
    }

    // Отмечаем что заказ отменен
    @SagaEventHandler(associationProperty = "orderId")
    public void on(ReservationCancelledEvent event) {
        log.info("Резерв отменён, отменяем заказ {}", event.getOrderId());
        commandGateway.send(new CancelOrderCommand(event.getOrderId()));
    }
    // Завершаем сагу
    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderCancelledEvent event) {
        log.info("Заказ {} отменён, завершаем сагу", event.getOrderId());
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderCompletedEvent event) {
        log.info("Сага завершена для заказа {}", orderId);
    }
}
