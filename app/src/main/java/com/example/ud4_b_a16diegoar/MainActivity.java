package com.example.ud4_b_a16diegoar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    public final static String D_TAG = "*** a16diegoar ***";

    public HashMap<String, Boolean> comidas = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        comidas.put("Tortilla de patacas", true);
        comidas.put("Polbo á feira", true);
        comidas.put("Pementos de Ṕadrón", false);
        comidas.put("Lasaña", true);
        comidas.put("Onigiri", false);
        comidas.put("Macarróns", false);
        comidas.put("Milanesa de polo", false);

        mostrarSeleccion();
    }

    public void showDialog(View v) {

        final String[] arrComidas = new String[comidas.size()];
        boolean[] arrSelec = new boolean[comidas.size()];

        int i = 0;
        for (Map.Entry<String, Boolean> e : comidas.entrySet()) {
            arrComidas[i] = e.getKey();
            arrSelec[i] = e.getValue();
            i++;
        }

        AlertDialog.Builder db = new AlertDialog.Builder(this);
        db.setTitle("Selecciona comida");

        db.setMultiChoiceItems(arrComidas, arrSelec, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                comidas.put(arrComidas[which], isChecked);
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
        db.create();
        db.show();
    }

    public void showFragment(View v) {
        Fragmento fr = new Fragmento();
        FragmentManager fm = getSupportFragmentManager();
        fr.recibeInstancia(this);
        fr.show(fm, "Fragmento");
    }

    public void mostrarSeleccion() {
        TextView tv = findViewById(R.id.tvDatos);
        String txt = "";

        for (Map.Entry<String, Boolean> e : comidas.entrySet()) {
            if (e.getValue()) {
                txt += e.getKey();
                txt += "\n";
            }
        }

        tv.setText(txt);
    }
}
