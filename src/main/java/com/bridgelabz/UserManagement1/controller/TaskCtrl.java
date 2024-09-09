package com.bridgelabz.UserManagement1.controller;

import com.bridgelabz.UserManagement1.model.Category;
import com.bridgelabz.UserManagement1.model.Task;
import com.bridgelabz.UserManagement1.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/task")
public class TaskCtrl
{
    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public Task createTask(@RequestBody Task task)
    {
        log.trace("create task in task ctrl");
        return taskService.createTask(task);
    }

    @PutMapping("/update/{id}")
    public Task updateTask(@PathVariable int id, @RequestBody Map<String, Object> updates)
    {
        log.trace("update task in task ctrl");
        return taskService.updateTask(id, updates);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteTask(@PathVariable int id)
    {
        log.trace("delete task in task ctrl");
        return taskService.deleteTask(id);
    }

    @GetMapping("/user/{userId}")
    public List<Task> getTasksByUser(@PathVariable Long userId,
                                     @RequestParam(required = false) String status,
                                     @RequestParam(required = false) String priority,
                                     @RequestParam(required = false) String dueDate)
    {
        log.trace("get task by user in task ctrl");
        return taskService.getTasksByUser(userId, status, priority, dueDate);
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable int id)
    {
        log.trace("get task by id in ctrl");
        return taskService.getTaskById(id);
    }

    @GetMapping("/filter/{status}")
    public List<Task> filterTasksByStatus(@PathVariable String status) {
        log.trace("Filter tasks by status");
        return taskService.filterTasksByStatus(status);
    }


    @GetMapping("/filter/{priority}")
    public List<Task> filterTasksByPriority(@PathVariable String priority) {
        log.trace("filter task by priority");
        return taskService.filterTasksByPriority(priority);
    }

    @GetMapping("/filter/{category}")
    public List<Task> filterTasksByCategory(@PathVariable Category category) {
        log.trace("filter task by category");
        return taskService.filterTasksByCategory(category);
    }
}
