package co.edu.uniquindio.marketplace.model;

import java.util.ArrayList;
import java.util.List;

public class Marketplace {
    private Administrador admin;
    private ArrayList<Vendedor> vendedores;

    public Marketplace(){
        vendedores = new ArrayList<>();
        admin = new Administrador("admin", "admin123");
    }
    /*public Vendedor crearEmpleado(String nombre, String apellido, String cedula, String fechaNacimiento) {

    }*/

    public Empleado autenticar (String email, String password){
        Vendedor vendedor= vendedores.stream()
                .filter( (vende)-> vende.getUser().getNombre().equals(email) && vende.getUser().getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (email.equals(admin.getUser().getNombre()) && password.equals(admin.getUser().getPassword())){
            return admin;
        } else if (vendedor!=null) {
            return vendedor;
        }
        else {
            return null;
        }

    }
}
