package com.example.worldskills.colorapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.worldskills.colorapp.clases.Conexion;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn1,btn2,btn3,btn4;
    TextView lblNombreColor,lblDesplegadas,lblCorrectas,lblOpcionalNombre,lblOpcionalValor,lbltickCambiar;

    int colores[]={Color.YELLOW,Color.RED,Color.BLUE,Color.GREEN};
    String [] coloresName={"Amarillo","Rojo","Azul","Verde"};
    int buttonColor[]=new int[4];

    int colorSelccionado=0;
    int contadorAciertos=0;
    int desplegados=0;
    long tiempoDesaparece=3000;
    int intentosRestantes=3;
    long tiempoTotal=10000;
    long tiempoCorriendo=0;
    long pausaTiempo=0;
    boolean isPause=false;

    int contadorFallidas=0;


    CountDownTimer timerCambio;
    CountDownTimer timerTotal;
    boolean juegoPorTiempo=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btn3);
        btn4=findViewById(R.id.btn4);
        lblNombreColor=findViewById(R.id.colorName);
        lblDesplegadas=findViewById(R.id.palabrasDesplegadas);
        lblCorrectas=findViewById(R.id.palabrasCorrectas);
        lblOpcionalNombre=findViewById(R.id.opcionalNombre);
        lblOpcionalValor=findViewById(R.id.opcionalPuntaje);
        lbltickCambiar=findViewById(R.id.tickCambiar);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);

        juegoPorTiempo=consultarModoJuego();

        if (juegoPorTiempo==false){
            lblOpcionalNombre.setText("Intentos restantes");
            lblOpcionalValor.setText(Integer.toString(intentosRestantes));
        }else{
            iniciarJuegoConTiempo();
            lblOpcionalNombre.setText("Tiempo restante");
            lblOpcionalValor.setText("hola");
        }

        generarLista();
        desplegados++;
        recargarLabel();
        timerCambio=new CountDownTimer(tiempoDesaparece,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                lbltickCambiar.setText(Long.toString(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                comparar();
            }
        }.start();

    }

    private void iniciarJuegoConTiempo() {
        if (isPause==true){
            tiempoTotal=pausaTiempo;
        }
        timerTotal=new CountDownTimer(tiempoTotal,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                lblOpcionalValor.setText(Long.toString(millisUntilFinished/1000));
                tiempoCorriendo=millisUntilFinished;
                if (isPause==true){
                    isPause=false;
                }
            }
            @Override
            public void onFinish() {
                if (isPause==true){

                }else{
                    terminarJuego();
                }
            }
        }.start();

    }

    private void disabledButton() {
        btn1.setEnabled(false);
        btn2.setEnabled(false);
        btn3.setEnabled(false);
        btn4.setEnabled(false);
        ImageButton button=findViewById(R.id.btnPause);
        button.setEnabled(false);
    }

    private boolean consultarModoJuego() {
        boolean retornar=false;
        String sql="select * from configuracion";
        Conexion conexion=new Conexion(this);
        SQLiteDatabase db=conexion.getReadableDatabase();
        Cursor cursor=db.rawQuery(sql,null);
        cursor.moveToNext();
        if (cursor.getString(0).equals("-1")){
            intentosRestantes=Integer.parseInt(cursor.getString(1));
            tiempoDesaparece=Integer.parseInt(cursor.getString(2));
            retornar= false;
        }if (!cursor.getString(0).equals("-1")){
            tiempoTotal=Integer.parseInt(cursor.getString(0));
            tiempoDesaparece=Integer.parseInt(cursor.getString(2));
            retornar= true;
        }
        if (cursor.getString(1).equals("3") && cursor.getString(2).equals("3000") ){
            intentosRestantes=3;
            tiempoDesaparece=3000;
            retornar= false;
        }
        return retornar;
    }

    private void recargarJuego() {

        desplegados++;
        colorSelccionado=0;
        timerCambio.cancel();
        generarLista();
        timerCambio=new CountDownTimer(tiempoDesaparece,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                lbltickCambiar.setText(Long.toString(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                comparar();

            }
        }.start();
    }

    private void enabledButton() {
        btn1.setEnabled(true);
        btn2.setEnabled(true);
        btn3.setEnabled(true);
        btn4.setEnabled(true);
    }

    private void generarLista() {
        enabledButton();
        buttonColor=new int[4];
        for (int i=0; i< colores.length;){
            int aleatorio= (int) (Math.random()*4);
            if (buttonColor[aleatorio]==0){
                buttonColor[aleatorio]=colores[i];
                i++;
            }
            cargarColorTexto();
        }

        btn1.setBackgroundColor(buttonColor[0]);
        btn2.setBackgroundColor(buttonColor[1]);
        btn3.setBackgroundColor(buttonColor[2]);
        btn4.setBackgroundColor(buttonColor[3]);

    }

    private void cargarColorTexto() {
        int aleatrorio= (int) (Math.random()*4);
        lblNombreColor.setText(coloresName[aleatrorio]);
        int aleatrorio2= (int) (Math.random()*4);
        if (aleatrorio==aleatrorio2){
            cargarColorTexto();
        }else{
            lblNombreColor.setTextColor(colores[aleatrorio2]);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                colorSelccionado=buttonColor[0];
                comparar();
                break;
            case R.id.btn2:
                colorSelccionado=buttonColor[1];
                comparar();
                break;
            case R.id.btn3:
                colorSelccionado=buttonColor[2];
                comparar();
                break;
            case R.id.btn4:
                colorSelccionado=buttonColor[3];
                comparar();
                break;
        }
    }

    private void comparar() {
        //comparar si los nombres de los colores son iguales.
        if (lblNombreColor .getCurrentTextColor()== colorSelccionado){
            aumentarPuntos();
            recargarJuego();
        }
        else{
            if (juegoPorTiempo==false){
                recargarJuego();
                intentosRestantes--;
                recargarLabel();
                if (intentosRestantes<=0){
                    disabledButton();
                    terminarJuego();
                }
            }else{
                contadorFallidas++;
                recargarJuego();
                recargarLabel();
            }
        }

    }

    private void terminarJuego() {
        timerCambio.cancel();
        disabledButton();
        guardarResultados();
        abrirVentanaResultados();
    }

    private void guardarResultados() {
        String sql="select * from configuracion";
        Conexion conexion=new Conexion(this);
        SQLiteDatabase db=conexion.getReadableDatabase();
        Cursor cursor=db.rawQuery(sql,null);
        cursor.moveToNext();
        if (cursor.getString(1).equals("3") && cursor.getString(2).equals("3000") ){
            sql="insert into puntuacion(puntaje, intentos, milisegundos) values("+Integer.toString(contadorAciertos)+",3,3000)";
            SQLiteDatabase db2=conexion.getWritableDatabase();
            db2.execSQL(sql);
        }
    }

    private void abrirVentanaResultados() {
        AlertDialog.Builder ventana=new AlertDialog.Builder(MainActivity.this);
        LayoutInflater layout=this.getLayoutInflater();
        View view=layout.inflate(R.layout.elian_desing_ventana,null,false);

        TextView correctos=view.findViewById(R.id.palabrasCorrectas);
        TextView fallidos=view.findViewById(R.id.palabrasFallidas);
        Button button=view.findViewById(R.id.menuPrincipal);

        correctos.setText(Integer.toString(contadorAciertos));
        fallidos.setText(Integer.toString(contadorFallidas));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
        ventana.setView(view);
        ventana.create();
        ventana.setCancelable(false);

        ventana.show();

    }


    private void aumentarPuntos() {
        contadorAciertos++;
        recargarLabel();
    }

    private void recargarLabel() {
        lblCorrectas.setText(Integer.toString(contadorAciertos));

        lblDesplegadas.setText(Integer.toString(desplegados));
        if (juegoPorTiempo==false){
            lblOpcionalValor.setText(Integer.toString(intentosRestantes));

        }
    }

    public void pause(View view) {
        timerCambio.cancel();
        if (juegoPorTiempo==true){
            pausaTiempo=tiempoCorriendo;
            timerTotal.cancel();
            isPause=true;

        }
        AlertDialog.Builder pausa=new AlertDialog.Builder(MainActivity.this);
        pausa.setTitle("JUEGO PAUSADO");
        pausa.setMessage("desea continuar");
        pausa.setCancelable(false);
        pausa.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (juegoPorTiempo==true){
                    iniciarJuegoConTiempo();
                }
                recargarJuego();
            }
        });
        pausa.show();
    }
}
