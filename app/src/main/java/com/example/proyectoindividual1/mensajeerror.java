package com.example.proyectoindividual1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

//Esta clase gestiona la interfaz que permite al usuario identificado comunicar un error.
public class mensajeerror extends AppCompatActivity implements View.OnClickListener {
    private String usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Llama a idiomas y pantalla para realizar los cambios de la interfaz acorde a lo especificado en las preferencias.
        new Idiomas().setIdioma(this);
        new Pantalla().cambiarPantallaMenus(this);
        if (savedInstanceState!= null) {
            usuario= savedInstanceState.getString("usuario");
        }
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            usuario = extras.getString("usuario");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajeerror);
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, menuPrincipal.class);
        i.putExtra("usuario", usuario);
        setResult(RESULT_OK, i);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.salidapan)
                .setCancelable(false)
                .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(i);
                        finish();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("usuario",usuario);
    }
    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("usuario",usuario);
    }
    @Override
    public void onClick(View view) {
        String aEnviar="Enviado";
        Log.i("Mensaje", "onClick: "+aEnviar);
        EditText mens = (EditText) findViewById(R.id.escritomens);
        aEnviar=""+mens.getText().toString();
        Log.i("Mensaje", "onClick: "+aEnviar);
        mens.setText("");
        Data.Builder data = new Data.Builder();
        //Se introducen los datos necesarios a pasar a ConexionPHP
        data.putString("mens",aEnviar);
        Log.i("Datos", "onClick: "+aEnviar);
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(mensajeFCMPHP.class)
                .setInputData(data.build())
                .build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo)
                    {
                        //Si se puede iniciar sesión porque devulve true se cambiará la actividad cerrando en la que se encuentra. Si la devolución es null o no es true se mostrará un toast en la interfaz actual.
                        if(workInfo != null && workInfo.getState().isFinished())
                        {
                            String inicio = workInfo.getOutputData().getString("result");
                            Log.i("TAG", "onChanged: "+inicio);
                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);
    }
}