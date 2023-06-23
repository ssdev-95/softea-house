/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.softea.modules.dto.ProductDTO;
import com.softea.modules.entity.Product;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductRepository
	  implements IProductRepository {
	private final ProductJPARepository jpaRepository;

	@Override
	public List<Product> listProducts() {
		return jpaRepository.findAll();
	}

	@Override
	public Optional<Product> findById(String id) {
		return jpaRepository.findById(id);
	}

	@Override
	public Product save(ProductDTO dto) {
	  Product product = new Product()
			.setSku(dto.getSku())
			.setPrice(dto.getPrice())
			.setOrders(Collections.emptyList());
		return jpaRepository.save(product);
	}
}
