/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity @Table(name="skus")
@Getter @Setter @Accessors(chain=true)
@NoArgsConstructor @AllArgsConstructor
public class Product {
	@Id @GeneratedValue(strategy=GenerationType.UUID)
	private String id;
	private String sku;
	private double price;

	@JsonIgnore
	@ManyToMany(mappedBy="orderItems")
	private List<Order> orders;

	@Override
	public String toString() {
		final String base = "{id:%s,sku:%s,price:%s}";
	  return String.format(base, id, sku, price);
	}
}
