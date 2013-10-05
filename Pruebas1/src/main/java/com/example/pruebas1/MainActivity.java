package com.example.pruebas1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.renderscript.Mesh;
import android.view.Menu;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import dia.upm.cconvexo.algoritmos.Andrew;
import dia.upm.cconvexo.algoritmos.AproximacionInferior;
import dia.upm.cconvexo.algoritmos.BusquedaAristas;
import dia.upm.cconvexo.algoritmos.DerivadosGraham;
import dia.upm.cconvexo.algoritmos.DivideYVencerasPreord;
import dia.upm.cconvexo.algoritmos.EliminacionPtosInteriores;
import dia.upm.cconvexo.algoritmos.GrahamNuevo;
import dia.upm.cconvexo.algoritmos.Incremental;
import dia.upm.cconvexo.gestores.GestorAlgoritmos;
import dia.upm.cconvexo.gestores.GestorConjuntoConvexo;
import dia.upm.cconvexo.interfaces.IAlgoritmoHullConvex;
import dia.upm.cconvexo.interfaces.IDelegatePaint;
import dia.upm.cconvexo.model.Arista;
import dia.upm.cconvexo.model.Punto;

import com.example.pruebas1.model.Point;
import com.example.pruebas1.view.PanelPuntosImage;

import java.util.Random;

public class MainActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener, IDelegatePaint{

    Button button;
    EditText textoPuntos;
    PanelPuntosImage imagenDibujo;
    Spinner listaExpandible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        textoPuntos = (EditText) findViewById(R.id.editText);
        imagenDibujo = (PanelPuntosImage) findViewById(R.id.SurfaceView);
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
        GestorAlgoritmos.getInstancia().setAlgoritmo(Andrew.nombre,new Andrew());
        GestorAlgoritmos.getInstancia().setAlgoritmo(BusquedaAristas.nombre,new BusquedaAristas());
        GestorAlgoritmos.getInstancia().setAlgoritmo(DivideYVencerasPreord.nombre,new DivideYVencerasPreord());
        GestorAlgoritmos.getInstancia().setAlgoritmo(EliminacionPtosInteriores.nombre,new EliminacionPtosInteriores());
        GestorAlgoritmos.getInstancia().setAlgoritmo(GrahamNuevo.nombre,new GrahamNuevo());
        GestorAlgoritmos.getInstancia().setAlgoritmo(Incremental.nombre,new Incremental());
        String[] datos = {Andrew.nombre,BusquedaAristas.nombre,DivideYVencerasPreord.nombre,EliminacionPtosInteriores.nombre,GrahamNuevo.nombre,Incremental.nombre};
        ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datos);
        listaExpandible.setAdapter(adapter);
        listaExpandible.setOnItemSelectedListener(this);

        GestorConjuntoConvexo.getInstancia().addListener(this);
        Thread myThread = new Thread(new UpdateThread());
        myThread.start();
    }

    public void onClick(View v) {
        // Perform action on click
        String puntos = textoPuntos.getText().toString();
        Integer numeroPuntos;
        if ( puntos.isEmpty()== false
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


    private void dibujaPuntos (Integer numeroPuntos, PanelPuntosImage imagenDibujo)
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
       imagenDibujo.invalidate();
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        IAlgoritmoHullConvex algoritmo = GestorAlgoritmos.getInstancia().getAlgoritmo((String)adapterView.getAdapter().getItem(i));
        GestorConjuntoConvexo.getInstancia().getConjuntoConvexo().clear();
        algoritmo.start(100);
    //  imagenDibujo.invalidate();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void paintPuntos() {
       this.updateHandler.sendEmptyMessage(9);
    }

    @Override
    public void paintPunto(Punto pto) {

    }

    @Override
    public void paintArista(Arista a1) {
        this.updateHandler.sendEmptyMessage(9);

    }

    @Override
    public void paintArista(Arista a1, int color) {
        this.updateHandler.sendEmptyMessage(0);

    }

    @Override
    public void borraRecta(Arista a1) {

    }

    @Override
    public void borraPuntos() {

    }

    @Override
    public void borraPuntoSubconjunto(Punto p) {

    }

    public Handler updateHandler = new Handler(){
        /** Gets called on every message that is received */
        // @Override
        public void handleMessage(Message msg) {

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

    }
}
