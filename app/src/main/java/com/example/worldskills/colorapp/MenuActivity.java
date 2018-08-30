package com.example.worldskills.colorapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void onClick(View view) {
        Intent intent=new Intent(MenuActivity.this,ConfiguracionActivity.class);
        startActivity(intent);
    }

    public void jugar(View view) {
        Intent intent=new Intent(MenuActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
