package com.app.model;

public abstract class Veiculo {
    private Integer id;
    private String tipo;
    private String modelo;
    private String fabricante;
    private int ano;
    private double preco;

    public Veiculo() {}

    public Veiculo(int id, String tipo, String modelo,  String fabricante, int ano, double preco){
        this.id = id;
        this.tipo = tipo;
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.ano = ano;
        this.preco = preco;
    }

    public Integer getId() {
        return id;
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

    public void setId(Integer id) { 
        this.id = id; 
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
