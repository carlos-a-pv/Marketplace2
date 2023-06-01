package co.edu.uniquindio.marketplace.model;

import co.edu.uniquindio.marketplace.exceptions.InicioSesionException;
import co.edu.uniquindio.marketplace.exceptions.VendedorException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Marketplace implements Serializable {

    private static final long serialVersionUID = 1L;
    private Administrador admin = new Administrador("admin", "admin123");
    private Vendedor vendedorSeleccionado = null;
    private Producto productoSeleccionado = new Producto();
    private ArrayList<Vendedor> vendedores = new ArrayList<>();
    private ArrayList<Producto> productos = new ArrayList<>();

    public Marketplace(){}

    public Marketplace(String nombre){

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
        Vendedor vendedor = vendedores.stream()
                .filter( (vende)-> vende.getUser().getNombre().equals(user) && vende.getContra().equals(password))
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

    public Producto getProductoSeleccionado() {
        return this.productoSeleccionado;
    }

    public void setProductoSeleccionado(String idProducto) {
        this.productoSeleccionado  = obtenerProductos().stream().filter(producto -> producto.getIdProducto().equals(idProducto)).findFirst().orElse(null);
        System.out.println(productoSeleccionado);
    }

    public ArrayList<Producto> obtenerProductos(){
        return llenarListaProducto();
    }
    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public Administrador getAdmin() {
        return admin;
    }

    public void setAdmin(Administrador admin) {
        this.admin = admin;
    }

    public void setProductoSeleccionado(Producto productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }

    public void setVendedores(ArrayList<Vendedor> vendedores) {
        this.vendedores = vendedores;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    private ArrayList<Producto> llenarListaProducto(){
       ArrayList<Producto> productosCargados = new ArrayList<>();
        this.vendedores.forEach(vendedor -> {
           productosCargados.addAll(vendedor.getProductos());
       });
        this.productos = productosCargados;
       return productosCargados;
    }

    public boolean eliminarProducto(String idProducto, Vendedor vendedorLogeado) {
        int cantidadProductos = llenarListaProducto().size();

        for (int i = 0; i < cantidadProductos; i++) {
            if(productos.get(i).getIdProducto().equals(idProducto)){
                System.out.println("SE HA ENCONTRADO DOS PRODUCTOS CON IGUAL ID");
                llenarListaProducto().remove(i);
                ArrayList<Producto> productorFiltrados = (ArrayList<Producto>) vendedorLogeado.getProductos().stream().filter(producto -> !producto.getIdProducto().equals(idProducto)).collect(Collectors.toList());
                vendedorLogeado.setProductos(productorFiltrados);
                return true;
            }
        }
        return false;
    }
}
