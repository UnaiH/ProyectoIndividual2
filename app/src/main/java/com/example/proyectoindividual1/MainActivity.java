package com.example.proyectoindividual1;
//Esta clase se emplea como pantalla de inicio al ejecutar la aplicación

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private int valor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        crearLista();
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
        new permisos().permisosInternet(MainActivity.this, MainActivity.this);
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
    private void crearLista(){
        //Se crea un archivo local con una serie de duplas autor-libro que se emplea para el widget de recomendación diaria de autor y obra.
        String[] autores=new String[]{"Stephen King, IT","Stephen King, Carrie","Stephen King, The Green Mile","Stephen King, Revival","Gabriel García Márquez, Cien años de soledad", "Ernest Hemingway, Fiesta", "Ernest Hemingway, Por quien doblan las campanas","Ernest Hemingway, Old man and the sea","Charles Dickens, Oliver Twist", "Charles Dickens, A Tale of Two Cities","Edgar Allan Poe, The Black Cat", "Edgar Allan Poe, The Bells","William Shakespeare, Hamlet","William Shakespeare, Otelo","William Shakespeare, Macbeth","Migel de Cervantes, Don Quijote de la Mancha", "Migel de Cervantes, Entremeses","Mark Twain, The Adventures of Tom Sawyer", "Oscar Wilde, The Picture of Dorian Gray", "George Orwell, 1984", "George Orwell, Animal Farm","George Orwell, Homage to Catalonia","Fiódor Dostoievski, Crime and Punishment", "Fiódor Dostoievski, CThe Brothers Karamazov", "Aldous Huxley, Brave New World", "Pio Baroja, El árbol de la ciencia", "Pio Baroja, La busca","Migel de Unamuno, Niebla", "Migel de Unamuno, San Manuel Bueno mártir","Goethe, Fausto", "Mary Shelley, Frankenstein", "Victor Hugo, Les Misérables", "Alejandro Dumas, Les Trois Mousquetaires", "Alejandro Dumas, Le Comte de Monte-Cristo","Julio Verne, Around the World in Eighty Days", "Julio Verne, Around the World in Eighty Days", "Julio Verne, Twenty Thousand Leagues Under the Sea", "León Tolstói, War and Peace","León Tolstói, Ana Karenina", "Bram Stoker, Dracula", "The Call of Cthulhu, H.P.Lovecraft", "The Shadow Out of Time, H.P.Lovecraft", "Isabel Allende, La casa de los espíritus", "Agatha Christie, Death on the Nile", "Agatha Christie, Murder on the Orient Express","Mario Vargas Llosa, El pez en el agua", "J. R. R. Tolkien, The Hobbit","J. R. R. Tolkien, The Lord of the Rings"};
        try {
            BufferedWriter fichero = new BufferedWriter(new OutputStreamWriter(openFileOutput("recomautores.txt", Context.MODE_PRIVATE)));
            int itr = 0;
            while (itr < autores.length) {
                fichero.write(autores[itr]);
                fichero.newLine();
                itr++;
            }
            fichero.close();
        }catch (IOException e) {
            Log.i("Error", "onClickAnadir");
        }
    }
    public void onClickServidor (View v)
    {
        EditText usuarios = (EditText) findViewById(R.id.Usuario);
        EditText contr = (EditText) findViewById(R.id.Contr);
        Data.Builder data = new Data.Builder();
        data.putString("usuario",usuarios.getText().toString());
        data.putString("contrasena",contr.getText().toString());
        //Se realiza la comparación de la contraseña del EditText y la guardada en la base de datos remotas mediante la conexión a una clase llamada ConexionPHP. Si inicio fuera null o se devolviera false como respuesta se lanza un toast sino se "inicia sesion" en la aplicacion.
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(ConexionPHP.class)
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
                                    new permisos().pedirpermisosLocalizar(MainActivity.this, MainActivity.this);
                                    Intent i = new Intent(getApplicationContext(), menuPrincipal.class);
                                    i.putExtra("usuario", usuarios.getText().toString());
                                    setResult(RESULT_OK, i);
                                    finish();
                                    startActivity(i);
                                } else {
                                    LayoutInflater inflater = getLayoutInflater();
                                    View el_layout = inflater.inflate(R.layout.ltoastregneg, (ViewGroup) findViewById(R.id.ltoastregmal));
                                    Toast toastcustomizado = new Toast(getApplicationContext());
                                    toastcustomizado.setGravity(Gravity.TOP, 0, 0);
                                    toastcustomizado.setDuration(Toast.LENGTH_LONG);
                                    toastcustomizado.setView(el_layout);
                                    toastcustomizado.show();
                                }
                            }
                            else {
                                LayoutInflater inflater = getLayoutInflater();
                                View el_layout = inflater.inflate(R.layout.ltoastregneg, (ViewGroup) findViewById(R.id.ltoastregmal));
                                Toast toastcustomizado = new Toast(getApplicationContext());
                                toastcustomizado.setGravity(Gravity.TOP, 0, 0);
                                toastcustomizado.setDuration(Toast.LENGTH_LONG);
                                toastcustomizado.setView(el_layout);
                                toastcustomizado.show();
                            }
                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);
    }
}