package com.app.controller;

import java.util.List;

import com.app.model.Carro;
import com.app.repository.CarroRepository;
import com.app.service.CarroService;

public class CarroController implements GenericController<Carro> {
    private final CarroService carroService;

    public CarroController() {
        this.carroService = new CarroService(new CarroRepository());
    }

    public List<Carro> index() {
        return carroService.findAll();
    }

    public Carro show(int id) {
        return carroService.findById(id);
    }

    public void create(Carro carro) {
        carroService.insert(carro);
    }

    public void update(int id,Carro carro) {
        carroService.update(id,carro);
    }

    public void delete(int id) {
        carroService.delete(id);
    }

}
