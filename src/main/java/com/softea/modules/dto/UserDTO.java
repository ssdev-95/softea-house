/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
@NoArgsConstructor @AllArgsConstructor
public class UserDTO {
	@NotNull
	private String name;
	@NotNull
	private String email;
	@NotNull @Size(min=8)
	private String password;
	@NotNull
	private String taxId;
}
