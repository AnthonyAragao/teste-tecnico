package com.app.request;

import com.app.model.Moto;

public class MotoRequest {
    public static void validar(Moto moto) {
        if (moto == null) {
            throw new IllegalArgumentException("A moto não pode ser nulo.");
        }
        if (moto.getTipo() == null || moto.getTipo().trim().isEmpty()) {
            throw new IllegalArgumentException("O tipo do moto é obrigatório.");
        }
        if (moto.getModelo() == null || moto.getModelo().trim().isEmpty()) {
            throw new IllegalArgumentException("O modelo da moto é obrigatório.");
        }
        if (moto.getFabricante() == null || moto.getFabricante().trim().isEmpty()) {
            throw new IllegalArgumentException("O fabricante da moto é obrigatório.");
        }
        if (moto.getAno() == 0) {
            throw new IllegalArgumentException("O ano da moto é obrigatório.");
        }
        if (moto.getAno() < 1886) { 
            throw new IllegalArgumentException("O ano da moto é inválido.");
        }
        if (moto.getPreco() == 0) {
            throw new IllegalArgumentException("O preço da moto é obrigatório.");
        }
        if(moto.getCilindrada() == 0) {
            throw new IllegalArgumentException("A cilindrada da moto é obrigatória.");
        }
        if(moto.getCilindrada() < 50) {
            throw new IllegalArgumentException("A cilindrada da moto deve ser maior que 50.");
        }
    }
}
