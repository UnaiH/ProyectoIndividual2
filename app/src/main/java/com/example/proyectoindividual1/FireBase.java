package com.example.proyectoindividual1;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FireBase extends FirebaseMessagingService {
    //Se genera el Token del movil que no se empleara realmente pues se hara uso de los topicos
    public void generarToken(){
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>(){
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task){
                String token;
                if (task.isSuccessful()){
                    token = task.getResult().getToken();
                    Log.i("Token", "onComplete: Token "+token);
                }
                else{
                    Log.i("Token", "onComplete: Problema en la generacion");
                }
            }
        });
    }
//Se lanzara un mensaje local al recibirse una notificacion de FCM usando como cuerpo del mensaje el de la notificacion
    @Override
    public void onMessageReceived( RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification() != null){
            NotificationManager elManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(FireBase.this, "CanalLibro");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel elCanal = new NotificationChannel("CanalLibro", "Mi Notificacion", NotificationManager.IMPORTANCE_HIGH);
                elManager.createNotificationChannel(elCanal);
            }
            builder.setContentTitle("Error comunicado")
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setAutoCancel(true);
            elManager.notify(1, builder.build());
        }
        else{
            Log.i("ErrorComunicacion", "Ha ocurrido un error en el envio del mensaje.");
        }
    }
    //Se emplea para suscribirse al topico "error"
    public void Subscribirse(){
        FirebaseMessaging.getInstance().subscribeToTopic("Error");
    }
    //Se emplea para desuscribirse al topico "error"
    public void Desuscribirse(){
        FirebaseMessaging.getInstance().unsubscribeFromTopic("Error");
    }
}
