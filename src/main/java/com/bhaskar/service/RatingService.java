package com.bhaskar.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bhaskar.exception.ProductException;
import com.bhaskar.model.Rating;
import com.bhaskar.model.User;
import com.bhaskar.request.RatingRequest;

//@Service
public interface RatingService {

	public Rating createRating(RatingRequest req, User user) throws ProductException;
	
	public List<Rating> getProductRating(Long productId);
	
}
