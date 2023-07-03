/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.repository;

import java.util.List;
import java.util.Optional;
import com.softea.modules.dto.UserDTO;
import com.softea.modules.entity.User;

public interface IUserRepository {
	List<User> findAll();
	Optional<User> findById(String id);
	Optional<User> findByTaxId(String taxId);
	User save(UserDTO dto);
}
