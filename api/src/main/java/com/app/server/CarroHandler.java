package com.app.server;

import com.app.controller.CarroController;
import com.app.model.Carro;

public class CarroHandler extends GenericHandler<Carro> {
    public CarroHandler(CarroController carroController) {
        super(carroController, Carro.class);
    }
}