/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.softea.modules.entity.User;

@Repository
public interface UserJPARepository
	  extends JpaRepository<User, String> {
	Optional<User> findByTaxId(String taxId);
}
