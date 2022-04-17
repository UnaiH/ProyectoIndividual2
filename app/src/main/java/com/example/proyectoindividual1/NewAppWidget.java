package com.example.proyectoindividual1;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.Random;

//Esta clase se encarga de la gestión del widget que tiene la aplicación.
public class NewAppWidget extends AppWidgetProvider {
    private static ArrayList<String> lista;
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        //Se carga el binomio autor-libro de un archivo local donde se guardan llamando a la clase cargar_fichero que se encarga de gestionar esto.
        lista=new cargar_fichero().obtenerListaAutores(context);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        //Se selecciona aleatoriamente el binomio a mostrar en el interfaz del widget.
        int indice = new Random().nextInt(lista.size());
        views.setTextViewText(R.id.textorecomendarwidget, context.getText(R.string.recomendacion)+lista.get(indice));
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}