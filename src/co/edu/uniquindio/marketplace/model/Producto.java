package co.edu.uniquindio.marketplace.model;

public class Producto {
    private  String nombre;
    private String precio;
    private Categoria categoria;
    private Disponibilidad estado;

    public Producto(String nombre, String precio, Categoria categoria){
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.estado = Disponibilidad.DISPONIBLE;
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
}
