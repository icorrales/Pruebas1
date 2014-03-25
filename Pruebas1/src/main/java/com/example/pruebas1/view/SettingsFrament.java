package com.example.pruebas1.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.NumberPicker;

import com.example.pruebas1.R;
import com.example.pruebas1.gestores.GestorConfiguracion;

/**
 * Created by ivan on 24/03/14.
 */
public class SettingsFrament extends DialogFragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment
        View layout=inflater.inflate(R.layout.settings,container, false);
        NumberPicker pick1 = (NumberPicker) layout.findViewById(R.id.settings_pick1);
        pick1.setMaxValue(1000);
        pick1.setMinValue(1);
        pick1.setValue(5);
        pick1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.d("Se cambia la configuracion de picker Retardo a " + newVal,"SettingsFragment");
                GestorConfiguracion.getInstancia().setSeconds(newVal);
            }
        });
        NumberPicker pick2 = (NumberPicker) layout.findViewById(R.id.settings_pick2);
        pick2.setMaxValue(1000);
        pick2.setMinValue(1);
        pick2.setValue(5);
        pick2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.d("Se cambia la configuracion de picker Franjas a " + newVal,"SettingsFragment");
                GestorConfiguracion.getInstancia().setFranjas(newVal);
            }
        });


        return layout;
    }

    /** The system calls this only when creating the layout in a dialog. */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }


}
