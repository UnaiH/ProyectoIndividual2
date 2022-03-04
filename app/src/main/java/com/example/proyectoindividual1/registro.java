package com.example.proyectoindividual1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class registro extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);
    }

    public void onClickRegistrarse(View view) {
        EditText usuarioret = (EditText) findViewById(R.id.UsuarioRegistro);
        EditText contret = (EditText) findViewById(R.id.RegContr);
        EditText contretrep = (EditText) findViewById(R.id.RegContrRep);
        Log.i("TAG", contret.getText().toString());
        Log.i("TAG", contretrep.getText().toString());
        if (contret.getText().toString().equals(contretrep.getText().toString())) {
            try {
                OutputStreamWriter fichero = new OutputStreamWriter(openFileOutput(usuarioret.getText().toString()+".txt", Context.MODE_PRIVATE));
                fichero.write(contret.getText().toString());
                fichero.close();
            } catch (IOException e) {
                Log.i("Error", "Error al registrarse");
            }
            finish();
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("regok", 1);
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

    @Override
    public void onClick(View view) {

    }
}
