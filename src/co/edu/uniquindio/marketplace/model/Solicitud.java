package co.edu.uniquindio.marketplace.model;

import co.edu.uniquindio.marketplace.controllers.SolicitudViewController;

import java.io.Serializable;

public class Solicitud implements Serializable {
    private static final long serialVersionUID = 1L;
    private Estado estado;
    private Vendedor emisor;
    private Vendedor receptor;

    public Solicitud(){}

    public Solicitud(Vendedor emisor, Vendedor receptor){
        this.emisor = emisor;
        this.receptor = receptor;
        this.estado = Estado.PENDIENTE;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Vendedor getEmisor() {
        return emisor;
    }

    public void setEmisor(Vendedor emisor) {
        this.emisor = emisor;
    }

    public Vendedor getReceptor() {
        return receptor;
    }

    public void setReceptor(Vendedor receptor) {
        this.receptor = receptor;
    }


}
