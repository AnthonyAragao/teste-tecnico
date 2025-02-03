package com.app.model;

public class Carro extends Veiculo
{
    private int quantidadePortas;
    private TipoCombustivel tipoCombustivel;

    public Carro()
    {
        super();
    }

    public Carro(String tipo, String modelo, String fabricante, int ano,  double preco, int quantidadePortas, TipoCombustivel tipoCombustivel){
        super(tipo, modelo, fabricante, ano, preco);
        this.quantidadePortas = quantidadePortas;
        this.tipoCombustivel = tipoCombustivel;
    }

    public Carro(int id, String tipo, String modelo, String fabricante, int ano,  double preco, int quantidadePortas, TipoCombustivel tipoCombustivel){
        super(id, tipo, modelo, fabricante, ano, preco);
        this.quantidadePortas = quantidadePortas;
        this.tipoCombustivel = tipoCombustivel;
    }


    public int getQuantidadePortas() 
    {
        return quantidadePortas;
    }

    public void setQuantidadePortas(int quantidadePortas) 
    {
        this.quantidadePortas = quantidadePortas;
    }

    public TipoCombustivel getTipoCombustivel() 
    {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(TipoCombustivel tipoCombustivel) 
    {
        this.tipoCombustivel = tipoCombustivel;
    }
    
    @Override
    public String toString() {
        return super.toString() + ", Portas: " + quantidadePortas + ", Combustível: " + tipoCombustivel;
    }
}
