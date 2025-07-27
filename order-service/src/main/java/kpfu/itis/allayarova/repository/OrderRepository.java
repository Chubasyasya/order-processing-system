package kpfu.itis.allayarova.repository;

import kpfu.itis.allayarova.data.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    @Override
    Optional<OrderEntity> findById(Long id);
}
