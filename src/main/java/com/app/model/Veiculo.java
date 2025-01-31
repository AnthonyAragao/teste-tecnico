package com.app.model;

public abstract class Veiculo 
{
    private Integer id;
    private String tipo;
    private String modelo;
    private String fabricante;
    private int ano;
    private double preco;

    public Veiculo(String tipo, String modelo,  String fabricante, int ano, double preco){
        this.tipo = tipo;
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.ano = ano;
        this.preco = preco;
    }

    public Integer getId() 
    {
        return id;
    }

    public void setId(Integer id) 
    { 
        this.id = id; 
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
