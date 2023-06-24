/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.service;

import com.softea.modules.dto.ProductDTO;
import com.softea.modules.entity.Product;
import com.softea.modules.repository.IProductRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {
	private final IProductRepository productRepository;

	public List<Product> listProducts() {
		return productRepository.listProducts();
	}
	
	public Optional<Product> getProduct(String id) {
		return productRepository.findById(id);
	}

	public Product saveProduct(ProductDTO dto) {
		return productRepository.save(dto);
	}
}
