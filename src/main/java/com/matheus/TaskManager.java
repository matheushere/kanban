package com.matheus;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class TaskManager {
    private List<Task> taskList = new ArrayList<>();
    private TaskRepository taskRepository;


    public TaskManager(TaskRepository taskRepository) throws IOException{
        this.taskRepository = taskRepository;
        taskList = taskRepository.recover();
    }

    //RETORNA O OBJETO JSON EM JAVA (CONCLUÍDA)
    public List<Task> loadTaskList() throws IOException{
        return taskList;
    }

    //VERIFICAR SE A TAREFAS SALVAS (CONCLUÍDA)
    public boolean taskListIsEmpty() throws IOException{
        return taskList.isEmpty();
    }

    //ADICIONAR TAREFA
    public boolean addTask(String taskID, String taskDescription) {
        try {
            Task task = new Task(taskID, taskDescription);
            taskList.add(task);
            taskRepository.save(taskList);
            return true;
        }
        catch(IOException e) {
            return false;
        }
    }

    //VERIFICAR SE JÁ HÁ UMA TAREFA COM MEMSMO ID (CONCLUÍDA)
    public boolean idAlreadyExists(String taskID) throws IOException{
        for(Task task : taskList) {
            if(task.getTaskID().equals(taskID)) {
                return true;
            }
        }
        return false;
    }

    //RETORNA TODAS AS TAREFAS (CONCLUÍDA)
    public List<Task> getAllTasks() throws IOException{
        return taskList;
    }

    //RETORNA A TAREFA PELO ID (CONCLUÍDA)
    public Task getTaskByID(String taskID) throws IOException{
        for(Task task : taskList) {
            if(task.getTaskID().equals(taskID)) {
                return task;
            }
        }
        return null;
    }

    //RETORNA UMA LISTA DE TAREFAS PELO STATUS (CONCLUÍDA)
    public List<Task> getTaskListByStatus(TaskStatus taskStatus) throws IOException{
        List<Task> taskListByStatus = new ArrayList<>();
        for(Task task : taskList) {
            if(task.getTaskStatus() == taskStatus) {
                taskListByStatus.add(task);
            }
        }
        return taskListByStatus;
    }

    //ATUALIZAR A DESCRIÇÃO DE UMA TAREFA BASEADA NO ID (CONCLUÍDA)
    public boolean updateTaskDescriptionByID(String taskID, String taskDescription) throws IOException{
        for(Task task : taskList) {
            if(task.getTaskID().equals(taskID)) {
                deleteTask(task);
                task.setTaskDescription(taskDescription);
                taskList.add(task);
                taskRepository.save(taskList);
                return true;
            }
        }
        return false;
    }

    //ATUALIZA O STATUS DE UMA TAREFA BASEADA NO ID (CONCLUÍDA)
    public boolean updateTaskStatusByID(String taskID, TaskStatus taskStatus) throws IOException{
        for(Task task : taskList) {
            if(task.getTaskID().equals(taskID)) {
                deleteTask(task);
                task.setTaskStatus(taskStatus);
                taskList.add(task);
                taskRepository.save(taskList);
                return true;
            }
        }
        return false;
    }

    //DELETA UMA TAREFA BASEADA PELO O SEU ID (CONCLUÍDA)
    public boolean deleteTask(Task task) throws IOException {
        if(taskList.remove(task)) {
            taskRepository.save(taskList);
            return true;
        }
        return false;
    }
}