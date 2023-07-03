/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.factory;

import com.softea.modules.entity.User;

public class UserFactory {
	public static User create(
			String id, String name, String email,
			String taxId, String password) {
		return new User()
			.setId(id)
			.setName(name)
			.setEmail(email)
			.setTaxId(taxId)
			.setPassword(password);
	}

	public static User create() {
		return create(
			"1irjq8d8j1nr8dajam",
			"Saloma",
			"saloma@dev.io",
			"183742929393",
			"wjgjso1jrKH28_1992");
	}
}
