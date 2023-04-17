package co.edu.uniquindio.marketplace.controllers;

import co.edu.uniquindio.marketplace.model.Vendedor;

import java.util.ArrayList;

public class CrudVendedorViewController {
    ModelFactoryController modelFactoryController;

    public CrudVendedorViewController(ModelFactoryController modelFactoryController) {
        this.modelFactoryController = modelFactoryController;
    }
    public ArrayList<Vendedor> obtenerVendedores() {
        return modelFactoryController.obtenerVendedores();
    }

    public Vendedor crearVendedor(String nombre, String apellido, String cedula, String direccion, String user, String password){
        return modelFactoryController.crearVendedor(nombre, apellido, cedula, direccion, user, password);
    }
    public boolean eliminarVendedor(String cedula){
        return modelFactoryController.eliminarVendedor(cedula);
    }
    public boolean actualizarVendedor(String cedulaActual, String nombre, String apellido, String cedula,String direccion, String user, String password) {
        return modelFactoryController.actualizarVendedor(cedulaActual, nombre, apellido, cedula, direccion, user, password);
    }

}
