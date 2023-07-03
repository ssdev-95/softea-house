/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
@AllArgsConstructor @NoArgsConstructor
public class OrderItemDTO {
	private int quantity;
	private String productId;
}
