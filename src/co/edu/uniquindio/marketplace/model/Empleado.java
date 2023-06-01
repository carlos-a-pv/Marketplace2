package co.edu.uniquindio.marketplace.model;

import java.io.Serializable;

public class Empleado implements Serializable {
    private static final long serialVersionUID = 1L;
    private Usuario user;
    public  Empleado(){}

    public Empleado(Usuario user){
        this.user = user;
    }

    public Usuario getUser(){
        return this.user;
    }

    public void setUser(Usuario user){
        this.user = user;
    }
}
