package com.app;

import com.app.controller.MotoController;
import com.app.model.Moto;
import com.app.repository.MotoRepository;

public class App 
{
    public static void main(String[] args) {
        MotoRepository repository = new MotoRepository();
        MotoController controller = new MotoController();


        // controller.findAll().forEach(System.out::println);
        // System.out.println(repository.findById(1));
        // Moto moto = new Moto("MOTO" , "XRE 190", "Honda", 2025, 30000.0, 190);
        // repository.insert(moto);
        controller.delete(9);

        controller.index().forEach(System.out::println);
    }
}
