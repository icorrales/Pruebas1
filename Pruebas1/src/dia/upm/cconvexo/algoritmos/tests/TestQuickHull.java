package dia.upm.cconvexo.algoritmos.tests;

import java.util.LinkedList;
import java.util.List;

import dia.upm.cconvexo.algoritmos.GrahamNuevo;
import dia.upm.cconvexo.algoritmos.QuickHull;
import dia.upm.cconvexo.algoritmos.QuickHullNuevo;
import dia.upm.cconvexo.gestores.GestorConjuntoConvexo;
import dia.upm.cconvexo.model.Punto;
import junit.framework.TestCase;

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
	
}
