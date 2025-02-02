package com.app.model;

public class Moto extends Veiculo
{
    private int cilindrada;
    private int veiculoId;

    public Moto(String tipo, String modelo, String fabricante, int ano, double preco, int cilindrada){
        super(tipo, modelo, fabricante, ano, preco);
        this.cilindrada = cilindrada;
    }

    public int getCilindrada() 
    {
        return cilindrada;
    }

    public int getVeiculoId() 
    {
        return veiculoId;
    }

    public void setVeiculoId(int veiculoId) 
    {
        this.veiculoId = veiculoId;
    }

    public void setCilindrada(int cilindrada) 
    {
        this.cilindrada = cilindrada;
    }

    @Override
    public String toString() {
        return super.toString() + ", Cilindrada: " + cilindrada + "cc";
    }
}
