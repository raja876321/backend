package com.stocks.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.stocks.entity.Role;
import com.stocks.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByEmailAndPassword(String email, String password);

	User findByEmail(String currentUser);

	Page<User> findAll(Pageable pageable);

	boolean existsByEmail(String string);

	List<User> findByRole(Role roleToDelete);

}