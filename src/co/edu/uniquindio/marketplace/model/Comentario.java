package co.edu.uniquindio.marketplace.model;

import java.io.Serializable;

public class Comentario implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fecha;
    private String autor;
    private String comentario;

    public Comentario(){}

    public Comentario(String fecha, String autor, String comentario){
        this.fecha = fecha;
        this.autor = autor;
        this.comentario = comentario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
