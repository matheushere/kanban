package com.matheus;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInterface {
    TaskRepository taskRepository;
    private TaskManager taskManager;
    private Scanner read;
    private String taskID;

    public UserInterface() throws IOException {
        taskRepository = new TaskRepository();
        taskManager = new TaskManager(taskRepository);
        read = new Scanner(System.in);
    }

    private void startMenu() {
        System.out.println(
                "################################\n" +
                "#            KANBAN            #\n" +
                "################################\n" +
                "| MENU:                        |\n" +
                "--------------------------------\n" +
                "| 1 - Criar tarefa             |\n" +
                "| 2 - Ver todas as tarefas     |\n" +
                "| 3 - Ver tarefas à fazer      |\n" +
                "| 4 - Ver tarefas em andamento |\n" +
                "| 5 - Ver tarefas concluídas   |\n" +
                "| 6 - Atualizar tarefa         |\n" +
                "| 7 - Deletar tarefa           |\n" +
                "| 8 - Sair                     |\n" +
                "--------------------------------"
        );
    }

    private boolean MenuOption() throws IOException{
        System.out.println("Digite a opção desejada: ");
        try {
            int choice = read.nextInt();
            read.nextLine();
            switch(choice) {
                case 1:
                    createTask();
                    breakLine();
                    break;
                case 2:
                    showAllTasks();
                    breakLine();
                    break;
                case 3:
                    showTasksTO_DO();
                    breakLine();
                    break;
                case 4:
                    showTasksDOING();
                    breakLine();
                    break;
                case 5:
                    showTasksDONE();
                    breakLine();
                    break;
                case 6:
                    taskUpdate();
                    breakLine();
                    break;
                case 7:
                    taskDelete();
                    breakLine();
                    break;
                case 8:
                    System.out.println("Saindo...");
                    return true;
                default :
                    messageInvalidOption();
                    breakLine();
                    break;
            }
        } catch (InputMismatchException e) {
            messageInvalidOptionEnterNumbers();
            read.nextLine();
            breakLine();
        }
        return false;
    }

    private void createTask() throws IOException {
        System.out.println("Digite o ID da tarefa");
        taskID = read.nextLine();
        if(taskManager.idAlreadyExists(taskID)) {
            System.out.println("Já existe uma tarefa cadastrada com esse ID");
        }
        else {
            System.out.println("Digite a descrição da tarefa");
            String taskDescription = read.nextLine();
            boolean success = taskManager.addTask(taskID, taskDescription);
            if (success){
                System.out.println("Tarefa de ID " + taskID + " criada com sucesso!");
            }
            else {
                System.out.println("Tarefa não foi criada.");
            }
        }
    }

    private void showAllTasks() throws IOException{
        //if(taskManager.loadTaskList() == null) {
        if(taskManager.taskListIsEmpty()) {
            messageThereAreNoTasks();
        }
        else {
            System.out.println("Essas são todas as tarefas:");
            System.out.println(taskManager.getAllTasks());
        }
    }

    private void showTasksTO_DO() throws IOException{
        if(taskManager.taskListIsEmpty()) {
            messageThereAreNoTasks();
        }
        else {
            System.out.println("Essas são todas as tarefas à fazer:");
            System.out.println(taskManager.getTaskListByStatus(TaskStatus.TO_DO));
        }
    }

    private void showTasksDOING() throws IOException{
        if(taskManager.taskListIsEmpty()) {
            messageThereAreNoTasks();
        }
        else {
            System.out.println("Essas são todas as tarefas em andamento:");
            System.out.println(taskManager.getTaskListByStatus(TaskStatus.DOING));
        }
    }

    private void showTasksDONE() throws IOException{
        if(taskManager.taskListIsEmpty()) {
            messageThereAreNoTasks();
        }
        else {
            System.out.println("Essas são todas as tarefas concluídas:");
            System.out.println(taskManager.getTaskListByStatus(TaskStatus.DONE));
        }
    }

    private void taskUpdate() throws IOException{
        if(taskManager.taskListIsEmpty()) {
            messageThereAreNoTasks();
        }
        else {
            System.out.println("Digite o ID da tarefa: ");
            taskID = read.nextLine();
            Task task = taskManager.getTaskByID(taskID);
            if(task == null) {
                messageTaskDoesNotExist();
            }
            else {
                boolean exitTaskUpdate = false;
                while(exitTaskUpdate == false) {
                    System.out.println("O que você deseja atualizar:\n" +
                            "| 1 - Descrição |\n" +
                            "| 2 - Status    |\n" +
                            "| 3 - Voltar    |");
                    try {
                        int chooseTaskUpdate = read.nextInt();
                        read.nextLine();
                        if(chooseTaskUpdate == 1) {
                            updateTaskDescription(taskID);
                            exitTaskUpdate = true;
                        }
                        else if(chooseTaskUpdate == 2) {
                            exitTaskUpdate = updateTaskStatus(taskID);
                        }
                        else if(chooseTaskUpdate == 3) {
                            exitTaskUpdate = true;
                        }
                        else {
                            messageInvalidOption();
                            breakLine();
                        }
                    }
                    catch (InputMismatchException e) {
                        messageInvalidOptionEnterNumbers();
                        read.nextLine();
                        breakLine();
                    }
                }
            }
        }
    }

    private void updateTaskDescription(String taskID) throws IOException{
        System.out.println("Escreva a descrição:");
        String descriptionTask = read.nextLine();
        taskManager.updateTaskDescriptionByID(taskID, descriptionTask);
        messageTaskUpdatedSuccessfully(taskID);
    }

    private boolean updateTaskStatus(String taskID) throws IOException{
        boolean exitUpdateTaskStatus = false;
        while(exitUpdateTaskStatus == false) {
            System.out.println("Para qual status você deseja mudar?\n" +
                    "| 1 - Para fazer   |\n" +
                    "| 2 - Em andamento |\n" +
                    "| 3 - Concluída    |\n" +
                    "| 4 - Voltar       |");
            try {
                int chooseTaskUpdateStatus = read.nextInt();
                switch (chooseTaskUpdateStatus) {
                    case 1:
                        taskManager.updateTaskStatusByID(taskID, TaskStatus.TO_DO);
                        messageTaskUpdatedSuccessfully(taskID);
                        return true;
                    case 2:
                        taskManager.updateTaskStatusByID(taskID, TaskStatus.DOING);
                        messageTaskUpdatedSuccessfully(taskID);
                        return true;
                    case 3:
                        taskManager.updateTaskStatusByID(taskID, TaskStatus.DONE);
                        messageTaskUpdatedSuccessfully(taskID);
                        return true;
                    case 4:
                        return false;
                    default:
                        System.out.println("Opção invalida. Escolha dentre as listadas");
                        breakLine();
                        break;
                }
            }
            catch(InputMismatchException e) {
                messageInvalidOptionEnterNumbers();
                read.nextLine();
                breakLine();
            }
        }
        return exitUpdateTaskStatus;
    }

    private void taskDelete() throws IOException{
        if(taskManager.taskListIsEmpty()) {
            messageThereAreNoTasks();
        }
        else {
            System.out.println("Digite o ID da tarefa que deseja deletar");
            taskID = read.nextLine();
            Task task = taskManager.getTaskByID(taskID);
            if(task == null) {
                messageTaskDoesNotExist();
            }
            else {
                taskManager.deleteTask(task);
                System.out.println("Tarefa de ID " + taskID + " deletada com sucesso!");
            }
        }
    }

    public void callAplication() throws IOException{
        System.out.println("Bem vindo(a) ao KANBAN!");
        boolean exit = false;
        while(!exit) {
            startMenu();
            exit = MenuOption();
        }
        System.out.println("Obrigado por usar o KANBAN! :)");
    }

    public void messageThereAreNoTasks() {
        System.out.println("Não existe tarefas até o momento.");
    }

    public void messageTaskDoesNotExist() {
        System.out.println("Não existe uma tarefa com esse ID.");
    }

    public void messageTaskUpdatedSuccessfully(String taskID) {
        System.out.println("Tarefa de ID " + taskID + " atualizada com sucesso!");
    }

    public void messageInvalidOption() {
        System.out.println("Opção invalida. Escolha dentre as listadas.");
    }

    public void messageInvalidOptionEnterNumbers() {
        System.out.println("Opção invalida. Digite apenas números.");
    }

    public void breakLine() {
        System.out.println("");
    }
}
