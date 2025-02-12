package com.app.server;

import com.app.controller.GenericController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GenericHandler<T> implements HttpHandler {
    private static final String METHOD_NOT_ALLOWED = "Método não permitido";
    private static final String INTERNAL_ERROR = "Erro interno: ";

    private final GenericController<T> controller;
    private final ObjectMapper objectMapper;
    private final Class<T> type;

    public GenericHandler(GenericController<T> controller, Class<T> type) {
        this.controller = controller;
        this.objectMapper = new ObjectMapper();
        this.type = type;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String response = processRequest(exchange);
            sendResponse(exchange, response, 200);
        } catch (Exception exception) {
            exception.printStackTrace();
            String errorMessage = INTERNAL_ERROR + exception.getMessage();
            sendResponse(exchange, errorMessage, 500);
        }
    }

    private String processRequest(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        return switch (method) {
            case "GET" -> handleGet(path);
            case "POST" -> handlePost(exchange);
            case "PUT" -> handlePut(exchange, path);
            case "DELETE" -> handleDelete(path);
            default -> {
                sendResponse(exchange, METHOD_NOT_ALLOWED, 405);
                yield METHOD_NOT_ALLOWED;
            }
        };
    }

    private String handleGet(String path) throws IOException {
        if (isSingleEntityRequest(path)) {
            int id = extractIdFromPath(path);
            T entity = controller.show(id);
            return entity != null 
                ? objectMapper.writeValueAsString(entity) 
                : type.getSimpleName() + " não encontrado";
        }

        List<T> entities = controller.index();
        return objectMapper.writeValueAsString(entities);
    }

    private String handlePost(HttpExchange exchange) throws IOException {
        T newEntity = objectMapper.readValue(exchange.getRequestBody(), type);
        controller.create(newEntity);
        return type.getSimpleName() + " criado com sucesso!";
    }

    private String handlePut(HttpExchange exchange, String path) throws IOException {
        int id = extractIdFromPath(path);
        T updatedEntity = objectMapper.readValue(exchange.getRequestBody(), type);
        controller.update(id, updatedEntity);
        return type.getSimpleName() + " atualizado com sucesso!";
    }

    private String handleDelete(String path) throws IOException {
        int id = extractIdFromPath(path);
        controller.delete(id);
        return type.getSimpleName() + " removido com sucesso!";
    }

    private int extractIdFromPath(String path) {
        String[] partes = path.split("/");
        return Integer.parseInt(partes[2]);
    }

    private boolean isSingleEntityRequest(String path) {
        return path.matches("^/\\w+/\\d+$");
    }

    private void sendResponse(HttpExchange exchange, String response, int statusCode) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }
}