package com.app.controller;

import java.util.List;
import com.app.model.Moto;
import com.app.repository.MotoRepository;
import com.app.service.MotoService;

public class MotoController implements GenericController<Moto> {
    private final MotoService motoService;

    public MotoController() {
        this.motoService = new MotoService(new MotoRepository());
    }

    public List<Moto> index() {
        return motoService.findAll();
    }

    public Moto show(int id) {
        return motoService.findById(id);
    }

    public void create(Moto moto) {
        motoService.insert(moto);
    }

    public void update(int id,Moto moto) {
        motoService.update(id,moto);
    }

    public void delete(int id) {
        motoService.delete(id);
    }
}
