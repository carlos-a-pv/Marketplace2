package co.edu.uniquindio.marketplace.controllers;


import co.edu.uniquindio.marketplace.exceptions.InicioSesionException;
import co.edu.uniquindio.marketplace.exceptions.VendedorException;
import co.edu.uniquindio.marketplace.model.Empleado;
import co.edu.uniquindio.marketplace.model.Marketplace;
import co.edu.uniquindio.marketplace.model.Vendedor;
import co.edu.uniquindio.marketplace.persistence.Persistencia;
import co.edu.uniquindio.marketplace.servicies.IModelFactoryService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ModelFactoryController implements IModelFactoryService {

    static Marketplace marketplace;

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
		cargarDatosDesdeArchivos();


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
        this.marketplace.getVendedores().add(new Vendedor("katherine", "verano", "123123","carrera","user2","1223"));

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
            e.printStackTrace();
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

    public Empleado autenticar(String user, String password) {
        Empleado empleado = null;
        try {
            empleado = getMarketplace().autenticar(user, password);
        } catch (InicioSesionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return empleado;
    }



}
