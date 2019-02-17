package com.vladan.dragdropimages01;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

public class dialigopuntaje extends DialogFragment {
    ProgressBar barraprogreso;
    int puntos = 0 ;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
    View view = inflater.inflate(R.layout.puntaje_layout,null);
        barraprogreso = (ProgressBar) view.findViewById(R.id.progressBar);
        barraprogreso.setProgress(this.puntos);
        builder.setView(view)
                .setTitle("")
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Toast.makeText(getActivity(),"Apreto Aceptar.",Toast.LENGTH_LONG).show();
                dialogInterface.cancel();
            }
        });
        return builder.create();

    }
    public void setpuntos(int _puntos){
        this.puntos = _puntos;
    }
}
