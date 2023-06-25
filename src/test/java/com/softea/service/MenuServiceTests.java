/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.service;

import java.util.Optional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import com.softea.factory.ProductFactory;
import com.softea.modules.dto.ProductDTO;
import com.softea.modules.entity.Product;
import com.softea.modules.handler.ProductNotFoundException;
import com.softea.modules.repository.IProductRepository;
import com.softea.modules.repository.ProductRepository;
import com.softea.modules.service.MenuService;
import jakarta.validation.ConstraintViolationException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MenuServiceTests {
	private MenuService menu;
	private IProductRepository pr;
	
	@BeforeAll
	void setup() {
		pr = Mockito.mock(ProductRepository.class);
		menu = new MenuService(pr);
	}

	@AfterAll
	void drop() {
		Mockito.reset(pr);
	}

	@Test
	void should_not_find_a_product() {
		String id = "12345";
		
		Mockito.when(pr.findById(id)).thenReturn(
			Optional.empty());
		
		Assertions.assertThrows(
			ProductNotFoundException.class,
			()->menu.getProduct(id));
	}

	@Test
	void should_find_a_product() {
		String id = "1irjq8d8j1nr8dajam";
		
		Mockito.when(pr.findById(id)).thenReturn(
			Optional.of(ProductFactory.create()));
		
		Assertions.assertDoesNotThrow(
			()->menu.getProduct(id));
	}

	@Test
	void should_fail_while_saving_a_product() {
		ProductDTO dto = new ProductDTO(null, 0d);
		
		Mockito.when(pr.save(dto)).thenThrow(
			ConstraintViolationException.class);
		
		Assertions.assertThrows(
			ConstraintViolationException.class,
			()->menu.saveProduct(dto));
	}

	@Test
	void should_save_a_product_without_fail() {
		ProductDTO dto = new ProductDTO(
			"Black Tea",7.95d);
	
		Mockito.when(pr.save(dto)).thenReturn(
			ProductFactory.create());

		Assertions.assertDoesNotThrow(
			()->menu.saveProduct(dto));
	}
}
