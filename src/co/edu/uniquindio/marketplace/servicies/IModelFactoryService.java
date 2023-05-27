package co.edu.uniquindio.marketplace.servicies;
import java.util.ArrayList;

import co.edu.uniquindio.marketplace.model.*;

public interface IModelFactoryService {


    public Vendedor crearVendedor(String nombre, String apellido, String cedula, String direccion, String user, String password);

    public boolean eliminarVendedor(String cedula);

    boolean actualizarVendedor(String cedulaActual, String nombre, String apellido, String cedula, String direccion, String user, String password);
    public ArrayList<Vendedor> obtenerVendedores();

    Producto crearProducto(String nombre, String precio, Categoria categoria);

    Solicitud aceptarSolicitud(Solicitud solicitudSeleccionada);

    boolean buscarVendedorAliado(Vendedor vendedorLogeado, Vendedor vendedorSeleccionado);

    //public Boolean eliminarEmpleado(String cedula);
    //public Empleado obtenerEmpleado(String cedula);
    //public ArrayList<Empleado> obtenerEmpleados();
    //boolean actualizarEmpleado(String cedulaActual, String nombre, String apellido, String cedula, String fechaNacimiento);



}
