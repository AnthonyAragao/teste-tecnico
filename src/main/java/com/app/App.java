package com.app;

import com.app.controller.MotoController;
import com.app.model.Moto;
import com.app.repository.MotoRepository;

public class App 
{
    public static void main(String[] args) {
        MotoRepository repository = new MotoRepository();
        MotoController controller = new MotoController();

        // Moto moto = new Moto("MOTO", "XRE 190", "Honda", 2025, 30000.0, 190);
        // repository.insert(moto);
        Moto moto = repository.findById(2);

        moto.setModelo("XRE 300");
        moto.setAno(2023);
        moto.setPreco(35000.0);
        moto.setCilindrada(300);
        repository.update(moto);

        controller.index().forEach(System.out::println);
    }
}
