package com.app.model;

public class Carro extends Veiculo
{
    private int quantidadePortas;
    private String tipoCombustivel;

    public Carro(String tipo, String modelo, String fabricante, int ano,  double preco, int quantidadePortas, String tipoCombustivel){
        super(tipo, modelo, fabricante, ano, preco);
        this.quantidadePortas = quantidadePortas;
        this.tipoCombustivel = tipoCombustivel;
    }

    public int getQuantidadePortas() 
    {
        return quantidadePortas;
    }

    public String getTipoCombustivel() 
    {
        return tipoCombustivel;
    }
    
    @Override
    public String toString() {
        return super.toString() + ", Portas: " + quantidadePortas + ", Combustível: " + tipoCombustivel;
    }
}
