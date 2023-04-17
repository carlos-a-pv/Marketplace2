package co.edu.uniquindio.marketplace.controllers;


import co.edu.uniquindio.marketplace.exceptions.InicioSesionException;
import co.edu.uniquindio.marketplace.exceptions.VendedorException;
import co.edu.uniquindio.marketplace.model.Empleado;
import co.edu.uniquindio.marketplace.model.Marketplace;
import co.edu.uniquindio.marketplace.model.Vendedor;
import co.edu.uniquindio.marketplace.servicies.IModelFactoryService;

import java.util.ArrayList;

public class ModelFactoryController implements IModelFactoryService {

    Marketplace marketplace;

    //------------------------------  Singleton ------------------------------------------------
    // Clase estatica oculta. Tan solo se instanciara el singleton una vez
    private static class SingletonHolder {
        // El constructor de Singleton puede ser llamado desde aquí al ser protected
    private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
    }

    // Método para obtener la instancia de nuestra clase
    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }

    public ModelFactoryController() {
        System.out.println("invocación clase singleton");
        inicializarDatos();
    }

    private void inicializarDatos() {
        marketplace = new Marketplace();
    }

    public Marketplace getMarketplace() {
        return marketplace;
    }


    public void setBanco(Marketplace marketplace) {
        this.marketplace = marketplace;
    }

    @Override
    public Vendedor crearVendedor(String nombre, String apellido, String cedula, String direccion, String user, String password) {
        Vendedor vendedor = null;
        try {
            vendedor = getMarketplace().crearEmpleado(nombre, apellido, cedula, direccion, user, password);
        } catch (VendedorException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return vendedor;
    }

    @Override
    public boolean eliminarVendedor(String nombre, String apellido, String cedula, String direccion, String user, String password) {
        return false;
    }

    @Override
    public boolean actualizarVendedor(String nombre, String apellido, String cedula, String direccion, String user, String password) {
        return false;
    }

    public Empleado autenticar(String user, String password) {
        Empleado empleado = null;
        try {
            empleado = getMarketplace().autenticar(user, password);
        } catch (InicioSesionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return empleado;
    }
/*
    @Override
    public Boolean eliminarEmpleado(String cedula) {
        boolean flagExiste = false;
        try {
            flagExiste = getBanco().eliminarEmpleado(cedula);
        } catch (EmpleadoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return flagExiste;
    }*/
/*
    @Override
    public Empleado obtenerEmpleado(String cedula) {
        // TODO Auto-generated method stub
        return null;
    }*/
/*
    @Override
    public ArrayList<Empleado> obtenerEmpleados() {
//		getBanco().getListaEmpleados();
        return getBanco().obtenerEmpleados();
    }

    @Override
    public boolean actualizarEmpleado(String cedulaActual, String nombre, String apellido, String cedula,
                                      String fechaNacimiento) {
        boolean flagExiste = false;
        try {
            flagExiste = getBanco().actualizarEmpleado(cedulaActual, nombre, apellido, cedula, fechaNacimiento);
        } catch (EmpleadoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return flagExiste;
    }

    @Override
    public boolean actualizarCliente(String cedula) {
        // TODO Auto-generated method stub
        return false;
    }*/


}
