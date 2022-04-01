package com.example.proyectoindividual1;

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

//Esta actividad gestiona lo relacionado con el registro como su nombre indica.
public class registro extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new Idiomas().setIdioma(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);
    }

    public void onClickRegistrarse(View view) {
        //Al clicar en registrarse se recoge lo escrito en los EditText para después comprobar que la contraseña coincide las dos veces que se escriben. Si coinciden se crea un archivo que se llama usuario.txt y contendrá la contraseña en su interior.
        EditText usuarioret = (EditText) findViewById(R.id.UsuarioRegistro);
        EditText contret = (EditText) findViewById(R.id.RegContr);
        EditText contretrep = (EditText) findViewById(R.id.RegContrRep);
        if (contret.getText().toString().equals(contretrep.getText().toString())) {
            BDExterna base = new BDExterna(registro.this);
            SQLiteDatabase db = base.getWritableDatabase();
            boolean existe =false;
            if (db==null){
                //Si hay un error se comunica mediante una notificación.
                enviarMensajeLocal(getResources().getString(R.string.error),getResources().getString(R.string.errorreg));
            }
            else{
                existe=base.registrarse(usuarioret.getText().toString(),contret.getText().toString());
            }
            if (!existe) {
                //Una vez creado el usuario  se lanzará una notificación y se mostrará un toast en el inicio porque se le pasará un valor en el intent.
                enviarMensajeLocal(getResources().getString(R.string.correg),getResources().getString(R.string.menscorreg));
                finish();
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("regok", 1);
                startActivity(i);
            }
            else{
                enviarMensajeLocal(getResources().getString(R.string.contract),getResources().getString(R.string.contractcontenido));
                finish();
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("regok", 1);
                startActivity(i);
            }
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
        //En caso de pulsar el botón de retroceder se lanzará un Dialog preguntando si realmente se quiere salir de la pantalla.
        Intent i = new Intent(this, MainActivity.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.salidapan)
                .setCancelable(false)
                .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        startActivity(i);
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
    private void enviarMensajeLocal(String titulo, String contenido){
        NotificationManager elManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(registro.this, "CanalLibro");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel elCanal = new NotificationChannel("CanalLibro", "Mi Notificacion", NotificationManager.IMPORTANCE_HIGH);
            elManager.createNotificationChannel(elCanal);
        }
        builder.setContentTitle(titulo)
                .setContentText(contenido)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true);
        elManager.notify(1, builder.build());
    }
}
