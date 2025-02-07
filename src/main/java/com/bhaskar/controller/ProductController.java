package com.bhaskar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bhaskar.exception.ProductException;
import com.bhaskar.model.Product;
import com.bhaskar.service.ProductService;


@RestController
@RequestMapping("/api")
public class ProductController {
	
	@Autowired
private ProductService productService;
	

	public ProductController(ProductService productService) {
		this.productService=productService;
	}
	
	
	@GetMapping("/products")
	public ResponseEntity<Page<Product>> findProductByCategoryHandler(
	        @RequestParam String category,
	        @RequestParam List<String> color,
	        @RequestParam List<String> size,
	        @RequestParam Integer minPrice,
	        @RequestParam Integer maxPrice,
	        @RequestParam Integer minDiscount,
	        @RequestParam String sort,
	        @RequestParam String stock,
	        @RequestParam Integer pageNumber,
	        @RequestParam Integer pageSize) {

	    // Log the input parameters
	    System.out.println("Received request with parameters:");
	    System.out.println("Category: " + category);
	    System.out.println("Colors: " + color);
	    System.out.println("Sizes: " + size);
	    System.out.println("Min Price: " + minPrice);
	    System.out.println("Max Price: " + maxPrice);
	    System.out.println("Min Discount: " + minDiscount);
	    System.out.println("Sort: " + sort);
	    System.out.println("Stock: " + stock);
	    System.out.println("Page Number: " + pageNumber);
	    System.out.println("Page Size: " + pageSize);
	    
	   
	    Page<Product> res = productService.getAllProduct(category, color, size, minPrice, maxPrice, 
	            minDiscount, sort, stock, pageNumber, pageSize);

	    // Handle null values for minDiscount
	    if (minDiscount == null) {
	        minDiscount = 0; // Set a default value or handle as needed
	    }

	    
	    if (res.isEmpty()) {
	        System.out.println("No products found for the given criteria.");
	        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 No Content if no products found
	    }

	    System.out.println("Products found: " + res.getTotalElements());
	    return ResponseEntity.ok(res); // ✅ Returns 200 OK with products
	}

//	
//	@GetMapping("/products")
//	public ResponseEntity<Page<Product>> findProductByCategoryHandler(@RequestParam String category,
//			@RequestParam List<String>color,@RequestParam List<String> size,@RequestParam Integer minPrice,
//			@RequestParam Integer maxPrice, @RequestParam Integer minDiscount, @RequestParam String sort, 
//			@RequestParam String stock, @RequestParam Integer pageNumber,@RequestParam Integer pageSize){
//
//		
//		Page<Product> res= productService.getAllProduct(category, color, size, minPrice, maxPrice, 
//				minDiscount, sort,stock,pageNumber,pageSize);
//		
//		 if (res.isEmpty()) {
//		        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(res); // 204 No Content if no products found
//		    }
//
//		
//		System.out.println("complete products");
//		return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
//		
//	}
//	
//	
	@GetMapping("/products/id/{productId}")
	public ResponseEntity<Product> findProductByIdHandler(@PathVariable Long productId) throws ProductException{
		
		Product product=productService.findProductById(productId);
		
		return new ResponseEntity<Product>(product,HttpStatus.ACCEPTED);
	}


	
	
	
	@GetMapping("/products/search")
	public ResponseEntity<List<Product>> searchProductHandler(@RequestParam String q){
		
		List<Product> products=productService.searchProduct(q);
		
		return new ResponseEntity<List<Product>>(products,HttpStatus.OK);
		
	}
}
