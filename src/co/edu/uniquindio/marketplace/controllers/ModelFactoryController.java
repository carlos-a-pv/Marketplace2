package co.edu.uniquindio.marketplace.controllers;


import co.edu.uniquindio.marketplace.exceptions.InicioSesionException;
import co.edu.uniquindio.marketplace.exceptions.VendedorException;
import co.edu.uniquindio.marketplace.model.*;
import co.edu.uniquindio.marketplace.persistence.Persistencia;
import co.edu.uniquindio.marketplace.servicies.IModelFactoryService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ModelFactoryController implements IModelFactoryService {

    static Marketplace marketplace;
    static Vendedor vendedorLogeado;

    //------------------------------  Singleton ------------------------------------------------
    // Clase estatica oculta. Tan solo se instanciara el singleton una vez
    private static class SingletonHolder {
        // El constructor de Singleton puede ser llamado desde aquí al ser protected
    private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
    }

    // Método para obtener la instancia de nuestra clase
    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }

    public ModelFactoryController() {
        //1. inicializar datos y luego guardarlo en archivos
        //inicializarSalvarDatos();


        //2. Cargar los datos de los archivos
		//cargarDatosDesdeArchivos();


        //3. Guardar y Cargar el recurso serializable binario
//		guardarResourceBinario();
//		cargarResourceBinario();


        //4. Guardar y Cargar el recurso serializable XML
//		guardarResourceXML();
//		cargarResourceXML();

        //Siempre se debe verificar si la raiz del recurso es null

        if(marketplace == null){
            inicializarDatos();
            //guardarResourceXML();
        }

        inicializarSalvarDatos();
    }

    private static void inicializarSalvarDatos() {
        //inicializarDatos();
        try {
            Persistencia.guardarVendedores(getMarketplace().getVendedores());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void cargarDatosDesdeArchivos() {
        this.marketplace = new Marketplace();
        try {
            ArrayList<Vendedor> listaVendedores =new ArrayList<Vendedor>();
            listaVendedores = Persistencia.cargarVendedores();
            if(listaVendedores != null){
                getMarketplace().getVendedores().addAll(listaVendedores);
            }else{
                marketplace = null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void inicializarDatos() {
        this.marketplace = new Marketplace();
        this.marketplace.getVendedores().add(new Vendedor("carlos", "perez", "1004827192","calle","user","123"));
        this.marketplace.getVendedores().get(0).getProductos().add(new Producto("carro","100", Categoria.VEHICULOS));
        this.marketplace.getVendedores().add(new Vendedor("katherine", "verano", "123123","carrera","user2","1223"));
        this.marketplace.getVendedores().get(1).getProductos().add(new Producto("moto","2500", Categoria.VEHICULOS));
    }
    public void registrarAccionesSistema(String mensaje, int nivel, String accion) {
        Persistencia.guardaRegistroLog(mensaje, nivel, accion);
    }
    public static Marketplace getMarketplace() {
        return marketplace;
    }

    public void setBanco(Marketplace marketplace) {
        this.marketplace = marketplace;
    }

    @Override
    public Vendedor crearVendedor(String nombre, String apellido, String cedula, String direccion, String user, String password) {
        Vendedor vendedor = null;
        try {
            vendedor = getMarketplace().crearEmpleado(nombre, apellido, cedula, direccion, user, password);
            inicializarSalvarDatos();
        } catch (VendedorException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            registrarAccionesSistema("El usuario ya existe en el sistema", 2, "Creacion de vendedor:");
        }
        inicializarSalvarDatos();
        return vendedor;
    }

    @Override
    public boolean eliminarVendedor(String cedula) {
        boolean flagExiste = false;
        try {
            flagExiste = getMarketplace().eliminarVendedor(cedula);
        } catch (VendedorException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        inicializarSalvarDatos();
        return flagExiste;
    }

    @Override
    public boolean actualizarVendedor(String cedulaActual, String nombre, String apellido, String cedula, String direccion, String user, String password) {
        boolean flagExiste = false;
        try {
            flagExiste = getMarketplace().actualizarEmpleado(cedulaActual, nombre, apellido, cedula, direccion, user, password);
        } catch (VendedorException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        inicializarSalvarDatos();
        return flagExiste;
    }

    @Override
    public ArrayList<Vendedor> obtenerVendedores() {
        return getMarketplace().obtenerVendedores();
    }

    @Override
    public Producto crearProducto(String nombre, String precio, Categoria categoria) {
        return vendedorLogeado.crearProducto(nombre, precio, categoria);
    }

    public Empleado autenticar(String user, String password) {
        Empleado empleado = null;
        try {
            empleado = getMarketplace().autenticar(user, password);
        } catch (InicioSesionException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            registrarAccionesSistema("El usuario no existe en el sistema", 2, "inicio de sesion");
        }
        if(empleado != null){
            vendedorLogeado = (Vendedor) empleado;
        }
        return empleado;
    }

    public  ArrayList<Producto> obtenerProductos(){
        return getMarketplace().getVendedores().get(0).getProductos();
    }

    public static Vendedor getVendedorLogeado() {
        return vendedorLogeado;
    }
}
