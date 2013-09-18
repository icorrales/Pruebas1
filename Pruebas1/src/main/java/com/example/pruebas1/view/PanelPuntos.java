package com.example.pruebas1.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

import com.example.pruebas1.gestores.GestorPuntos;

import java.util.Iterator;
import java.util.List;

import dia.upm.cconvexo.gestores.GestorConjuntoConvexo;
import dia.upm.cconvexo.model.Punto;

/**
 * Created by ivan on 17/09/13.
 */
public class PanelPuntos extends SurfaceView implements SurfaceHolder.Callback {


    public MySurfaceThread thread;

    public PanelPuntos(Context context)
    {
        super(context);
        this.getHolder().addCallback(this);

    };

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {


        thread = new MySurfaceThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        Paint pcirculo = new Paint();
        pcirculo.setColor(Color.BLACK);
        pcirculo.setStyle(Paint.Style.FILL);
        List<Punto> listaPuntos= GestorConjuntoConvexo.getInstancia().getListaPuntos();
        for (java.util.Iterator puntoIterator = listaPuntos.iterator(); puntoIterator.hasNext(); ) {
            Punto next= (Punto) puntoIterator.next();
            canvas.drawCircle((float) next.getX(),(float) next.getY(),1,pcirculo);

        }

    }
}
