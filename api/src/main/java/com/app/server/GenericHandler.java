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
        this.objectMapper = new ObjectMapper();
        this.type = type;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String metodo = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String resposta = "";

        try {
            switch (metodo) {
                case "GET":
                    resposta = handleGet(path);
                    break;
                case "POST":
                    resposta = handlePost(exchange);
                    break;
                case "PUT":
                    resposta = handlePut(exchange, path);
                    break;
                case "DELETE":
                    resposta = handleDelete(path);
                    break;
                default:
                    resposta = "Método não permitido";
                    exchange.sendResponseHeaders(405, resposta.length());
            }
        } catch (Exception e) {
            e.printStackTrace();
            resposta = "Erro interno: " + e.getMessage();
            exchange.sendResponseHeaders(500, resposta.length());
        }

        enviarResposta(exchange, resposta);
    }

    private String handleGet(String path) throws IOException {
        if (path.matches("/" + type.getSimpleName().toLowerCase() + "/\\d+")) {
            int id = Integer.parseInt(path.split("/")[2]);
            T entity = controller.show(id);
            return entity != null ? objectMapper.writeValueAsString(entity) : type.getSimpleName() + " não encontrado";
        } else {
            List<T> entities = controller.index();
            return objectMapper.writeValueAsString(entities);
        }
    }

    private String handlePost(HttpExchange exchange) throws IOException {
        T newEntity = objectMapper.readValue(exchange.getRequestBody(), type);
        controller.create(newEntity);
        return type.getSimpleName() + " criado com sucesso!";
    }

    private String handlePut(HttpExchange exchange, String path) throws IOException {
        if (!path.matches("/" + type.getSimpleName().toLowerCase()+"s" + "/\\d+")) {
            return "ID inválido!";
        }
        int id = Integer.parseInt(path.split("/")[2]);
        T updatedEntity = objectMapper.readValue(exchange.getRequestBody(), type);
        // Aqui você pode definir um método no controlador para associar o ID ao objeto
        // Por exemplo: updatedEntity.setId(id);
        controller.update(id,updatedEntity);
        return type.getSimpleName() + " atualizado com sucesso!";
    }

    private String handleDelete(String path) throws IOException {
        if (!path.matches("/" + type.getSimpleName().toLowerCase() + "/\\d+")) {
            return "ID inválido!";
        }
        int id = Integer.parseInt(path.split("/")[2]);
        controller.delete(id);
        return type.getSimpleName() + " removido com sucesso!";
    }

    private void enviarResposta(HttpExchange exchange, String resposta) throws IOException {
        exchange.sendResponseHeaders(200, resposta.getBytes(StandardCharsets.UTF_8).length);
        OutputStream os = exchange.getResponseBody();
        os.write(resposta.getBytes(StandardCharsets.UTF_8));
        os.close();
    }
}
