package co.edu.uniquindio.marketplace.model;

import java.io.Serializable;

public class Administrador extends Empleado implements Serializable {
    private static final long serialVersionUID = 1L;

    private String user;
    private String password;

    public Administrador(String user, String password){
        super(new Usuario(user, password));

    }
}
