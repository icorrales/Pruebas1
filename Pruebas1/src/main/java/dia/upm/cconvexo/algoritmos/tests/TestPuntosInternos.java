package dia.upm.cconvexo.algoritmos.tests;

import java.util.LinkedList;
import java.util.List;

import dia.upm.cconvexo.algoritmos.EliminacionPtosInteriores;
import dia.upm.cconvexo.algoritmos.Jarvis;
import dia.upm.cconvexo.gestores.GestorConjuntoConvexo;
import dia.upm.cconvexo.model.Punto;

/**
 * Created by ivan on 6/12/14.
 */
public class TestPuntosInternos extends TestAbstract {

    EliminacionPtosInteriores algoritmo = null;

    public TestPuntosInternos(String name) {
        super(name);
    }

    /* (non-Javadoc)
 * @see junit.framework.TestCase#setUp()
 */
    protected void setUp() throws Exception {
        super.setUp();
        algoritmo = new EliminacionPtosInteriores();
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

    public void initConjuntoPuntosComparador()
    {
        list.clear();
        intoDataList(707,369,list);
        intoDataList(130,328,list);
        intoDataList(658,492,list);
        intoDataList(640,215,list);
        intoDataList(576,104,list);
    }



    public void testComparador() throws Exception
    {
        initConjuntoPuntosComparador();
        Punto pFinal = new Punto(list.get(1).getX(),list.get(1).getY());
        Punto centroide = algoritmo.busquedaPuntoMenorOrdenada(list);
        algoritmo.ordenarAngularmente(list,centroide);
        assertTrue(list.get(list.size() -1).equals(pFinal));

    }

}
