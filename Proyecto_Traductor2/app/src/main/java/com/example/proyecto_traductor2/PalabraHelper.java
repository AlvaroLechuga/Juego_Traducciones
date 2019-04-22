package com.example.proyecto_traductor2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class PalabraHelper extends SQLiteOpenHelper {

    //Sentencia SQL para crear la tabla de palabra
    String sqlCreate = "CREATE TABLE palabra (id INTEGER PRIMARY KEY AUTOINCREMENT, palabraSP VARCHAR, palabraEN VARCHAR, fechaIntroduccion VARCHAR, fechaUltimoTest VARCHAR, aciertos INTEGER)";

    String consulta1;
    String consulta2;
    String consulta3;
    String consulta4;
    String consulta5;
    String consulta6;
    String consulta7;
    String consulta8;
    String consulta9;
    String consulta10;
    String consulta11;
    String consulta12;

    Date result = new Date();

    public PalabraHelper(Context contexto, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);

        String fecha = result.toString();
        consulta1 = "INSERT INTO palabra values (null, 'Coche', 'Cart', '"+fecha+"', '"+fecha+"', 0)";
        consulta2 = "INSERT INTO palabra values (null, 'Casa', 'House', '"+fecha+"', '"+fecha+"', 0)";
        consulta3 = "INSERT INTO palabra values (null, 'Escribir', 'Write', '"+fecha+"', '"+fecha+"', 0)";
        consulta4 = "INSERT INTO palabra values (null, 'Largo', 'Long', '"+fecha+"', '"+fecha+"', 0)";
        consulta5 = "INSERT INTO palabra values (null, 'Ir', 'Go', '"+fecha+"', '"+fecha+"', 0)";
        consulta6 = "INSERT INTO palabra values (null, 'Gente', 'People', '"+fecha+"', '"+fecha+"', 0)";
        consulta7 = "INSERT INTO palabra values (null, 'Saber', 'Know', '"+fecha+"', '"+fecha+"', 0)";
        consulta8 = "INSERT INTO palabra values (null, 'Primero', 'First', '"+fecha+"', '"+fecha+"', 0)";
        consulta9 = "INSERT INTO palabra values (null, 'Ahora', 'Now', '"+fecha+"', '"+fecha+"', 0)";
        consulta10 = "INSERT INTO palabra values (null, 'Carta', 'Card', '"+fecha+"', '"+fecha+"', 0)";
        consulta11 = "INSERT INTO palabra values (null, 'Trabajar', 'Work', '"+fecha+"', '"+fecha+"', 0)";
        consulta12 = "INSERT INTO palabra values (null, 'Nombre', 'Name', '"+fecha+"', '"+fecha+"', 0)";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(sqlCreate);
        db.execSQL(consulta1);
        db.execSQL(consulta2);
        db.execSQL(consulta3);
        db.execSQL(consulta4);
        db.execSQL(consulta5);
        db.execSQL(consulta6);
        db.execSQL(consulta7);
        db.execSQL(consulta8);
        db.execSQL(consulta9);
        db.execSQL(consulta10);
        db.execSQL(consulta11);
        db.execSQL(consulta12);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {

        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS palabra");

        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreate);
        db.execSQL(consulta1);
        db.execSQL(consulta2);
        db.execSQL(consulta3);
        db.execSQL(consulta4);
        db.execSQL(consulta5);
        db.execSQL(consulta6);
        db.execSQL(consulta7);
        db.execSQL(consulta8);
        db.execSQL(consulta9);
        db.execSQL(consulta10);
        db.execSQL(consulta11);
        db.execSQL(consulta12);
    }
}
