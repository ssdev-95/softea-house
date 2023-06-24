/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity @Table(name="orders")
@Getter @Setter @Accessors(chain=true)
@NoArgsConstructor @AllArgsConstructor
public class Order {
	@Id @GeneratedValue(strategy=GenerationType.UUID)
	private String id;
	private long customer;
	private int table;
	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(
	  name="order_items",
		joinColumns={ @JoinColumn(name="order_id") },
		inverseJoinColumns={
			@JoinColumn(name="product_id",unique=false)
		}
	)
	private List<Product> orderItems;

	public double orderPrice() {
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
