package com.example.worldskills.colorapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn1,btn2,btn3,btn4;
    TextView lblNombreColor,lblDesplegadas,lblCorrectas,lblOpcionalNombre,lblOpcionalValor;

    int colores[]={Color.YELLOW,Color.RED,Color.BLUE,Color.GREEN};
    String [] coloresName={"Amarillo","Rojo","Azul","Verde"};
    int buttonColor[]=new int[4];

    int colorSelccionado=0;
    int contadorAciertos=0;
    int desplegados=0;
    int timempoDesaparece=3;


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

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);

        generarLista();



    }

    private void generarLista() {
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
        if (lblNombreColor .getCurrentTextColor()== colorSelccionado){aumentarPuntos();}
        else{ reducirPuntos();}
    }

    private void reducirPuntos() {

    }

    private void aumentarPuntos() {
        contadorAciertos++;
        recargarLabel();
        cambiarColores();
    }

    private void cambiarColores() {

    }

    private void recargarLabel() {
        lblDesplegadas.setText(Integer.toString(contadorAciertos));
    }
}
