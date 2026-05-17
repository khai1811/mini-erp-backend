package org.example.manager.repository;

import org.example.manager.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
Order findByOrderCode(String OrderCode);
}
