package com.softea.controller;

import java.time.LocalDate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/checkout")
public class CheckoutController {
	@GetMapping("/tables/{table}")
	public String getTableOrders(@PathVariable Integer table) {
		return "None orders for this table { " + table + "} yet.";
	}

	@PostMapping("/orders/new")
	public String placeOrder(@RequestBody Object body) {
		System.out.println(body);
		return "Order successfully placed: k2f;2.÷_2$!$÷_#_×";
	}

	@PatchMapping("/orders/{orderId}/pay")
	public String payOrder(@PathVariable Long orderId) {
		return String.format(
				"Order %1s successfully canceled.", orderId);
	}

	@PatchMapping("/orders/{orderId}/cancel")
	public String cancelOrder(@PathVariable Long orderId) {
		return String.format(
				"Order %1s successfully canceled.", orderId);
	}

	@PatchMapping("/orders/{orderId}/reverse")
	public String reverseOrder(@PathVariable Long orderId) {
		return String.format(
				"Order %1s successfully reversed.", orderId);
	}

	@GetMapping("/revenue")
	public String getDailyRevenue(@RequestParam LocalDate date) {
		return "No revenue found for date: " + date;
	}
}
