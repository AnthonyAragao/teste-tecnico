package com.app.service;

import java.util.List;
import com.app.model.Moto;
import com.app.repository.interfaces.IMotoRepository;

public class MotoService {
    private final IMotoRepository repository;

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

    public void update(int id, Moto motoAtualizada) {
        Moto motoExistente = repository.findById(id);
        if (motoExistente == null) {
            throw new RuntimeException("Moto não encontrada");
        }
        Moto motoParaAtualizar = mergeMoto(motoExistente, motoAtualizada);
        repository.update(motoParaAtualizar);
    }

    public void delete(int id) {
        if (repository.findById(id) == null) {
            throw new RuntimeException("Moto não encontrada");
        }
        repository.delete(id);
    }

    private Moto mergeMoto(Moto motoExistente, Moto motoAtualizada) {
        if (motoAtualizada.getModelo() != null) {
            motoExistente.setModelo(motoAtualizada.getModelo());
        }
        if (motoAtualizada.getFabricante() != null) {
            motoExistente.setFabricante(motoAtualizada.getFabricante());
        }
        if (motoAtualizada.getAno() != 0) {
            motoExistente.setAno(motoAtualizada.getAno());
        }
        if (motoAtualizada.getPreco() != 0) {
            motoExistente.setPreco(motoAtualizada.getPreco());
        }
        if (motoAtualizada.getCilindrada() != 0) {
            motoExistente.setCilindrada(motoAtualizada.getCilindrada());
        }
        return motoExistente;
    }
}
