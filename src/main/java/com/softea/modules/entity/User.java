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

@Entity @Table(name="t_users")
@Getter @Setter @Accessors(chain=true)
@NoArgsConstructor @AllArgsConstructor
public class User {
	@Id @GeneratedValue(strategy=GenerationType.UUID)
	private String id;
	private String name;
	private String email;
	@JsonIgnore
	private String password;
	@JsonIgnore @Column(name="tax_id")
	private String taxId;
}
