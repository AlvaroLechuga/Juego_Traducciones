package com.example.proyecto_traductor2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
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

import com.example.proyecto_traductor2.DAO.DAOPalabra;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
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

    PalabraHelper dbHelper;

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

        dbHelper = new PalabraHelper(getApplicationContext(), "traductor", null, 1);

        DAOPalabra dao = new DAOPalabra();
        palabras = dao.ObtenerPalabras(dbHelper);
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
                opcion = "inglés";
                txtPalabraBuscar.setVisibility(View.VISIBLE);
                break;
            case R.id.btnBuscar:
                switch(opcion){
                    case "alfabetico":
                        Intent alfabetico = new Intent(getApplicationContext(), MostrarBusqueda.class);
                        alfabetico.putExtra("orden", "alfabetico");
                        startActivity(alfabetico);
                        break;
                    case "aciertos":
                        Intent aciertos = new Intent(getApplicationContext(), MostrarBusqueda.class);
                        aciertos.putExtra("orden", "aciertos");
                        startActivity(aciertos);
                        break;
                    case "español":
                        if(!txtPalabraBuscar.getText().toString().equals("")){
                            Intent esp = new Intent(getApplicationContext(), MostrarBusqueda.class);
                            esp.putExtra("palabraES", txtPalabraBuscar.getText().toString());
                            startActivity(esp);
                        }else{
                            Toast.makeText(getApplicationContext(), "No has escrito ninguna palabra", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "inglés":

                        if(!txtPalabraBuscar.getText().toString().equals("")){
                            Intent ing = new Intent(getApplicationContext(), MostrarBusqueda.class);
                            ing.putExtra("palabraIN", txtPalabraBuscar.getText().toString());
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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.nav_manage){
            Intent i = new Intent(getApplicationContext(), Opciones.class);
            startActivity(i);
        }else if(id == R.id.nav_export){
            if(permisoEscritura()){
                exportDatabase();
            }

        }else if(id == R.id.nav_import){
            if(permisoEscritura()){
                importDatabase();
            }
        }else if(id == R.id.nav_juego_parejas){
            Intent i2 = new Intent(getApplicationContext(), juego_correspondencia.class);
            startActivity(i2);
        }else if(id == R.id.nav_juego_comecocos){
            Intent i3 = new Intent(getApplicationContext(), juego_comecocos.class);
            startActivity(i3);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean permisoEscritura() {
        if (Build.VERSION.SDK_INT >= 23) {
            int writepermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (getApplicationContext().checkSelfPermission(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                return true;
            } else {
                requestPermissions(
                        new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                        1);
                return false;
            }
        } else {
            return true;
        }
    }

    public void exportDatabase() {

        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source=null;
        FileChannel destination=null;
        String currentDBPath = "/data/"+ "com.example.proyecto_traductor2" +"/databases/"+"traductor";
        String backupDBPath = "bd_traductor.db";
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Toast.makeText(this, "Base de datos Traducción exportada", Toast.LENGTH_LONG).show();
            Log.i("erroresRaros", currentDB.getAbsolutePath());
            Log.i("erroresRaros", backupDB.getAbsolutePath());
        } catch(IOException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    public void importDatabase(){

        FileChannel source=null;
        FileChannel destination=null;
        File currentDB = new File("/storage/emulated/0/bd_traductor.db");
        File backupDB = new File("/data/data/com.example.proyecto_traductor2/databases/traductor");
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Toast.makeText(this, "Base de datos Traducción importada", Toast.LENGTH_LONG).show();
        } catch(IOException e) {
            Log.i("erroresRaros", e.toString());
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }
}
