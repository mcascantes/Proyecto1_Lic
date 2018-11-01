package com.lic.prgra.proyecto1;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class UsuariosAdapter extends BaseAdapter{
    private Activity activity;
    private ArrayList<Usuarios> data=null;
    private static LayoutInflater inflater =null;
    NumberFormat format = new DecimalFormat("###,###,##0.00");

    public UsuariosAdapter(Activity activity, ArrayList<Usuarios> data) {
        this.activity = activity;
        this.data = data;
        inflater =(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {

        return (data == null) ? 0 : data.size();

    }

    public void updateUsuarioList(ArrayList<Usuarios> newlist) {
        data.clear();
        data.addAll(newlist);
        this.notifyDataSetChanged();
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
           vi=inflater.inflate(R.layout.lista_item,null);

        TextView titulo=vi.findViewById(R.id.titulo);
        TextView liquidez=vi.findViewById(R.id.liquidez);

        titulo.setText(data.get(position).Nombre + " " + data.get(position).Apellidos);
        liquidez.setText("Liquidez ₡" + String.valueOf(format.format(data.get(position).Liquidez)));



        return vi;
    }
}
