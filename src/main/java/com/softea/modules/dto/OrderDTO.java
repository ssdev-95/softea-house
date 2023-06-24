/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.dto;

import com.softea.modules.entity.Product;
import java.util.List;
import java.util.Objects;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
@NoArgsConstructor @AllArgsConstructor
public class OrderDTO {
	@NotNull
	private List<Product> orderItems;
	@NotNull
	private long customer;
	@NotNull
	private int table;

	@Override
	public int hashCode() {
	  return Objects.hash(orderItems,customer);
	}
}
