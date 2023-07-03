/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.softea.modules.dto.UserDTO;
import com.softea.modules.entity.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserRepository
	  implements IUserRepository {
	private final UserJPARepository jpaRepository;

	@Override
	public Optional<User> findById(String id) {
	  return jpaRepository.findById(id);
	}

	@Override
	public Optional<User> findByTaxId(String taxId) {
		return jpaRepository.findByTaxId(taxId);
	}

	@Override
	public User save(UserDTO dto) {
	  var user = new User();
		return jpaRepository.save(user);
	}

	@Override
	public List<User> findAll() {
	  return jpaRepository.findAll();
	}
}
