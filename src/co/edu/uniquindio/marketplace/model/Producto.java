package co.edu.uniquindio.marketplace.model;

import co.edu.uniquindio.marketplace.persistence.ArchivoUtil;
import co.edu.uniquindio.marketplace.persistence.Persistencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;
    private  String nombre;
    private String nombreVendedor;
    private String precio;
    private Categoria categoria;
    private Disponibilidad estado;
    private String idProducto;
    private String idVendedor;
    private ArrayList<Comentario> comentarios;
    private int likes;
    private String fechaPublicacion;


    public Producto(){}
    public Producto(String nombre, String precio, Categoria categoria ){
        cargarFechaSistema();
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.estado = Disponibilidad.DISPONIBLE;
        this.idProducto = generarId();
        this.fechaPublicacion = cargarFechaSistema();
        this.comentarios = new ArrayList<>();
        this.comentarios.add(new Comentario("hoy", "yo", "hola, esto es una prueba"));
        this.likes = 0;
    }

    private String generarId() {
        String numeroID = "";
        for (int i = 0; i < 4; i++) {
            int n =  (int) Math.round(Math.random()*10);
            numeroID += String.valueOf(n);
        }
        return numeroID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Disponibilidad getEstado() {
        return estado;
    }

    public void setDisponibilidad(Disponibilidad estado) {
        this.estado = estado;
    }

    public String getIdProducto() {
        return this.idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(String idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getNombreVendedor() {
        return nombreVendedor;
    }

    public void setNombreVendedor(String nombreVendedor) {
        this.nombreVendedor = nombreVendedor;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes += likes;
    }

    @Override
    public String toString() {
        return idProducto+","+nombre+","+precio+","+categoria+","+estado+","+idProducto+","+fechaPublicacion;
    }

    public ArrayList<Comentario> getComentarios() {
        return comentarios;
    }

    private String cargarFechaSistema() {
        String fechaSistema = "";
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

        //		fecha_Actual+= año+"-"+mesN+"-"+ diaN;
        fechaSistema = año+"-"+mesN+"-"+diaN+"-"+hora+":"+minuto;

        return fechaSistema;
        //fechaSistema = año+"-"+mesN+"-"+diaN;
        //		horaFechaSistema = hora+"-"+minuto;
    }

    public void setEstado(Disponibilidad estado) {
        this.estado = estado;
    }

    public void setComentarios(ArrayList<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
}
