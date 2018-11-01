package com.lic.prgra.proyecto1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class PrincipalActivity extends AppCompatActivity {

    ListView lista;
    ArrayList<Usuarios> Users;
    UsuariosAdapter Uadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        lista=findViewById(R.id.Lista);

        if(Users ==null)
        {
            Users=new ArrayList<Usuarios>();
        }

        //Toast.makeText(this, "Cantidad de Usuarios= " + Users.size() , Toast.LENGTH_SHORT).show();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent IA = new Intent(PrincipalActivity.this, InsertarActivity.class);
                startActivityForResult(IA, 1);

                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        Uadapter=new UsuariosAdapter(this, Users);
        lista.setAdapter(Uadapter);
        Uadapter.notifyDataSetChanged();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent IA = new Intent(PrincipalActivity.this, InsertarActivity.class);
                IA.putExtra("Usuario",Users.get(position));
                IA.putExtra("index",String.valueOf(position));
                startActivityForResult(IA, 1);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (lista!= null) {
            lista.invalidateViews();
        }
        Uadapter.notifyDataSetChanged();
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                Bundle U=data.getExtras();
                Usuarios users=U.getParcelable("Usuario");
                String posicion=U.getString("index");

                //Toast.makeText(this, "RECIBIO POSICION " + posicion, Toast.LENGTH_SHORT).show();
                if(!posicion.isEmpty() )
                {
                    int index= Integer.parseInt(posicion);
                    Users.set(index,users);
                }else{
                    Users.add(users);
                }
                Uadapter.notifyDataSetChanged();


            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
