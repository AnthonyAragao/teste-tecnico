package com.app.repository.interfaces;

import java.util.List;

import com.app.model.Carro;

public interface ICarroRepository {
    public List<Carro> findAll();
    public Carro findById(int id);
    public void insert(Carro veiculo);
    public void update(Carro veiculo);
    public void delete(int id);
}
