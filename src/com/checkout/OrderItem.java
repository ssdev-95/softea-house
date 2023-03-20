package com.checkout;

public class OrderItem {
	private String prod_id;
	private Integer prod_quantity;
	private Double ammount_price;
	private Double unity_price;
	private String order_id;

	private OrderItem() {
		order_id = null;
		prod_quantity = null;
		prod_id = null;
		ammount_price = null;
		unity_price = null;
	}

	private OrderItem(OrderItemBuilder builder) {
		order_id = builder.order_id;
		prod_quantity = builder.prod_quantity;
		prod_id = builder.prod_id;
		unity_price = builder.unity_price;
		ammount_price = builder.ammount_price;
	}

	public String getProdId() { return prod_id; }
	public String getOrderId() { return order_id; }
	public Double getUnityPrice() { return unity_price; }
	public Integer getProdQuantity() { return prod_quantity; }
	public Double getAmmountPrice() { return ammount_price; }

	@Override
	public String toString() {
		return String.format("%1$s,%2$s,%3$s,%4$s",
			order_id,
			prod_id,
			unity_price,
			prod_quantity
		);
	}

	public static class OrderItemBuilder {
		private String prod_id;
		private Integer prod_quantity;
		private Double ammount_price;
		private Double unity_price;
		private String order_id;

		public OrderItemBuilder() { }

		public OrderItemBuilder prodId(String id) {
			prod_id = id;
			return this;
		}

		public OrderItemBuilder prodQuantity(Integer quantity) {
			prod_quantity = quantity;
			return this;
		}

		public OrderItemBuilder prodUnityPrice(Double price) {
			unity_price = price;
			return this;
		}

		public OrderItemBuilder prodOrderId(String order) {
			order_id = order;
			return this;
		}

		public OrderItemBuilder prodAmount() {
			ammount_price = prod_quantity + unity_price;
			return this;
		}

		public OrderItem build() {
			return new OrderItem(this);
		}
	}
}

