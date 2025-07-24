package kpfu.itis.allayarova;

import kpfu.itis.allayarova.command.CheckInventoryCommand;
import kpfu.itis.allayarova.command.CompleteOrderCommand;
import kpfu.itis.allayarova.data.model.OrderItemEntity;
import kpfu.itis.allayarova.event.InventoryCheckedEvent;
import kpfu.itis.allayarova.event.OrderCompletedEvent;
import kpfu.itis.allayarova.event.OrderCreatedEvent;
import kpfu.itis.allayarova.command.ProcessPaymentCommand;
import kpfu.itis.allayarova.event.PaymentProcessedEvent;
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
    @SagaEventHandler(associationProperty = "orderId")
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
    public void on(PaymentProcessedEvent event) {
        log.info("Оплата прошла успешно для заказа {}", orderId);

        commandGateway.send(new CompleteOrderCommand(orderId));
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderCompletedEvent event) {
        log.info("Сага завершена для заказа {}", orderId);
    }
}
