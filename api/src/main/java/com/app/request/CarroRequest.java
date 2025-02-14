package com.app.request;

import com.app.model.Carro;

public class CarroRequest {
    public static void validar(Carro carro) {
        if (carro == null) {
            throw new IllegalArgumentException("O carro não pode ser nulo.");
        }
        if (carro.getTipo() == null || carro.getTipo().trim().isEmpty()) {
            throw new IllegalArgumentException("O tipo do carro é obrigatório.");
        }
        if (carro.getModelo() == null || carro.getModelo().trim().isEmpty()) {
            throw new IllegalArgumentException("O modelo do carro é obrigatório.");
        }
        if (carro.getFabricante() == null || carro.getFabricante().trim().isEmpty()) {
            throw new IllegalArgumentException("O fabricante do carro é obrigatório.");
        }
        if (carro.getAno() == 0) {
            throw new IllegalArgumentException("O ano do carro é obrigatório.");
        }
        if (carro.getAno() < 1886) { 
            throw new IllegalArgumentException("O ano do carro é inválido.");
        }
        if (carro.getPreco() == 0) {
            throw new IllegalArgumentException("O preço do carro é obrigatório.");
        }
     
        if (carro.getQuantidadePortas() == 0) {
            throw new IllegalArgumentException("A quantidade de portas do carro é obrigatória.");
        }

        if (carro.getTipoCombustivel() == null) {
            throw new IllegalArgumentException("O tipo de combustível do carro é obrigatório.");
        }
    }
}
