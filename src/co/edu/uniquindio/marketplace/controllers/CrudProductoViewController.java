package co.edu.uniquindio.marketplace.controllers;

import co.edu.uniquindio.marketplace.model.Producto;
import co.edu.uniquindio.marketplace.model.Vendedor;

import java.util.ArrayList;

public class CrudProductoViewController {
    ModelFactoryController modelFactoryController;
    public CrudProductoViewController(ModelFactoryController modelFactoryController){
        this.modelFactoryController = modelFactoryController;

    }

    public ArrayList<Producto> obtenerProductos() {
        return modelFactoryController.obtenerProductos();
    }
}
