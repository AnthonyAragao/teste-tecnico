package com.app.model;

public abstract class Veiculo {
    private Integer veiculoId;
    private String tipo;
    private String modelo;
    private String fabricante;
    private int ano;
    private double preco;

    public Veiculo() {}

    public Veiculo(int veiculoId, String tipo, String modelo,  String fabricante, int ano, double preco){
        this.veiculoId = veiculoId;
        this.tipo = tipo;
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.ano = ano;
        this.preco = preco;
    }

    public Integer getVeiculoId() {
        return veiculoId;
    }

    public String getTipo() {
        return tipo;
    }

    public String getModelo() {
        return modelo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public int getAno() {
        return ano;
    }

    public double getPreco() {
        return preco;
    }

    public void setVeiculoId(Integer veiculoId) { 
        this.veiculoId = veiculoId; 
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public void setPreco(double preco) {
        if (preco < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo.");
        }
        this.preco = preco;
    }

    @Override
    public String toString() {
        return String.format("%s: %s %s (%d) - R$ %.2f", tipo, fabricante, modelo, ano, preco);
    }
}
