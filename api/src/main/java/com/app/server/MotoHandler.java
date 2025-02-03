package com.app.server;

import com.app.controller.MotoController;
import com.app.model.Moto;

public class MotoHandler extends GenericHandler<Moto> {
    public MotoHandler(MotoController motoController) {
        super(motoController, Moto.class);
    }
}
