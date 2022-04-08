package com.example.proyectoindividual1;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FireBaseRecibir extends FirebaseMessagingService {
    public FireBaseRecibir(){
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("TAG", "onMessageReceived: llamado"+remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
        }
        if (remoteMessage.getNotification() != null) {
            String titulo= remoteMessage.getNotification().getTitle();
            String mensaje= remoteMessage.getNotification().getBody();

            NotificationManager elManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"CanalLibro");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel elCanal = new NotificationChannel("CanalLibro", "Mi Notificacion", NotificationManager.IMPORTANCE_HIGH);
                elManager.createNotificationChannel(elCanal);
            }
            builder.setContentTitle(titulo)
                    .setContentText(mensaje)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setAutoCancel(true)
                    .setColor(Color.BLUE);
            elManager.notify(1, builder.build());
        }
    }
    @Override
    public void onDeletedMessages(){
        super.onDeletedMessages();
        Log.d("TAG", "onDeletedMessages: llamado");
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d("TAG", "onNewToken: llamado");
    }
}
