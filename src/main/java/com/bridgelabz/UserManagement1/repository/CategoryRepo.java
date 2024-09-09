package com.bridgelabz.UserManagement1.repository;

import com.bridgelabz.UserManagement1.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Integer> {
}
