package com.bridgelabz.UserManagement1.service;

import com.bridgelabz.UserManagement1.exception.CustomeException;
import com.bridgelabz.UserManagement1.model.Category;
import com.bridgelabz.UserManagement1.model.Task;
import com.bridgelabz.UserManagement1.repository.CategoryRepo;
import com.bridgelabz.UserManagement1.repository.TaskRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CategoryService
{
    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    public Category createCategory(Category category)
    {
        log.info("insert in category");
        return categoryRepo.save(category);
    }

    public Category updateCategory(Category category) throws CustomeException
    {

        if (categoryRepo.existsById(category.getId()))
        {
//            category.setId(category.getId());
            return categoryRepo.save(category);
        } else {
            throw new RuntimeException("Category not found");
        }
    }

    public void deleteCategory(int id)
    {
        if (categoryRepo.existsById(id))
        {
            log.info("delete by in on category");
            categoryRepo.deleteById(id);
        } else
        {
            log.error("category not found for deletion ");
            throw new RuntimeException("Category not found");
        }
    }

    public List<Category> getAllCategories()
    {
        log.info("view all from category");
        return categoryRepo.findAll();
    }

    public Optional<Category> getCategoryById(int id)
    {
        log.info("view by id from category");
        return categoryRepo.findById(id);
    }

    public Category assignTasksToCategory(int categoryId, List<Task> tasks)
    {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        for (Task task : tasks) {
            task.setCategory(category);
        }
        log.info("assign tasks to category");
        return categoryRepo.save(category);
    }

    @Transactional
    public Category assignTasksToCategory2(int categoryId, List<Task> tasks) {
        // Find the category by ID
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Assign the category to each task and save the task
        for (Task task : tasks) {
            // Check if the task already exists, and if not, create a new task
            Optional<Task> existingTask = taskRepo.findById(task.getId());
            if (existingTask.isPresent()) {
                Task taskToUpdate = existingTask.get();
                taskToUpdate.setCategory(category);
                taskRepo.save(taskToUpdate);
            } else {
                task.setCategory(category);
                taskRepo.save(task);
            }
        }

        // Optionally update the category's task list if you want to maintain consistency
        category.setTask(new HashSet<>(tasks));

        // Save the updated category
        return categoryRepo.save(category);
    }
}
