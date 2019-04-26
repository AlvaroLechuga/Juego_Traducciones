package com.example.proyecto_traductor2;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto_traductor2.DAO.DAOPalabra;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Test extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    TextView txtPalabra;
    Button btnOPC1;
    Button btnOPC2;
    Button btnOPC3;
    Button btnOPC4;
    Random rand = new Random();
    List<Palabra> palabrasInicial;
    List<Palabra> palabrasCopia;
    List<Palabra> palabrasUsadas = new ArrayList<>();
    List<String> palabrasIngles = new ArrayList<>();
    int puntuacion = 0;
    int n = 0;
    int totalPalabras;
    int contadorPalabras = 0;
    boolean juego = true;
    int tiempoMaximo = 0;

    MediaPlayer correct;
    MediaPlayer fail;

    Contador counter = null;

    PalabraHelper dbHelper;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
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

        correct = MediaPlayer.create(getApplicationContext(), R.raw.correct);
        fail = MediaPlayer.create(getApplicationContext(), R.raw.fail);

        txtPalabra = findViewById(R.id.txtPalabra);

        btnOPC1 = findViewById(R.id.btnOPC1);
        btnOPC2 = findViewById(R.id.btnOPC2);
        btnOPC3 = findViewById(R.id.btnOPC3);
        btnOPC4 = findViewById(R.id.btnOPC4);

        btnOPC1.setOnClickListener(this);
        btnOPC2.setOnClickListener(this);
        btnOPC3.setOnClickListener(this);
        btnOPC4.setOnClickListener(this);

        dbHelper = new PalabraHelper(getApplicationContext(), "traductor", null, 1);

        DAOPalabra dao = new DAOPalabra();

        palabrasInicial = dao.ObtenerPalabras(dbHelper);
        palabrasInicial.sort(new AvanzadoSorter());

        palabrasCopia = dao.ObtenerPalabras(dbHelper);
        palabrasCopia.sort(new AvanzadoSorter());

        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        totalPalabras = prefs.getInt("nPreguntas", 5);
        tiempoMaximo = prefs.getInt("tPreguntas", 10);
        GenerarJuego();

        tiempoMaximo = tiempoMaximo * 1000;

        counter = new Contador(tiempoMaximo,1000);
    }

    @Override
    public void onResume() {
        super.onResume();
        counter.start();
    }

    @Override
    public void onClick(View v) {
        if(juego){
            switch (v.getId()) {
                case R.id.btnOPC1:
                    if (palabrasInicial.get(n).getPalabraEN().equals(btnOPC1.getText().toString())) {
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        correct.start();
                        palabrasInicial.get(n).setAciertos(palabrasInicial.get(n).getAciertos()+1);
                        palabrasIngles.clear();
                        contadorPalabras++;
                        puntuacion++;
                        n++;
                        Toast.makeText(getApplicationContext(), "Acierto", Toast.LENGTH_SHORT).show();
                        counter.start();

                    } else {
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        fail.start();
                        palabrasInicial.get(n).setAciertos(0);
                        palabrasIngles.clear();
                        contadorPalabras++;
                        n++;
                        Toast.makeText(getApplicationContext(), "Fallo", Toast.LENGTH_SHORT).show();
                        counter.start();
                    }
                    break;
                case R.id.btnOPC2:
                    if (palabrasInicial.get(n).getPalabraEN().equals(btnOPC2.getText().toString())) {
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        correct.start();
                        palabrasInicial.get(n).setAciertos(palabrasInicial.get(n).getAciertos()+1);
                        palabrasIngles.clear();
                        contadorPalabras++;
                        puntuacion++;
                        n++;
                        Toast.makeText(getApplicationContext(), "Acierto", Toast.LENGTH_SHORT).show();
                        counter.start();
                    } else {
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        fail.start();
                        palabrasInicial.get(n).setAciertos(0);
                        palabrasIngles.clear();
                        contadorPalabras++;
                        n++;
                        Toast.makeText(getApplicationContext(), "Fallo", Toast.LENGTH_SHORT).show();
                        counter.start();
                    }
                    break;
                case R.id.btnOPC3:
                    if (palabrasInicial.get(n).getPalabraEN().equals(btnOPC3.getText().toString())) {
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        correct.start();
                        palabrasInicial.get(n).setAciertos(palabrasInicial.get(n).getAciertos()+1);
                        palabrasIngles.clear();
                        contadorPalabras++;
                        puntuacion++;
                        n++;
                        Toast.makeText(getApplicationContext(), "Acierto", Toast.LENGTH_SHORT).show();
                        counter.start();
                    } else {
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        fail.start();
                        palabrasInicial.get(n).setAciertos(0);
                        palabrasIngles.clear();
                        contadorPalabras++;
                        n++;
                        Toast.makeText(getApplicationContext(), "Fallo", Toast.LENGTH_SHORT).show();
                        counter.start();
                    }
                    break;
                case R.id.btnOPC4:
                    if (palabrasInicial.get(n).getPalabraEN().equals(btnOPC4.getText().toString())) {
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        correct.start();
                        palabrasInicial.get(n).setAciertos(palabrasInicial.get(n).getAciertos()+1);
                        palabrasIngles.clear();
                        contadorPalabras++;
                        puntuacion++;
                        n++;
                        Toast.makeText(getApplicationContext(), "Acierto", Toast.LENGTH_SHORT).show();
                        counter.start();
                    } else {
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        fail.start();
                        palabrasInicial.get(n).setAciertos(0);
                        palabrasIngles.clear();
                        contadorPalabras++;
                        n++;
                        Toast.makeText(getApplicationContext(), "Fallo", Toast.LENGTH_SHORT).show();
                        counter.start();
                    }
                    break;
            }

            if (contadorPalabras == totalPalabras) {
                Intent returnIntent = new Intent();
                returnIntent.putParcelableArrayListExtra("listaPalabra",(ArrayList) palabrasInicial);
                setResult(Activity.RESULT_OK,returnIntent);
                txtPalabra.setText("Has acertado: " + puntuacion + " de " + totalPalabras);
                btnOPC1.setText("");
                btnOPC2.setText("");
                btnOPC3.setText("");
                btnOPC4.setText("");
                juego = false;
            }else{
                GenerarJuego();
            }
        }
    }


    private void GenerarJuego() {

        palabrasUsadas.add(palabrasInicial.get(n));
        palabrasIngles.add(palabrasInicial.get(n).getPalabraEN());

        txtPalabra.setText(palabrasInicial.get(n).getPalabraSP());

        int posCorrecta = rand.nextInt(4);

        switch (posCorrecta) {
            case 0:
                btnOPC1.setText(palabrasInicial.get(n).getPalabraEN());
                btnOPC2.setText(ObtenerPalabra());
                btnOPC3.setText(ObtenerPalabra());
                btnOPC4.setText(ObtenerPalabra());
                break;
            case 1:
                btnOPC1.setText(ObtenerPalabra());
                btnOPC2.setText(palabrasInicial.get(n).getPalabraEN());
                btnOPC3.setText(ObtenerPalabra());
                btnOPC4.setText(ObtenerPalabra());
                break;
            case 2:
                btnOPC1.setText(ObtenerPalabra());
                btnOPC2.setText(ObtenerPalabra());
                btnOPC3.setText(palabrasInicial.get(n).getPalabraEN());
                btnOPC4.setText(ObtenerPalabra());
                break;
            case 3:
                btnOPC1.setText(ObtenerPalabra());
                btnOPC2.setText(ObtenerPalabra());
                btnOPC3.setText(ObtenerPalabra());
                btnOPC4.setText(palabrasInicial.get(n).getPalabraEN());
                break;
        }
    }

    public class Contador extends CountDownTimer {

        public Contador(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {/////CUANDO SE TERMINA EL CONTEO DEL TIEMPO
            if(juego){
                palabrasInicial.get(n).setAciertos(0);
                palabrasIngles.clear();
                n++;
                contadorPalabras++;
                fail.start();
                GenerarJuego();
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int segundos = (int) (millisUntilFinished / 1000);///CADA VEZ QUE PASA UN SEGUNDO LLEGA ACA
        }

    }

    public String ObtenerPalabra(){

        int n2 = rand.nextInt(palabrasCopia.size());;

        while (palabrasIngles.contains(palabrasInicial.get(n2).getPalabraEN())){
            n2 = rand.nextInt(palabrasCopia.size());
        }

        String palabra = palabrasInicial.get(n2).getPalabraEN();

        palabrasIngles.add(palabra);

        return palabra;
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
