package com.example.ud4_b_a16diegoar;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.HashMap;
import java.util.Map;

public class Fragmento extends DialogFragment {

    MainActivity ma;
    HashMap<String, Boolean> comidas;

    public void recibeMapa(HashMap<String, Boolean> comidas) {
        this.comidas = comidas;
    }

    public void recibeInstancia(MainActivity ma) {
        this.ma = ma;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final String[] arrComidas = new String[comidas.size()];
        boolean[] arrSelec = new boolean[comidas.size()];

        int i = 0;
        for (Map.Entry<String, Boolean> e : comidas.entrySet()) {
            arrComidas[i] = e.getKey();
            arrSelec[i] = e.getValue();
            i++;
        }

        AlertDialog.Builder db = new AlertDialog.Builder(getContext());
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
                ma.mostrarSeleccion();
            }
        });

        db.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return db.create();
    }
}
