/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import com.softea.modules.dto.ProductDTO;
import com.softea.modules.service.MenuService;
import com.softea.repository.ProductRepository;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MenuServiceTests {
	private MenuService menu;
	@BeforeAll
	void setup() {
		ProductRepository pr = new ProductRepository();
		menu = new MenuService(pr);
	}
	@Test
	void should_not_find_a_product() {
		Assertions.assertThrows(
			Exception.class,
			()->menu.getProduct("12345"));
	}

	@Test
	void should_find_a_product() {
		ProductDTO dto = new ProductDTO()
			.setSku("Bubble Tea")
			.setPrice(2.45d);
		String id = String.valueOf(dto.hashCode());
		menu.saveProduct(dto);
		Assertions.assertDoesNotThrow(
			()->menu.getProduct(id));
	}

	@Test
	void should_throw_an_exception_saving_a_product() {
		ProductDTO dto = new ProductDTO(null, null);
		Assertions.assertThrows(
			Exception.class,
			()->menu.saveProduct(dto));
	}
}
