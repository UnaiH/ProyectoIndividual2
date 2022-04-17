package com.example.proyectoindividual1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.core.content.ContextCompat;
//Es un Broadcast para la alrma.
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent servicio1 = new Intent(context, notificar.class);
        servicio1.setData(Uri.parse("custom://" + System.currentTimeMillis()));
        ContextCompat.startForegroundService(context,servicio1);
    }
}
