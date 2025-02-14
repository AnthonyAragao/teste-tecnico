package com.app.server;

import com.app.controller.MotoController;
import com.app.controller.CarroController;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServidorHttp {
    private static final int PORTA = 8080;

    public static void main(String[] args) throws IOException {
        HttpServer server = criarServidor();
        configurarRoteamento(server);
        iniciarServidor(server);
    }

    private static HttpServer criarServidor() throws IOException {
        return HttpServer.create(new InetSocketAddress(PORTA), 0);
    }

    private static void configurarRoteamento(HttpServer server) {
        MotoController motoController   = new MotoController();
        CarroController carroController = new CarroController();

        server.createContext("/motos", new GenericHandler<>(motoController, com.app.model.Moto.class));
        server.createContext("/carros", new GenericHandler<>(carroController, com.app.model.Carro.class));
    }

    private static void iniciarServidor(HttpServer server) {
        server.setExecutor(null);
        server.start();
        System.out.println("Servidor rodando em http://localhost:" + PORTA);
    }
}
