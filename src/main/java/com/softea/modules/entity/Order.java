/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import com.softea.modules.entity.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity @Table(name="t_orders")
@Getter @Setter @Accessors(chain=true)
@AllArgsConstructor
public class Order {
	@Id @GeneratedValue(strategy=GenerationType.UUID)
	private String id;
	private long customer;
	private int tableNumber;

	private LocalDateTime created_at;
	private LocalDateTime updated_at;

	@ManyToMany(
	  fetch = FetchType.EAGER,
		cascade={CascadeType.REFRESH}
	)
	@JoinTable(
	  name="order_items",
    joinColumns={ @JoinColumn(name="order_id") },
		inverseJoinColumns={
			@JoinColumn(name="product_id",unique=false)
		}
	)
	private List<Product> orderItems;

	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	public Order() {
		id = null;
		customer = 1l;
		tableNumber = 1;
		orderItems = Collections.emptyList();
		created_at = LocalDateTime.now();
		updated_at = null;
		orderStatus = OrderStatus.OPEN_ORDER;
	}

	private double orderPrice() {
		return orderItems
			.stream()
			.collect(Collectors.toSet())
			.stream()
			.reduce(0d,
				(sum, next)->sum+(next.getPrice()*getProductCount(next.getId())),
				Double::sum);
	}

	private long getProductCount(String id) {
		return orderItems
			.stream()
			.filter(i->i.getId().equals(id))
			.count();
	}

	public boolean isOpen() {
		return orderStatus.equals(
			OrderStatus.OPEN_ORDER);
	}

	public boolean isPaid() {
		return orderStatus.equals(
			OrderStatus.PAID_ORDER);
	}

	@Override
	public String toString() {
		final String base = "{id:%s,customer:%s,price:%s},items:%s";
	  return String.format(base,
			id,
			customer,
			orderPrice(),
			orderItems.size());
	}
}
