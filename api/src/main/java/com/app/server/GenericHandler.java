package com.app.server;

import com.app.controller.GenericController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GenericHandler<T> implements HttpHandler {
    private final GenericController<T> controller;
    private final ObjectMapper objectMapper;
    private final Class<T> type;

    public GenericHandler(GenericController<T> controller, Class<T> type) {
        this.controller = controller;
        this.type = type;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String response = processRequest(exchange);
            sendResponse(exchange, response, 200);
        } catch (Exception exception) {
            exception.printStackTrace();
            sendResponse(exchange, exception.getMessage(), 500);
        }
    }

    private String processRequest(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path   = exchange.getRequestURI().getPath();

        return switch (method) {
            case "GET" -> handleGet(exchange, path);
            case "POST" -> handlePost(exchange);
            case "PUT" -> handlePut(exchange, path);
            case "DELETE" -> handleDelete(exchange, path);
            default -> {
                String METHOD_NOT_ALLOWED = "Método não permitido";
                sendResponse(exchange, METHOD_NOT_ALLOWED, 405);
                yield METHOD_NOT_ALLOWED;
            }
        };
    }

    private String handleGet(HttpExchange exchange, String path) throws IOException {
        if (isSingleEntityRequest(path)) {
            Integer id = extractIdFromPath(path);
            if (id == null) {
                sendResponse(exchange, "ID inválido", 400);
                return "ID inválido";
            }
            T entity = controller.show(id);

            if (entity == null) {
                sendResponse(exchange, type.getSimpleName() + " não encontrado", 404);
                return type.getSimpleName() + " não encontrado";
            } 

            String response = objectMapper.writeValueAsString(entity);
            sendResponse(exchange, response, 200);
            return response;
        }

        List<T> entities = controller.index();
        String response = objectMapper.writeValueAsString(entities);
        sendResponse(exchange, response, 200);
        return response;
    }

    private String handlePost(HttpExchange exchange) throws IOException {
        try {
            String requestBody = new String(exchange.getRequestBody().readAllBytes());
        
            // Converte para o objeto esperado
            T newEntity = objectMapper.readValue(requestBody, type);

            // Envia para o controller
            controller.create(newEntity);
            
            sendResponse(exchange, type.getSimpleName() + " criado com sucesso!", 201);
            return type.getSimpleName() + " criado com sucesso!";
        } catch (IllegalArgumentException exception) {
            System.err.println("Erro de validação: " + exception.getMessage());
            sendResponse(exchange, exception.getMessage(), 400);
            return exception.getMessage();
        } catch (Exception exception) {
            exception.printStackTrace(); 
            sendResponse(exchange, "Erro ao criar " + type.getSimpleName(), 500);
            return "Erro ao criar " + type.getSimpleName();
        }
    }

    private String handlePut(HttpExchange exchange, String path) throws IOException {
        try {
            Integer id = extractIdFromPath(path);
            if (id == null) {
                sendResponse(exchange, "ID inválido", 400);
                return "ID inválido";
            }
            T updatedEntity = objectMapper.readValue(exchange.getRequestBody(), type);
            controller.update(id, updatedEntity);
            return type.getSimpleName() + " atualizado com sucesso!";
        } catch (IllegalArgumentException exception) { 
            System.err.println("Erro de validação: " + exception.getMessage());
            sendResponse(exchange, exception.getMessage(), 400);
            return exception.getMessage();
        } catch (Exception exception) { 
            exception.printStackTrace();
            String errorMessage = "Erro ao atualizar " + type.getSimpleName() + ": " + exception.getMessage();
            sendResponse(exchange, errorMessage, 500);
            return errorMessage;
        }
    }

    private String handleDelete(HttpExchange exchange, String path) throws IOException {
        Integer id = extractIdFromPath(path);
        if (id == null) {
            sendResponse(exchange, "ID inválido", 400);
            return "ID inválido";
        }
        controller.delete(id);
        sendResponse(exchange, type.getSimpleName() + " removido com sucesso!", 204);
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
    
        exchange.sendResponseHeaders(statusCode, response.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }
}
