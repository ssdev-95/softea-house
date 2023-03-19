package com.product;

public class Product {
	private String cod;
	private String sku;
	private String category;
	private double price;
	private String pic;

	public String getCategory() { return category; }
	public double getPrice() { return price; }
	public String getCod() { return cod; }
	public String getPic() { return pic; }
	public String getSku() { return sku; }

	private Product(ProductBuilder builder) {
		this.category = builder._category;
		this.cod = builder._cod;
		this.pic = builder._pic;
		this.price = builder._price;
		this.sku = builder._sku;
	}

	public static class ProductBuilder {
		private String _cod;
		private String _sku;
		private String _category;
		private double _price;
		private String _pic;

		public ProductBuilder() {}

		public ProductBuilder price(double price) {
			_price = price;
			return this;
		}

		public ProductBuilder cod(String cod) {
			_cod = cod;
			return this;
		}

		public ProductBuilder category(String category) {
			_category = category;
			return this;
		}

		public ProductBuilder pic(String pic) {
			_pic = pic;
			return this;
		}

		public ProductBuilder sku(String sku) {
			_sku = sku;
			return this;
		}

		public Product build() {
			return new Product(this);
		}
	}

	public String toString(int index) {
		return String.format(
				"%1$s. - %2$s [R$ %3$s]", index, sku, price);
	}
}

