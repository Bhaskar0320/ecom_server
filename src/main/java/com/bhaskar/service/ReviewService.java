package com.bhaskar.service;

import java.util.List;

import com.bhaskar.exception.ProductException;
import com.bhaskar.model.Review;
import com.bhaskar.model.User;
import com.bhaskar.request.ReviewRequest;

public interface ReviewService {

	public Review createReview(ReviewRequest req, User user) throws ProductException;
	
	public List<Review> getAllReview(Long productId);
	
	
}
