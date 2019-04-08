package com.example.proyecto_traductor2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InsertarPalabra extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    Button btnInsertar;
    EditText txtPalabraSP;
    EditText txtPalabraEN;
    List<Palabra> palabras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_palabra);
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

        btnInsertar = findViewById(R.id.btnInsertar);
        txtPalabraSP = findViewById(R.id.txtPalabraSP);
        txtPalabraEN = findViewById(R.id.txtPalabraEN);

        btnInsertar.setOnClickListener(this);

        palabras = (ArrayList) getIntent().getParcelableArrayListExtra("listaPalabra");

        Log.i("sizeList", String.valueOf(palabras.size()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnInsertar:

                String palabraSP = txtPalabraSP.getText().toString();
                String palabraEN = txtPalabraEN.getText().toString();

                if(palabraSP.equals("") || palabraEN.equals("")){
                    Toast.makeText(getApplicationContext(), "No has rellenado una palabra", Toast.LENGTH_SHORT).show();
                }else {
                    Date result = new Date();
                    String fecha = result.toString();

                    Palabra palabra = new Palabra(palabraSP, palabraEN, fecha, fecha, 0);

                    if(!palabras.contains(palabra)){
                        palabras.add(palabra);
                    }else{
                        for(int i = 0; i < palabras.size(); i++){
                            if(palabras.get(i).getPalabraSP().equals(palabra.getPalabraSP())){
                                palabras.get(i).setPalabraEN(palabra.getPalabraEN());
                                palabras.get(i).setAciertos(palabra.getAciertos());
                                palabras.get(i).setFechaIntroduccion(palabra.getFechaIntroduccion());
                                palabras.get(i).setFechaUltimoTest(palabra.getFechaUltimoTest());
                            }
                        }
                    }

                    txtPalabraSP.setText("");
                    txtPalabraEN.setText("");
                    Toast.makeText(getApplicationContext(), "Se ha insertado la palabra", Toast.LENGTH_SHORT).show();

                    Intent returnIntent = new Intent();
                    returnIntent.putParcelableArrayListExtra("listaPalabra",(ArrayList) palabras);
                    setResult(Activity.RESULT_OK,returnIntent);

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
