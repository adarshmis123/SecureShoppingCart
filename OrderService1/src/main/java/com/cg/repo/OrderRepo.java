package com.cg.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cg.entity.Orders;

public interface OrderRepo extends JpaRepository<Orders, Integer> {
    List<Orders> findByUserId(int userId);
}
