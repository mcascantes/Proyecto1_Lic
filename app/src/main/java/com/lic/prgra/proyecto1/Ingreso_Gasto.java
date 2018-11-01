package com.lic.prgra.proyecto1;


import android.os.Parcel;
import android.os.Parcelable;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Ingreso_Gasto implements Parcelable {
    String Tipo;
    String Concepto;
    double Monto;

    public Ingreso_Gasto() {
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getConcepto() {
        return Concepto;
    }

    public void setConcepto(String concepto) {
        Concepto = concepto;
    }

    public double getMonto() {
        return Monto;
    }

    public void setMonto(double monto) {
        Monto = monto;
    }

    protected Ingreso_Gasto(Parcel in) {
        Tipo = in.readString();
        Concepto = in.readString();
        Monto = in.readDouble();
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Tipo);
        dest.writeString(Concepto);
        dest.writeDouble(Monto);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Ingreso_Gasto> CREATOR = new Parcelable.Creator<Ingreso_Gasto>() {
        @Override
        public Ingreso_Gasto createFromParcel(Parcel in) {
            return new Ingreso_Gasto(in);
        }

        @Override
        public Ingreso_Gasto[] newArray(int size) {
            return new Ingreso_Gasto[size];
        }
    };
    public String[] DeduccionesLey(double MontoBruto){

        NumberFormat format = new DecimalFormat("###,###,##0.00");
        double ENFERMEDAD_MATERNIDAD=MontoBruto * 0.055;
        double IVM=MontoBruto * 0.0384;
        double FOP=MontoBruto * 0.01;
        double Total_Deducciones= ENFERMEDAD_MATERNIDAD + IVM + FOP + Renta(MontoBruto);
        double Liquidez=MontoBruto - Total_Deducciones;

        String Desglose= "Enfermedad y Maternidad 5.5%: ₡" + format.format(ENFERMEDAD_MATERNIDAD) +
                         "\nInvalidez y Muerte 3.84%: ₡" + format.format(IVM) +
                         "\nFondo de Pensiones 1%: ₡" + format.format(FOP) +
                         "\nRenta: ₡" + format.format(Renta(MontoBruto)) +
                         "\n\nTOTAL DEDUCCIONES: ₡" + format.format(Total_Deducciones) +
                         "\n\nSALARIO NETO: ₡" + format.format(Liquidez);
        String[] valores={Desglose,String.valueOf(Liquidez)};

        return valores;
    }

    public double Renta(double MontoBruto){

        double RENTA=0;
        if(MontoBruto > 792000 && MontoBruto < 1188000)
        {
           RENTA=MontoBruto * 0.10;
        }
        if(MontoBruto > 1188001){
            return  RENTA = MontoBruto *0.15;
        }
       return RENTA;
    }



}