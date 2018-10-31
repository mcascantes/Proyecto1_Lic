package com.lic.prgra.proyecto1;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Usuarios implements Parcelable {
    String Cedula;
    String Nombre;
    String Apellidos;
    ArrayList<Ingreso_Gasto> Ingreso_Gasto;
    double Total_gastos;
    double Total_Ingresos;
    double Liquidez;

    public Usuarios() {

    }

    public String getCedula() {
        return Cedula;
    }

    public void setCedula(String cedula) {
        Cedula = cedula;
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

    public ArrayList<com.lic.prgra.proyecto1.Ingreso_Gasto> getIngreso_Gasto() {
        return Ingreso_Gasto;
    }

    public void setIngreso_Gasto(ArrayList<com.lic.prgra.proyecto1.Ingreso_Gasto> ingreso_Gasto) {
        Ingreso_Gasto = ingreso_Gasto;
    }

    public double getTotal_gastos() {
        return Total_gastos;
    }

    public void setTotal_gastos(double total_gastos) {
        Total_gastos = total_gastos;
    }

    public double getTotal_Ingresos() {
        return Total_Ingresos;
    }

    public void setTotal_Ingresos(double total_Ingresos) {
        Total_Ingresos = total_Ingresos;
    }

    public double getLiquidez() {
        return Liquidez;
    }

    public void setLiquidez(double liquidez) {
        Liquidez = liquidez;
    }

    protected Usuarios(Parcel in) {
        Cedula = in.readString();
        Nombre = in.readString();
        Apellidos = in.readString();
        if (in.readByte() == 0x01) {
            Ingreso_Gasto = new ArrayList<Ingreso_Gasto>();
            in.readList(Ingreso_Gasto, Ingreso_Gasto.class.getClassLoader());
        } else {
            Ingreso_Gasto = null;
        }
        Total_gastos = in.readDouble();
        Total_Ingresos = in.readDouble();
        Liquidez = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Cedula);
        dest.writeString(Nombre);
        dest.writeString(Apellidos);
        if (Ingreso_Gasto == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(Ingreso_Gasto);
        }
        dest.writeDouble(Total_gastos);
        dest.writeDouble(Total_Ingresos);
        dest.writeDouble(Liquidez);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Usuarios> CREATOR = new Parcelable.Creator<Usuarios>() {
        @Override
        public Usuarios createFromParcel(Parcel in) {
            return new Usuarios(in);
        }

        @Override
        public Usuarios[] newArray(int size) {
            return new Usuarios[size];
        }
    };
}