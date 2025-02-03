package com.app.server;

import com.app.controller.MotoController;
import com.app.controller.CarroController;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServidorHttp {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        MotoController motoController = new MotoController();
        CarroController carroController = new CarroController();

        // Adicionando os handlers para cada rota
        server.createContext("/motos", new MotoHandler(motoController));
        server.createContext("/carros", new CarroHandler(carroController));

        server.setExecutor(null);
        server.start();
        System.out.println("Servidor rodando em http://localhost:8080/");
    }
}