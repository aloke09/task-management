package com.bridgelabz.UserManagement1.repository;

import com.bridgelabz.UserManagement1.model.Category;
import com.bridgelabz.UserManagement1.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepo extends JpaRepository<Task,Integer>
{
    List<Task> findByUserId(Long userId);
    List<Task> findByUserIdAndStatus(Long userId, String status);
    List<Task> findByUserIdAndPriority(Long userId, String priority);
    List<Task> findByUserIdAndDueDate(Long userId, LocalDate dueDate);

    List<Task> findByStatus(String status);
    List<Task> findByPriority(String priority);
    List<Task> findByCategory(Category category);
}
