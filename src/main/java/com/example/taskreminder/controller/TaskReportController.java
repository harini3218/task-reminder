package com.example.taskreminder.controller;

import com.example.taskreminder.csv.TaskcsvService;
import com.example.taskreminder.model.Task;
import com.example.taskreminder.service.TaskService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class TaskReportController {

    private final TaskService taskService;
    private final TaskcsvService csvService;

    public TaskReportController(TaskService taskService, TaskcsvService csvService) {
        this.taskService = taskService;
        this.csvService = csvService;
    }

    @GetMapping("/export")
    public void exportTasksToCsv(HttpServletResponse response) throws IOException {

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=tasks.csv");

        List<Task> tasks = taskService.getAllTasks();
        csvService.writeTasksToCsv(tasks, response.getWriter());
    }
}
