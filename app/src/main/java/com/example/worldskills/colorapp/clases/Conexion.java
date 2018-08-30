package com.example.worldskills.colorapp.clases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class Conexion extends SQLiteOpenHelper{
    Context context;
    public Conexion(Context context) {
        super(context, "color", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE configuracion (tiempo INTEGER, intentos INTEGER, milisegundos INTEGER)");
            db.execSQL("CREATE TABLE puntuacion (id INTEGER PRIMARY KEY AUTOINCREMENT, puntaje INTEGER, intentos INTEGER, milisegundos INTEGER)");
            db.execSQL("INSERT INTO configuracion (tiempo, intentos, milisegundos) VALUES (-1, 3, 3000)");
            db.execSQL("INSERT INTO puntuacion (puntaje, intentos, milisegundos) VALUES (0, 3, 3000)");
            db.execSQL("INSERT INTO puntuacion (puntaje, intentos, milisegundos) VALUES (1, 3, 7000)");
            db.execSQL("INSERT INTO puntuacion (puntaje, intentos, milisegundos) VALUES (1, 3, 7000)");
            db.execSQL("INSERT INTO puntuacion (puntaje, intentos, milisegundos) VALUES (1, 3, 7000)");
            db.execSQL("INSERT INTO puntuacion (puntaje, intentos, milisegundos) VALUES (2, 5, 3000)");
            db.execSQL("INSERT INTO puntuacion (puntaje, intentos, milisegundos) VALUES (3, 8, 3000)");
            db.execSQL("INSERT INTO puntuacion (puntaje, intentos, milisegundos) VALUES (1, 3, 3000)");
            db.execSQL("INSERT INTO puntuacion (puntaje, intentos, milisegundos) VALUES (1, 3, 5000)");
        } catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
