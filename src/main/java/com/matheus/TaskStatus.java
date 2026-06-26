package com.matheus;

public enum TaskStatus {
    TO_DO("Para fazer"),
    DOING("Em andamento"),
    DONE("Concluída");

    private String taskStatusDescription;

    TaskStatus(String taskStatusDescription) {
        this.taskStatusDescription = taskStatusDescription;
    }

    public String getTaskStatusDescription() {
        return this.taskStatusDescription;
    }
}
