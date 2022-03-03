package com.example.proyectoindividual1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private int valor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        valor=0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            valor= extras.getInt("regok");
        }
        if ( valor == 1) {
            LayoutInflater inflater = getLayoutInflater();
            View el_layout = inflater.inflate(R.layout.toastregistro,(ViewGroup) findViewById(R.id.ltoastreg));
            Toast toastcustomizado = new Toast(this);
            toastcustomizado.setGravity(Gravity.TOP, 0, 0);
            toastcustomizado.setDuration(Toast.LENGTH_LONG);
            toastcustomizado.setView(el_layout);
            toastcustomizado.show();
        }
    }

    public void IniciarSesion(View view) {
        finish();
        Intent i = new Intent(this, menuPrincipal.class);
        startActivity(i);
    }
    public void Registrarse(View view) {
        finish();
        Intent i = new Intent(this, registro.class);
        startActivity(i);
    }
    @Override
    public void onClick(View view) {

    }
}