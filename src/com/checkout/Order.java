package com.checkout;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import java.io.IOException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.product.Menu;
import com.product.Product;
import com.utils.FileSystem;
import com.checkout.enums.*;

public class Order {
	private final String FIRST_LINE = "order_id,tea_id,unity_price,quantity,created_at,order_status";
  private String id;
  private List<OrderItem> cart;
	private Long createdAt;
	private Double totalPrice = 0d;
	private OrderStatus order_status;

	private Order() {
		id = UUID.randomUUID().toString();
		createdAt = System.currentTimeMillis();
		cart = new ArrayList<OrderItem>();
		order_status = OrderStatus.OPEN_ORDER;
	}

	private Order(OrderBuilder builder) {
		id =  builder.orderId;
		createdAt = builder.createdAt;
		cart = builder.cart;
		order_status = builder.status;

		for(OrderItem item : cart) {
			this.totalPrice += item.getAmmountPrice();
		}
	}

	public String getId() { return id; }
	public Long getCreatedAt() { return createdAt; }
	public List<OrderItem> getCart() { return cart; }
	public Double getTotalPrice() { return totalPrice; }
	public OrderStatus showStatus() { return order_status; }

	public void setOrderStatus(String status) {
		order_status = OrderStatus.valueOf(status);
	}

	public OrderItem addItem(Product prod,int quantity) {
		OrderItem item = new OrderItem.OrderItemBuilder()
			.prodId(prod.getCod())
			.prodOrderId(id)
			.prodQuantity(quantity)
			.prodUnityPrice(prod.getPrice())
			.prodAmount()
			.build();
		
		totalPrice += item.getAmmountPrice();
		this.cart.add(item);
		return item;
	}

	public void getSummary(Menu menu) {
		String date = formatOrderCreationDate();
		String strItems = "";

		for (OrderItem item : cart) {
			int index = cart.indexOf(item);
			double subtotal = item.getProdQuantity()*item.getUnityPrice();
			String sku = menu.findOne(item.getProdId()).getSku();
			strItems += String.format(
				"  %1$s. %2$s ->       %3$s x %4$s = %5$s\n",
				index,
				sku,
				item.getProdQuantity(),
				item.getUnityPrice(),
				String.format("%.2f", subtotal)
			);
		}

		String totalLog = String.format(
			formatSummary(),date,id,strItems,cart.size(),totalPrice);

		System.out.println(totalLog);
	}

	private String formatOrderCreationDate() {
		LocalDateTime date = Instant
			.ofEpochMilli(createdAt)
			.atZone(ZoneId.systemDefault())
			.toLocalDateTime();
	
		String formatedDate = DateTimeFormatter
			.ofPattern("E MMM dd, yyyy - HH:mm")
			.format(date)
			.concat(" [UTC]");

		return formatedDate;
	}

	private String formatSummary() {
		String summary = "" +
 "===========================================================\n"+
 "   Casa das Teas                                    %1$s   \n"+
 "-----------------------------------------------------------\n"+
 "   Order  -  %2$s                                          \n"+
 "===========================================================\n"+
 "%3$s"+
 "===========================================================\n"+
 "   Items: %4$s        Total price: R$ %5$s\n"+
 "-----------------------------------------------------------\n\n "+
 "                    Comeback anytime !!                    \n\n"+
 "===========================================================\n";

		return summary;
	}

	public void save(  String fileName, List<String> externalFile)
			throws IOException {
		List<String> skus = new ArrayList<>();
		skus.add(FIRST_LINE);

		for(String line : externalFile) {
			System.out.println(line);
			int lineSteps = line.split(",").length;

			if(lineSteps <= 4) {
				skus.add(String.format(
						"%1$s,%2$s,%3$s", line, createdAt, order_status ));
			} else {
				skus.add(line);
			}

		}

		FileSystem.save(fileName, skus);
		System.out.println("Order saved!");
	}

	public static class OrderBuilder {
		private String orderId;
		private Long createdAt;
		private List<OrderItem> cart;
		private OrderStatus status;

		public OrderBuilder() { }

		public OrderBuilder orderId(String orderId) {
			this.orderId = orderId;
			return this;
		}

		public OrderBuilder createdAt(Long createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public OrderBuilder cart(List<OrderItem> cart) {
			this.cart = cart;
			return this;
		}

		public OrderBuilder orderStatus(OrderStatus status) {
			this.status = status;
			return this;
		}

		public Order build() {
			if(
			orderId == null &&
			createdAt == null &&
			status == null)
				return new Order();

			return new Order(this);
		}
	}
}

