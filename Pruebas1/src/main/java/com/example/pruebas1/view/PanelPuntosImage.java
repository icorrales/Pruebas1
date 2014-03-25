package com.example.pruebas1.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.util.List;

import dia.upm.cconvexo.gestores.GestorConjuntoConvexo;
import dia.upm.cconvexo.interfaces.IDelegatePaint;
import dia.upm.cconvexo.model.Arista;
import dia.upm.cconvexo.model.Punto;

/**
 * Created by ivan on 18/09/13.
 */
public class PanelPuntosImage extends ImageView implements IDelegatePaint {

    Canvas canvas;
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
        this.canvas=canvas;
        canvas.drawColor(Color.WHITE);

        Paint pcirculo = new Paint();
        pcirculo.setColor(Color.BLACK);
        pcirculo.setStyle(Paint.Style.FILL);
        List<Punto> listaPuntos= GestorConjuntoConvexo.getInstancia().getListaPuntos();
        List<Arista>listaAristas= GestorConjuntoConvexo.getInstancia().getConjuntoConvexo();
        List<Arista>aristaTmp = GestorConjuntoConvexo.getInstancia().getSubconjuntoArista();



        for (java.util.Iterator puntoIterator = listaPuntos.iterator(); puntoIterator.hasNext(); ) {
            Punto next= (Punto) puntoIterator.next();
            canvas.drawCircle((float) next.getX(),(float) next.getY(),3.0f,pcirculo);

        }

        for (Arista listaArista : listaAristas) {
            Punto origen = listaArista.getOrigen();
            Punto destino = listaArista.getDestino();
            canvas.drawLine((float) origen.getX(),(float) origen.getY(),(float) destino.getX(),(float) destino.getY(),pcirculo);
        }

        for (Arista arista : aristaTmp) {
            pcirculo.setColor(Color.RED);
            Punto origen = arista.getOrigen();
            Punto destino = arista.getDestino();
            canvas.drawLine((float) origen.getX(),(float) origen.getY(),(float) destino.getX(),(float) destino.getY(),pcirculo);
        }

    }

    public void borrarBMP()
    {

    }


    @Override
    public void paintPuntos() {
      this.invalidate();
    }

    @Override
    public void paintPunto(Punto pto) {
        Paint pcirculo = new Paint();
        pcirculo.setColor(Color.RED);
        pcirculo.setStyle(Paint.Style.FILL);
        this.canvas.drawCircle((float) pto.getX(),(float) pto.getY(),3.0f,pcirculo);
    }

    @Override
    public void paintArista(Arista a1) {
        this.postInvalidate();
    }

    @Override
    public void paintArista(Arista a1, int color) {
        this.postInvalidate();
    }

    @Override
    public void borraRecta(Arista a1) {
        Paint pcirculo = new Paint();
        pcirculo.setColor(Color.WHITE);
        pcirculo.setStyle(Paint.Style.FILL);
        Punto origen = a1.getOrigen();
        Punto destino = a1.getDestino();
        canvas.drawLine((float) origen.getX(),(float) origen.getY(),(float) destino.getX(),(float) destino.getY(),pcirculo);
        pcirculo.setColor(Color.BLACK);
        canvas.drawCircle((float) origen.getX(), (float) origen.getY(), 3.0f, pcirculo);
        canvas.drawCircle((float) destino.getX(),(float) destino.getY(),3.0f,pcirculo);

    }

    @Override
    public void borraPuntos() {
        this.invalidate();


    }

    @Override
    public void borraPuntoSubconjunto(Punto p) {

    }

    private int x=0;
    public void update() {
        if(x < 200)
            x++;
        else
            x=0;
    }
}
