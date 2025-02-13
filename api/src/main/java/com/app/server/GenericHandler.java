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
            Integer id = extractIdFromPath(path);
            if (id == null) {
                return "ID inválido";
            }
            T entity = controller.show(id);
            return entity != null 
                ? objectMapper.writeValueAsString(entity) 
                : type.getSimpleName() + " não encontrado";
        }

        List<T> entities = controller.index();
        return objectMapper.writeValueAsString(entities);
    }

    private String handlePost(HttpExchange exchange) throws IOException {
        try {
            T newEntity = objectMapper.readValue(exchange.getRequestBody(), type);
            controller.create(newEntity);
            return type.getSimpleName() + " criado com sucesso!";
        } catch (IllegalArgumentException e) { // Erro de validação
            sendResponse(exchange, e.getMessage(), 400);
            return e.getMessage();
        } catch (Exception e) {
            sendResponse(exchange, "Erro ao criar " + type.getSimpleName(), 500);
            return "Erro ao criar " + type.getSimpleName();
        }
    }

    private String handlePut(HttpExchange exchange, String path) throws IOException {
        Integer id = extractIdFromPath(path);
        if (id == null) {
            return "ID inválido";
        }
        T updatedEntity = objectMapper.readValue(exchange.getRequestBody(), type);
        controller.update(id, updatedEntity);
        return type.getSimpleName() + " atualizado com sucesso!";
    }

    private String handleDelete(String path) throws IOException {
        Integer id = extractIdFromPath(path);
        if (id == null) {
            return "ID inválido";
        }
        controller.delete(id);
        return type.getSimpleName() + " removido com sucesso!";
    }

    private Integer extractIdFromPath(String path) {
        String[] partes = path.split("/");
        if (partes.length < 3) {
            return null;
        }
        try {
            return Integer.parseInt(partes[2]);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private boolean isSingleEntityRequest(String path) {
        return path.matches("^/\\w+/\\d+$");
    }

    private void sendResponse(HttpExchange exchange, String response, int statusCode) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
    
        // Se for uma requisição OPTIONS (preflight), só devolvemos os cabeçalhos necessários
        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }
    
        exchange.sendResponseHeaders(statusCode, response.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }
}
