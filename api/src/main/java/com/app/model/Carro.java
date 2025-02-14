package com.app.model;

public class Carro extends Veiculo {
    private Integer id;
    private int quantidadePortas;
    private TipoCombustivel tipoCombustivel;

    public Carro() {
        super();
    }

    public Carro(int id, String tipo, String modelo, String fabricante, int ano,  double preco, int quantidadePortas, TipoCombustivel tipoCombustivel, Integer veiculoId) {
        super(veiculoId, tipo, modelo, fabricante, ano, preco);
        this.id = id;
        this.quantidadePortas = quantidadePortas;
        this.tipoCombustivel = tipoCombustivel;
    }

    public Integer getId() {
        return id;
    }

    public int getQuantidadePortas() {
        return quantidadePortas;
    }

    public void setQuantidadePortas(int quantidadePortas) {
        this.quantidadePortas = quantidadePortas;
    }

    public TipoCombustivel getTipoCombustivel(){
        return tipoCombustivel;
    }

    public void setTipoCombustivel(TipoCombustivel tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }
    
    @Override
    public String toString() {
        return super.toString() + String.format(" | %d portas | Combustível: %s", quantidadePortas, tipoCombustivel);
    }
}
