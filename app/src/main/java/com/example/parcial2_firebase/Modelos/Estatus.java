package com.example.parcial2_firebase.Modelos;
public class Estatus {
    private String NombreEstatus;

    public Estatus(String nombreEstatus) {
        NombreEstatus = nombreEstatus;
    }

    public String getNombreEstatus() {
        return NombreEstatus;
    }

    public void setNombreEstatus(String nombreEstatus) {
        NombreEstatus = nombreEstatus;
    }

    @Override
    public String toString() {
        return NombreEstatus;
    }
}
