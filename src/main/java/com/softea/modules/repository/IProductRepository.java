/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.repository;

import java.util.List;
import java.util.Optional;
import com.softea.modules.dto.ProductDTO;
import com.softea.modules.entity.Product;

public interface IProductRepository {
	Optional<Product> findById(String id);
	List<Product> listProducts();
	Product save(ProductDTO dto);
}
