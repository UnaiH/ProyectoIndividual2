package com.example.proyectoindividual1;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.io.IOException;
import java.io.OutputStreamWriter;
//Esta actividad gestiona lo relacionado con el registro como su nombre indica.
public class registro extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);
    }

    public void onClickRegistrarse(View view) {
        //Al clicar en registrarse se recoge lo escrito en los EditText para después comprobar que la contraseña coincide las dos veces que se escriben. Si coinciden se crea un archivo que se llama usuario.txt y contendrá la contraseña en su interior.
        EditText usuarioret = (EditText) findViewById(R.id.UsuarioRegistro);
        EditText contret = (EditText) findViewById(R.id.RegContr);
        EditText contretrep = (EditText) findViewById(R.id.RegContrRep);
        if (contret.getText().toString().equals(contretrep.getText().toString())) {
            try {
                OutputStreamWriter fichero = new OutputStreamWriter(openFileOutput(usuarioret.getText().toString()+".txt", Context.MODE_PRIVATE));
                fichero.write(contret.getText().toString());
                fichero.close();
            } catch (IOException e) {
                Log.i("Error", "Error al registrarse");
            }
            //Una vez creado se lanzará una notificación y se mostrará un toast en el inicio porque se le pasará un valor en el intent.
            NotificationManager elManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(registro.this,"CanalLibro");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel elCanal = new NotificationChannel("CanalLibro", "Mi Notificacion", NotificationManager.IMPORTANCE_HIGH);
                elManager.createNotificationChannel(elCanal);
            }
            builder.setContentTitle("Registro correcto")
                    .setContentText("Se ha registrado el usuario correctamente: "+usuarioret)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setAutoCancel(true);
            elManager.notify(1, builder.build());

            finish();
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("regok", 1);
            startActivity(i);
        }
        else{
            //Se mostrará un toast indicando que no se ha registrado corectamente.
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
    @Override
    public void onBackPressed() {
        //En caso de pulsar el botón de retroceder se lanzará un Dialog preguntando si realmente se quiere salir.
        //En caso de pulsar el botón de retroceder se lanzará un Dialog preguntando si realmente se quiere salir.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Seguro que deseas salir de la aplicacion?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
