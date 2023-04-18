package co.edu.uniquindio.marketplace.model;

import co.edu.uniquindio.marketplace.exceptions.InicioSesionException;
import co.edu.uniquindio.marketplace.exceptions.VendedorException;

import java.util.ArrayList;
import java.util.List;

public class Marketplace {
    private Administrador admin;
    private Vendedor vendedorSeleccionado;
    private ArrayList<Vendedor> vendedores;

    public Marketplace(){
        vendedores = new ArrayList<>();
        admin = new Administrador("admin", "admin123");

    }
    public Vendedor crearEmpleado(String nombre, String apellido, String cedula, String direccion, String user, String password) throws VendedorException {
        Vendedor nuevoVendedor = null;
        //1. verificar si existe
        boolean vendedorExiste = verificarVendedorExistente(cedula);
        if(vendedorExiste){
            throw new VendedorException("El vendedor con cedula: "+cedula+" ya existe");
        }else{
            nuevoVendedor = new Vendedor(nombre, apellido, cedula, direccion, user, password);
            vendedores.add(nuevoVendedor);
        }
        return nuevoVendedor;
    }

    public Boolean eliminarVendedor(String cedula) throws VendedorException {
        Empleado empleado = null;
        boolean flagExiste = false;
        empleado = obtenerEmpleado(cedula);
        if(empleado == null)
            throw new VendedorException("El empleado a eliminar no existe");
        else{
            vendedores.remove(empleado);
            flagExiste = true;
        }
        return flagExiste;
    }
    public boolean verificarVendedorExistente(String cedula) {
        Empleado empleado = null;
        empleado = obtenerEmpleado(cedula);
        if(empleado == null)
            return false;
        else
            return true;
    }

    public Empleado obtenerEmpleado(String cedula) {
        Vendedor vendedorEncontrado = null;
        for (Vendedor vendedor : getVendedores()) {
            if(vendedor.getCedula().equalsIgnoreCase(cedula)){
                vendedorEncontrado = vendedor;
                break;
            }
        }
        return vendedorEncontrado;
    }
    public Empleado autenticar (String user, String password) throws InicioSesionException {
        Usuario usuarioAValidar = new Usuario(user, password);

        Vendedor vendedor= vendedores.stream()
                .filter( (vende)-> vende.getUser().getNombre().equals(user) && vende.getUser().getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (user.equals(admin.getUser().getNombre()) && password.equals(admin.getUser().getPassword())){
            return admin;
        } else if (vendedor!=null) {
            return vendedor;
        }
        else {
            throw new InicioSesionException("El empleado con usuario: "+user+" no existe");
        }

    }


    public ArrayList<Vendedor> getVendedores() {
        return this.vendedores;
    }

    public void setVendedorSeleccionado(Vendedor vendedor) {
        this.vendedorSeleccionado = vendedor;
    }
    public Vendedor getVendedorSeleccionado(){
        return this.vendedorSeleccionado;
    }

    public boolean actualizarEmpleado(String cedulaActual, String nombre, String apellido, String cedula, String direccion, String user, String password) throws VendedorException {
        Vendedor vendedor = null;
        boolean flagExiste;
        vendedor = obtenerVendedor(cedulaActual);
        if(vendedor == null)
            throw new VendedorException("El empleado a actualizar no existe");
        else{
            vendedor.setNombre(nombre);
            vendedor.setApellido(apellido);
            vendedor.setCedula(cedula);
            vendedor.setDireccion(direccion);
            vendedor.getUser().setNombre(user);
            vendedor.setContra(password);
            flagExiste = true;

        }
        return flagExiste;
    }


    public Vendedor obtenerVendedor(String cedula) {
        Vendedor vendedorEncontrado = null;
        for (Vendedor vendedor : vendedores) {
            if(vendedor.getCedula().equalsIgnoreCase(cedula)){
                vendedorEncontrado = vendedor;
                break;
            }
        }
        return vendedorEncontrado;
    }

    public ArrayList<Vendedor> obtenerVendedores() {
        return getVendedores();
    }
}
