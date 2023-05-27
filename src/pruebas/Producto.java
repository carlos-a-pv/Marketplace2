package pruebas;

public class Producto {
    String idProducto;

    public Producto(){
        this.idProducto = generarId();
        System.out.println(this.idProducto);

    }
    private String generarId() {
        String numeroID = "";
        for (int i = 0; i < 4; i++) {
            int n =  (int) Math.round(Math.random()*10);
            numeroID += String.valueOf(n);
        }
        return numeroID;
    }

    public String getIdProducto() {
        return idProducto;
    }
}
