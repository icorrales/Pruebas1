package dia.upm.cconvexo.android.view;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Toast;

import dia.upm.cconvexo.android.adapters.OnSwipeTouchListener;
import dia.upm.cconvexo.android.gestores.GestorConfiguracion;

/**
 * Created by ivan on 17/09/13.
 */
public class MySurfaceThread extends Thread{

    private SurfaceHolder sh;
    private PanelPuntos view;
    private boolean run;
    //Nuestro Constructor recibe como parametros la referencia a SurfaceHolder y nuestra SurfaceView
    public MySurfaceThread(SurfaceHolder sh, PanelPuntos view) {

        this.sh = sh;
        this.view = view;
        init();

    }

    private void init() {
        assert view != null;


/*
        view.setOnTouchListener(new OnSwipeTouchListener(view.getContext()) {
            public void onSwipeTop() {
                Toast.makeText(view.getContext(), "top", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {
                Toast.makeText(view.getContext(), "right", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeLeft() {
                Toast.makeText(view.getContext(), "left", Toast.LENGTH_SHORT).show();
                synchronized (view.disconnectCallback) {
                    view.disconnectCallback.notify();
                }


            }

            public void onSwipeBottom() {
                Toast.makeText(view.getContext(), "bottom", Toast.LENGTH_SHORT).show();
            }

            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }});
*/


    }


    //Lo utilizaremos para establecer cuando el hilo corra o no.
    public void setRunning(boolean run) {
        this.run = run;
    }

    public void run() {

//Instancia a canvas
        Canvas canvas;
//Mientras la variable run sea true va a pintar contínuamente.
        while(true) {

            if (view.refresh) {
                canvas = null;


                try {
//definimos nulo el area en donde pintar
                    canvas = this.sh.lockCanvas();
//usamos synchronized para asegurarnos que no halla ningun otro thread usando ese objeto

//Le decimos al surface view que ejecute el metodo onDraw y el cavas para dibujar
                    view.doDraw(canvas);


                } finally {
/*En caso de que halla algun error liberamos el canvas
* para no dejar el surfaceview en un estado inconsistente
*/
                    if(canvas != null)
//liberamos el canvas
                        sh.unlockCanvasAndPost(canvas);
                }
            }

/*
            if (view.new_step == false)
            {
                
                view.dialog.show();
                view.new_step = true;
            }
*/
            try {
                Thread.sleep(GestorConfiguracion.getInstancia().getSeconds() * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

/*
    public void onSwipeTop() {
        Toast.makeText(view.getContext(), "top", Toast.LENGTH_SHORT).show();
    }
    public void onSwipeRight() {
        Toast.makeText(view.getContext(), "right", Toast.LENGTH_SHORT).show();
    }
    public void onSwipeLeft() {
        Toast.makeText(view.getContext(), "left", Toast.LENGTH_SHORT).show();
        view.refresh = true;


    }
    public void onSwipeBottom() {
        Toast.makeText(view.getContext(), "bottom", Toast.LENGTH_SHORT).show();
    }

    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

*/
}

