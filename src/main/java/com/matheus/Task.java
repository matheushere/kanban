package com.matheus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    private String taskID;
    private String taskDescription;
    private TaskStatus taskStatus;
    private LocalDateTime created;
    private LocalDateTime updated;

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public Task() {

    }

    public Task(String taskID, String taskDescription) {
        this.taskID = taskID;
        this.taskDescription = taskDescription;
        this.taskStatus = TaskStatus.TO_DO;
        this.created = LocalDateTime.now();
        this.updated = LocalDateTime.now();
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public LocalDateTime getCreation() {
        return created;
    }

    public void setCreation(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated() {
        this.updated = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return
                "{\n" +
                        "    \"ID\" : " + taskID + "\n" +
                        "    \"Descrição\" : " + taskDescription + "\n" +
                        "    \"Status\" : " + taskStatus.getTaskStatusDescription() + "\n" +
                        "    \"Data de criação\" : " + created.format(dateTimeFormatter) + "\n" +
                        "    \"Data de atualização\" : " + updated.format(dateTimeFormatter) + "\n" +
                        "}";
    }
}