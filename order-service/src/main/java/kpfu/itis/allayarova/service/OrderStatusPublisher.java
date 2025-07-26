package kpfu.itis.allayarova.service;

import kpfu.itis.allayarova.data.view.OrderView;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OrderStatusPublisher {

    private final Map<Long, Sinks.Many<OrderView>> sinks = new ConcurrentHashMap<>();

    public Flux<OrderView> subscribeToOrder(Long orderId) {
        return sinks
                .computeIfAbsent(orderId, id -> Sinks.many().multicast().onBackpressureBuffer())
                .asFlux();
    }

    public void publishStatus(OrderView orderView) {
        Sinks.Many<OrderView> sink = sinks.get(orderView.getId());
        if (sink != null) {
            sink.tryEmitNext(orderView);
        }
    }
}

