/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.service;

import com.softea.modules.dto.ProductDTO;
import com.softea.modules.entity.Product;
import com.softea.modules.repository.IProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MenuService {
	private final IProductRepository productRepository;

	public List<Product> listProducts() {
		return productRepository.listProducts();
	}
	
	public Product getProduct(String id) {
		Product product = productRepository.findById(id)
			.orElseThrow();
		return product;
	}

	public Product saveProduct(ProductDTO dto) {
		return productRepository.save(dto);
	}
}
