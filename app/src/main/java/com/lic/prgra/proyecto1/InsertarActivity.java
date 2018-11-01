package com.lic.prgra.proyecto1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class InsertarActivity extends AppCompatActivity {

    EditText _Cedula,_Nombre,_Apellidos;
    Button _Guardar,_Agregar;
    Spinner _gasto_ingreso;
    ListView _Lista;
    Toolbar _Tools;

    Usuarios U = new Usuarios();
    IngresoGastoAdapter adapter;
    String posicion="";


    double _liquidez=0,_gastos=0,_ingresos=0;

   // ArrayList<Gastos> G = new ArrayList<Gastos>();
    //ArrayList<Ingresos> I = new ArrayList<Ingresos>();

    ArrayList<Ingreso_Gasto> IG=new ArrayList<Ingreso_Gasto>();

    final Context c = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar);

        //ToolBar
        Toolbar toolbari = findViewById(R.id.toolInsert);
        setSupportActionBar(toolbari);
        getSupportActionBar().setTitle(null);
        toolbari.setTitle("");




        //EditText
        _Cedula=findViewById(R.id.TxtCedula);
        _Nombre=findViewById(R.id.TxtNombre);
        _Apellidos=findViewById(R.id.TxtApellidos);

        //Botones

        _Agregar=findViewById(R.id.BtnAgregar);

        //Spinner
        _gasto_ingreso=findViewById(R.id.spSelector);


        //ListView
        _Lista=findViewById(R.id.Lista_gasto_ingreso);

        Intent intent = getIntent();
        Bundle User = intent.getExtras();
        if(User != null)
        {
            U =  User.getParcelable("Usuario");
            posicion=User.getString("index");
            //Alerta("Info","Recibio Posicion " + posicion);
            CargarDatos(U);

        }


        adapter=new IngresoGastoAdapter(this,IG);
        _Lista.setAdapter(adapter);





        //----------------Boton Agregar-----------------------------------------


        _Agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String valor=_gasto_ingreso.getSelectedItem().toString();

                    if(_Cedula.getText().toString().isEmpty() || _Nombre.getText().toString().isEmpty() ||
                            _Apellidos.getText().toString().isEmpty())
                    {
                        Alerta("Error","Cedula, Nombre o Apellidos no pueden ser vacios");

                    }else {
                        switch (valor) {
                            case "Ingreso":
                                MostrarIngreso();
                                break;
                            case "Gasto":
                                MostrarGasto();
                                break;
                        }
                    }
            }
        });





       


    }

     private void CargarDatos(Usuarios Us)
     {

         _Cedula.setText(Us.Cedula);
         _Nombre.setText(Us.Nombre);
         _Apellidos.setText(Us.Apellidos);
         IG=Us.getIngreso_Gasto();
         U.Ingreso_Gasto=Us.Ingreso_Gasto;

     }

    private void MostrarGasto()
    {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
        View mView = layoutInflaterAndroid.inflate(R.layout.gasto_form, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
        alertDialogBuilderUserInput.setView(mView);

        final Spinner _TipoGasto= mView.findViewById(R.id.spTipoGasto);
        final EditText _GastoMonto =  mView.findViewById(R.id.TxtMontoGasto);
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        Ingreso_Gasto Gasto=new Ingreso_Gasto();
                        Gasto.Tipo="Gasto";
                        Gasto.Concepto=_TipoGasto.getSelectedItem().toString();
                        Gasto.Monto= Double.parseDouble(_GastoMonto.getText().toString());
                        _gastos=_gastos + Gasto.Monto;
                        U.Total_gastos=_gastos;
                        IG.add(Gasto);
                        U.Ingreso_Gasto=IG;
                        CalcularLiquidez();
                        adapter.notifyDataSetChanged();
                    }
                })

                .setNegativeButton("Salir",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();


    }

    private void MostrarIngreso()
    {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
        View mView = layoutInflaterAndroid.inflate(R.layout.ingreso_form, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
        alertDialogBuilderUserInput.setView(mView);



        final EditText _TipoIngreso= mView.findViewById(R.id.TxtIngreso);
        final EditText _IngresoMonto =  mView.findViewById(R.id.TxtMontoIngreso);
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        Ingreso_Gasto Ingreso=new Ingreso_Gasto();
                        Ingreso.Tipo="Ingreso";
                        Ingreso.Concepto=_TipoIngreso.getText().toString();
                        double SalarioBruto= Double.parseDouble(_IngresoMonto.getText().toString());
                        Ingreso.Monto= Double.parseDouble(Ingreso.DeduccionesLey(SalarioBruto)[1]);
                        MostrarDesucciones(Ingreso.DeduccionesLey(SalarioBruto)[0]);
                        _ingresos=_ingresos + Ingreso.Monto;
                        U.Total_Ingresos=_ingresos;
                        IG.add(Ingreso);
                        U.Ingreso_Gasto=IG;
                        CalcularLiquidez();
                        adapter.notifyDataSetChanged();


                    }
                })

                .setNegativeButton("Salir",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();



    }
    private void CalcularLiquidez(){

       U.Liquidez=U.Total_Ingresos-U.Total_gastos;

    }

    private void MostrarDesucciones(String Deducciones){

        AlertDialog.Builder Alerta= new AlertDialog.Builder(c);
        Alerta.setTitle("Deducciones de Ley Aplicadas");
        Alerta.setPositiveButton("OK",null);
        Alerta.setMessage(Deducciones);
        Alerta.create();
        Alerta.show();
    }

    private void Alerta(String titulo,String Mensaje){

        AlertDialog.Builder Alerta= new AlertDialog.Builder(c);
        Alerta.setTitle(titulo);
        Alerta.setPositiveButton("Aceptar",null);
        Alerta.setMessage(Mensaje);

        Alerta.create();
        Alerta.show();
    }

   private void Agregar_Usuario()
    {


        U.Cedula=_Cedula.getText().toString();
        U.Nombre=_Nombre.getText().toString();
        U.Apellidos=_Apellidos.getText().toString();
        Intent MA = new Intent();
        MA.putExtra("Usuario",U);
        MA.putExtra("index",posicion);
        setResult(RESULT_OK,MA);
        finish();




    }


    @Override
    public void onBackPressed() {
        if(!validarDatos())
        {
        Agregar_Usuario();
        }else{
            Alerta("Error","Datos Incompletos");
        }

    }
    public boolean validarDatos(){
       boolean v=false;
        if(IG.size()==0 || _Cedula.getText().toString().isEmpty() || _Nombre.getText().toString().isEmpty() ||
                _Apellidos.getText().toString().isEmpty() )
        {
            v=true;
        }else {
            v=false;
        }
        return v;

    }

    private void Deshabilitar(){

        _Cedula.setEnabled(false);
        _Nombre.setEnabled(false);
        _Apellidos.setEnabled(false);

    }

    private void Habilitar(){

        _Cedula.setEnabled(true);
        _Nombre.setEnabled(true);
        _Apellidos.setEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_insertar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.Guardar) {
            if(!validarDatos())
            {
                Agregar_Usuario();
            }else{
                Alerta("Error","Datos Incompletos");
            }
        }

        if(id==R.id.Estadisticas)
        {
            if(IG.size()!=0) {
                HashMap<String, String> arrayMap = new HashMap<>();
                for (int i = 0; i < IG.size(); i++) {
                    if (IG.get(i).Tipo.equals("Gasto")) {
                        arrayMap.put(IG.get(i).Concepto, String.valueOf(IG.get(i).Monto));
                    }
                }
                if(!arrayMap.isEmpty()) {

                    Intent E = new Intent(InsertarActivity.this, EstadisticaActivity.class);
                    E.putExtra("Gastos", arrayMap);
                    startActivity(E);
                }else
                {
                    Alerta("Error", "No hay Gastos");
                }
            }else{
                Alerta("Error", "No hay Gastos");
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
