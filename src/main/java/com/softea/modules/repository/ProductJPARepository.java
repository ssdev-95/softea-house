/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.softea.modules.entity.Product;

@Repository
public interface ProductJPARepository
  extends JpaRepository<Product, String> { }
