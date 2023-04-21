package co.edu.uniquindio.marketplace.controllers;

import co.edu.uniquindio.marketplace.model.Categoria;
import co.edu.uniquindio.marketplace.model.Producto;
import co.edu.uniquindio.marketplace.model.Vendedor;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class CrudProductoViewController {
    ModelFactoryController modelFactoryController;

    public CrudProductoViewController(){}
    public CrudProductoViewController(ModelFactoryController modelFactoryController){
        this.modelFactoryController = modelFactoryController;

    }

    public ArrayList<Producto> obtenerProductos() {
        return modelFactoryController.obtenerProductos();
    }

    public Producto crearProducto(String nombre, String precio, Categoria categoria) {
        return modelFactoryController.crearProducto(nombre, precio, categoria);
    }
}