/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity @Table(name="t_order_item")
@Getter @Setter @Accessors(chain=true)
@AllArgsConstructor @NoArgsConstructor
public class OrderItem {
	@Id @GeneratedValue(strategy=GenerationType.UUID)
	private String id;
	private int quantity;

	@JsonIgnore @ManyToOne @JoinColumn(
	  name="order_id", referencedColumnName="id")
	private Order order;

	@ManyToOne @JoinColumn(
	  name="product_id", referencedColumnName="id")
	private Product product;
	
	public double getSubtotal() {
		return quantity * product.getPrice();
	}
}
