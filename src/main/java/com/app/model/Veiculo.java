package com.app.model;

public abstract class Veiculo 
{
    protected int id;
    protected String tipo;
    protected String modelo;
    protected String fabricante;
    protected int ano;
    protected double preco;

    public Veiculo(int id, String tipo, String modelo,  String fabricante, int ano, double preco){
        this.id = id;
        this.tipo = tipo;
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.ano = ano;
        this.preco = preco;
    }

    public int getId() 
    {
        return id;
    }

    public String getTipo() 
    {
        return tipo;
    }

    public String getModelo() 
    {
        return modelo;
    }

    public String getFabricante() 
    {
        return fabricante;
    }

    public int getAno() 
    {
        return ano;
    }

    public double getPreco() 
    {
        return preco;
    }

    @Override
    public String toString() 
    {
        return "Veiculo{" +
               "id=" + id +
               ", modelo='" + modelo + '\'' +
               ", fabricante='" + fabricante + '\'' +
               ", ano=" + ano +
               ", preco=" + preco +
               '}';
    }
}
