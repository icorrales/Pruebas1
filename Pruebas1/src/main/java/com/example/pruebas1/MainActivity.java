package com.example.pruebas1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.pruebas1.adapters.AlgorithmAdapter;
import com.example.pruebas1.gestores.GestorConfiguracion;
import com.example.pruebas1.gestores.GestorPuntos;
import com.example.pruebas1.view.FireMissilesDialogFragment;
import com.example.pruebas1.view.PanelPuntos;
import com.example.pruebas1.view.SettingsActivity;
import com.example.pruebas1.view.SettingsFrament;

import java.util.List;
import java.util.Random;

import dia.upm.cconvexo.algoritmos.Andrew;
import dia.upm.cconvexo.algoritmos.AproximacionInferior;
import dia.upm.cconvexo.algoritmos.AproximacionSuperior;
import dia.upm.cconvexo.algoritmos.BusquedaAristas;
import dia.upm.cconvexo.algoritmos.DivideYVencerasPreord;
import dia.upm.cconvexo.algoritmos.EliminacionPtosInteriores;
import dia.upm.cconvexo.algoritmos.GrahamNuevo;
import dia.upm.cconvexo.algoritmos.Incremental;
import dia.upm.cconvexo.algoritmos.Jarvis;
import dia.upm.cconvexo.algoritmos.QuickHullNuevo;
import dia.upm.cconvexo.gestores.GestorAlgoritmos;
import dia.upm.cconvexo.gestores.GestorConjuntoConvexo;
import dia.upm.cconvexo.interfaces.IAlgoritmoHullConvex;
import dia.upm.cconvexo.interfaces.IDelegatePaint;
import dia.upm.cconvexo.model.Arista;
import dia.upm.cconvexo.model.Punto;

public class MainActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener, IDelegatePaint{

    Button button;
    EditText textoPuntos;
//    PanelPuntosImage imagenDibujo;
    PanelPuntos imagenDibujo;

    Spinner listaExpandible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        textoPuntos = (EditText) findViewById(R.id.editText);
//        imagenDibujo = (PanelPuntosImage) findViewById(R.id.SurfaceView);
        imagenDibujo = (PanelPuntos) findViewById(R.id.SurfaceView);
        button.setOnClickListener(this);

        // Lista de Algoritmos.
        listaExpandible = (Spinner) findViewById(R.id.expandableListView);



        init();






//        ArrayAdapter<String> adaptador;
//        adaptador = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, datos);
//        listaExpandible = (ListView) findViewById(R.id.expandableListView);
//        listaExpandible.setAdapter(adaptador);

    }

    private void init() {
        GestorAlgoritmos gestorAlgoritmos=GestorAlgoritmos.getInstancia();
        //String[] datos = gestorAlgoritmos.getClaves();
        //String[] datos = {Andrew.nombre,BusquedaAristas.nombre,DivideYVencerasPreord.nombre,EliminacionPtosInteriores.nombre,GrahamNuevo.nombre,Incremental.nombre, Jarvis.nombre,QuickHullNuevo.nombre, AproximacionInferior.nombre, AproximacionSuperior.nombre};
        ArrayAdapter adapter = new AlgorithmAdapter(this,android.R.layout.simple_list_item_1);
        listaExpandible.setAdapter(adapter);
        listaExpandible.setOnItemSelectedListener(this);

//        GestorConjuntoConvexo.getInstancia().addListener(this);
//        Thread myThread = new Thread(new UpdateThread());
//        myThread.start();
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

       Random r=new Random();
       int MaxY = imagenDibujo.getHeight();
       int MaxX = imagenDibujo.getWidth();
       for (int i=0; i<numeroPuntos ; i++)
       {
           Punto p = new Punto();
           p.setY(r.nextInt(MaxY));
           p.setX(r.nextInt(MaxX));
           GestorConjuntoConvexo.getInstancia().getListaPuntos().add(p);



       }
       imagenDibujo.refreshFinal();
        
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
            }
        return true; //to execute the event here
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
        alertDialog.setTitle("Lista Puntos");
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
        IAlgoritmoHullConvex algoritmo = GestorAlgoritmos.getInstancia().getAlgoritmo((String)adapterView.getAdapter().getItem(i));
        GestorConjuntoConvexo.getInstancia().initGestor();

        algoritmo.start(100);
        imagenDibujo.refreshFinal();
    //  imagenDibujo.invalidate();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void paintPuntos() {
  //     this.updateHandler.sendEmptyMessage(9);
    }

    @Override
    public void paintPunto(Punto pto) {

    }

    @Override
    public void paintArista(Arista a1) {
 //       this.updateHandler.sendEmptyMessage(9);

        imagenDibujo.refresh();

    }

    @Override
    public void paintArista(Arista a1, int color) {
 //       this.updateHandler.sendEmptyMessage(0);
        imagenDibujo.refresh();

    }

    @Override
    public void borraRecta(Arista a1) {
        imagenDibujo.refresh();
    }

    @Override
    public void borraPuntos() {

    }

    @Override
    public void borraPuntoSubconjunto(Punto p) {

    }

//    public Handler updateHandler = new Handler(){
        /** Gets called on every message that is received */
        // @Override
  /*      public void handleMessage(Message msg) {

            imagenDibujo.update();
            imagenDibujo.invalidate();
            super.handleMessage(msg);
        }
    };

    public class UpdateThread implements Runnable {

        @Override
        public void run() {
            while(true){
                MainActivity.this.updateHandler.sendEmptyMessage(0);
            }
        }

    } */
}
