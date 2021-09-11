package com.ecommerce.project.order.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "orderid")
	String orderid;
	@Column(nullable = false)
	String buyerid;
	@Column(nullable = false)
	Double amount;
	@Column(nullable = false)
	Date orderdate;
	@Column(nullable = false)
	String address;
	@Column(nullable = false)
	String status;
	//@Column(nullable = false)
	//int seller_id;
	//@Column(nullable = false)
	//Double quantity;

	public Order() {
		super();
	}

	public Order(String orderid, String buyerid, Double amount, Date orderdate, String address, String status) {
		super();
		this.orderid = orderid;
		this.buyerid = buyerid;
		this.amount = amount;
		this.orderdate = orderdate;
		this.address = address;
		this.status = status;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderId(String orderid) {
		this.orderid = orderid;
	}

	public String getBuyerid() {
		return buyerid;
	}

	public void setBuyerid(String buyerid) {
		this.buyerid = buyerid;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Order [orderid=" + orderid + ", buyerid=" + buyerid + ", amount=" + amount + ", orderdate=" + orderdate
				+ ", address=" + address + ", status=" + status + "]";
	}
	
}
