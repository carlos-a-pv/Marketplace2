package co.edu.uniquindio.marketplace.model;

public class Administrador extends Empleado {
    private String user;
    private String password;

    public Administrador(String user, String password){
        super(new Usuario(user, password));

    }

}
