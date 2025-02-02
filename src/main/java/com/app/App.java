package com.app;

import com.app.model.Carro;
import com.app.model.Moto;
import com.app.model.TipoCombustivel;
import com.app.repository.CarroRepository;
import com.app.repository.MotoRepository;

public class App 
{
    public static void main(String[] args) {
        MotoRepository repository = new MotoRepository();

        // Moto moto = new Moto("MOTO", "XRE 190", "Honda", 2025, 30000.0, 190);
        // repository.insert(moto);

        // Moto moto = repository.findById(1);
        // moto.setModelo("XRE 300");
        // moto.setAno(2023);
        // moto.setPreco(35000.0);
        // moto.setCilindrada(300);
        // repository.update(moto);

        // controller.index().forEach(System.out::println);

        CarroRepository carroRepository = new CarroRepository();
        Carro carro = new Carro("CARRO", "Gol", "Volkswagen", 2021, 50000.0, 4, TipoCombustivel.DIESEL);
        carroRepository.insert(carro);
        
        // Carro carro = carroRepository.findById(2);
        // carro.setPreco(55000.0);
        // carro.setAno(2025);
        // carro.setPreco(70000.0);
        // carro.setTipoCombustivel(TipoCombustivel.FLEX);
        // carroRepository.update(carro);
        // carroRepository.delete(1);
        carroRepository.findAll().forEach(System.out::println);
    }
}
