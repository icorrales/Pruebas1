package dia.upm.cconvexo.android.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.example.pruebas1.R;
import dia.upm.cconvexo.android.gestores.GestorConfiguracion;

import java.util.List;

import dia.upm.cconvexo.gestores.GestorConjuntoConvexo;
import dia.upm.cconvexo.gestores.GestorFranjas;
import dia.upm.cconvexo.interfaces.IDelegatePaint;
import dia.upm.cconvexo.model.Arista;
import dia.upm.cconvexo.model.Punto;

/**
 * Created by ivan on 17/09/13.
 */
public class PanelPuntos extends SurfaceView implements SurfaceHolder.Callback, IDelegatePaint, View.OnTouchListener {


    public MySurfaceThread thread;
    boolean refresh = false;
    public boolean new_step = true;
    public Object syncToken = "TOKEN";
    Thread disconnectCallback = new Thread() {
        @Override
        public void run() {
            try {
                synchronized (disconnectCallback)
                {
                    disconnectCallback.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
    public AlertDialog dialog;

    public PanelPuntos(Context context)
    {
        super(context);
        init();

    };

    public PanelPuntos(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PanelPuntos(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init() {
        this.getHolder().addCallback(this);
        thread = new MySurfaceThread(getHolder(), this);
        setFocusable(true);
        GestorConjuntoConvexo.getInstancia().addListener(this);
        this.setOnTouchListener(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                synchronized (dialog)
                {
                    dialog.notify();
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                GestorConfiguracion.getInstancia().setTipoEjecucion(R.string.directo);
                synchronized (dialog)
                {
                    dialog.notify();
                }
            }
        });
        dialog = builder.create();

        /*this.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            public void onSwipeTop() {
                Toast.makeText(getContext(), "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                Toast.makeText(getContext(), "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                Toast.makeText(getContext(), "left", Toast.LENGTH_SHORT).show();
                synchronized (disconnectCallback) { disconnectCallback.notify(); }


            }
            public void onSwipeBottom() {
                Toast.makeText(getContext(), "bottom", Toast.LENGTH_SHORT).show();
            }

            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });*/


    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

     //   thread.setRunning(true);
        thread.start();
     //   thread.setRunning(false);
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


    public void doDraw(Canvas canvas)
    {
        canvas.drawColor(Color.WHITE);

        Paint pcirculo = new Paint();
        pcirculo.setColor(Color.BLACK);
        pcirculo.setStyle(Paint.Style.FILL);
        Paint plinea= new Paint();
        pcirculo.setColor(Color.BLACK);
        pcirculo.setStyle(Paint.Style.FILL);

        List<Punto> listaPuntos= GestorConjuntoConvexo.getInstancia().getListaPuntos();
        List<Arista>listaAristas= GestorConjuntoConvexo.getInstancia().getConjuntoConvexo();
        List<Arista>aristaTmp = GestorConjuntoConvexo.getInstancia().getSubconjuntoArista();
        List<Punto> listaPuntosGeograficos = GestorConjuntoConvexo.getInstancia().getPuntosGeograficos();
        List<Punto> listaPuntosSubconjunto = GestorConjuntoConvexo.getInstancia().getSubconjuntoPuntos();


        for (java.util.Iterator puntoIterator = listaPuntos.iterator(); puntoIterator.hasNext(); ) {
            Punto next= (Punto) puntoIterator.next();
            canvas.drawCircle((float) next.getX(),(float) next.getY(),5.0f,pcirculo);

        }

        for (Arista listaArista : listaAristas) {
            Punto origen = listaArista.getOrigen();
            Punto destino = listaArista.getDestino();
            canvas.drawLine((float) origen.getX(),(float) origen.getY(),(float) destino.getX(),(float) destino.getY(),plinea);
        }

        for (Arista arista : aristaTmp) {
            pcirculo.setColor(Color.RED);
            Punto origen = arista.getOrigen();
            Punto destino = arista.getDestino();
            canvas.drawLine((float) origen.getX(),(float) origen.getY(),(float) destino.getX(),(float) destino.getY(),pcirculo);
        }

        if ( listaPuntosGeograficos.size() >=2)
        {
            int indice = 0;
            while (indice < listaPuntosGeograficos.size() -1)
            {
                Punto origen = listaPuntosGeograficos.get(indice);
                Punto destino = listaPuntosGeograficos.get(indice + 1);
                canvas.drawLine((float) origen.getX(),(float) origen.getY(),(float) destino.getX(),(float) destino.getY(),pcirculo);
                indice= indice +1 ;
            }
        }

        for (Punto punto: listaPuntosSubconjunto) {
            canvas.drawCircle((float) punto.getX(),(float) punto.getY(),5.0f,pcirculo);
        }

        if (GestorFranjas.getInstancia().isactivate())
        {
            GestorFranjas instancia = GestorFranjas.getInstancia();

            float minX = (float) instancia.getPuntoMin().getX();
            long ancho = instancia.anchoFranja();
            canvas.drawLine((float) instancia.getPuntoMin().getX(),(float) instancia.getPuntoMin().getY(),(float) instancia.getPuntoMax().getX(),(float) instancia.getPuntoMax().getY(),plinea);

            for (int i = 0; i <= instancia.getNumeroFranjas(); i++) {
                float coord_x = minX + (ancho*i);
                canvas.drawLine(coord_x, 0, coord_x, canvas.getMaximumBitmapHeight(), plinea);
            }
        }

        refresh = false;
        new_step = false;

    }

    public void doCircle(Canvas canvas)
    {
        Paint pcirculo= new Paint();
        canvas.drawColor(Color.WHITE);
        pcirculo.setColor(Color.BLACK);
        pcirculo.setStyle(Paint.Style.FILL);
        canvas.drawCircle((float) 200,(float) 200,50.0f,pcirculo);
    }

    @Override
    public void paintPuntos() {
        refresh();

    }

    @Override
    public void paintPunto(Punto pto) {
        refresh();
    }

    @Override
    public void paintArista(Arista a1) {
        refresh();
    }

    @Override
    public void paintArista(Arista a1, int color) {
        refresh();
    }

    @Override
    public void borraRecta(Arista a1) {
        refresh();
    }

    @Override
    public void borraPuntos() {
        refresh();

    }

    @Override
    public void borraPuntoSubconjunto(Punto p) {
        refresh();

    }

    public void refresh() {

        if (GestorConfiguracion.getInstancia().getTipoEjecucion() == R.string.directo) {
            refresh = false; sleepProcess();}
        else if  (GestorConfiguracion.getInstancia().getTipoEjecucion() == R.string.retardo) { refresh = true; sleepProcess();}
        else {

            new_step = false;

            try {
                synchronized (dialog)
                {
                dialog.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            refresh = true;
            sleepProcess();

        }

    }

    public void sleepProcess() {
        int delay = GestorConfiguracion.getInstancia().getSeconds();
        while (refresh) {
        try {
            Thread.sleep(delay * 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        }
    }

    public void refreshFinal()
    {
        refresh = true;
        sleepProcess();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        Toast.makeText(getContext(), event.getX() + "-" + event.getY(),Toast.LENGTH_SHORT ).show();

        return true;
    }
}
