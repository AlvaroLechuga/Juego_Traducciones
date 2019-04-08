package com.example.proyecto_traductor2;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Opciones extends AppCompatActivity implements View.OnClickListener {

    EditText txtNPreguntas;
    EditText txtTPreguntas;
    Button btnGuardar;
    List<Palabra> palabras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);

        palabras = (ArrayList) getIntent().getParcelableArrayListExtra("listaPalabra");

        txtNPreguntas = findViewById(R.id.txtNPreguntas);
        txtTPreguntas = findViewById(R.id.txtTPreguntas);
        btnGuardar = findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGuardar:

                if(txtNPreguntas.getText().toString().equals("") || txtTPreguntas.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "No has completado todos los campos", Toast.LENGTH_LONG).show();
                }else{
                    if(Integer.parseInt(txtNPreguntas.getText().toString()) > palabras.size()){
                        Toast.makeText(getApplicationContext(), "Cantidad de preguntas máxima: "+palabras.size(), Toast.LENGTH_LONG).show();
                    }else{
                        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt("nPreguntas", Integer.parseInt(txtNPreguntas.getText().toString()));
                        editor.putInt("tPreguntas", Integer.parseInt(txtTPreguntas.getText().toString()));
                        editor.commit();

                        Toast.makeText(getApplicationContext(), "Se han guardado las opciones", Toast.LENGTH_LONG).show();
                    }
                }

                break;
        }
    }
}
