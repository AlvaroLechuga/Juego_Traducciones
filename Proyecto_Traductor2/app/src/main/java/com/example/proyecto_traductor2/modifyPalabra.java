package com.example.proyecto_traductor2;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Toast;

import com.example.proyecto_traductor2.DAO.DAOPalabra;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class modifyPalabra extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText txtPalabraSPMod;
    EditText txtPalabraENMod;
    Button btnModificarPalabra;

    List<Palabra> palabra;

    PalabraHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_palabra);
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

        palabra = (ArrayList) getIntent().getParcelableArrayListExtra("palabraModificar");

        txtPalabraSPMod = findViewById(R.id.txtPalabraSPMod);
        txtPalabraENMod = findViewById(R.id.txtPalabraENMod);
        btnModificarPalabra = findViewById(R.id.btnModificarPalabra);

        txtPalabraSPMod.setText(palabra.get(0).getPalabraSP());
        txtPalabraENMod.setText(palabra.get(0).getPalabraEN());

        dbHelper = new PalabraHelper(getApplicationContext(), "traductor", null, 1);

        btnModificarPalabra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtPalabraENMod.getText().equals("") || txtPalabraSPMod.getText().equals("")){
                    Toast.makeText(getApplicationContext(), "No has completado algún campo", Toast.LENGTH_SHORT).show();
                }else{
                    Palabra word = new Palabra();
                    word.setId(palabra.get(0).getId());
                    word.setAciertos(0);
                    word.setPalabraSP(txtPalabraSPMod.getText().toString());
                    word.setPalabraEN(txtPalabraENMod.getText().toString());

                    Date date = new Date();
                    String fecha = date.toString();

                    word.setFechaIntroduccion(fecha);
                    word.setFechaUltimoTest(fecha);

                    DAOPalabra dao = new DAOPalabra();

                    if(dao.ModificarPalabra(word, dbHelper) == 1){
                        Toast.makeText(getApplicationContext(), "Se ha modificado la palabra", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(), MostrarBusqueda.class);
                        finish();
                        startActivity(i);
                    }else{
                        Toast.makeText(getApplicationContext(), "Ha ocurrido un error al modificar la palabra", Toast.LENGTH_LONG).show();
                        Intent i2 = new Intent(getApplicationContext(), MostrarBusqueda.class);
                        finish();
                        startActivity(i2);
                    }

                }
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
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_manage) {
            Intent i = new Intent(getApplicationContext(), Opciones.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
