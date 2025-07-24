package kpfu.itis.allayarova.service;


import kpfu.itis.allayarova.data.view.OrderView;
import kpfu.itis.allayarova.exception.OrderNotFoundException;
import kpfu.itis.allayarova.mapper.OrderMapper;
import kpfu.itis.allayarova.query.OrderQuery;
import kpfu.itis.allayarova.repository.OrderRepository;
import kpfu.itis.allayarova.exception.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderQueryHandler {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    public OrderView handle(OrderQuery query){
        return orderMapper.toView(
                orderRepository.findById(query.getId())
                        .orElseThrow(() -> new OrderNotFoundException(query.getId())));
    }

}
