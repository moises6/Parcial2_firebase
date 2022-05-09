package com.example.parcial2_firebase.Modelos;

public class DatosRegistros {
    private String id_Dato;
    private String Titulo;
    private String Descripcion;
    private String Fecha;
    private String Hora;
    private String Estatus;
    private String Usuario;

    public String getId_Dato() {
        return id_Dato;
    }

    public void setId_Dato(String id_Dato) {
        this.id_Dato = id_Dato;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public String getEstatus() {
        return Estatus;
    }

    public void setEstatus(String estatus) {
        Estatus = estatus;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario=usuario;
    }


}
