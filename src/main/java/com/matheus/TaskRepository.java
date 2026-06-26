package com.matheus;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT).registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public void save(List<Task> taskList) throws IOException {
        objectMapper.writeValue(new File("lista_de_tarefas.json"), taskList);
    }

    public List<Task> recover() throws IOException {
        File file = new File("lista_de_tarefas.json");

        if (!file.exists()) {
            List<Task> emptyList = new ArrayList<>();
            objectMapper.writeValue(file, emptyList);
            return emptyList;
        }

        return objectMapper.readValue(
                file,
                new TypeReference<List<Task>>() {}
        );
    }
}