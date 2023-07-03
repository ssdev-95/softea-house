/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.entity;

import com.softea.modules.entity.enums.OrderStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
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

	@Column(name="created_at")
	private LocalDateTime createdAt;

	@Column(name="updated_at")
	private LocalDateTime updatedAt;

	@ManyToOne
	@JoinColumn(
	  name="user_id", referencedColumnName="id")
	private User customer;

	@OneToMany(mappedBy="order")
	private List<OrderItem> orderItems;

	@Enumerated(EnumType.STRING)
	@Column(name="order_status")
	private OrderStatus orderStatus;

	public Order() {
		id = null;
		customer = null;
		orderItems = Collections.emptyList();
		createdAt = LocalDateTime.now();
		updatedAt = null;
		orderStatus = OrderStatus.OPEN_ORDER;
	}

	private double orderPrice() {
		return orderItems
			.stream()
			.collect(Collectors.toSet())
			.stream()
			.reduce(0d,
				(sum, next)->sum+(next.getSubtotal()),
				Double::sum);
	}

	public boolean isPaid() {
		return orderStatus.equals(
			OrderStatus.PAID_ORDER);
	}

	public boolean isOpen() {
		return orderStatus.equals(
			OrderStatus.OPEN_ORDER);
	}

	@Override
	public String toString() {
		final String base = "{id:%s,customer:%s,price:%s},items:%s";
	  return String.format(base,
			id,
			customer.getTaxId(),
			orderPrice(),
			orderItems.size());
	}
}
