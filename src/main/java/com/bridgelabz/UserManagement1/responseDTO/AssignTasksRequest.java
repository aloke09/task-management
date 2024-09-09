package com.bridgelabz.UserManagement1.responseDTO;

import com.bridgelabz.UserManagement1.model.Task;

import java.util.List;

public class AssignTasksRequest
{
    private List<Task> tasks;
    private int categoryId;

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
