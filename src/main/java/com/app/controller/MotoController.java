package com.app.controller;

import java.util.List;
import com.app.model.Moto;
import com.app.service.MotoService;

public class MotoController
{
    private MotoService service;

    public MotoController(MotoService service) 
    {
        this.service = service;
    }

    public List<Moto> findAll() 
    {
        return service.findAll();
    }
}
