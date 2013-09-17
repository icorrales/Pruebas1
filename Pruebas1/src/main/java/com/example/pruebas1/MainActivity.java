package com.example.pruebas1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.app.Activity;
import android.renderscript.Mesh;
import android.view.Menu;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import dia.upm.cconvexo.model.Punto;

import com.example.pruebas1.model.Point;

import java.util.Random;

public class MainActivity extends Activity implements View.OnClickListener{

    Button button;
    EditText textoPuntos;
    SurfaceView imagenDibujo;
    ListView listaExpandible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        textoPuntos = (EditText) findViewById(R.id.editText);
        imagenDibujo = (SurfaceView) findViewById(R.id.SurfaceView);
        button.setOnClickListener(this);


        final String[] datos =
                new String[]{"Algoritmo1","Algoritmo2","Algoritmo3","Algoritmo4","Algoritmo5"};

//        ArrayAdapter<String> adaptador;
//        adaptador = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, datos);
//        listaExpandible = (ListView) findViewById(R.id.expandableListView);
//        listaExpandible.setAdapter(adaptador);

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


    private void dibujaPuntos (Integer numeroPuntos, SurfaceView imagenDibujo)
    {

       Random r=new Random();
       int MaxY = imagenDibujo.getHeight();
       int MaxX = imagenDibujo.getWidth();
       for (int i=0; i<numeroPuntos ; i++)
       {
           Punto p = new Punto();
           p.setY(r.nextInt(MaxY));
           p.setX(r.nextInt(MaxX));



       }
        imagenDibujo.invalidate();
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    

    
    
}
