package dia.upm.cconvexo.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.hardware.Camera;
import android.location.Address;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;


import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import dia.upm.cconvexo.R;
import dia.upm.cconvexo.algoritmos.AlgoritmoGeneradorPuntos;
import dia.upm.cconvexo.android.adapters.AlgorithmAdapter;

import dia.upm.cconvexo.android.gestores.GestorConfiguracion;
import dia.upm.cconvexo.android.gestores.GestorMensajes;

import dia.upm.cconvexo.android.view.HelpFragment;

import dia.upm.cconvexo.android.view.PanelPuntos;
import dia.upm.cconvexo.android.view.SettingsFrament;

import java.util.List;
import java.util.Random;

import dia.upm.cconvexo.gestores.GestorAlgoritmos;
import dia.upm.cconvexo.gestores.GestorConjuntoConvexo;
import dia.upm.cconvexo.interfaces.IAlgoritmoHullConvex;
import dia.upm.cconvexo.interfaces.IDelegatePaint;
import dia.upm.cconvexo.interfaces.IMessage;
import dia.upm.cconvexo.model.Arista;
import dia.upm.cconvexo.model.Punto;

public class MainActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    ImageButton button;
    EditText textoPuntos;
//    PanelPuntosImage imagenDibujo;
    PanelPuntos imagenDibujo;

    Spinner listaExpandible;
    private Intent descriptionIntent;
    private ImageButton algDescription;
    final Context context = this;
    private FrameLayout frame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (ImageButton) findViewById(R.id.button);
        textoPuntos = (EditText) findViewById(R.id.editText);
        imagenDibujo = (PanelPuntos) findViewById(R.id.SurfaceView);
        frame = (FrameLayout) findViewById(R.id.framePanel);
        algDescription = (ImageButton) findViewById(R.id.buttonInfo);
        algDescription.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, DescriptionwebviewActivity.class);
                startActivity(intent);
            }

        });
        button.setOnClickListener(this);

        // Lista de Algoritmos.
        listaExpandible = (Spinner) findViewById(R.id.expandableListView);
//        descriptionText = (TextView) findViewById(R.id.descriptionText);
        init();
    }

    private void init() {
        // Este mensaje debe ser el primero ya que debemos pasarle el contexto.
        GestorMensajes.getInstancia().setC(getApplicationContext());
        GestorAlgoritmos gestorAlgoritmos=GestorAlgoritmos.getInstancia();
        //String[] datos = gestorAlgoritmos.getClaves();
        //String[] datos = {Andrew.nombre,BusquedaAristas.nombre,DivideYVencerasPreord.nombre,EliminacionPtosInteriores.nombre,GrahamNuevo.nombre,Incremental.nombre, Jarvis.nombre,QuickHullNuevo.nombre, AproximacionInferior.nombre, AproximacionSuperior.nombre};
        ArrayAdapter adapter = new AlgorithmAdapter(this,android.R.layout.simple_list_item_1);
        listaExpandible.setAdapter(adapter);
        listaExpandible.setOnItemSelectedListener(this);
        descriptionIntent = new Intent(getApplicationContext(),DescriptionwebviewActivity.class);

    }

    public void onClick(View v) {
        // Perform action on click
        String puntos = textoPuntos.getText().toString();
        Integer numeroPuntos;
        if ( puntos.length() > 0
                )
        {
          numeroPuntos=  new Integer(puntos);
        }
        else
        {
          numeroPuntos= 0;
        }


        dibujaPuntos(numeroPuntos, imagenDibujo);
    }


    private void dibujaPuntos (Integer numeroPuntos, PanelPuntos imagenDibujo)
    {
        GestorConjuntoConvexo.getInstancia().borraListaPuntos();
       int MaxY = imagenDibujo.getHeight() -10;
       int MaxX = imagenDibujo.getWidth() -10;
       GestorConjuntoConvexo.getInstancia().setListaPuntos(AlgoritmoGeneradorPuntos.getListaPuntos(GestorConfiguracion.getInstancia().getTipoPuntosAleatorio(),MaxY,MaxX,numeroPuntos));
       imagenDibujo.refreshPuntos();
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

            // Inflate the menu items for use in the action bar
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main_activity_actions, menu);
            return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){//decide which MenuItem was pressed based on its id
            case R.id.action_settings:
               //Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
               //startActivity(intent);//start the PrefsActivity.java
                settings();
               break;
            case R.id.action_list:
              //  FireMissilesDialogFragment dialog = new  FireMissilesDialogFragment();
              //  dialog.show( this.getFragmentManager(), "NoticeDialogFragment");
               showList();
               break;
            case R.id.action_help:
                //  FireMissilesDialogFragment dialog = new  FireMissilesDialogFragment();
                //  dialog.show( this.getFragmentManager(), "NoticeDialogFragment");
                showHelp();
                break;
            }
        return true; //to execute the event here
        }

    private void showHelp() {
        Intent intent = new Intent(context,HelpWebViewActivity.class);
        startActivity(intent);


    }

    public void settings() {
        DialogFragment newFragment = new SettingsFrament();
        newFragment.show(getFragmentManager(), "Settings");
    }

    private void showList() {
        List<Punto> listaPuntos = GestorConjuntoConvexo.getInstancia().getListaPuntos();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.custom, null);
        alertDialog.setView(convertView);
        alertDialog.setTitle(getString(R.string.pointListTitle));
        ListView lv = (ListView) convertView.findViewById(R.id.listView1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        for (int i = 0; i < listaPuntos.size(); i++) {
            Punto punto =  listaPuntos.get(i);
            adapter.add(punto.toString());
        }

        lv.setAdapter(adapter);
        alertDialog.show();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        final IAlgoritmoHullConvex algoritmo;
        algoritmo = GestorAlgoritmos.getInstancia().getAlgoritmo((String)adapterView.getAdapter().getItem(i));
        executeAlgorithm(algoritmo);
    }

    public void executeAlgorithm(final IAlgoritmoHullConvex algoritmo) {
        if (GestorConfiguracion.getInstancia().isRunning() == false)
        {

            GestorConfiguracion.getInstancia().setRunning(true);
            Thread newThread = new Thread() {
                @Override
                public void run() {
                    algoritmo.init();
                    algoritmo.start(100);
                    GestorConfiguracion.getInstancia().setRunning(false);
                    imagenDibujo.refreshFinal();

                }
            };
            newThread.start();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }



    private View.OnClickListener webViewAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
//                startActivity(browserIntent);
//                webView.loadUrl("file:///android_asset/teoria.html");
                startActivity(descriptionIntent);
            }

    };

    @Override
    public void onPause()
    {
        Log.d(MainActivity.class.getName(),"Pasamos a la pausa");
        super.onPause();

        // Detectamos si el pause es por bloqueo de pantalla.
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        boolean isScreenOn = powerManager.isScreenOn();

        if (isScreenOn) {
            imagenDibujo.thread.setRunning(false);
        }

    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.d(MainActivity.class.getName(),"Volvemos al Main");
        // Esto es para cuando bloqueamos la pantalla con el botón y mueren todos los hilos.

    }

}
