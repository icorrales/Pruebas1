package dia.upm.cconvexo.android.test;

import java.util.LinkedList;

import dia.upm.cconvexo.algoritmos.Andrew;
import dia.upm.cconvexo.algoritmos.tests.*;
import dia.upm.cconvexo.android.gestores.GestorPuntos;
import dia.upm.cconvexo.gestores.GestorConjuntoConvexo;
import dia.upm.cconvexo.model.Punto;

/**
 * Created by ivan on 9/11/14.
 */
public class TestPuntos extends TestAbstract {

    Punto p_aprox = null;
    Punto p_no_aprox = null;

    public TestPuntos ()
    {

    }

    /* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
    protected void setUp() throws Exception {
        super.setUp();

        list = new LinkedList<Punto>();
        initPuntos();
        p_aprox = new Punto();
        p_aprox.setY(249);
        p_aprox.setX(70);
        p_no_aprox = new Punto();
        p_no_aprox.setY(230);
        p_no_aprox.setX(70);

    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        p_aprox = null;
        list = null;
        super.tearDown();
    }


    /**
     * Test for checking that distance between point is correct.
     */
    public void test1()
    {
        GestorConjuntoConvexo.getInstancia().setListaPuntos(this.list);
        assertTrue(GestorConjuntoConvexo.getInstancia().isClose(list.get(0)));

        assertTrue(GestorConjuntoConvexo.getInstancia().isClose(p_aprox));
        assertFalse(GestorConjuntoConvexo.getInstancia().isClose(p_no_aprox));
    }
}
