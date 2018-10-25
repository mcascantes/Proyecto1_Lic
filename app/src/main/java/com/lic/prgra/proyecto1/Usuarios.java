package com.lic.prgra.proyecto1;

public class Usuarios {
    String Nombre="";
    String Apellidos="";
    String Monto="";

    public Usuarios(String nombre, String apellidos, String monto) {
        Nombre = nombre;
        Apellidos = apellidos;
        Monto = monto;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getMonto() {
        return Monto;
    }

    public void setMonto(String monto) {
        Monto = monto;
    }
}
