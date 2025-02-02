package com.bhaskar.service;

import com.bhaskar.exception.ProductException;
import com.bhaskar.model.Cart;
import com.bhaskar.model.CartItem;
import com.bhaskar.model.User;
import com.bhaskar.request.AddItemRequest;

public interface CartService {

	public Cart createCart(User user);
	
	public CartItem addCartItem(Long userId, AddItemRequest req) throws ProductException;
	
	public Cart findUserCart(Long userId);
}
