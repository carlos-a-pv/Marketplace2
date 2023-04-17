package co.edu.uniquindio.marketplace.servicies;
import java.util.ArrayList;

import co.edu.uniquindio.marketplace.model.Vendedor;

public interface IModelFactoryService {


    public Vendedor crearVendedor(String nombre, String apellido, String cedula, String direccion, String user, String password);

    public boolean eliminarVendedor(String cedula);

    boolean actualizarVendedor(String cedulaActual, String nombre, String apellido, String cedula, String direccion, String user, String password);
    public ArrayList<Vendedor> obtenerVendedores();

    //public Boolean eliminarEmpleado(String cedula);
    //public Empleado obtenerEmpleado(String cedula);
    //public ArrayList<Empleado> obtenerEmpleados();
    //boolean actualizarEmpleado(String cedulaActual, String nombre, String apellido, String cedula, String fechaNacimiento);



}
