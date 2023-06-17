/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.softea.modules.dto.ProductDTO;
import com.softea.modules.entity.Product;
import com.softea.modules.repository.IProductRepository;

public class ProductRepository
	  implements IProductRepository {
	List<Product> prods;
	public ProductRepository() {
		prods = new ArrayList<>();
	}
	@Override
	public Optional<Product> findById(String id) {
		return prods
			.stream()
			.filter(p->p.getId().equals(id))
			.findFirst();
	}

	@Override
	public Product save(ProductDTO dto) {
		Product product = new Product()
			.setId(String.valueOf(dto.hashCode()))
			.setSku(dto.getSku())
			.setPrice(dto.getPrice());
		prods.add(product);
	  return product;
	}
}
