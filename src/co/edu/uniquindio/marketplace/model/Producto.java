package co.edu.uniquindio.marketplace.model;

public class Producto {
    private  String nombre;
    private String precio;
    private Categoria categoria;
    private Disponibilidad estado;
    private String idProducto;

    public Producto(String nombre, String precio, Categoria categoria ){
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.estado = Disponibilidad.DISPONIBLE;
        this.idProducto = generarId();
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
}
