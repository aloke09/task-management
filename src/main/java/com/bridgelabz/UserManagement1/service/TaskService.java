package com.bridgelabz.UserManagement1.service;
import com.bridgelabz.UserManagement1.model.Category;
import com.bridgelabz.UserManagement1.model.Task;
import com.bridgelabz.UserManagement1.repository.TaskRepo;
import com.bridgelabz.UserManagement1.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class TaskService {

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private UserRepo userRepo;

    public Task createTask(Task task)
    {
        log.info("save task in db ");
        return taskRepo.save(task);
    }

    public Task updateTask(int id, Map<String, Object> updates)
    {
        log.info("update task in db");
        Optional<Task> optionalTask = taskRepo.findById(id);
        if (optionalTask.isPresent())
        {
            Task task = optionalTask.get();
            updates.forEach((key, value) ->
            {
                switch (key) {
                    case "status":
                        task.setStatus((String) value);
                        break;
                    case "priority":
                        task.setPriority((String) value);
                        break;
                    case "dueDate":
                        task.setDueDate(LocalDate.parse((String) value)); // Adjust type as necessary
                        break;
                    case "completed":
                        task.setStatus("0");
                        break;
                }
            });
            return taskRepo.save(task);
        } else {
            throw new RuntimeException("Task not found");
        }
    }

    public String deleteTask(int id)
    {
        if (taskRepo.existsById(id))
        {
            taskRepo.deleteById(id);
            log.info("successfully deleted by id in db");
            return "Task successfully deleted";
        } else {
            log.error("task not found in db!!");
            return "Task not found";
        }
    }

    public List<Task> getTasksByUser(Long userId, String status, String priority, String dueDate)
    {
        if (status != null)
        {
            log.info("get by id and status from db");
            return taskRepo.findByUserIdAndStatus(userId, status);
        } else if (priority != null) {
            log.info("get by id and priority from db");
            return taskRepo.findByUserIdAndPriority(userId, priority);
        } else if (dueDate != null) {
            log.info("get by id and duedate from db");
            return taskRepo.findByUserIdAndDueDate(userId, LocalDate.parse(dueDate));
        } else {
            log.info("get by id from db");
            return taskRepo.findByUserId(userId);
        }
    }

    public Task getTaskById(int id)
    {
        Task task = taskRepo.findById(id).orElse(null);
        if(task!=null)
        {
            log.info("task get by id from db");
            return task;
        }
        else {
            log.error("task not found in db");
            throw new RuntimeException("Task not found");
        }

//        return taskRepo.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public List<Task> filterTasksByStatus(String status)
    {
        log.info("filterTasksByStatus from db");
        return taskRepo.findByStatus(status);
    }

    public List<Task> filterTasksByPriority(String priority) {
        log.info("filterTasksByPriority from db");
        return taskRepo.findByPriority(priority);
    }

    public List<Task> filterTasksByCategory(Category category) {
        log.info("filterTasksByCategory from db");
        return taskRepo.findByCategory(category);
    }
}

