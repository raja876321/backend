package com.stocks.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.stocks.entity.User;
import com.stocks.request.ResetPasswordRequest;
import com.stocks.security.ApiResponse;
import com.stocks.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/users")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@GetMapping("/getAllUsers")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ROLE_USER_LIST')")
	public ResponseEntity<Page<User>> getAllUsers(@RequestParam(defaultValue = "0") int page, // Default page number
			@RequestParam(defaultValue = "10") int size, // Default page size
			@RequestParam(defaultValue = "id") String sortBy, // Default sorting field
			@RequestParam(defaultValue = "asc") String order) // Default sorting order
	{
		// Determine sort direction
		Sort sort = order.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

		// Create pageable object
		Pageable pageable = PageRequest.of(page, size, sort);

		// Get paginated and sorted users
		Page<User> users = userService.getAllUsers(pageable);

		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<User> getUserById(@PathVariable int id) {
		User user = userService.getUserById(id);
		if (user != null) {
			return new ResponseEntity<>(user, HttpStatus.OK);
		} else {
			// You can customize the response based on your requirements
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/createUser")
	public ResponseEntity<User> createUser(@RequestBody User user, HttpServletRequest request) {

		String url = request.getRequestURL().toString();
		url = url.replace(request.getServletPath(), "");
		User createUser = userService.createUser(user, url);

		if (createUser != null) {
			return new ResponseEntity<>(createUser, HttpStatus.OK);
		} else {
			// You can customize the response based on your requirements
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable int id, @RequestBody User updatedUser) {
		User user = userService.updateUser(id, updatedUser);
		ApiResponse<User> response = new ApiResponse<>("success", "User updated successfully", user,
				HttpStatus.OK.value());
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/delete/{id}")
	public void deleteUser(@PathVariable int id) {
		userService.deleteUser(id);
	}

	@SuppressWarnings("unused")
	@PostMapping("/reset-password")
	public ResponseEntity<ApiResponse<String>> resetPassword(@RequestBody ResetPasswordRequest request,
			HttpServletRequest httpRequest) {
		logger.info("Received API request to reset password for user: {}", request.getEmail());
		logger.info("Request URI: {}", httpRequest.getRequestURI());

		try {
			boolean isUpdated = userService.resetPassword(request.getCurrentPassword(), request.getEmail(),
					request.getNewPassword(), request.getConfNewPassword());
           if(isUpdated)
           {
			ApiResponse<String> response = new ApiResponse<>("success", "Password reset successfully", null,
					HttpStatus.OK.value());

			logger.info("Password reset successfully for user: {}", request.getEmail());
			return ResponseEntity.ok(response);
			
           }else {
        	   ApiResponse<String> response = new ApiResponse<>("Failed", "Password reset failed", null,
   					HttpStatus.NOT_MODIFIED.value());

   			logger.info("Password reset successfully for user: {}", request.getEmail());
   			return ResponseEntity.ok(response);
           }
           
           
           
		} catch (Exception e) {
			logger.error("Unexpected error occurred while resetting password for user: {}", request.getEmail(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					new ApiResponse<>("error", "Something went wrong", null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
		}
	}
}
