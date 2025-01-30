package com.app.model;

public class Moto extends Veiculo
{
    private int cilindrada;

    public Moto(int id, String tipo, String modelo, String fabricante, int ano, double preco, int cilindrada){
        super(id, tipo, modelo, fabricante, ano, preco);
        this.cilindrada = cilindrada;
    }

    public int getCilindrada() 
    {
        return cilindrada;
    }

    @Override
    public String toString() 
    {
        return "Motorcycle{" +
               "id=" + getId() +
               ", tipo='" + getTipo() + '\'' +
               ", modelo='" + getModelo() + '\'' +
               ", fabricante='" + getFabricante() + '\'' +
               ", ano=" + getAno() +
               ", preco=" + getPreco() +
               ", cilindrada=" + cilindrada +
               '}';
    }
}
