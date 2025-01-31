package com.app.service;

import java.util.List;
import com.app.model.Moto;
import com.app.repository.interfaces.IMotoRepository;

public class MotoService 
{
    private IMotoRepository repository;
    
    public MotoService(IMotoRepository repository) 
    {
        this.repository = repository;
    }
    
    public List<Moto> findAll() 
    {
        return repository.findAll();
    }

    public Moto findById(int id) 
    {
        return repository.findById(id);
    }

    public void insert(Moto moto) 
    {
        repository.insert(moto);
    }

    public void update(Moto moto) 
    {
        repository.update(moto);
    }

    public void delete(int id) 
    {
        repository.delete(id);
    }
}
