package com.medicalfield.core.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{
    
    Optional<User> findByIdentity(String identity);

    boolean existsByIdentityOrEmail(String identity, String email);
}
