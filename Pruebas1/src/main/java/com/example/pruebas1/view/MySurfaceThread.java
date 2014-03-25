package com.example.pruebas1.view;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by ivan on 17/09/13.
 */
public class MySurfaceThread extends Thread {

    private SurfaceHolder sh;
    private PanelPuntos view;
    private boolean run;
    //Nuestro Constructor recibe como parametros la referencia a SurfaceHolder y nuestra SurfaceView
    public MySurfaceThread(SurfaceHolder sh, PanelPuntos view) {
        this.sh = sh;
        this.view = view;

    }
    //Lo utilizaremos para establecer cuando el hilo corra o no.
    public void setRunning(boolean run) {
        this.run = run;
    }

    public void run() {
        super.run();
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
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

