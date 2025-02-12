package com.app.service;

import java.util.List;

import com.app.model.Carro;
import com.app.repository.interfaces.ICarroRepository;

public class CarroService {

    private ICarroRepository repository;

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

    public void update(int id, Carro carro) {
        Carro carroFinded = null;
        try {
            carroFinded = repository.findById(id);
        } catch (Exception e) {
            System.out.println("Carro não encontrado");
            e.printStackTrace();
        }
        Carro newCarro = new Carro(
            carroFinded.getId(),
            carro.getTipo() != null ? carro.getTipo() : carroFinded.getTipo(),
            carro.getModelo() != null ? carro.getModelo() : carroFinded.getModelo(),
            carro.getFabricante() != null ? carro.getFabricante() : carroFinded.getFabricante(),
            carro.getAno() != 0 ? carro.getAno() : carroFinded.getAno(),
            carro.getPreco() != 0 ? carro.getPreco() : carroFinded.getPreco(),
            carro.getQuantidadePortas() != 0 ? carro.getQuantidadePortas() : carroFinded.getQuantidadePortas(),
            carro.getTipoCombustivel() != null ? carro.getTipoCombustivel() : carroFinded.getTipoCombustivel()
        );
        repository.update(newCarro);
    }

    public void delete(int id) {
        if (repository.findById(id) == null) {
            throw new RuntimeException("Carro não encontrado");
        }

        repository.delete(id);
    }
}
