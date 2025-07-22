package kpfu.itis.allayarova.repository;

import kpfu.itis.allayarova.data.model.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<Long, OrderItemEntity> {
}
