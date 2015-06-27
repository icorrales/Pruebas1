package dia.upm.cconvexo.android.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.support.v4.view.GestureDetectorCompat;
import android.view.View;
import android.widget.Toast;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import dia.upm.cconvexo.R;
import dia.upm.cconvexo.android.MainActivity;
import dia.upm.cconvexo.android.adapters.ConvexHullGestureListener;
import dia.upm.cconvexo.android.gestores.GestorConfiguracion;

import java.util.List;

import dia.upm.cconvexo.android.gestores.GestorMensajes;
import dia.upm.cconvexo.android.gestores.GestorPuntos;
import dia.upm.cconvexo.gestores.GestorConjuntoConvexo;
import dia.upm.cconvexo.gestores.GestorFranjas;
import dia.upm.cconvexo.interfaces.IDelegatePaint;
import dia.upm.cconvexo.model.Arista;
import dia.upm.cconvexo.model.Circle;
import dia.upm.cconvexo.model.Punto;

/**
 * Created by ivan on 17/09/13.
 */
public class PanelPuntos extends SurfaceView implements SurfaceHolder.Callback, IDelegatePaint, View.OnTouchListener {


    private Handler mhandler = null;
    public MySurfaceThread thread;
    boolean refresh = false;
    public boolean new_step = true;
    public Object syncToken = "TOKEN";
    private GestureDetectorCompat mDetector;
    private Context c;
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
        c = context;
        init();

    };

    public PanelPuntos(Context context, AttributeSet attrs) {
        super(context, attrs);
        c = context;
        init();
    }

    public PanelPuntos(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        c = context;
        init();
    }

    public void init() {
        this.getHolder().addCallback(this);
        mDetector = new GestureDetectorCompat(this.getContext(),new ConvexHullGestureListener(this));

        setFocusable(true);
        GestorConjuntoConvexo.getInstancia().addListener(this);
//        this.setOnTouchListener(this);
        mhandler = new Handler(Looper.getMainLooper()) {

            /*
      * handleMessage() defines the operations to perform when
      * the Handler receives a new Message to process.
      */
            @Override
            public void handleMessage(Message inputMessage) {

                if ( GestorMensajes.getInstancia().getHistoricoMensajes().size() > 0)
                {
                    Crouton crouton = Crouton.makeText((Activity) getContext(), GestorMensajes.getInstancia().getLastMessage(), Style.INFO);
                    crouton.setConfiguration(new Configuration.Builder().setDuration(GestorConfiguracion.getInstancia().getSeconds() * 50).build());
                    crouton.show();
                }
                else
                {
                    if (GestorConfiguracion.getInstancia().getTipoEjecucion() == R.string.paso_paso)
                    {
                        Crouton crouton = Crouton.makeText((Activity) getContext(), R.string.nextStep, Style.INFO);
                        crouton.setConfiguration(new Configuration.Builder().setDuration(GestorConfiguracion.getInstancia().getSeconds() * 50).build());
                        crouton.show();
                    }
                }
            }

        };
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        thread = new MySurfaceThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
        refresh = true;
        sleepProcess();

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
            pcirculo.setColor(Color.RED);
            canvas.drawCircle((float) punto.getX(),(float) punto.getY(),5.0f,pcirculo);
        }

        if (GestorConjuntoConvexo.getInstancia().getSelected() != null)
        {
            Punto punto = GestorConjuntoConvexo.getInstancia().getSelected();
            pcirculo.setColor(Color.RED);
            canvas.drawCircle((float) punto.getX(),(float) punto.getY(),10.0f,pcirculo);

        }


        pcirculo.setColor(Color.RED);
        pcirculo.setStyle(Paint.Style.STROKE);

        if ( GestorConjuntoConvexo.getInstancia().getCircleTmp() != null)
        {
            Circle circleTmp = GestorConjuntoConvexo.getInstancia().getCircleTmp();
            canvas.drawCircle((float)circleTmp.centro.getX(),(float)circleTmp.centro.getY(),(float)circleTmp.radius,pcirculo);
        }

        pcirculo.setColor(Color.BLUE);
        if ( GestorConjuntoConvexo.getInstancia().getMEC() != null)
        {
            Circle mec = GestorConjuntoConvexo.getInstancia().getMEC();
            canvas.drawCircle((float)mec.centro.getX(),(float)mec.centro.getY(),(float)mec.radius,pcirculo);
        }





        refresh = false;


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

    @Override
    public void mensajeDescripcion(String s) {
/*        if (GestorConfiguracion.getInstancia().getTipoEjecucion() == R.string.paso_paso) {
            Toast.makeText(c,s,Toast.LENGTH_LONG).show();
        }
        else if (GestorConfiguracion.getInstancia().getTipoEjecucion() == R.string.retardo) {
            Toast.makeText(c,s,Toast.LENGTH_SHORT).show();
        } */

    }

    public void refresh() {

        if (GestorConfiguracion.getInstancia().getTipoEjecucion() == R.string.directo) {
            refresh = false;}
        else{

            if  (GestorConfiguracion.getInstancia().getTipoEjecucion() == R.string.retardo)
                { refresh = true;
                  mhandler.sendEmptyMessage(0);
                    sleepProcess();
                }
            else {
                new_step = false;
                refresh = true;
                int delay = GestorConfiguracion.getInstancia().getSeconds();
                mhandler.sendEmptyMessage(0);
                while (!new_step) {
                    try {
                        Thread.sleep(delay * 100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }



        }

    }



    public void sleepProcess() {
        int delay = GestorConfiguracion.getInstancia().getSeconds();
        while (refresh) {
        try {
            if (GestorConfiguracion.getInstancia().isRunning())
            {

                if (GestorMensajes.getInstancia().getHistoricoMensajes().size() == 0)
                {
                    Thread.sleep(delay * 50);
                }
                else
                {
                    Thread.sleep(delay * 300);
                }
            }
            else
            {
                Thread.sleep(delay * 10);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        }
    }

    public void refreshPuntos() {

        GestorConjuntoConvexo.getInstancia().initCC();
        GestorMensajes.getInstancia().getHistoricoMensajes().clear();
        refresh = true;
        sleepProcess();
    }


    public void refreshFinal()
    {
        if ( GestorConfiguracion.getInstancia().isRunning())
        {
            GestorMensajes.getInstancia().getHistoricoMensajes().clear();
            GestorMensajes.getInstancia().addMessage(R.string.cc_c_1);
            mhandler.sendEmptyMessage(0);
        }
        GestorConjuntoConvexo.getInstancia().borraSubconjuntoArista();
        GestorConjuntoConvexo.getInstancia().borraSubconjuntoPuntos();
        refresh = true;
        sleepProcess();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        Toast.makeText(getContext(), event.getX() + "-" + event.getY(),Toast.LENGTH_SHORT ).show();
        return true;
    }

    @Override
    /**
     * Method to implement user touch in screen, depending if convex hull is being calculated or not.
     */
    public boolean onTouchEvent(MotionEvent event){

        Log.d(PanelPuntos.class.getName(), "Event Action: " + event.toString());
        boolean touchEvent = false;
        if (GestorConfiguracion.getInstancia().isRunning() == false)
        {

            touchEvent = this.mDetector.onTouchEvent(event);
            this.refreshPuntos();
        }
        else
        {

            touchEvent = this.mDetector.onTouchEvent(event);

        }

        Log.d(PanelPuntos.class.getName(), "Return touch event: " + touchEvent);
        return touchEvent;

    }



}
