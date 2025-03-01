////package com.bhaskar.config;
////
////import java.util.Arrays;
////import java.util.Collections;
////
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//////import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.AuthorizedUrl;
////import org.springframework.security.config.http.SessionCreationPolicy;
////import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
////import org.springframework.security.crypto.password.PasswordEncoder;
////import org.springframework.security.web.SecurityFilterChain;
////import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
////import org.springframework.stereotype.Service;
////import org.springframework.web.cors.CorsConfiguration;
////import org.springframework.web.cors.CorsConfigurationSource;
////
////import jakarta.servlet.http.HttpServletRequest;
////
////@Configuration
////public class AppConfig {
////
////	@SuppressWarnings("removal")
////	@Bean
////	public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
////		
////		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////		.and()
////		.authorizeHttpRequests(Authorize->Authorize.requestMatchers("/api/**").authenticated().anyRequest().permitAll())
////		.addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class)
////		.csrf().disable()
////		.cors().configurationSource(new CorsConfigurationSource() {
////			
////			@Override
////			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
////				
////				CorsConfiguration cfg = new CorsConfiguration();
////				
////				cfg.setAllowedOrigins(Arrays.asList(
////						"http://localhost:3000",
////						"http://localhost:4200"
////						));
////				cfg.setAllowedMethods(Collections.singletonList("*"));
////				cfg.setAllowCredentials(true);
////				cfg.setAllowedHeaders(Collections.singletonList("*"));
////				cfg.setExposedHeaders(Arrays.asList("Authorization"));
////				cfg.setMaxAge(3600L);
////				return cfg;
////			}
////		})
////		.and().httpBasic().and().formLogin();
////		
////		
////		
////		return http.build();
////	}
////	
////	@Bean
////	public PasswordEncoder passwordEncoder() {
////		
////		
////		return new BCryptPasswordEncoder();
////	}
////	
////}
//
//
//
//
//
//package com.bhaskar.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.Arrays;
//import java.util.Collections;
//
//@Configuration
//public class AppConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//            // Disable CSRF (Cross-Site Request Forgery) protection
//            .csrf().disable()
//            
//            // Configure CORS
//            .cors().configurationSource(corsConfigurationSource()).and()
//            
//            // Set session management to stateless (for JWT-based authentication)
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//            
//            // Configure authorization rules
//            .authorizeHttpRequests(authorize -> authorize
//                .requestMatchers("/api/**").authenticated() // Secure /api/** endpoints
//                .anyRequest().permitAll() // Allow all other requests
//            )
//            
//            // Add JWT validation filter
//            .addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class)
//            
//            // Enable HTTP Basic authentication (optional)
//            .httpBasic().and()
//            
//            // Enable form login (optional)
//            .formLogin();
//
//        return http.build();
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        
//        // Allow specific origins
//        configuration.setAllowedOrigins(Arrays.asList(
//            "http://localhost:3000",
//            "http://localhost:4200",
//            "http://bhaskar-ecom.vercel.app"
//        ));
//        
//        // Allow all HTTP methods
//        configuration.setAllowedMethods(Collections.singletonList("*"));
//        
//        // Allow all headers
//        configuration.setAllowedHeaders(Collections.singletonList("*"));
//        
//        // Allow credentials (e.g., cookies)
//        configuration.setAllowCredentials(true);
//        
//        // Expose specific headers in the response
//        configuration.setExposedHeaders(Arrays.asList("Authorization"));
//        
//        // Set the max age for preflight requests (in seconds)
//        configuration.setMaxAge(3600L);
//
//        // Apply the configuration to all endpoints
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//
//        return source;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}


//adding comments only


package com.bhaskar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

@Configuration
public class AppConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF
            .csrf().disable()

            // Configure CORS
            .cors().configurationSource(corsConfigurationSource()).and()

            // Set session management to stateless (for JWT-based authentication)
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

            // Configure authorization rules
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/**")
                .authenticated() // Secure /api/** endpoints
                .anyRequest().permitAll() // Allow all other requests
            )

            // Add HTTPS redirection filter
            .addFilterBefore(new HttpsRedirectFilter(), BasicAuthenticationFilter.class)

            // Add JWT validation filter
            .addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class)

            // Enable HTTP Basic authentication
            .httpBasic().and()

            // Enable form login
            .formLogin();

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Allow specific origins
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:3000",
            "http://localhost:4200",
            "https://bhaskar-ecom.vercel.app"
        ));
        
        // Allow all HTTP methods
        configuration.setAllowedMethods(Collections.singletonList("*"));
        
        // Allow all headers
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        
        // Allow credentials (e.g., cookies)
        configuration.setAllowCredentials(false);
        
        // Expose specific headers in the response
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        
        // Set the max age for preflight requests (in seconds)
        configuration.setMaxAge(3600L);

        // Apply the configuration to all endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * HTTPS Redirection Filter
     */
    private static class HttpsRedirectFilter implements Filter {
        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            if ("http".equalsIgnoreCase(httpRequest.getHeader("X-Forwarded-Proto"))) {
                httpResponse.sendRedirect("https://" + httpRequest.getServerName() + httpRequest.getRequestURI());
                return;
            }

            chain.doFilter(request, response);
        }
    }
}
