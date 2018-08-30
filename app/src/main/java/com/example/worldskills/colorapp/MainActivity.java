package com.example.worldskills.colorapp;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    CountDownTimer timerCambio;
    LinearLayout tiempoTotalLayout;
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
        tiempoTotalLayout=findViewById(R.id.layoutTiempo);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);

        juegoPorTiempo=consultarModoJuego();

        if (juegoPorTiempo==false){
            tiempoTotalLayout.setVisibility(View.INVISIBLE);
            lblOpcionalNombre.setText("Intentos restantes");
        }else{
            tiempoTotalLayout.setVisibility(View.INVISIBLE);
        }

        generarLista();
        timerCambio=new CountDownTimer(tiempoDesaparece,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                lbltickCambiar.setText(Long.toString(millisUntilFinished/1000));

            }

            @Override
            public void onFinish() {
               recargarJuego();
            }
        }.start();

    }

    private boolean consultarModoJuego() {
        return false;
    }

    private void recargarJuego() {
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
                recargarJuego();
                comparar();
            }
        }.start();
    }

    private void generarLista() {
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
        }
        else{
            if (juegoPorTiempo==false){
                intentosRestantes--;
                recargarLabel();
                if (intentosRestantes==0){
                    terminarJuego();
                }
            }
        }

    }

    private void terminarJuego() {
        Toast.makeText(this, "juego terminado", Toast.LENGTH_SHORT).show();
    }


    private void aumentarPuntos() {
        contadorAciertos++;
        recargarLabel();
    }

    private void recargarLabel() {
        lblCorrectas.setText(Integer.toString(contadorAciertos));
        lblOpcionalValor.setText(Integer.toString(intentosRestantes));
    }
}
