/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

	@Override
	public String toString() {
	  return String.format("{id:%s,sku:%s,price:%s}",
		id, sku, price);
	}
}
