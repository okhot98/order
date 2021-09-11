package com.ecommerce.project.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.project.order.entity.Order;

public interface ReorderRepository extends JpaRepository<Order, String>{
	Order findByOrderid(String orderid);
}
