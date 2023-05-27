package pruebas;

import co.edu.uniquindio.marketplace.model.Categoria;
import co.edu.uniquindio.marketplace.model.Producto;

public class Main {
    public static void main(String[] args) {
        //Producto producto1 = new Producto("","", Categoria.VEHICULOS);
        //Producto producto2 = new Producto("","", Categoria.VEHICULOS);

        //System.out.println(producto1.equals(producto2));
        String idProducto = "";
        //System.out.println();
        /*for (int i = 0; i < 4; i++) {
            int n =  (int) Math.round(Math.random()*10);
            idProducto += String.valueOf(n);
        }*/

        //System.out.println(idProducto);

        pruebas.Producto producto1 = new pruebas.Producto();
        System.out.println(producto1.getIdProducto());
    }
}
