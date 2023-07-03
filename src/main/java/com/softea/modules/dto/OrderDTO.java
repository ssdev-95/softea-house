/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.dto;

import java.util.List;
import java.util.Objects;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
@NoArgsConstructor @AllArgsConstructor
public class OrderDTO {
	@NotEmpty
	private List<OrderItemDTO> orderItems;
	@NotNull
	private String customer;
	@NotNull @PositiveOrZero @Max(9)
	private int table;

	@Override
	public int hashCode() {
	  return Objects.hash(orderItems,customer);
	}
}
