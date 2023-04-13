package co.edu.uniquindio.marketplace.model;

public class Empleado {
    private Usuario user;

    public Empleado(Usuario user){
        this.user = user;
    }

    public Usuario getUser(){
        return this.user;
    }
}
