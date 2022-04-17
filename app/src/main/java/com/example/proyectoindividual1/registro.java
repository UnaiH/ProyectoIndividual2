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
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

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
            Data.Builder data = new Data.Builder();
            data.putString("usuario",usuarioret.getText().toString());
            data.putString("contrasena",contret.getText().toString());
            OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(ConexionPHPInsertUsuario.class)
                    .setInputData(data.build())
                    .build();
            WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                    .observe(this, new Observer<WorkInfo>() {
                        @Override
                        public void onChanged(WorkInfo workInfo)
                        {
                            if(workInfo != null && workInfo.getState().isFinished())
                            {
                                String inicio = workInfo.getOutputData().getString("result");
                                Log.i("TAG", "onChanged: "+inicio);
                                if (inicio!=null) {
                                    if (inicio.equals("true")) {
                                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                        i.putExtra("usuario", usuarioret.getText().toString());
                                        setResult(RESULT_OK, i);
                                        finish();
                                        startActivity(i);
                                    } else {
                                        enviarMensajeLocal(getResources().getString(R.string.error),getResources().getString(R.string.errorreg));
                                        finish();
                                        Intent i = new Intent(getApplicationContext(), registro.class);
                                        i.putExtra("regok", 1);
                                        startActivity(i);
                                    }
                                }
                                else {
                                    enviarMensajeLocal(getResources().getString(R.string.error),getResources().getString(R.string.errorreg));
                                }
                            }
                        }
                    });
            WorkManager.getInstance(this).enqueue(otwr);
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
