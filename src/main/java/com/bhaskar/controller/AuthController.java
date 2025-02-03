package com.bhaskar.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bhaskar.config.JwtProvider;
import com.bhaskar.exception.UserException;
import com.bhaskar.model.Cart;
import com.bhaskar.model.User;
import com.bhaskar.repository.UserRepository;
import com.bhaskar.request.LoginRequest;
import com.bhaskar.response.AuthResponse;
import com.bhaskar.service.CartService;
import com.bhaskar.service.CustomeUserServiceImplementation;

@RestController
@RequestMapping("/auth")
//@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")

//@CrossOrigin(origins = "https://bhaskar-ecom.vercel.app", allowedHeaders = "*", allowCredentials = "true")
public class AuthController {
	
	
	private JwtProvider jwtProvider;
	
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private CustomeUserServiceImplementation customeUserService;
	private CartService cartService;
	
	public AuthController(UserRepository userRepository,
			CustomeUserServiceImplementation customeUserService,
			PasswordEncoder passwordEncoder,
			JwtProvider jwtProvider,
			CartService cartService) {
		// TODO Auto-generated constructor stub
		this.userRepository = userRepository;
		this.customeUserService = customeUserService;
		this.passwordEncoder = passwordEncoder;
		this.jwtProvider = jwtProvider;
		this.cartService = cartService;
	}
	
	//signup method
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse>createUserHandler(@RequestBody User user) throws UserException{
		
		String email = user.getEmail();
		String password = user.getPassword();
		String firstName = user.getFirstName();
		String lastName = user.getLastName();
		
		User isEmailExist = userRepository.findByEmail(email);
		
		if(isEmailExist != null) {
			throw new UserException("Email already exist...");
		}
		
		User createdUser = new User();
		
		createdUser.setEmail(email);
		createdUser.setPassword(passwordEncoder.encode(password));
		createdUser.setFirstName(firstName);
		createdUser.setLastName(lastName);
		
		
		//saving user in repository
		User savedUser = userRepository.save(createdUser);
		Cart cart = cartService.createCart(savedUser);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtProvider.generateToken(authentication);
		
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(token);
		authResponse.setMessage("signup success");
		
		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
		
		
	}
	
	
	//signin method-->taking data from loginRequest class and message from authResponse & authenticating and then saving token in security context holder
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse>loginUserHandler(@RequestBody LoginRequest loginRequest){
		
		String username = loginRequest.getEmail();
		String password = loginRequest.getPassword();
		
		Authentication authentication = authenticate(username, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtProvider.generateToken(authentication);
		
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(token);
		authResponse.setMessage("signin success");
		
		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
		
		
	}

	private Authentication authenticate(String username, String password) {
		// TODO Auto-generated method stub
		
		UserDetails userDetails = customeUserService.loadUserByUsername(username);
		
		if(userDetails == null) {
			throw new BadCredentialsException("Invalid username");
		}
		
		
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid password");
		}
		
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
}
