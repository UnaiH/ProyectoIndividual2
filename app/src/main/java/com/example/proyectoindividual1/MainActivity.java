package com.example.proyectoindividual1;
//Esta clase se emplea como pantalla de inicio al ejecutar la aplicación
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private int valor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Se carga el idioma al inicio en la actividad principal por si acaso se ha cambiado el idioma del sistema. Si no se ha especificado el idioma en preferencias se pondrá en inglés por considerarse un idioma oficial.
        valor=0;
        new Idiomas().setIdioma(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Existe un toast para indicar que al registrarse y volver a esta el registro se ha realizado correctamente.
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
        };
    }

    public void IniciarSesion(View view) {
        //OnClick del botón para iniciar sesión una vez se clica se lee lo escrito en EditText en el que se escribe el Usuario. Se comprueba que el archivo que tiene como nombre el usuario.txt y se comprueba que el fichero tiene la contraseña indicada en el EditText
        EditText usuarios = (EditText) findViewById(R.id.Usuario);
        EditText contr = (EditText) findViewById(R.id.Contr);
        BDExterna base = new BDExterna(MainActivity.this);
        SQLiteDatabase db = base.getWritableDatabase();
        boolean inicio =false;
        if (db==null){
            NotificationManager elManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"CanalLibro");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel elCanal = new NotificationChannel("CanalLibro", "Mi Notificacion", NotificationManager.IMPORTANCE_HIGH);
                elManager.createNotificationChannel(elCanal);
            }
            builder.setContentTitle("Error")
                    .setContentText(String.valueOf(getResources().getString(R.string.errorsesion)))
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setAutoCancel(true);
            elManager.notify(1, builder.build());

            finish();
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("regok", 1);
            startActivity(i);
        }
        else {
            inicio = base.iniciarSesion(usuarios.getText().toString(), contr.getText().toString());
            //Se realiza la comparación de la contraseña del EditText y la guardada en el archivo .txt.
            if (inicio) {
                Intent i = new Intent(this, menuPrincipal.class);
                i.putExtra("usuario", usuarios.getText().toString());
                setResult(RESULT_OK, i);
                finish();
                startActivity(i);
            } else {
                //Si la constraseña no coincide o en su defecto no existe el usuario se mostrará un toast indicando que las contraseñas no coinciden.
                LayoutInflater inflater = getLayoutInflater();
                View el_layout = inflater.inflate(R.layout.ltoastregneg, (ViewGroup) findViewById(R.id.ltoastregmal));
                Toast toastcustomizado = new Toast(this);
                toastcustomizado.setGravity(Gravity.TOP, 0, 0);
                toastcustomizado.setDuration(Toast.LENGTH_LONG);
                toastcustomizado.setView(el_layout);
                toastcustomizado.show();
            }
        }
    }
    public void Registrarse(View view) {
        //El boton registrarse redirige a la actividad del registro.
        finish();
        Intent i = new Intent(this, registro.class);
        startActivity(i);
    }
    @Override
    public void onBackPressed() {
        //En caso de pulsar el botón de retroceder se lanzará un Dialog preguntando si realmente se quiere salir.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.salida)
                .setCancelable(false)
                .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
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
    public void onClick(View view) {

    }
}