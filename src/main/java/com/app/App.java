package com.app;

import com.app.controller.MotoController;
import com.app.model.Moto;
import com.app.repository.MotoRepository;
import com.app.service.MotoService;

public class App 
{
    public static void main(String[] args) {
        MotoRepository repository = new MotoRepository();
        MotoService service = new MotoService(repository);
        MotoController controller = new MotoController(service);


        // controller.findAll().forEach(System.out::println);
        // System.out.println(repository.findById(1));
        Moto moto = new Moto(0, "MOTO" , "CB 500", "Honda", 2021, 40000.0, 600);
        repository.insert(moto);

    }
}
