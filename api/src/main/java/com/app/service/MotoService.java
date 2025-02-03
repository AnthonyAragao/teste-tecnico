package com.app.service;

import java.util.List;
import com.app.model.Moto;
import com.app.repository.interfaces.IMotoRepository;

public class MotoService {
    private IMotoRepository repository;

    public MotoService(IMotoRepository repository) {
        this.repository = repository;
    }

    public List<Moto> findAll() {
        return repository.findAll();
    }

    public Moto findById(int id) {
        return repository.findById(id);
    }

    public void insert(Moto moto) {
        repository.insert(moto);
    }

    public void update(int id, Moto moto) {
        Moto motoFinded = null;
        try {
            motoFinded = repository.findById(id);
        } catch (Exception e) {
            System.out.println("Moto não encontrada");
            e.printStackTrace();
        }
        Moto newMoto = new Moto(
            motoFinded.getId(),
            moto.getTipo() != null ? moto.getTipo() : motoFinded.getTipo(),
            moto.getModelo() != null ? moto.getModelo() : motoFinded.getModelo(),
            moto.getFabricante() != null ? moto.getFabricante() : motoFinded.getFabricante(),
            moto.getAno() != 0 ? moto.getAno() : motoFinded.getAno(),
            moto.getPreco() != 0 ? moto.getPreco() : motoFinded.getPreco(),
            moto.getCilindrada() != 0 ? moto.getCilindrada() : motoFinded.getCilindrada()
        );
        repository.update(newMoto);
    }

    public void delete(int id) {
        repository.delete(id);
    }
}
