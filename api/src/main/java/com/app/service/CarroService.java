package com.app.service;

import java.util.List;

import com.app.model.Carro;
import com.app.repository.interfaces.ICarroRepository;

public class CarroService {
    private final ICarroRepository repository;

    public CarroService(ICarroRepository repository) {
        this.repository = repository;
    }

    public List<Carro> findAll() {
        return repository.findAll();
    }

    public Carro findById(int id) {
        return repository.findById(id);
    }

    public void insert(Carro carro) {
        repository.insert(carro);
    }

    public void update(int id, Carro carroAtualizado) {
        Carro carroExistente = repository.findById(id);
        if (carroExistente == null) {
            throw new RuntimeException("Carro não encontrado");
        }
        Carro carroParaAtualizar = mergeCarro(carroExistente, carroAtualizado);
        repository.update(carroParaAtualizar);
    }

    public void delete(int id) {
        if (repository.findById(id) == null) {
            throw new RuntimeException("Carro não encontrado");
        }
        repository.delete(id);
    }

    private Carro mergeCarro(Carro carroExistente, Carro carroAtualizado) {
        if (carroAtualizado.getModelo() != null) {
            carroExistente.setModelo(carroAtualizado.getModelo());
        }
        if (carroAtualizado.getFabricante() != null) {
            carroExistente.setFabricante(carroAtualizado.getFabricante());
        }
        if (carroAtualizado.getAno() != 0) {
            carroExistente.setAno(carroAtualizado.getAno());
        }
        if (carroAtualizado.getPreco() != 0) {
            carroExistente.setPreco(carroAtualizado.getPreco());
        }
        if (carroAtualizado.getQuantidadePortas() != 0) {
            carroExistente.setQuantidadePortas(carroAtualizado.getQuantidadePortas());
        }
        if (carroAtualizado.getTipoCombustivel() != null) {
            carroExistente.setTipoCombustivel(carroAtualizado.getTipoCombustivel());
        }
        return carroExistente;
    }
}
