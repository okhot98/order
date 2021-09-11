package com.ecommerce.project.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.project.order.dto.OrderDTO;
import com.ecommerce.project.order.entity.Order;
import com.ecommerce.project.order.exception.EcommerceException;
import com.ecommerce.project.order.repository.OrderRepository;
import com.ecommerce.project.order.repository.ReorderRepository;

@Service
@Transactional
public class OrderService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	OrderRepository orderrepo;

	@Autowired
	ReorderRepository reorderRepo;

	// Get specific order by id
	public List<OrderDTO> getSpecificOrder(String orderid) throws EcommerceException {

		logger.info("Order details of Id {}", orderid);

		Iterable<Order> order = orderrepo.findByOrderid(orderid);
		List<OrderDTO> orderDTO = new ArrayList<OrderDTO>();

		order.forEach(ord -> {
			orderDTO.add(OrderDTO.valueOf(ord));
		});
		if (orderDTO.isEmpty())
			throw new EcommerceException("Service.ORDERS_NOT_FOUND");
		logger.info("{}", orderDTO);
		return orderDTO;
	}

	// Get all order details
	public List<OrderDTO> getAllOrder() throws EcommerceException {

		Iterable<Order> orders = orderrepo.findAll();
		List<OrderDTO> orderDTOs = new ArrayList<>();

		orders.forEach(order -> {
			OrderDTO orderDTO = OrderDTO.valueOf(order);
			orderDTOs.add(orderDTO);
		});
		if (orderDTOs.isEmpty())
			throw new EcommerceException("Service.ORDERS_NOT_FOUND");
		logger.info("Order Details : {}", orderDTOs);
		return orderDTOs;
	}

//	public String addOrder(OrderDTO order) throws InfyMarketException {
//		Order order1 = new Order();
//		order1.setAddress(order.getAddress());
//		order1.setBuyerid(order.getBuyerid());
//		Order ord = orderrepo.save(order1);
//		return ord.getBuyerid();
//	}

	// Place order
	public String saveOrder(OrderDTO orderDTO) throws EcommerceException {

		Order order = orderrepo.getOrderByBuyerIdAndAddress(orderDTO.getBuyerid(), orderDTO.getAddress());
		if (order != null) {
			return order.getOrderid();
		} else {
			throw new EcommerceException("Services.ORDER_NOT_PLACED");
		}

	}

	// Reorder
	public boolean reOrder(OrderDTO orderDTO) throws EcommerceException {
		logger.info("Reordering the order{}", orderDTO.getOrderid());
		Order ord = reorderRepo.findByOrderid(orderDTO.getOrderid());
		if (ord != null && ord.getOrderid().equals(orderDTO.getOrderid())) {
			return true;
		} else {
			throw new EcommerceException("Services.ORDER_NOT_PLACED");
		}
	}

	// Delete order
	public void deleteOrder(String orderid) throws EcommerceException {
		if(orderid != null) {
		Optional<Order> ord = orderrepo.findById(orderid);
		//ord.orElseThrow(() -> new EcommerceException("Service.ORDERS_NOT_FOUND"));
		orderrepo.deleteById(orderid);
	}else {
		throw new EcommerceException("Service.ORDERS_NOT_FOUND");
	}
}
}