/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.dto;

import java.util.Objects;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
@NoArgsConstructor @AllArgsConstructor
public class ProductDTO {
	@NotNull
	private String sku;
	@NotNull @Min(1)
	private Double price;

	@Override
	public int hashCode() {
	  return Objects.hash(sku, price);
	}
}
