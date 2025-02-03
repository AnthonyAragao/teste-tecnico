package com.app.model;

public abstract class Veiculo 
{
    private Integer id;
    private String tipo;
    private String modelo;
    private String fabricante;
    private int ano;
    private double preco;

    public Veiculo()
    {
        super();
    }

    public Veiculo(String tipo, String modelo,  String fabricante, int ano, double preco){
        this.tipo = tipo;
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.ano = ano;
        this.preco = preco;
    }

    public Veiculo(int id, String tipo, String modelo,  String fabricante, int ano, double preco){
        this.id = id;
        this.tipo = tipo;
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.ano = ano;
        this.preco = preco;
    }

    // Getters
    public Integer getId() 
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

    // Setters
    public void setId(Integer id) 
    { 
        this.id = id; 
    }

    public void setModelo(String modelo) 
    {
        this.modelo = modelo;
    }

    public void setFabricante(String fabricante) 
    {
        this.fabricante = fabricante;
    }

    public void setAno(int ano) 
    {
        this.ano = ano;
    }

    public void setPreco(double preco) 
    {
        this.preco = preco;
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
