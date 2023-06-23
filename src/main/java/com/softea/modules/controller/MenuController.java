/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.softea.modules.dto.ProductDTO;
import com.softea.modules.entity.Product;
import com.softea.modules.service.MenuService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {
	private final MenuService menuService;

	@GetMapping("/")
	public ResponseEntity<List<Product>> getProducts() {
		return ResponseEntity.ok(
			menuService.listProducts());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> getProduct(
			@PathVariable(value="id") String id) {
		try {
  		return ResponseEntity.ok(
  			menuService.getProduct(id));
		} catch(RuntimeException re) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/add-product")
	public ResponseEntity<Product> registerProduct(
			@RequestBody ProductDTO body){
		return ResponseEntity.created(null).body(
			menuService.saveProduct(body));
	}

}
