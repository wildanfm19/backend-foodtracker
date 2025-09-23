package com.erlin.foodtracker.repositories;

import com.erlin.foodtracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User , Long> {

    Optional<User> findByEmail(String email);
}
