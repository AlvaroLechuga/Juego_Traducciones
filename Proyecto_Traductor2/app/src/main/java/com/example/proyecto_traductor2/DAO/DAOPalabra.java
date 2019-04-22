package com.example.proyecto_traductor2.DAO;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.proyecto_traductor2.Palabra;
import com.example.proyecto_traductor2.PalabraHelper;

import java.util.ArrayList;
import java.util.List;

public class DAOPalabra {

    ArrayList<Palabra> palabras;

    public DAOPalabra() {
        this.palabras = new ArrayList<>();
    }

    public int InsertarPalabra(Palabra palabra, PalabraHelper dbHelper){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String palabraSP = palabra.getPalabraSP();
        String palabraEN = palabra.getPalabraEN();
        String fechaIntroduccion = palabra.getFechaIntroduccion();
        String fechaUltimoTest = palabra.getFechaUltimoTest();

        try{
            String consulta = "INSERT INTO palabra values (null, '"+palabraSP+"', '"+palabraEN+"', '"+fechaIntroduccion+"', '"+fechaUltimoTest+"', 0)";
            db.execSQL(consulta);
            return 1;
        }catch (SQLException sqle){
            return 0;
        }catch (Exception e){
            return 0;
        }

    }

    public List<Palabra> ObtenerPalabras(PalabraHelper dbHelper){
        palabras = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Palabra palabra = null;

        String consulta = "SELECT * FROM palabra";
        Cursor cursor = db.rawQuery(consulta, null);

        while (cursor.moveToNext()){
            palabra = new Palabra();
            palabra.setId(cursor.getInt(0));
            palabra.setPalabraSP(cursor.getString(1));
            palabra.setPalabraEN(cursor.getString(2));
            palabra.setFechaIntroduccion(cursor.getString(3));
            palabra.setFechaUltimoTest(cursor.getString(4));
            palabra.setAciertos(cursor.getInt(5));
            palabras.add(palabra);
        }

        return palabras;
    }

    public int ModificarPalabra(Palabra palabra, PalabraHelper dbHelper){

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        int id = palabra.getId();
        String palabraSP = palabra.getPalabraSP();
        String palabraEN = palabra.getPalabraEN();
        String fechaIntroduccion = palabra.getFechaIntroduccion();
        String fechaUltimoTest = palabra.getFechaUltimoTest();
        int aciertos = palabra.getAciertos();

        try {
            String consulta = "UPDATE palabra SET palabraSP = '"+palabraSP+"', palabraEN = '"+palabraEN+"', fechaIntroduccion = '"+fechaIntroduccion+"', fechaUltimoTest = '"+fechaUltimoTest+"', aciertos = '"+aciertos+"' WHERE id = '"+id+"'";
            db.execSQL(consulta);
            return 1;
        }catch (SQLException sqle){
            return 0;
        }catch (Exception e){
            return 0;
        }
    }

    public int EliminarPalabra(Palabra palabra, PalabraHelper dbHelper){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try{
            db.delete("palabra", "id="+palabra.getId(), null);
            return 1;
        }catch (SQLException sqle){
            return 0;
        }catch (Exception e){
            return 0;
        }
    }
}
