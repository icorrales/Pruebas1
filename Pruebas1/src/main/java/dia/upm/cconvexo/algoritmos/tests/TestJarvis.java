package dia.upm.cconvexo.algoritmos.tests;

import java.util.LinkedList;
import java.util.List;

import dia.upm.cconvexo.algoritmos.BusquedaAristas;
import dia.upm.cconvexo.algoritmos.Jarvis;
import dia.upm.cconvexo.gestores.GestorConjuntoConvexo;
import dia.upm.cconvexo.model.Arista;
import dia.upm.cconvexo.model.Punto;

public class TestJarvis extends TestAbstract {
	
	Jarvis algoritmo = null;
			

	public TestJarvis(String name) {
		super(name);
	}
		// TODO Auto-generated constructor stub
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		algoritmo = new Jarvis();
		list= new LinkedList<Punto>();
		initPuntos();
		initCierreConvexoReal();
		GestorConjuntoConvexo.getInstancia().borraListaPuntos();
		GestorConjuntoConvexo.getInstancia().setListaPuntos(list);
	}
	
	protected void tearDown() throws Exception {
		list = null;
		algoritmo = null;
		super.tearDown();
	}
	
	public void test1() throws Exception
	{
		List<Punto> cierreConvexo = new LinkedList<Punto>();
		algoritmo.start(0);
		//List listaCC= GestorConjuntoConvexo.getInstancia().getListaPuntos();
		//compruebaCierreConvexo(listaCC);
		List listaAristas = GestorConjuntoConvexo.getInstancia().getConjuntoConvexo();
		compruebaCierreConvexoPorAristas(listaAristas);
	}
	
	public void test2() throws Exception
	{
		initPuntos20();
		initCierreConvexoReal20();
		GestorConjuntoConvexo.getInstancia().borraListaPuntos();
		GestorConjuntoConvexo.getInstancia().setListaPuntos(list);
		List<Punto> cierreConvexo = new LinkedList<Punto>();
		algoritmo.start(0);
		//List listaCC= GestorConjuntoConvexo.getInstancia().getListaPuntos();
		//compruebaCierreConvexo(listaCC);
		List listaAristas = GestorConjuntoConvexo.getInstancia().getConjuntoConvexo();
		compruebaCierreConvexoPorAristasGenerico(listaAristas,cierreConvexoReal20);
	}

    public void testUnitario() throws Exception
    {
        initUnitarioAbst();
        algoritmo.start(0);
        List listaAristas = GestorConjuntoConvexo.getInstancia().getConjuntoConvexo();
        List listPuntos = GestorConjuntoConvexo.getInstancia().getConjuntoConvexoPuntos();
        assertEquals(listPuntos.size(), 1);
        assertEquals(listaAristas.size(),1);
    }

    public void test2Puntos() throws Exception
    {
        init2PuntosAbst();
        algoritmo.start(0);
        List listaAristas = GestorConjuntoConvexo.getInstancia().getConjuntoConvexo();
        List listPuntos = GestorConjuntoConvexo.getInstancia().getConjuntoConvexoPuntos();
        assertEquals(listPuntos.size(), 2);
        assertEquals(listaAristas.size(),2);
        assertTrue(listaAristas.contains(new Arista(list.get(1),list.get(0))));
    }

}
