package dia.upm.cconvexo.android.view;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.NumberPicker;

import dia.upm.cconvexo.R;
import dia.upm.cconvexo.android.gestores.GestorConfiguracion;

/**
 * Created by ivan on 13/12/14.
 */
public class HelpFragment extends DialogFragment {

        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout to use as dialog or embedded fragment
            View layout=inflater.inflate(R.layout.descriptionwebview,container, false);
            WebView wv = (WebView) layout.findViewById(R.id.webview_parent);
            wv.loadUrl("https://www.google.es");


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
