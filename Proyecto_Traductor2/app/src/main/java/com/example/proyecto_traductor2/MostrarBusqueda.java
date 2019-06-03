package com.example.proyecto_traductor2;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.proyecto_traductor2.DAO.DAOPalabra;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MostrarBusqueda extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    List<Palabra> palabras;
    ListView lvPalabra;
    TextToSpeech tts;
    Context context = this;

    PalabraHelper dbHelper;

    AdapterPalabra adapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_busqueda);
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

        dbHelper = new PalabraHelper(getApplicationContext(), "traductor", null, 1);

        DAOPalabra dao = new DAOPalabra();
        palabras = dao.ObtenerPalabras(dbHelper);

        String opcion = getIntent().getStringExtra("orden");
        String palabraES = getIntent().getStringExtra("palabraES");
        String palabraEN = getIntent().getStringExtra("palabraIN");

        if(opcion != null){
            switch (opcion){
                case "alfabetico":
                    palabras.sort(new AlfabeticoSorter());
                    break;
                case "aciertos":
                    palabras.sort(new PuntuacionSorter());
                    break;
            }
        }else{
            if(palabraES != null){
                for(int i = 0; i <= palabras.size(); i++){
                    if(palabraES.equals(palabras.get(i).getPalabraSP())){
                        Palabra p = palabras.get(i);
                        palabras.clear();
                        palabras.add(p);
                        break;
                    }
                }
            }else{
                for(int i = 0; i <= palabras.size(); i++){
                    if(palabraEN.equals(palabras.get(i).getPalabraEN())){
                        Palabra p = palabras.get(i);
                        palabras.clear();
                        palabras.add(p);
                        break;
                    }
                }
            }
        }

        lvPalabra = findViewById(R.id.lvPalabra);

        adapter = new AdapterPalabra(this, (ArrayList<Palabra>) palabras);

        lvPalabra.setAdapter(adapter);

        lvPalabra.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {

                tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {

                    @Override
                    public void onInit(int status) {

                        if (status == TextToSpeech.SUCCESS) {
                            int result = tts.setLanguage(Locale.US);
                            if (result == TextToSpeech.LANG_MISSING_DATA ||
                                    result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                Log.e("error", "This Language is not supported");
                            } else {
                                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                                View view1 = inflater.inflate(R.layout.details_palabra, null);
                                Button btnPlay = view1.findViewById(R.id.btnPlay);
                                Button btnEdit = view1.findViewById(R.id.btnEdit);
                                Button btnDelete = view1.findViewById(R.id.btnDelete);
                                Button btnCerrarDialogo = view1.findViewById(R.id.btnCerrarDialogo);

                                final AlertDialog alertDialog = new AlertDialog.Builder(context)
                                        .setView(view1)
                                        .setCancelable(false)
                                        .create();
                                alertDialog.show();

                                btnPlay.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        tts.speak(palabras.get(position).getPalabraEN(), TextToSpeech.QUEUE_FLUSH, null);
                                    }
                                });

                                btnEdit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent modificarPalabra = new Intent(getApplicationContext(), modifyPalabra.class);

                                        List<Palabra> palabra = new ArrayList<>();

                                        palabra.add(palabras.get(position));

                                        modificarPalabra.putParcelableArrayListExtra("palabraModificar", (ArrayList) palabra);
                                        finish();
                                        startActivity(modificarPalabra);

                                    }
                                });

                                btnDelete.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        new AlertDialog.Builder(context)
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .setTitle("Eliminar Palabra")
                                                .setMessage("¿Estás seguro de eliminar la palabra?")
                                                .setNegativeButton(android.R.string.cancel, null)// sin listener
                                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {// un listener que al pulsar, cierre la aplicacion
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // Eliminar palabra
                                                        DAOPalabra dao = new DAOPalabra();
                                                        Palabra palabra = new Palabra();
                                                        palabra.setId(palabras.get(position).getId());
                                                        if (dao.EliminarPalabra(palabra, dbHelper) == 1) {
                                                            Toast.makeText(getApplicationContext(), "Se ha eliminado la palabra", Toast.LENGTH_SHORT).show();
                                                            palabras = dao.ObtenerPalabras(dbHelper);
                                                            adapter = new AdapterPalabra((Activity) context, (ArrayList<Palabra>) palabras);
                                                            lvPalabra.setAdapter(adapter);
                                                            alertDialog.cancel();
                                                        } else {
                                                            Toast.makeText(getApplicationContext(), "Ha ocurrido un error al eliminar la palabra", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                })
                                                .show();

                                    }
                                });

                                btnCerrarDialogo.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        alertDialog.cancel();
                                    }
                                });
                            }
                        } else
                            Log.e("error", "Initilization Failed!");
                    }
                });

            }
        });
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

        if (id == R.id.nav_manage) {
            Intent i = new Intent(getApplicationContext(), Opciones.class);
            startActivity(i);
        } else if (id == R.id.nav_export) {
            if (permisoEscritura()) {
                exportDatabase();
            }

        } else if (id == R.id.nav_import) {
            if (permisoEscritura()) {
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
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
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
        FileChannel source = null;
        FileChannel destination = null;
        String currentDBPath = "/data/" + "com.example.proyecto_traductor2" + "/databases/" + "traductor";
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
        } catch (IOException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    public void importDatabase() {

        FileChannel source = null;
        FileChannel destination = null;
        File currentDB = new File("/storage/emulated/0/bd_traductor.db");
        File backupDB = new File("/data/data/com.example.proyecto_traductor2/databases/traductor");
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Toast.makeText(this, "Base de datos Traducción importada", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.i("erroresRaros", e.toString());
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }
}
