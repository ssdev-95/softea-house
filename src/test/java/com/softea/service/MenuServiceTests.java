/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.service;

import java.util.Optional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import com.softea.factory.ProductFactory;
import com.softea.modules.dto.ProductDTO;
import com.softea.modules.handler.ProductNotFoundException;
import com.softea.modules.repository.IProductRepository;
import com.softea.modules.repository.ProductRepository;
import com.softea.modules.service.MenuService;
import jakarta.validation.ConstraintViolationException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MenuServiceTests {
	private MenuService menu;
	private IProductRepository pr;
	
	@BeforeAll
	void setup() {
		pr = mock(ProductRepository.class);
		menu = new MenuService(pr);
	}

	@AfterAll
	void drop() {
		reset(pr);
	}

	@Test
	void should_not_find_a_product() {
		final String id = "12345";
		
		when(pr.findById(id)).thenReturn(
			Optional.empty());
		
		var exception = assertThrows(
			ProductNotFoundException.class,
			()->menu.getProduct(id));

		assertEquals(
			"[EXCEPTION] Product not found",
			exception.getMessage());
	}

	@Test
	void should_find_a_product() {
		final String id = "1irjq8d8j1nr8dajam";
		
		when(pr.findById(id)).thenReturn(Optional.of(
			ProductFactory.create()));
		
		var product = assertDoesNotThrow(
			()->menu.getProduct(id));

		assertNotNull(product);
	}

	@Test
	void should_fail_saving_a_product() {
		final ProductDTO dto = new ProductDTO(null, 0d);
		
		when(pr.save(dto)).thenThrow(
			ConstraintViolationException.class);
		
		assertThrows(
			ConstraintViolationException.class,
			()->menu.saveProduct(dto));
	}

	@Test
	void should_save_a_product_without_fail() {
		final ProductDTO dto = new ProductDTO(
			"Black Tea",7.95d);
	
		when(pr.save(dto)).thenReturn(
			ProductFactory.create());

		var product = assertDoesNotThrow(
			()->menu.saveProduct(dto));

		assertNotNull(product);
	}
}
