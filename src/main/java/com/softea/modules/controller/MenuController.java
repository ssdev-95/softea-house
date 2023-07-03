/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.softea.modules.dto.ProductDTO;
import com.softea.modules.entity.Product;
import com.softea.modules.handler.ProductNotFoundException;
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
			@PathVariable(value="id") String id)
			throws ProductNotFoundException {
		return ResponseEntity.ok(
			menuService.getProduct(id));
	}

	@PostMapping("/add-product")
	public ResponseEntity<Product> registerProduct(
			@RequestBody ProductDTO body){
		return ResponseEntity.created(null).body(
		  menuService.saveProduct(body));
	}

}
