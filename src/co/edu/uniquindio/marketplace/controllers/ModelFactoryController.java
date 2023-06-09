package co.edu.uniquindio.marketplace.controllers;


import co.edu.uniquindio.marketplace.exceptions.InicioSesionException;
import co.edu.uniquindio.marketplace.exceptions.VendedorException;
import co.edu.uniquindio.marketplace.model.*;
import co.edu.uniquindio.marketplace.persistence.Persistencia;
import co.edu.uniquindio.marketplace.servicies.IModelFactoryService;
import javafx.scene.control.Alert;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ModelFactoryController implements IModelFactoryService, Runnable {

    static Marketplace marketplace;
    static Vendedor vendedorLogeado;
    static Producto productoSeleccionado;
    static Vendedor vendedorSeleccionado;

    Thread hiloGuardarXML;


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
		 //cargarVendedoresDesdeArchivos();
         //cargarProdcutoDesdeArchivos();


        //3. Guardar y Cargar el recurso serializable binario
//		guardarResourceBinario();
//		cargarResourceBinario();


        //4. Guardar y Cargar el recurso serializable XML
		cargarResourceXML();
		guardarResourceXML();

        //Siempre se debe verificar si la raiz del recurso es null

        if(marketplace == null){
            System.out.println("NO LEYÓ EL ARCHIVO XML !!!!");
            inicializarDatos();
            //guardarResourceXML();
        }

        //inicializarSalvarDatos();
    }
    private void cargarResourceXML() {
        marketplace = Persistencia.cargarRecursoBancoXML();

    }

    public void guardarResourceXML() {
        hiloGuardarXML = new Thread();
        hiloGuardarXML.start();

    }

    public void cargarProdcutoDesdeArchivos() {
        try{
           getMarketplace().getProductos().addAll(Persistencia.cargarProductos());
        }catch (IOException e){

        }
    }


    private static void inicializarSalvarDatos() {
        //inicializarDatos();
        try {
            Persistencia.guardarVendedores(getMarketplace().getVendedores());
            Persistencia.guardarRecursoBancoXML(getMarketplace());
            if(vendedorLogeado != null){
                Persistencia.guardarProductos(vendedorLogeado.getProductos(), vendedorLogeado);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void cargarVendedoresDesdeArchivos() {
        this.marketplace = new Marketplace("");
        try {
            ArrayList<Vendedor> listaVendedores;
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
        this.marketplace = new Marketplace("");
        this.marketplace.getVendedores().add(new Vendedor("carlos", "perez", "1004827192","calle","user","123"));
        this.marketplace.getVendedores().get(0).getProductos().add(new Producto("carro","100", Categoria.VEHICULOS));
        this.marketplace.getVendedores().add(new Vendedor("katherine", "verano", "123123","carrera","user2","1223"));
        this.marketplace.getVendedores().get(1).getProductos().add(new Producto("moto","2500", Categoria.VEHICULOS));
        //vendedorLogeado= marketplace.getVendedores().get(0);


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
    public  ArrayList<Producto> obtenerProductos(){
        return getMarketplace().obtenerProductos();
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
        if(empleado instanceof Administrador){
            return empleado;
        }else if(empleado != null){
            vendedorLogeado = (Vendedor) empleado;
        }
        return empleado;
    }


    @Override
    public Producto crearProducto(String nombre, String precio, Categoria categoria) {
        Producto productoCreado =  vendedorLogeado.crearProducto(nombre, precio, categoria);
        inicializarSalvarDatos();
        return productoCreado;
    }

    @Override
    public Solicitud aceptarSolicitud(Solicitud solicitudSeleccionada) {
        return vendedorLogeado.aceptarSolicitud(solicitudSeleccionada);
    }

    @Override
    public boolean buscarVendedorAliado(Vendedor vendedorLogeado, Vendedor vendedorSeleccionado) {
        Vendedor vendedorEncontrado = vendedorLogeado.getVendedoresAliados().stream().filter((vendedor)-> vendedor.equals(vendedorSeleccionado)).findFirst().orElse(null);

        if(vendedorEncontrado != null){
            return true;
        }
        return false;
    }

    @Override
    public boolean eliminarProducto(String idProducto){
        return getMarketplace().eliminarProducto(idProducto, getVendedorLogeado());
    }



    public void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {

        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }

    public static Vendedor getVendedorSeleccionado() {
        return vendedorSeleccionado;
    }

    public static void setVendedorSeleccionado(Vendedor vende) {
        vendedorSeleccionado = vende;
    }
    public void vendedorLogiado(Vendedor empleadoIniciado) {
        vendedorLogeado = empleadoIniciado;
    }
    public static Vendedor getVendedorLogeado() {
        return vendedorLogeado;
    }

    @Override
    public void run() {
        if(Thread.currentThread()==hiloGuardarXML){
            Persistencia.guardarRecursoBancoXML(marketplace);
        }
    }
}

