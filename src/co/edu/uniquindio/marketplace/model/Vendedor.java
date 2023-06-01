package co.edu.uniquindio.marketplace.model;

import javax.sound.sampled.Port;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Vendedor extends Empleado implements Serializable {
    private static final long serialVersionUID = 1L;
    private  String contra;
    private String userName;
    private String nombre;
    private String apellido;
    private String cedula;
    private String direccion;
    private String descripcion = "";
    private ArrayList<Producto> productos;
    private ArrayList<Solicitud> solicitudesEnviadas;
    private ArrayList<Solicitud> solicitudesRecibidas;
    private ArrayList<Vendedor> vendedoresAliados;

    public Vendedor(){
        super(new Usuario("",""));
    }
    public Vendedor(String nombre, String apellido, String cedula, String direccion, String user, String password){
         super(new Usuario(user, password));
         Usuario userNew = new Usuario(user, password);
         this.nombre = nombre;
         this.apellido = apellido;
         this.cedula = cedula;
         this.direccion = direccion;
         this.contra = userNew.getPassword();
         this.userName = userNew.getNombre();
         this.solicitudesEnviadas = new ArrayList<>();
         this.solicitudesRecibidas = new ArrayList<>();
         this.productos = new ArrayList<>();
         this.vendedoresAliados = new ArrayList<>();
     }

     public Producto crearProducto(String nombre, String precio, Categoria categoria ){
        Producto x = new Producto(nombre, precio, categoria);
        getProductos().add(x);
        return x;
     }

     public String getContra(){
         return this.contra;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public String getDireccion() {
        return direccion;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public ArrayList<Producto> getProductos() {
        return this.productos;
    }

    public String getDescripcion() {
        if(this.descripcion.isEmpty()){
            return "Añada una descipcion a su perfil...";
        }else{
            return descripcion;
        }
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ArrayList<Solicitud> getSolicitudesEnviadas() {
        return solicitudesEnviadas;
    }

    public ArrayList<Solicitud> getSolicitudesRecibidas() {
        return solicitudesRecibidas;
    }

    public Solicitud aceptarSolicitud(Solicitud solicitud) {
        Solicitud solicitudEncontrada = getSolicitudesRecibidas().stream().filter((soli)-> soli.equals(solicitud)).findFirst().orElse(null);
        if(solicitudEncontrada != null){
            solicitudEncontrada.setEstado(Estado.ACEPTADA);
            return solicitudEncontrada;
        }
        return null;
    }

    public ArrayList<Vendedor> getVendedoresAliados() {
        return vendedoresAliados;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    public void setSolicitudesEnviadas(ArrayList<Solicitud> solicitudesEnviadas) {
        this.solicitudesEnviadas = solicitudesEnviadas;
    }

    public void setSolicitudesRecibidas(ArrayList<Solicitud> solicitudesRecibidas) {
        this.solicitudesRecibidas = solicitudesRecibidas;
    }

    public void setVendedoresAliados(ArrayList<Vendedor> vendedoresAliados) {
        this.vendedoresAliados = vendedoresAliados;
    }


    public String getUsername() {
        return userName;
    }

    public void setUserName(String user) {
        this.userName = user;
    }

    public void eliminarAmigo(Vendedor amigoSeleccionado) {
        vendedoresAliados = (ArrayList<Vendedor>) vendedoresAliados.stream().filter(vendedor -> !vendedor.equals(amigoSeleccionado)).collect(Collectors.toList());
        ArrayList<Vendedor> p = (ArrayList<Vendedor>) amigoSeleccionado.getVendedoresAliados().stream().filter(vendedor -> !vendedor.equals(amigoSeleccionado)).collect(Collectors.toList());
        amigoSeleccionado.setVendedoresAliados(p);
    }
}
