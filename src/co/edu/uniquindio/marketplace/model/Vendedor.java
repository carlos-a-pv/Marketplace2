package co.edu.uniquindio.marketplace.model;

public class Vendedor extends Empleado{
    private  String contra;
    private String nombre;
    private String apellido;
    private String cedula;
    private String direccion;

     public Vendedor(String nombre, String apellido, String cedula, String direccion, String user, String password){
         super(new Usuario(user, password));
         Usuario userNew = new Usuario(user, password);
         this.nombre = nombre;
         this.apellido = apellido;
         this.cedula = cedula;
         this.direccion = direccion;
         contra = userNew.getPassword();
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
        return "Vendedor{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", cedula='" + cedula + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
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
}
