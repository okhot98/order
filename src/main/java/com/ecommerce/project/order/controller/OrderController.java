package com.ecommerce.project.order.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.project.order.dto.OrderDTO;
import com.ecommerce.project.order.dto.ProductsorderedDTO;
import com.ecommerce.project.order.exception.EcommerceException;
import com.ecommerce.project.order.service.OrderService;
import com.ecommerce.project.order.service.ProductOrderService;

@RestController
@CrossOrigin
@RequestMapping
public class OrderController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	Environment environment;
	@Autowired
	private OrderService orderservice;
	@Autowired
	ProductOrderService proser;

	// Get orders by ID
	@RequestMapping(value = "/api/orders/{orderid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<OrderDTO>> getSpecificOrder(@PathVariable String orderid) throws Exception {

		logger.info("Order details {}", orderid);
		List<OrderDTO> orders = orderservice.getSpecificOrder(orderid);
		return new ResponseEntity<>(orders, HttpStatus.OK);

	}

	// Get all orders
	@GetMapping(value = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<OrderDTO>> getAllOrder() throws Exception {

		logger.info("Fetching all products");
		List<OrderDTO> orderdto = orderservice.getAllOrder();
		return new ResponseEntity<>(orderdto, HttpStatus.OK);

	}

	// Place orders
	@RequestMapping(value = "/orders/placeorders", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> searchOrder(@RequestBody OrderDTO orderDTO) throws Exception {

		String orderid = orderservice.saveOrder(orderDTO);
		String successMessage = environment.getProperty("API.ORDER_SUCCESS") + orderid;
		return new ResponseEntity<>(successMessage, HttpStatus.CREATED);

	}

	// Reorder
	@PostMapping(value = "/orders/reorder/{orderid}/{orderid}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> reOrder(@RequestBody OrderDTO orderDTO) throws Exception {

		logger.info("Reordering the order {}", orderDTO.getOrderid());
		boolean order = orderservice.reOrder(orderDTO);
		String successMessage = environment.getProperty("API.ORDER_SUCCESS") + order;
		return new ResponseEntity<>(successMessage, HttpStatus.CREATED);

	}

	// Get products ordered by ProductId
	@RequestMapping(value = "/api/productsorders/{prodid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProductsorderedDTO>> getProductById(@PathVariable String prodid) throws Exception {

		logger.info("product details request for ordered product {}", prodid);
		List<ProductsorderedDTO> orders = proser.getProductById(prodid);
		return new ResponseEntity<>(orders, HttpStatus.CREATED);

	}

	// Delete order
	@DeleteMapping(value = "/order/{orderid}")
	public ResponseEntity<String> deleteOrder(@PathVariable String orderid) throws Exception {

		orderservice.deleteOrder(orderid);
		String successMessage = environment.getProperty("API.DELETE_SUCCESS");
		return new ResponseEntity<>(successMessage, HttpStatus.OK);

	}
}
