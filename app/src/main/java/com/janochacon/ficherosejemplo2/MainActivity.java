package com.janochacon.ficherosejemplo2;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    //declaramos los botones
    private Button btnEscribirFichero;
    private Button btnLeerFichero;
    private Button btnEscribirSD;
    private Button btnLeerSD;
    private Button btnLeerRaw;
    private TextView salida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //enlazamos los botones
        btnEscribirFichero = (Button)findViewById(R.id.BtnEscribirFichero);
        btnLeerFichero = (Button)findViewById(R.id.BtnLeerFichero);
        btnEscribirSD = (Button)findViewById(R.id.BtnEscribirSD);
        btnLeerSD = (Button)findViewById(R.id.BtnLeerSD);
        btnLeerRaw = (Button)findViewById(R.id.BtnLeerRaw);
        salida = (TextView)findViewById(R.id.textView);

        btnEscribirFichero.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0)
            {
                try {
                    OutputStreamWriter fout =
                            new OutputStreamWriter(
                                    openFileOutput("prueba_int.txt", Context.MODE_PRIVATE));

                    fout.write("Texto de prueba.");
                    fout.close();
                    salida.setText("Fichero creado!");
                    Log.i("Ficheros", "Fichero creado!");

                } catch (Exception ex){
                    Log.e("Ficheros", "Error al escribir fichero a memoria interna");
                }
            }
        });

        btnLeerFichero.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0)
            {
                try {
                    BufferedReader fin =
                            new BufferedReader(
                                    new InputStreamReader(
                                            openFileInput("prueba_int.txt")));
                    String texto = fin.readLine();
                    fin.close();

                    Log.i("Ficheros", "Fichero leido!");
                    salida.setText("Fichero leido!");
                    Log.i("Ficheros", "Texto: " + texto);
                }catch (Exception ex){
                    Log.e("Ficheros", "Error al leer fichero desde memoria interna");
                }
            }
        });

        btnLeerRaw.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0)
            {
                String linea = "";
                try {
                    InputStream fraw =
                            getResources().openRawResource(R.raw.prueba_raw);

                    BufferedReader brin =
                            new BufferedReader(new InputStreamReader(fraw));

                    linea = brin.readLine();
                    fraw.close();

                    Log.i("Ficheros", "Fichero RAW leido!");
                    salida.setText("Fichero leido!" + linea);
                    Log.i("Ficheros", "Texto: " + linea);
                } catch (Exception ex) {
                    Log.e("Ficheros", "Error al leer fichero desde recurso raw");;
                }
            }
        });

        btnEscribirSD.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0)
            {
                boolean sdDisponible = false;
                boolean sdAccesoEscritura = false;

                //Comprobamos el estado de la memoria externa (tarjeta SD)
                String estado = Environment.getExternalStorageState();

                if (estado.equals(Environment.MEDIA_MOUNTED))
                {
                    sdDisponible = true;
                    sdAccesoEscritura = true;
                }
                else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY))
                {
                    sdDisponible = true;
                    sdAccesoEscritura = false;
                }
                else
                {
                    sdDisponible = false;
                    sdAccesoEscritura = false;
                }

                //Si la memoria externa está disponible y se puede escribir
                if (sdDisponible && sdAccesoEscritura)
                {
                    try
                    {
                        File ruta_sd_global = Environment.getExternalStorageDirectory();
                        //File ruta_sd_app_musica = getExternalFilesDir(Environment.DIRECTORY_MUSIC);

                        File f = new File(ruta_sd_global.getAbsolutePath(), "prueba_sd.txt");

                        OutputStreamWriter fout =
                                new OutputStreamWriter(
                                        new FileOutputStream(f));

                        fout.write("Texto de prueba.");
                        fout.close();

                        Log.i("Ficheros", "Fichero SD creado!");
                        salida.setText("Fichero SD creado!");
                    }
                    catch (Exception ex)
                    {
                        Log.e("Ficheros", "Error al escribir fichero a tarjeta SD");
                    }
                }
            }
        });

        btnLeerSD.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0)
            {
                try
                {
                    File ruta_sd_global = Environment.getExternalStorageDirectory();

                    File f = new File(ruta_sd_global.getAbsolutePath(), "prueba_sd.txt");

                    BufferedReader fin =
                            new BufferedReader(
                                    new InputStreamReader(
                                            new FileInputStream(f)));

                    String texto = fin.readLine();
                    fin.close();

                    Log.i("Ficheros", "Fichero SD leido!");
                    salida.setText("Fichero SD leido!" + texto);
                    Log.i("Ficheros", "Texto: " + texto);
                }
                catch (Exception ex)
                {
                    Log.e("Ficheros", "Error al leer fichero desde tarjeta SD");
                }
            }
        });
    }

}

