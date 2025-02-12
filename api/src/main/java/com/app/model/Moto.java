package com.app.model;

public class Moto extends Veiculo {
    private int cilindrada;

    public Moto() {
        super();
    }

    public Moto(int id, String tipo, String modelo, String fabricante, int ano, double preco, int cilindrada) {
        super(id, tipo, modelo, fabricante, ano, preco);
        this.cilindrada = cilindrada;
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
