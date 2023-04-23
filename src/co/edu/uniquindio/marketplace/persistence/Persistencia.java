package co.edu.uniquindio.marketplace.persistence;


import co.edu.uniquindio.marketplace.model.*;

import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
public class Persistencia {

    public static final String RUTA_ARCHIVO_VENDEDORES = "C://td//persistencia//archivos/archivoVendedores.txt";
    public static final String RUTA_ARCHIVO_LOG = "C://td//persistencia//log//BancoLog.txt";
    public static final String RUTA_ARCHIVO_PRODUCTO ="C://td//Persistencia//Archivos/archivoProductos";
    //public static final String RUTA_ARCHIVO_MODELO_BANCO_BINARIO = "C://td//persistencia//model.dat";
    //public static final String RUTA_ARCHIVO_MODELO_BANCO_XML = "C://td//persistencia//model.xml";




    public static void cargarDatosArchivos(Marketplace marketplace) throws FileNotFoundException, IOException {


        //cargar archivo de vendedores
        ArrayList<Vendedor> vendedoresCargador = cargarVendedores();
        ArrayList<Producto> productosCargados = cargarProductos();

        if(vendedoresCargador.size() > 0)
            marketplace.getVendedores().addAll(vendedoresCargador);

        if (productosCargados.size() > 0)
            marketplace.getVendedorSeleccionado().getProductos().addAll(productosCargados);

        //cargar archivo objetos(productos)

        //cargar archivo empleados

        //cargar archivo prestamo/compras

    }

    /**
     * Guarda en un archivo de texto todos la información de las personas almacenadas en el ArrayList
     * @param //objetos
     * @param //ruta
     * @throws IOException
     */
    public static void guardarVendedores(ArrayList<Vendedor> listaVendedores) throws IOException {
        String contenido = "";

        for(Vendedor vendedor:listaVendedores)
        {
            contenido+= vendedor.getNombre()+"@@"+vendedor.getApellido()+"@@"+vendedor.getCedula()+"@@"+vendedor.getDireccion()
                    +"@@"+vendedor.getUser().getNombre()+"@@"+vendedor.getContra()+"\n";
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_VENDEDORES, contenido, false);

    }
    public static void guardarProductos(ArrayList<Producto>listProducto) throws IOException{
        String contenido ="";

        for (Producto producto:listProducto)
        {
            contenido+= producto.getNombre()+"@@"+producto.getPrecio()+"@@"+producto.getCategoria()+"@@"+producto.getEstado()+"\n";
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_PRODUCTO,contenido,false);
    }

//	----------------------LOADS------------------------

    /**
     *
     * @param //tipoPersona
     * @param //ruta
     * @return un Arraylist de personas con los datos obtenidos del archivo de texto indicado
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static ArrayList<Vendedor> cargarVendedores() throws FileNotFoundException, IOException {
        ArrayList<Vendedor> vendedores =new ArrayList<Vendedor>();
        ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_VENDEDORES);
        if(contenido ==null){
            return null;
        }
        String linea="";

        for (int i = 0; i < contenido.size(); i++)
        {
            linea = contenido.get(i);//juan,arias,125454,Armenia,uni1@,12454,125444
            Vendedor vendedor = new Vendedor("","","","","","");
            vendedor.setNombre(linea.split("@@")[0]);
            vendedor.setApellido(linea.split("@@")[1]);
            vendedor.setCedula(linea.split("@@")[2]);
            vendedor.setDireccion(linea.split("@@")[3]);
            vendedor.getUser().setNombre(linea.split("@@")[4]);
            vendedor.setContra(linea.split("@@")[5]);
            vendedores.add(vendedor);
        }
        return vendedores;
    }
    public static ArrayList<Producto> cargarProductos()throws FileNotFoundException,IOException{
        ArrayList<Producto> productos = new ArrayList<>();
        ArrayList <String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_PRODUCTO);
        if (contenido == null){
            return null;
        }
        String linea = "";

        for (int i = 0;i<contenido.size();i++){

            linea = contenido.get(i);
            Producto producto = new Producto("","",Categoria.valueOf(""),Disponibilidad.valueOf(""));
            producto.setNombre(linea.split("@@")[0]);
            producto.setPrecio(linea.split("@@")[1]);
            producto.setCategoria(Categoria.valueOf(linea.split("@@")[2]));
            producto.setDisponibilidad(Disponibilidad.valueOf(linea.split("@@")[3]));
            productos.add(producto);
         }
            return  productos;
    }


    public static void guardaRegistroLog(String mensajeLog, int nivel, String accion) {
        ArchivoUtil.guardarRegistroLog(mensajeLog, nivel, accion, RUTA_ARCHIVO_LOG);
    }

//	----------------------SAVES------------------------

    /**
     * Guarda en un archivo de texto todos la información de las personas almacenadas en el ArrayList
     * @param //objetos
     * @param //ruta
     * @throws IOException
     */
    /*
    public static void guardarObjetos(ArrayList<Cliente> listaClientes, String ruta) throws IOException  {
        String contenido = "";

        for(Cliente clienteAux:listaClientes) {
            contenido+= clienteAux.getNombre()+","+clienteAux.getApellido()+","+clienteAux.getCedula()+clienteAux.getDireccion()
                    +","+clienteAux.getCorreo()+","+clienteAux.getFechaNacimiento()+","+clienteAux.getTelefono()+"\n";
        }
        ArchivoUtil.guardarArchivo(ruta, contenido, true);
    }*/





    //------------------------------------SERIALIZACIÓN  y XML

    /*
    public static Marketplace cargarRecursoBancoBinario() {

        Marketplace marketplace = null;

        try {
            marketplace = (Marketplace) ArchivoUtil.cargarRecursoSerializado(RUTA_ARCHIVO_MODELO_BANCO_BINARIO);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return marketplace;
    }*/

    /*
    public static void guardarRecursoBancoBinario(Marketplace marketplace) {

        try {
            ArchivoUtil.salvarRecursoSerializado(RUTA_ARCHIVO_MODELO_BANCO_BINARIO, marketplace);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/

    /*
    public static Marketplace cargarRecursoBancoXML() {

        Marketplace marketplace = null;

        try {
            marketplace = (Marketplace)ArchivoUtil.cargarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_BANCO_XML);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return marketplace;

    }*/


    /*
    public static void guardarRecursoBancoXML(Marketplace marketplace) {

        try {
            ArchivoUtil.salvarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_BANCO_XML, marketplace);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/
}

