package com.example.worldskills.colorapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.worldskills.colorapp.clases.Conexion;

public class ConfiguracionActivity extends AppCompatActivity {
    RadioButton rbTiempo, rbIntentos, rbMilisegundosDefecto, rbMilisegundos, rbIntentosDefecto;
    TextView txtTexto;
    EditText txtNumero, txtNumero2;
    Button button;
    Conexion conexion = new Conexion(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        rbTiempo = findViewById(R.id.rbTiempo);
        rbIntentos = findViewById(R.id.rbIntentos);
        rbIntentosDefecto = findViewById(R.id.rbIntentosDefecto);
        rbMilisegundosDefecto = findViewById(R.id.rbMilisegundosDefecto);
        rbMilisegundos = findViewById(R.id.rbMilisegundos);
        txtTexto = findViewById(R.id.txtTiempoIntentos);
        txtNumero = findViewById(R.id.txtNumber);
        txtNumero2 = findViewById(R.id.txtNumber2);
        button = findViewById(R.id.button);

        cargarConfiguracion();

        rbTiempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTexto.setVisibility(View.VISIBLE);
                txtTexto.setText("Tiempo en milisegundos");
                txtNumero.setVisibility(View.VISIBLE);
            }
        });

        rbIntentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTexto.setVisibility(View.VISIBLE);
                txtTexto.setText("Número de intentos");
                txtNumero.setVisibility(View.VISIBLE);
            }
        });

        rbIntentosDefecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTexto.setVisibility(View.INVISIBLE);
                txtNumero.setVisibility(View.INVISIBLE);
            }
        });

        rbMilisegundos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtNumero2.setVisibility(View.VISIBLE);
            }
        });

        rbMilisegundosDefecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtNumero2.setVisibility(View.INVISIBLE);
            }
        });



        //se actualizan lods datos
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tiempo = txtNumero.getText().toString();
                String milisegundos = txtNumero2.getText().toString();
                if (tiempo.equals("")){
                    tiempo = "-1";
                }
                if (milisegundos.equals("")){
                    milisegundos = "3000";
                }
                SQLiteDatabase sqLiteDatabase = conexion.getWritableDatabase();
                if (rbTiempo.isChecked()){
                    sqLiteDatabase.execSQL("UPDATE configuracion SET tiempo ="+tiempo);
                    sqLiteDatabase.execSQL("UPDATE configuracion SET intentos = -1");
                } else{
                    if (rbIntentosDefecto.isChecked()){
                        sqLiteDatabase.execSQL("UPDATE configuracion SET tiempo = -1");
                        sqLiteDatabase.execSQL("UPDATE configuracion SET intentos = 3");
                    } else{
                        sqLiteDatabase.execSQL("UPDATE configuracion SET tiempo = -1");
                        sqLiteDatabase.execSQL("UPDATE configuracion SET intentos =" +tiempo);
                    }
                }
                if (rbMilisegundos.isChecked()){
                    sqLiteDatabase.execSQL("UPDATE configuracion SET milisegundos ="+milisegundos);
                } else {
                    sqLiteDatabase.execSQL("UPDATE configuracion SET milisegundos = 3000");
                }
                finish();
            }
        });

    }

    private void cargarConfiguracion() {
        try {
            SQLiteDatabase database = conexion.getReadableDatabase();
            Cursor cursor = database.rawQuery("SELECT * FROM configuracion", null);
            cursor.moveToNext();

            if (cursor.getString(0).equals("-1")){
                rbIntentos.setChecked(true);
                txtTexto.setText("Número de intentos");
                txtNumero.setText(cursor.getString(1));
            } else{
                rbTiempo.setChecked(true);
                txtTexto.setText("Tiempo en milisegundos");
                txtNumero.setText(cursor.getString(0));
            }
            if (cursor.getString(2).equals("3000")){
                rbMilisegundosDefecto.setChecked(true);
                rbMilisegundos.setChecked(false);
                txtNumero2.setVisibility(View.INVISIBLE);
            } else {
                rbMilisegundosDefecto.setChecked(false);
                rbMilisegundos.setChecked(true);
                txtNumero2.setText(cursor.getString(2));
            }

            if (cursor.getString(1).equals("3")) {
                
                rbIntentosDefecto.setChecked(true);
                txtTexto.setVisibility(View.INVISIBLE);
                txtNumero.setVisibility(View.INVISIBLE);

            }


            Toast.makeText(ConfiguracionActivity.this, cursor.getString(0)+ cursor.getString(1)+ cursor.getString(2), Toast.LENGTH_LONG).show();
        } catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
