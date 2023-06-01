package co.edu.uniquindio.marketplace.model;

import co.edu.uniquindio.marketplace.persistence.ArchivoUtil;

import javax.sound.sampled.Port;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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
    private ArrayList<Chat> chats = new ArrayList<>();

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
            Chat chat = new Chat();
            //chat.setEmisor();
            //chat.setReceptor();
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

    public Mensaje enviarMensaje(String mensaje, String cedula, Vendedor vendedorLogeado){
        Chat chatEncontrado = new Chat();
        Mensaje mensajeNuevo = new Mensaje();
        Vendedor receptor = vendedoresAliados.stream().filter(vendedor -> vendedor.getCedula().equals(cedula)).findFirst().orElse(null);
        if(receptor != null){
            chatEncontrado = vendedorLogeado.getChats().stream().filter(chat -> chat.getVendedor1().equals(vendedorLogeado) && chat.getVendedor2().getCedula().equals(cedula)).findFirst().orElse(null);
            mensajeNuevo.setContenido(mensaje);
            mensajeNuevo.setBandera(true);
            mensajeNuevo.setFecha(cargarFechaSistema());
            chatEncontrado.getMensajes().add(mensajeNuevo);

        }
        return mensajeNuevo;
    }

    public ArrayList<Chat> getChats() {
        return chats;
    }

    public void setChats(ArrayList<Chat> chats) {
        this.chats = chats;
    }

    private String cargarFechaSistema() {

        String diaN = "";
        String mesN = "";
        String añoN = "";

        Calendar cal1 = Calendar.getInstance();

        int  dia = cal1.get(Calendar.DATE);
        int mes = cal1.get(Calendar.MONTH)+1;
        int año = cal1.get(Calendar.YEAR);
        int hora = cal1.get(Calendar.HOUR);
        int minuto = cal1.get(Calendar.MINUTE);


        if(dia < 10){
            diaN+="0"+dia;
        }
        else{
            diaN+=""+dia;
        }
        if(mes < 10){
            mesN+="0"+mes;
        }
        else{
            mesN+=""+mes;
        }

        return hora+":"+minuto+" "+año+"-"+mesN+"-"+diaN;

    }

    public Chat encontrarChat(String cedula, Vendedor vendedorLogeado) {
        return vendedorLogeado.getChats().stream().filter(chat -> chat.getVendedor1().equals(vendedorLogeado) && chat.getVendedor2().getCedula().equals(cedula)).findFirst().orElse(null);
    }
}
