package com.bridgelabz.UserManagement1.repository;

import com.bridgelabz.UserManagement1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Integer>
{
    Optional<User> findByEmail(String email);
}
