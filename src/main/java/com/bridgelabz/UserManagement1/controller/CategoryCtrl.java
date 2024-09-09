package com.bridgelabz.UserManagement1.controller;

import com.bridgelabz.UserManagement1.exception.CustomeException;
import com.bridgelabz.UserManagement1.model.Category;
import com.bridgelabz.UserManagement1.model.Task;
import com.bridgelabz.UserManagement1.responseDTO.AssignTasksRequest;
import com.bridgelabz.UserManagement1.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
//@RequestMapping("/category")
public class CategoryCtrl
{
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/insert")
    public Category createCategory(@RequestBody Category category)
    {
        log.trace("insert on category");
        return categoryService.createCategory(category);
    }

    @PutMapping("/update")
    public Category updateCategory( @RequestBody Category category)
    {
        log.trace("update on category");
        try {
            return categoryService.updateCategory(category);
        } catch (CustomeException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable int id) {
        log.trace("deletion on category");
        categoryService.deleteCategory(id);
    }

    @GetMapping
    public List<Category> getAllCategories() {
        log.trace("view all category");
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable int id)
    {
        log.trace("view by id on category");
        Optional<Category> category = categoryService.getCategoryById(id);
        return category.orElse(null); // Or handle the not found case as needed
    }

//    @PostMapping("/{id}/tasks")
//    public Category assignTasksToCategory(@PathVariable int id, @RequestBody List<Task> tasks)
//    {
//        log.trace("assign tasks to category");
//        return categoryService.assignTasksToCategory2(id, tasks);
//    }
@PostMapping("/{id}/tasks")
public Category assignTasksToCategory(@PathVariable int id, @RequestBody AssignTasksRequest request) {
    log.trace("Assign tasks to category with ID: {}", id);
    return categoryService.assignTasksToCategory(request.getCategoryId(), request.getTasks());
}




}
