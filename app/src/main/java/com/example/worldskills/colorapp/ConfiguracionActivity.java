package com.example.worldskills.colorapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    EditText txtNumero;
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
        button = findViewById(R.id.button);

        cargarConfiguracion();

        rbTiempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        rbIntentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        //se actualizan lods datos
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase sqLiteDatabase = conexion.getWritableDatabase();
                if (rbTiempo.isChecked()){
                    sqLiteDatabase.execSQL("UPDATE configuracion SET tiempo = -1");
                } else{
                    sqLiteDatabase.execSQL("UPDATE configuracion SET tiempo = -1");
                }
                if (rbIntentos.isChecked()){
                    sqLiteDatabase.execSQL("UPDATE configuracion SET intentos = -1");
                } else {
                    sqLiteDatabase.execSQL("UPDATE configuracion SET intentos = -1");
                }
            }
        });
    }

    private void cargarConfiguracion() {
        try {
            SQLiteDatabase database = conexion.getReadableDatabase();
            Cursor cursor = database.rawQuery("SELECT * FROM configuracion", null);
            cursor.moveToNext();
            if (cursor.getString(0).equals("-1")){
                rbTiempo.setChecked(true);
                rbIntentos.setChecked(false);
                rbIntentosDefecto.setChecked(false);
                txtTexto.setText("Tiempo en milisegundos");
                txtNumero.setText(cursor.getString(0));
            } else {
                if (cursor.getString(0).equals("3")){
                    rbTiempo.setChecked(false);
                    rbIntentos.setChecked(false);
                    rbIntentosDefecto.setChecked(true);
                    txtTexto.setVisibility(View.INVISIBLE);
                    txtNumero.setVisibility(View.INVISIBLE);
                } else {
                    rbTiempo.setChecked(false);
                    rbIntentos.setChecked(true);
                    rbIntentosDefecto.setChecked(true);
                    txtTexto.setText("Número de intentos");
                    txtNumero.setText(cursor.getString(1));
                }

            }

            if (cursor.getString(2).equals("3000")){
                rbMilisegundosDefecto.setChecked(true);
                rbMilisegundos.setChecked(false);
            } else {
                rbMilisegundosDefecto.setChecked(false);
                rbMilisegundos.setChecked(true);
            }
        } catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
