/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.softea.modules.dto.OrderDTO;
import com.softea.modules.entity.Order;
import com.softea.modules.handler.CheckoutFailureException;
import com.softea.modules.service.OrderService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class CheckoutController {
	private final OrderService orderService;

	@GetMapping("/")
	public ResponseEntity<List<Order>> listOrders() {
		return ResponseEntity.ok(
			orderService.getAllOrders());
	}

	@GetMapping("/{table}/orders")
	public ResponseEntity<List<Order>> listOrdersByTable(@PathVariable("table") int table) {
		return ResponseEntity.ok(
			orderService.getAllTableOrders(table));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Order> findOrder(
			@PathVariable(value="id") String id) {
		try {
  		return ResponseEntity.ok(
				orderService.retrieveOrder(id));
		} catch(CheckoutFailureException e) {
			System.out.println(e);
  		return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/place-order")
	public ResponseEntity<Order> placeOrder(
			@RequestBody OrderDTO body) {
		return ResponseEntity.created(null).body(
			orderService.placeOrder(body));
	}

	@PatchMapping("/{id}/pay")
	ResponseEntity<Order> payOrder(
			@PathVariable("id") String id) {
		try {
		  return ResponseEntity.ok(
				orderService.closeOrder(id));
		} catch(CheckoutFailureException e) {
			System.out.println(e);
			switch(e.getMessage()) {
				case "[EXCEPTION] Order not found":
					return ResponseEntity.notFound().build();
				case "[EXCEPTION] Cannot close not open order":
				case "[EXCEPTION] Can only update orders from current date":
					return ResponseEntity.badRequest().build();
				default:
					return ResponseEntity.internalServerError()
						.build();
			}
		}
	}

	@PatchMapping("/{id}/reverse")
	ResponseEntity<Order> reverseOrder(
			@PathVariable("id") String id) {
		try {
			return ResponseEntity.ok(
				orderService.reverseOrder(id));
		} catch(CheckoutFailureException e) {
			System.out.println(e);
			switch(e.getMessage()) {
				case "[EXCEPTION] Order not found":
					return ResponseEntity.notFound().build();
				case "[EXCEPTION] Cannot reverse not paid order":
				case "[EXCEPTION] Can only update orders from current date":
					return ResponseEntity.badRequest().build();
				default:
					return ResponseEntity.internalServerError()
						.build();
			}
		}
	}

	@PatchMapping("/{id}/cancel")
	ResponseEntity<Order> cancelOrder(
			@PathVariable("id") String id) {
		try {
			return ResponseEntity.ok(
				orderService.cancelOrder(id));
		} catch(CheckoutFailureException e) {
			System.out.println(e);
			switch(e.getMessage()) {
				case "[EXCEPTION] Order not found":
					return ResponseEntity.notFound().build();
				case "[EXCEPTION] Cannot cancel not open order":
				case "[EXCEPTION] Can only update orders from current date":
					return ResponseEntity.badRequest().build();
				default:
					return ResponseEntity.internalServerError()
						.build();
			}
		}
  }
}
