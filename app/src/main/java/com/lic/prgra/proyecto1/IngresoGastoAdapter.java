package com.lic.prgra.proyecto1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class IngresoGastoAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<Ingreso_Gasto> data=null;
    private static LayoutInflater inflater =null;
    NumberFormat format = new DecimalFormat("###,###,##0.00");

    public IngresoGastoAdapter(Activity activity, ArrayList<Ingreso_Gasto> data) {
        this.activity = activity;
        this.data = data;
        inflater =(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {

        return (data == null) ? 0 : data.size();

    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi= convertView;
        if(convertView == null)
            vi=inflater.inflate(R.layout.lista_ingreso_gasto,null);

        TextView Concepto=vi.findViewById(R.id.TxtConcepto);
        TextView Tipo=vi.findViewById(R.id.TxtTipo);
        TextView Monto=vi.findViewById(R.id.TxtMonto);

        Concepto.setText(data.get(position).Concepto);
        Tipo.setText(data.get(position).Tipo);
        Monto.setText("₡"+ String.valueOf(format.format(data.get(position).Monto)));



        return vi;
    }
}
