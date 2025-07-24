package kpfu.itis.allayarova.repository;

import kpfu.itis.allayarova.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
