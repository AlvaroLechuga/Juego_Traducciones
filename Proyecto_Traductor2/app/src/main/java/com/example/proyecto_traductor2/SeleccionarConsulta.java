package com.example.proyecto_traductor2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SeleccionarConsulta extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    RadioButton rbAlfabetico;
    RadioButton rbAciertos;
    RadioButton rbESP;
    RadioButton rbEng;
    EditText txtPalabraBuscar;
    Button btnBuscar;

    String opcion = "";

    List<Palabra> palabras;
    List<Palabra> listaEnviadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_consulta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listaEnviadas = new ArrayList<>();

        rbAlfabetico = findViewById(R.id.rbAlfabetico);
        rbAciertos = findViewById(R.id.rbAciertos);
        rbESP = findViewById(R.id.rbESP);
        rbEng = findViewById(R.id.rbEng);
        txtPalabraBuscar = findViewById(R.id.txtPalabraBuscar);
        btnBuscar = findViewById(R.id.btnBuscar);

        rbAlfabetico.setOnClickListener(this);
        rbAciertos.setOnClickListener(this);
        rbESP.setOnClickListener(this);
        rbEng.setOnClickListener(this);
        btnBuscar.setOnClickListener(this);

        palabras = (ArrayList) getIntent().getParcelableArrayListExtra("listaPalabra");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rbAlfabetico:
                opcion = "alfabetico";
                txtPalabraBuscar.setVisibility(View.INVISIBLE);
                break;
            case R.id.rbAciertos:
                opcion = "aciertos";
                txtPalabraBuscar.setVisibility(View.INVISIBLE);
                break;
            case R.id.rbESP:
                opcion = "español";
                txtPalabraBuscar.setVisibility(View.VISIBLE);
                break;
            case R.id.rbEng:
                opcion = "ingles";
                txtPalabraBuscar.setVisibility(View.VISIBLE);
                break;
            case R.id.btnBuscar:
                switch(opcion){
                    case "alfabetico":
                        Intent alfabetico = new Intent(getApplicationContext(), MostrarBusqueda.class);
                        listaEnviadas.clear();
                        listaEnviadas = palabras;
                        listaEnviadas.sort(new AlfabeticoSorter());
                        alfabetico.putParcelableArrayListExtra("listaPalabra",(ArrayList) listaEnviadas);
                        startActivity(alfabetico);
                        break;
                    case "aciertos":
                        Intent aciertos = new Intent(getApplicationContext(), MostrarBusqueda.class);
                        listaEnviadas.clear();
                        listaEnviadas = palabras;
                        listaEnviadas.sort(new PuntuacionSorter());
                        aciertos.putParcelableArrayListExtra("listaPalabra",(ArrayList) listaEnviadas);
                        startActivity(aciertos);
                        break;
                    case "español":
                        if(!txtPalabraBuscar.getText().toString().equals("")){
                            Intent esp = new Intent(getApplicationContext(), MostrarBusqueda.class);

                            Palabra palabra = new Palabra();

                            for(int i = 0; i < palabras.size(); i++){

                                if(palabras.get(i).getPalabraSP().equals(txtPalabraBuscar.getText().toString())){
                                    palabra.setPalabraSP(palabras.get(i).getPalabraSP());
                                    palabra.setPalabraEN(palabras.get(i).getPalabraEN());
                                    palabra.setFechaIntroduccion(palabras.get(i).getFechaIntroduccion());
                                    palabra.setFechaUltimoTest(palabras.get(i).getFechaUltimoTest());
                                    palabra.setAciertos(palabras.get(i).getAciertos());
                                }
                            }
                            listaEnviadas.clear();
                            listaEnviadas.add(palabra);
                            esp.putParcelableArrayListExtra("listaPalabra",(ArrayList) listaEnviadas);
                            startActivity(esp);
                        }else{
                            Toast.makeText(getApplicationContext(), "No has escrito ninguna palabra", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "inglés":

                        if(txtPalabraBuscar.getText().toString().equals("")){

                            Intent ing = new Intent(getApplicationContext(), MostrarBusqueda.class);

                            Palabra palabra2 = new Palabra();

                            for(int i = 0; i < palabras.size(); i++){

                                if(palabras.get(i).getPalabraEN().equals(txtPalabraBuscar.getText().toString())){
                                    palabra2.setPalabraSP(palabras.get(i).getPalabraSP());
                                    palabra2.setPalabraEN(palabras.get(i).getPalabraEN());
                                    palabra2.setFechaIntroduccion(palabras.get(i).getFechaIntroduccion());
                                    palabra2.setFechaUltimoTest(palabras.get(i).getFechaUltimoTest());
                                    palabra2.setAciertos(palabras.get(i).getAciertos());
                                }
                            }
                            listaEnviadas.clear();
                            listaEnviadas.add(palabra2);
                            ing.putParcelableArrayListExtra("listaPalabra",(ArrayList) listaEnviadas);
                            startActivity(ing);

                        }else{
                            Toast.makeText(getApplicationContext(), "No has escrito ninguna palabra", Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case "":
                        Toast.makeText(getApplicationContext(), "No has seleccionado una opción", Toast.LENGTH_SHORT).show();
                        break;
                }

                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.nav_manage){
            Intent i = new Intent(getApplicationContext(), Opciones.class);
            i.putParcelableArrayListExtra("listaPalabra",(ArrayList) palabras);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
