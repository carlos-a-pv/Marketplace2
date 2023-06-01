package co.edu.uniquindio.marketplace.model;

import java.io.Serializable;

public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre;
    private String password;

    public Usuario(){}

    public Usuario(String nombre, String password){
        this.nombre = nombre;
        this.password = password;
    }

    public String getNombre(){
        return this.nombre;
    }
    public String getPassword(){
        return this.password;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
