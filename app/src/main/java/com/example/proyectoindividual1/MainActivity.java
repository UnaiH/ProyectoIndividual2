package com.example.proyectoindividual1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
            View el_layout = inflater.inflate(R.layout.toastregistro,(ViewGroup) findViewById(R.id.ltoastregpos));
            Toast toastcustomizado = new Toast(this);
            toastcustomizado.setGravity(Gravity.TOP, 0, 0);
            toastcustomizado.setDuration(Toast.LENGTH_LONG);
            toastcustomizado.setView(el_layout);
            toastcustomizado.show();
        }
    }

    public void IniciarSesion(View view) {
        EditText usuariois = (EditText) findViewById(R.id.Usuario);
        String Linea ="";
        try {
            BufferedReader ficherointerno = new BufferedReader(new InputStreamReader(
                    openFileInput(usuariois.getText().toString()+".txt")));
            Linea = ficherointerno.readLine();
            Log.i("TAG", Linea );
            ficherointerno.close();
        } catch (IOException e) {
            Log.i("Error", "Error en el inicio de sesión");
        }
        EditText contr = (EditText) findViewById(R.id.Contr);
        if(contr.getText().toString().equals(Linea)) {
            finish();
            Intent i = new Intent(this, menuPrincipal.class);
            startActivity(i);
        }
        else{
            LayoutInflater inflater = getLayoutInflater();
            View el_layout = inflater.inflate(R.layout.ltoastregneg,(ViewGroup) findViewById(R.id.ltoastregmal));
            Toast toastcustomizado = new Toast(this);
            toastcustomizado.setGravity(Gravity.TOP, 0, 0);
            toastcustomizado.setDuration(Toast.LENGTH_LONG);
            toastcustomizado.setView(el_layout);
            toastcustomizado.show();
        }
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