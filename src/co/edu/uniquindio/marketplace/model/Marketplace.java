package co.edu.uniquindio.marketplace.model;

import co.edu.uniquindio.marketplace.exceptions.InicioSesionException;

import java.util.ArrayList;
import java.util.List;

public class Marketplace {
    private Administrador admin;
    private Vendedor vendedorSeleccionado;
    private ArrayList<Vendedor> vendedores;

    public Marketplace(){
        vendedores = new ArrayList<>();
        admin = new Administrador("admin", "admin123");
        vendedores.add(new Vendedor("carlos", "perez", "123123","calle","user","123"));
        vendedores.add(new Vendedor("katherine", "verano", "123123","carrera","user2","1223"));
    }
    public void crearEmpleado(String nombre, String apellido, String cedula, String direccion, String user, String password) {
        Vendedor vendedorNuevo = new Vendedor(nombre, apellido,cedula,direccion,user,password);
        vendedores.add(vendedorNuevo);
    }

    public Empleado autenticar (String user, String password) throws InicioSesionException {
        Usuario usuarioAValidar = new Usuario(user, password);

        Vendedor vendedor= vendedores.stream()
                .filter( (vende)-> vende.getUser().getNombre().equals(user) && vende.getUser().getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (user.equals(admin.getUser().getNombre()) && password.equals(admin.getUser().getPassword())){
            return admin;
        } else if (vendedor!=null) {
            return vendedor;
        }
        else {
            throw new InicioSesionException("El empleado con cedula: "+user+" no existe");
        }

    }


    public List<Vendedor> getVendedores() {
        return this.vendedores;
    }

    public void setVendedorSeleccionado(Vendedor vendedor) {
        this.vendedorSeleccionado = vendedor;
    }
    public Vendedor getVendedorSeleccionado(){
        return this.vendedorSeleccionado;
    }
}
