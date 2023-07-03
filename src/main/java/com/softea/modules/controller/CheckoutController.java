/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.softea.modules.dto.OrderDTO;
import com.softea.modules.entity.Order;
import com.softea.modules.handler.*;
import com.softea.modules.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class CheckoutController {
	private final OrderService orderService;

	@GetMapping("")
	public ResponseEntity<List<Order>> listOrders(
		@RequestParam("date") Optional<String> date)
			throws OrderNotFoundException {
		if(date.isPresent()) return ResponseEntity.ok(
			orderService.retrieveOrdersByDate(date.get()));

		return ResponseEntity.ok(
			orderService.getAllOrders());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Order> findOrder(
			@PathVariable(value="id") @Valid String id)
			throws OrderNotFoundException {
  	return ResponseEntity.ok(
			orderService.retrieveOrder(id));
	}

	@PostMapping("/place-order")
	public ResponseEntity<Order> placeOrder(
			@RequestBody @Valid OrderDTO body) {
		return ResponseEntity.created(null).body(
			orderService.placeOrder(body));
	}

	@PatchMapping("/{id}/pay")
	ResponseEntity<Order> payOrder(
			@PathVariable("id") @Valid String id)
			throws OrderProcessingException,
						 OrderNotFoundException,
						 OrderPaymentException {
		return ResponseEntity.ok(
				orderService.closeOrder(id));
	}

	ResponseEntity<Order> reverseOrder(
			@PathVariable("id") @Valid String id)
			throws OrderProcessingException,
				     OrderNotFoundException,
						 OrderReversalException {
		return ResponseEntity.ok(
			orderService.reverseOrder(id));
	}

	@PatchMapping("/{id}/cancel")
	ResponseEntity<Order> cancelOrder(
			@PathVariable("id") @Valid String id)
			throws OrderProcessingException,
						 OrderNotFoundException,
						 OrderCancelmentException {
		return ResponseEntity.ok(
			orderService.cancelOrder(id));
	}
}
