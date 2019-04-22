package com.example.proyecto_traductor2;

import android.app.Activity;
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
import android.widget.Toast;

import com.example.proyecto_traductor2.DAO.DAOPalabra;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    Button btnIntroducir;
    Button btnConsultas;
    Button btnTest;
    Button btnSalir;
    List<Palabra> listaPalabras = new ArrayList<>();

    PalabraHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        btnIntroducir = findViewById(R.id.btnIntroducir);
        btnConsultas = findViewById(R.id.btnConsultas);
        btnTest = findViewById(R.id.btnTest);
        btnSalir = findViewById(R.id.btnSalir);

        btnIntroducir.setOnClickListener(this);
        btnConsultas.setOnClickListener(this);
        btnTest.setOnClickListener(this);
        btnSalir.setOnClickListener(this);

        dbHelper = new PalabraHelper(getApplicationContext(), "traductor", null, 1);

        listaPalabras = addPalabras();

        Bundle parametros = this.getIntent().getExtras();
        if(parametros != null) {
            listaPalabras = (ArrayList) getIntent().getParcelableArrayListExtra("listaPalabra");
        }
    }

    private List<Palabra> addPalabras() {

        List<Palabra> listado = new ArrayList<>();

        DAOPalabra dao = new DAOPalabra();
        listado = dao.ObtenerPalabras(dbHelper);

        return listado;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnIntroducir:
                Intent introducir = new Intent(getApplicationContext(), InsertarPalabra.class);
                startActivity(introducir);
                break;

            case R.id.btnConsultas:
                Intent consultas = new Intent(getApplicationContext(), SeleccionarConsulta.class);
                startActivity(consultas);
                break;

            case R.id.btnTest:
                Intent test = new Intent(getApplicationContext(), Test.class);
                startActivity(test);
                break;

            case R.id.btnSalir:
                System.exit(0);
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
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
