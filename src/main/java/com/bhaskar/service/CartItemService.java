package com.bhaskar.service;

import com.bhaskar.exception.CartItemException;
import com.bhaskar.exception.UserException;
import com.bhaskar.model.Cart;
import com.bhaskar.model.CartItem;
import com.bhaskar.model.Product;

public interface CartItemService {

	public CartItem createCartItem(CartItem cartItem);
	
	public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException;

	public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);
	
	public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException;
	
	public CartItem findCartItemById(Long cartItemId) throws CartItemException;
	
}
