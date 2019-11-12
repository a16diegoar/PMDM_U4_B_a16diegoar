package com.example.ud4_b_a16diegoar;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    public final static String D_TAG = "*** a16diegoar ***";

    private static String nomFile = "comidas.txt";

    public static ArrayList<String> comidas = new ArrayList<>();
    public static ArrayList<Boolean> selec = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getFilesDir() no devuelve la ruta correcta
        String rutaFile = "/data/data/com.example.ud4_b_a16diegoar/files/";

        //Crear el archivo si no existe
        File f = new File(rutaFile+ nomFile);
        Log.d(D_TAG, "Comprobando archivo " + f.getAbsolutePath());
        if (!f.exists()) {

            escribeOpcion("Tortilla de patacas", true);
            escribeOpcion("Polbo á feira", false);
            escribeOpcion("Pementos de Ṕadrón", false);
            escribeOpcion("Lasaña", true);
            escribeOpcion("Onigiri", false);
            escribeOpcion("Macarróns", false);
            escribeOpcion("Milanesa de polo", false);
        }

        escribeArchivo();

        mostrarSeleccion();
    }

    public void showDialog(View v) {
        AlertDialog.Builder db = new AlertDialog.Builder(this);
        db.setTitle("Selecciona comida");

        leerOpciones();
        final String[] arrComidas = new String[comidas.size()];
        final boolean[] arrSel = new boolean[selec.size()];
        for (int i = 0; i < selec.size(); i++) {
            arrSel[i] = selec.get(i);
            arrComidas[i] = comidas.get(i);
        }

        db.setMultiChoiceItems(arrComidas, arrSel, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                selec.set(which, isChecked);
                arrSel[which] = isChecked;
            }
        });

        db.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mostrarSeleccion();
            }
        });
        db.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        db.create().show();
    }

    public void showFragment(View v) {

    }

    public void escribeOpcion(String linea, boolean selec) {
        //String rutaFile = "/data/data/com.example.ud4_b_a16diegoar/files/";

        try (OutputStreamWriter osw = new OutputStreamWriter(openFileOutput(nomFile, Context.MODE_APPEND))) {

            String l = linea;
            l += ", ";
            l += (selec ? "1" : "0");
            l += "\n";
            osw.write(l);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void leerOpciones() {
        comidas = new ArrayList<>();
        selec = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(nomFile)))) {

            String l = null;
            do {
                l = br.readLine();
                if (l == null) {
                    break;
                }

                Pattern p = Pattern.compile("^(.*), ([0|1])");
                Matcher m = p.matcher(l);

                if (m.find()) {
                    comidas.add(m.group(1));

                    selec.add(m.group(2).matches("1"));
                }

            } while (true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void escribeArchivo() {

    }

    public void mostrarSeleccion() {
        TextView tv = findViewById(R.id.tvDatos);
        String txt = "";
        for (int i = 0; i < comidas.size(); i++) {
            if (selec.get(i)) {
                txt += comidas.get(i);
                txt += "\n";
            }
        }

        tv.setText(txt);
    }
}
