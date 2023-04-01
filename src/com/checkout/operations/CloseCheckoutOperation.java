package com.checkout.operations;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.checkout.enums.OrderStatus;
import com.exception.CheckoutOperationException;

public class CloseCheckoutOperation
	  implements CheckoutOperation {
	private static List<String> orderItems = new ArrayList<>();

	public static void closeCheckout(
		List<String> persistence,
		String fileName
	) throws IOException {
		LocalDate localDate;
		Scanner sc = new Scanner(System.in);

		System.out.print("Show daily revenue from: ");
		String whenFromGetRevenueInfo = sc.nextLine();

		if(whenFromGetRevenueInfo.isBlank() || whenFromGetRevenueInfo.isEmpty() || whenFromGetRevenueInfo == null) {
			whenFromGetRevenueInfo = "now";
		}

		if(whenFromGetRevenueInfo == "now") {
			localDate = LocalDate.now();
		} else {
			List<Integer> date = Stream.of(
					whenFromGetRevenueInfo.split("/"))
				.map(Integer::parseInt)
				.collect(Collectors.toList());

			localDate = LocalDate.of(
					date.get(2), date.get(1), date.get(0));
		}

		if(isFutureDate(localDate)) {
			findOrderByDate(localDate, persistence);
			showResumee(whenFromGetRevenueInfo);
		}
		sc.close();
	}

	private static void findOrderByDate(
			LocalDate date, List<String> orders) {
		orders.stream().filter(t -> {
			LocalDate time = LocalDate.ofInstant(Instant.ofEpochMilli(
					Long.parseLong(t.split(",")[4])),
					ZoneId.systemDefault());

			return time.equals(date);
		})
			.collect(Collectors.toList())
			.forEach(item -> orderItems.add(item));
	}

	private static void showResumee(String parameter) {
		String LOG = "" +
 "===========================================================\n"+
 "                           Softea ©" + "\n" +
 "     Revenues  -  " + parameter +
 "            orders:  " + countOrders() + "\n" +
 "===========================================================\n"+
 "%1$s" +
 "\n===========================================================";
		if(orderItems.size() < 1) {
			LOG = String.format(
					LOG, "No data found for parameter(s): " + parameter);
		} else {
			double rvAmount = getDailyReveredAmount();
			double bRevenue = getDailyRevenue();
			double lRevenue = (bRevenue - rvAmount);

			String log =	String.format(
			 "   Brute receipts:   ------------------------    %.2f\n"+
			 "   Reverses:         ------------------------    %1s\n"+
			 "   Reverse ammout:   ------------------------    %.2f\n\n"+
			 "   Liquid revenue:   ------------------------    %.2f",
			 bRevenue,
			 countReversedOrders(),
			 rvAmount,
			 lRevenue);

			LOG = String.format(LOG, log);
		}

		System.out.println(LOG);
	}

	private static Integer countOrders() {
		Set<String> orders = orderItems
			.stream()
			.map(item-> item.split(",")[0])
			.collect(Collectors.toSet());

		return orders.size();
	}

	private static Integer countReversedOrders() {
		final String status = OrderStatus.REVERSED_ORDER.toString();

		Set<String> orders = orderItems
			.stream()
			.filter(t-> t.contains(status))
			.map(item-> item.split(",")[0])
			.collect(Collectors.toSet());

		return orders.size();
	}

	private static Double getDailyRevenue() {
		double revenue = 0d;

		for(String item : orderItems) {
			Double price = Double.parseDouble(item.split(",")[2]);
			Integer quantity = Integer.parseInt(item.split(",")[3]);
			revenue += (price * quantity);
		}

		return revenue;
	}

	private static Double getDailyReveredAmount() {
		final String status = OrderStatus.REVERSED_ORDER.toString();
		double ammount = 0d;

		List<String> reversedItems = orderItems
			.stream()
			.filter(t-> t.contains(status))
			.collect(Collectors.toList());

		for(String item : reversedItems) {
			Double price = Double.parseDouble(item.split(",")[2]);
			Integer quantity = Integer.parseInt(item.split(",")[3]);
			ammount += (price * quantity);
		}

		return ammount;
	}

	private static boolean isFutureDate(LocalDate date) {
		int isFuture = date.compareTo(LocalDate.now());

		if(isFuture == 0)  throw new CheckoutOperationException(
				"Cannot query for revenues before checkout is closed.");

		if(isFuture == 1)  throw new CheckoutOperationException(
				"Cannot query for future revenues.");

		return isFuture == -1;
	}
}
