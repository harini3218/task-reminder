package com.example.taskreminder.controller;

import com.example.taskreminder.model.Task;
import com.example.taskreminder.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public Task addTask(@RequestBody Task task) {
        return taskService.addTask(task);
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
    @PutMapping("/complete/{id}")
    public Task markCompleted(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        task.setCompleted(true);
        return taskService.updateTask(id, task);
    }

    @GetMapping("/status/{id}")
    public String getStatus(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return task.isCompleted() ? "COMPLETED" : "PENDING";
    }

}
