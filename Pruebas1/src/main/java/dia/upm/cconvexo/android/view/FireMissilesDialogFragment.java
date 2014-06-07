package dia.upm.cconvexo.android.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.pruebas1.R;

import java.util.List;

import dia.upm.cconvexo.gestores.GestorConjuntoConvexo;
import dia.upm.cconvexo.model.Punto;

/**
 * Created by ivan on 23/03/14.
 */
public class FireMissilesDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction


        List<Punto> lista=GestorConjuntoConvexo.getInstancia().getListaPuntos();

        String[] listaToString = null;
        if (lista != null)
        {

            listaToString = new String[lista.size()];
            int i =0;
            for (Punto punto : lista) {
                listaToString[i]=punto.toString();
                i++;
            }
        }
        else
        {
            listaToString = new String[1];
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.listaPuntos);
        builder.setItems(listaToString,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // The 'which' argument contains the index position
                // of the selected item
            }
        }
        );

        return builder.create();
    }
}