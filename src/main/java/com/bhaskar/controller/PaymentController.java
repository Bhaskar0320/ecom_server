

package com.bhaskar.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bhaskar.exception.OrderException;
import com.bhaskar.model.Order;
import com.bhaskar.repository.OrderRepository;
import com.bhaskar.service.OrderService;
import com.bhaskar.service.UserService;
import com.bhaskar.user.OrderStatus;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.bhaskar.response.ApiResponse;
import com.bhaskar.response.PaymentLinkResponse;

@RestController
@RequestMapping("/api")
public class PaymentController {

	   @Value("${razorpay.api.key}")
	    private String apiKey;

	    @Value("${razorpay.api.secret}")
	    private String apiSecret;

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderRepository orderRepository;

    public PaymentController(OrderService orderService, UserService userService, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.userService = userService;
        this.orderRepository = orderRepository;
    }

    @PostMapping("/payments/{orderId}")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(@PathVariable Long orderId,
            @RequestHeader("Authorization") String jwt) throws OrderException, RazorpayException {

        Order order = orderService.findOrderById(orderId);

        try {
            RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecret);

            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", order.getTotalPrice() * 100);
            paymentLinkRequest.put("currency", "INR");

            JSONObject customer = new JSONObject();
            customer.put("name", order.getUser().getFirstName());
            customer.put("email", order.getUser().getEmail());
            paymentLinkRequest.put("customer", customer);

            JSONObject notify = new JSONObject();
            notify.put("sms", true);
            notify.put("email", true);
            paymentLinkRequest.put("notify", notify);

            paymentLinkRequest.put("callback_url", "http://localhost:3000/payment/" + orderId);
            paymentLinkRequest.put("callback_method", "get");

            PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);

            String paymentLinkId = payment.get("id");
            String paymentLinkUrl = payment.get("short_url");

            PaymentLinkResponse res = new PaymentLinkResponse();
            res.setPayment_link_id(paymentLinkId);
            res.setPayment_link_url(paymentLinkUrl);

            return new ResponseEntity<>(res, HttpStatus.CREATED);

        } catch (Exception e) {
            throw new RazorpayException(e.getMessage());
        }
    }

    

    
    @GetMapping("/payments")
    public ResponseEntity<ApiResponse> redirect(
            @RequestParam(name = "payment_id") String paymentId,
            @RequestParam(name = "order_id") Long orderId) throws OrderException, RazorpayException {

        // Validate payment_id and order_id
        if (paymentId == null || paymentId.isEmpty() || orderId == null) {
            ApiResponse response = new ApiResponse();
            response.setMessage("Payment ID or Order ID is missing or invalid");
            response.setStatus(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Log the incoming parameters
        System.out.println("Payment ID: " + paymentId);
        System.out.println("Order ID: " + orderId);

        Order order = orderService.findOrderById(orderId);
        RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecret);

        try {
            Payment payment = razorpay.payments.fetch(paymentId);

            if (payment.get("status").equals("captured")) {
                order.getPaymentDetails().setPaymentId(paymentId);
                order.getPaymentDetails().setStatus("COMPLETED");
                order.setOrderStatus(OrderStatus.PLACED);
                orderRepository.save(order);
            }

            ApiResponse response = new ApiResponse();
            response.setMessage("Your order has been placed");
            response.setStatus(true);

            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);

        } catch (Exception e) {
            throw new RazorpayException(e.getMessage());
        }
    }

}


//getting payment status 

//@GetMapping("/payments")
//public ResponseEntity<ApiResponse> redirect(@RequestParam(name = "payment_id") String paymentId,
//      @RequestParam(name = "order_id") Long orderId) throws OrderException, RazorpayException {
//
//  Order order = orderService.findOrderById(orderId);
//  RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecret);
//
//  try {
//      Payment payment = razorpay.payments.fetch(paymentId);
//
//      if (payment.get("status").equals("capture")) {
//          order.getPaymentDetails().setPaymentId(paymentId);
//          order.getPaymentDetails().setStatus("COMPLETED");
//          order.setOrderStatus(OrderStatus.PLACED); // Fix typo here
//          orderRepository.save(order);
//      }
//
//      ApiResponse response = new ApiResponse();
//      response.setMessage("Your order has been placed");
//      response.setStatus(true);
//
//      return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
//
//  } catch (Exception e) {
//      throw new RazorpayException(e.getMessage());
//  } 
//}
//}


















