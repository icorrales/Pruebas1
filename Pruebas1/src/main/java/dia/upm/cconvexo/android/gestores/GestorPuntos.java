package dia.upm.cconvexo.android.gestores;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import dia.upm.cconvexo.interfaces.IDelegatePaint;
import dia.upm.cconvexo.model.Punto;

/**
 * Class to implement a puntos manager.
 * It is implemented with a Singlenton Pattern
 */
public class GestorPuntos {

    static GestorPuntos instancia = null;
    private List<Punto> listaPuntos = null;
    private List<Punto> conjuntoConvexo = null;
    private List<Punto> subconjuntoPuntos = null;
    private List<IDelegatePaint> listaListener = null;

    public Punto getSelected() {
        return selected;
    }

    public void setSelected(Punto selected) {
        this.selected = selected;
    }

    private Punto selected;


    /**
     * Private constructo for GestorPuntos
     */
    private GestorPuntos() {
        // TODO Auto-generated constructor stub
        listaListener = new LinkedList<IDelegatePaint>();
        conjuntoConvexo = new LinkedList<Punto>();

    }

    /**
     * Method to implement Singlenton pattern in a manager.
     * @return GestorPuntos instance.
     */
    public static GestorPuntos getInstancia ()
    {
        if (instancia == null)
        {
            instancia = new GestorPuntos();
        }
        assert instancia != null;
        return instancia;
    }

    /**
     * Get currect point list
     * @return
     */
    public List<Punto> getListaPuntos() {
        return listaPuntos;
    }

    /**
     * Set listaPuntos as the new point list to manage.
     * @param listaPuntos
     */
    public void setListaPuntos(List<Punto> listaPuntos) {
        assert listaPuntos != null;
        assert listaListener != null;
        this.listaPuntos = listaPuntos;

        pintaPuntos();

    }

    private void pintaPuntos() {
        for (Iterator<IDelegatePaint> iterator = listaListener.iterator(); iterator.hasNext();) {
            IDelegatePaint delegate = iterator.next();
            delegate.paintPuntos();

        }
    }



    /**
     * Method to test that there are one point selected and that should be painted over the canvas.
     */
    public void setSelected() {

        pintaPuntos();
    }

    public void addPunto(Punto p) {
        assert p != null;
        listaPuntos.add(p);
        pintaPuntos();
    }
}
