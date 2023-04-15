package co.edu.uniquindio.marketplace.model;

public class Vendedor extends Empleado{
    private String nombre;
    private String apellido;
    private String cedula;
    private String direccion;

     public Vendedor(String nombre, String apellido, String cedula, String direccion, String user, String password){
         super(new Usuario(user, password));
         this.nombre = nombre;
         this.apellido = apellido;
         this.cedula = cedula;
         this.direccion = direccion;
     }

}
