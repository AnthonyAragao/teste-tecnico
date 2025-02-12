package com.app.server;

import com.app.controller.MotoController;
import com.app.controller.CarroController;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServidorHttp 
{
    private static final int PORTA = 8080;

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORTA), 0);
        MotoController motoController = new MotoController();
        CarroController carroController = new CarroController();

        // Adicionando os handlers para cada rota
        server.createContext("/motos", new MotoHandler(motoController));
        server.createContext("/carros", new CarroHandler(carroController));

        server.setExecutor(null);
        server.start();
        System.out.println("Servidor rodando em http://localhost:" + PORTA);
    }
}