package com.app.model;

public class Moto extends Veiculo {
    private Integer id;
    private int cilindrada;

    public Moto() {
        super();
    }

    public Moto(int id, String tipo, String modelo, String fabricante, int ano, double preco, int cilindrada, Integer veiculoId) {
        super(veiculoId, tipo, modelo, fabricante, ano, preco);
        if (cilindrada <= 0) {
            throw new IllegalArgumentException("A cilindrada deve ser maior que zero.");
        }
        this.id = id;
        this.cilindrada = cilindrada;
    }

    public Integer getId() {
        return id;
    }

    public int getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(int cilindrada) {
        this.cilindrada = cilindrada;
    }

    @Override
    public String toString() {
        return super.toString() + ", Cilindrada: " + cilindrada + "cc";
    }
}
