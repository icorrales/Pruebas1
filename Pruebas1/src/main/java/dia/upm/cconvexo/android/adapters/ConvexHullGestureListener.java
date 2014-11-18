package dia.upm.cconvexo.android.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import dia.upm.cconvexo.R;
import dia.upm.cconvexo.android.gestores.GestorConfiguracion;
import dia.upm.cconvexo.android.gestores.GestorMensajes;
import dia.upm.cconvexo.android.view.PanelPuntos;
import dia.upm.cconvexo.gestores.GestorConjuntoConvexo;
import dia.upm.cconvexo.model.Punto;

/**
 * Created by ivan on 15/06/14.
 */
public class ConvexHullGestureListener extends GestureDetector.SimpleOnGestureListener {

    View vista = null;
    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;


    public ConvexHullGestureListener(View panelPuntos) {
        assert panelPuntos != null;
        this.vista = panelPuntos;


    }



    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {


    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        this.WriteEvent(e,"SingleTapUp");
        if (GestorConfiguracion.getInstancia().isRunning())
        {
            ((PanelPuntos) this.vista).new_step = true;
        }
        else
        {
            Punto selected = GestorConjuntoConvexo.getInstancia().getSelected();
            double x = e.getX();
            double y = e.getY();
            Punto p = new Punto();
            p.setX(x);
            p.setY(y);
            if (selected == null)
            {
                if (GestorConjuntoConvexo.getInstancia().isClose(p) == false)
                {
                    GestorConjuntoConvexo.getInstancia().getListaPuntos().add(p);
                    GestorConjuntoConvexo.getInstancia().setSelected(null);
                }
            } else {
                if (GestorConjuntoConvexo.getInstancia().isClose(p) == false)
                {
                    GestorConjuntoConvexo.getInstancia().getListaPuntos().remove(selected);
                    GestorConjuntoConvexo.getInstancia().getListaPuntos().add(p);
                    GestorConjuntoConvexo.getInstancia().setSelected(null);
                }
            }
        }

        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        this.WriteEvent(e,"DoubleTapUp");
        if (GestorConfiguracion.getInstancia().isRunning())
        {
            GestorConfiguracion.getInstancia().setTipoEjecucion(R.string.paso_paso);
            Context c = this.vista.getContext();
            Crouton.makeText((Activity) c, c.getString(R.string.method) + c.getString(R.string.paso_paso), Style.INFO).show();
        }


        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        this.WriteEvent(e1,"Scroll "  + e1.toString() + " " + e2.toString());
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        this.WriteEvent(e,"LongPress");
        if (GestorConfiguracion.getInstancia().isRunning() == false)
        {
            double x = e.getX();
            double y = e.getY();
            Punto p = new Punto();
            p.setX(x);
            p.setY(y);
            if (GestorConjuntoConvexo.getInstancia().isClose(p) == false)
            {
                GestorConjuntoConvexo.getInstancia().setSelected(null);
            }
            // else ya se ha seleccionado el selected

        }

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        this.WriteEvent(e1,"Fling" + e1.toString() + " " + e2.toString());
        boolean result = false;
        if (GestorConfiguracion.getInstancia().isRunning())
        {
            chooseMovement(e1, e2, velocityX, velocityY);
        }
        return result;
    }

    /**
     * Private method called to manager finger gestures. After this method calls to particular method fo every gesture ( left, right,top,down)
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     */
    private void chooseMovement(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        try {
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                }
            } else {
                if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom();
                    } else {
                        onSwipeTop();
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    public void onSwipeRight() {

    }

    public void onSwipeLeft() {

    }

    public void onSwipeTop() {
        assert GestorConfiguracion.getInstancia().isRunning();
        GestorConfiguracion.getInstancia().setTipoEjecucion(R.string.directo);
        Context c = this.vista.getContext();
        Crouton.makeText((Activity) c, c.getString(R.string.method) + c.getString(R.string.directo), Style.INFO).show();
//        Toast.makeText(c, c.getString(R.string.method) + c.getString(R.string.directo),Toast.LENGTH_SHORT ).show();
        ((PanelPuntos) this.vista).new_step = true;
    }

    public void onSwipeBottom() {
        GestorConfiguracion.getInstancia().setTipoEjecucion(R.string.retardo);
        Context c = this.vista.getContext();
        Crouton.makeText((Activity) c, c.getString(R.string.method) + c.getString(R.string.retardo), Style.INFO).show();
//        Toast.makeText(c, c.getString(R.string.method) + c.getString(R.string.retardo),Toast.LENGTH_SHORT ).show();
        ((PanelPuntos) this.vista).new_step = true;

    }

    public boolean WriteEvent( MotionEvent event,String nameEvent) {

        assert event != null;
        assert nameEvent != null;
        Log.d(ConvexHullGestureListener.class.getName(), nameEvent);
        //Toast.makeText(vista.getContext(), nameEvent, Toast.LENGTH_SHORT).show();


        return true;
    }
}
