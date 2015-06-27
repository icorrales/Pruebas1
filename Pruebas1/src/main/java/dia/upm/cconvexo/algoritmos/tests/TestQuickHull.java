package dia.upm.cconvexo.algoritmos.tests;

import java.util.LinkedList;
import java.util.List;

import dia.upm.cconvexo.algoritmos.QuickHullNuevo;
import dia.upm.cconvexo.gestores.GestorConjuntoConvexo;
import dia.upm.cconvexo.model.Arista;
import dia.upm.cconvexo.model.Punto;

public class TestQuickHull extends TestAbstract {
	
	protected QuickHullNuevo algoritmo = null;

	public TestQuickHull(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		algoritmo = new QuickHullNuevo();
		list = new LinkedList<Punto>();
		initPuntos();
		initCierreConvexoReal();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		list = null;
		algoritmo = null;
		super.tearDown();
	}
	
	public void test1() throws Exception
	{
		GestorConjuntoConvexo.getInstancia().setListaPuntos(list);
		List<Punto> cierreConvexo = new LinkedList<Punto>();
		algoritmo.start(0);
//		compruebaCierreConvexo(cierreConvexo);
		compruebaCierreConvexoPorAristas(GestorConjuntoConvexo.getInstancia().getConjuntoConvexo());
	}
	
	public void test2() throws Exception
	{
		initPuntos20();
		initCierreConvexoReal20();
		GestorConjuntoConvexo.getInstancia().borraListaPuntos();
		GestorConjuntoConvexo.getInstancia().setListaPuntos(list);
		algoritmo.start(0);

//		compruebaCierreConvexo(cierreConvexoReal20);
		List listaAristas = GestorConjuntoConvexo.getInstancia().getConjuntoConvexo();
		compruebaCierreConvexoPorAristasGenerico(listaAristas,cierreConvexoReal20);
	}

    public void testUnitario() throws Exception
    {
        initUnitarioAbst();
        algoritmo.start(0);
        List listaAristas = GestorConjuntoConvexo.getInstancia().getConjuntoConvexo();
        List listPuntos = GestorConjuntoConvexo.getInstancia().getConjuntoConvexoPuntos();
        assertEquals(0,listPuntos.size());
        assertEquals(0,listaAristas.size());
    }

    public void test2Puntos() throws Exception
    {
        init2PuntosAbst();
        algoritmo.start(0);
        List listaAristas = GestorConjuntoConvexo.getInstancia().getConjuntoConvexo();
        List listPuntos = GestorConjuntoConvexo.getInstancia().getConjuntoConvexoPuntos();
        assertEquals(2,listPuntos.size());
        assertEquals(1,listaAristas.size());
        Arista aDirecta = new Arista(list.get(0),list.get(1));
        Arista aInversa = new Arista(list.get(1),list.get(0));
        assertTrue(listaAristas.contains( aDirecta) || listaAristas.contains(aInversa));
    }
	
}
