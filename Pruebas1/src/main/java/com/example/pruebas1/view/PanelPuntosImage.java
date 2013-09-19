package com.example.pruebas1.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.util.List;

import dia.upm.cconvexo.gestores.GestorConjuntoConvexo;
import dia.upm.cconvexo.model.Punto;

/**
 * Created by ivan on 18/09/13.
 */
public class PanelPuntosImage extends ImageView {
    public PanelPuntosImage(Context context) {
        super(context);
    }

    public PanelPuntosImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PanelPuntosImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw( Canvas canvas)
    {
        Paint pcirculo = new Paint();
        pcirculo.setColor(Color.BLACK);
        pcirculo.setStyle(Paint.Style.FILL);
        List<Punto> listaPuntos= GestorConjuntoConvexo.getInstancia().getListaPuntos();


        for (java.util.Iterator puntoIterator = listaPuntos.iterator(); puntoIterator.hasNext(); ) {
            Punto next= (Punto) puntoIterator.next();
            canvas.drawPoint((float) next.getX(),(float) next.getY(),pcirculo);

        }
    }
        
        
}
