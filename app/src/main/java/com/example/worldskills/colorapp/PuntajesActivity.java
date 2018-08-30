package com.example.worldskills.colorapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.worldskills.colorapp.clases.Conexion;

import java.util.ArrayList;
import java.util.List;

public class PuntajesActivity extends AppCompatActivity {
    ListView listView;
    List<String> list= new  ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntajes);

        listView = findViewById(R.id.listView);

        cargarPuntajes();
    }

    private void cargarPuntajes() {
        try {
            Conexion conexion = new Conexion(this);
            SQLiteDatabase database = conexion.getReadableDatabase();
            Cursor cursor = database.rawQuery("SELECT * FROM puntuacion WHERE intentos = 3 AND milisegundos = 3000 ORDER BY puntaje DESC LIMIT 4", null);
            int i = 1;
            while (cursor.moveToNext()){
                list.add(i+". "+cursor.getString(1));
                i++;
            }
            ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, list);
            listView.setAdapter(arrayAdapter);
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}
