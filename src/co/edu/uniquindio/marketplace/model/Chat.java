package co.edu.uniquindio.marketplace.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Chat implements Serializable {
    private static final long serialVersionUID = 1L;

    Vendedor vendedor1;
    Vendedor vendedor2;
    ArrayList<Mensaje> mensajes = new ArrayList<>();



    public Chat(){}

    public Vendedor getVendedor1() {
        return vendedor1;
    }

    public void setVendedor1(Vendedor vendedor1) {
        this.vendedor1 = vendedor1;
    }

    public Vendedor getVendedor2() {
        return vendedor2;
    }

    public void setVendedor2(Vendedor vendedor2) {
        this.vendedor2 = vendedor2;
    }

    public ArrayList<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(ArrayList<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }
}
