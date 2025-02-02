package com.bhaskar.service;

import org.springframework.stereotype.Service;

import com.bhaskar.model.OrderItem;
import com.bhaskar.repository.OrderItemRepository;

@Service
public class OrderItemServiceImplementation implements OrderItemService{

	private OrderItemRepository orderItemRepository;
	
	public OrderItemServiceImplementation(OrderItemRepository orderItemRepository) {
		// TODO Auto-generated constructor stub
		this.orderItemRepository = orderItemRepository;
	}
	
	
	@Override
	public OrderItem createOrderItem(OrderItem orderItem) {
		return orderItemRepository.save(orderItem);
	}

	
	
}
