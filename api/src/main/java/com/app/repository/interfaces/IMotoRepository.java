package com.app.repository.interfaces;

import java.util.List;

import com.app.model.Moto;

public interface IMotoRepository {
    public List<Moto> findAll();
    public Moto findById(int id);
    public void insert(Moto veiculo);
    public void update(Moto veiculo);
    public void delete(int id);
}
