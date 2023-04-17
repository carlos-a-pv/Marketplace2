package co.edu.uniquindio.marketplace.controllers;

import co.edu.uniquindio.marketplace.model.Vendedor;

public class CrudVendedorViewController {
    ModelFactoryController modelFactoryController;

    public CrudVendedorViewController(ModelFactoryController modelFactoryController) {
        this.modelFactoryController = modelFactoryController;
    }

    public Vendedor crearVendedor(String nombre, String apellido, String cedula, String direccion, String user, String password){
        return modelFactoryController.crearVendedor(nombre, apellido, cedula, direccion, user, password);
    }
    public boolean eliminarVendedor(String nombre, String apellido, String cedula, String direccion, String user, String password){
        return modelFactoryController.eliminarVendedor(nombre, apellido, cedula, direccion, user, password);
    }
    public boolean actualizarVendedor(String nombre, String apellido, String cedula, String direccion, String user, String password){
        return modelFactoryController.actualizarVendedor(nombre, apellido, cedula, direccion, user, password);
    }

}
