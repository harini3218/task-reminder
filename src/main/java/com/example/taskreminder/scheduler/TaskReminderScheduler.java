package com.example.taskreminder.scheduler;

import com.example.taskreminder.model.Task;
import com.example.taskreminder.service.EmailService;
import com.example.taskreminder.service.TaskService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class TaskReminderScheduler {

    private final TaskService taskService;
    private final EmailService emailService;

    public TaskReminderScheduler(TaskService taskService, EmailService emailService) {
        this.taskService = taskService;
        this.emailService = emailService;
    }

    @Scheduled(fixedRate = 60000) // Run every 1 minute
    public void checkDueTasks() {
        System.out.println("Checking tasks at " + LocalDateTime.now());

        List<Task> allTasks = taskService.getAllTasks(); // <-- original code used getAllTasks()

        for (Task task : allTasks) {
            if (!task.isCompleted()) { // only process tasks that are pending
                if (task.getEmail() != null && !task.getEmail().isEmpty()) {
                    if (task.getDueDate().isBefore(LocalDateTime.now()) || 
                        task.getDueDate().isEqual(LocalDateTime.now())) {
                        try {
                            emailService.sendReminder(
                                task.getEmail(),
                                "Task Reminder: " + task.getTitle(),
                                "Hello! Your task '" + task.getTitle() + "' is due now."
                            );
                            System.out.println("✅ Reminder sent for task: " + task.getTitle());
                            task.setCompleted(true);
                            taskService.updateTask(task.getId(), task);
                        } catch (Exception e) {
                            System.out.println("? Failed to send reminder for task: " + task.getTitle());
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
