package com.bhaskar.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bhaskar.exception.ProductException;
import com.bhaskar.model.Product;
import com.bhaskar.model.Rating;
import com.bhaskar.model.User;
import com.bhaskar.repository.RatingRepository;
import com.bhaskar.request.RatingRequest;

@Service
public class RatingServiceImplementation implements RatingService{

	private RatingRepository ratingRepository;
	private ProductService productService;
	
	public RatingServiceImplementation(RatingRepository ratingRepository,
			ProductService productService) {
		// TODO Auto-generated constructor stub
		this.ratingRepository = ratingRepository;
		this.productService = productService;
		
	}
	
	
	@Override
	public Rating createRating(RatingRequest req, User user) throws ProductException {
		Product product = productService.findProductById(req.getProductId());
		
		Rating rating = new Rating();
		rating.setProduct(product);
		rating.setUser(user);
		rating.setRating(req.getRating());
		rating.setCreatedAt(LocalDateTime.now());
		return ratingRepository.save(rating);
	}

	@Override
	public List<Rating> getProductRating(Long productId) {
		
		return ratingRepository.getAllProductsRating(productId);
	}

}
