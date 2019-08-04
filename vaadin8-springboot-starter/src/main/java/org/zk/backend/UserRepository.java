package org.zk.backend;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.zk.backend.data.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

	Page<User> findByEmailLikeIgnoreCaseOrNameLikeIgnoreCaseOrRoleLikeIgnoreCase(String emailLike, String nameLike,
			String roleLike, Pageable pageable);

	long countByEmailLikeIgnoreCaseOrNameLikeIgnoreCase(String emailLike, String nameLike);
}
