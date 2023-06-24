/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.softea.modules.entity.Order;

@Repository
public interface OrderJPARepository
	  extends JpaRepository<Order, String> {
	List<Order> findAllByTable(int table);
}
