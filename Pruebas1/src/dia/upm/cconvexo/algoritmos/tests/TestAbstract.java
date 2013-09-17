package dia.upm.cconvexo.algoritmos.tests;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import dia.upm.cconvexo.algoritmos.Incremental;
import dia.upm.cconvexo.model.Arista;
import dia.upm.cconvexo.model.Punto;
import junit.framework.TestCase;

public abstract class TestAbstract extends TestCase {
	
	
	protected List<Punto> list;
	protected List<Punto> cierreConvexoReal = new LinkedList<Punto> ();

	public TestAbstract(String name)
	{		
		super(name);
		
	}

	public void initPuntos() {		
		intoDataList(94,-392,list);
		intoDataList(116,-315,list);
		intoDataList(444,-349,list);
		intoDataList(360,-366,list);
		intoDataList(72,-247,list);
		intoDataList(428,-492,list);
		intoDataList(328,-207,list);
		intoDataList(496,-73,list);
		intoDataList(350,-466,list);
		intoDataList(380,-69,list);
	}
	
	public void initCierreConvexoReal()
	{
		cierreConvexoReal.add(list.get(4));
		cierreConvexoReal.add(list.get(9));
		cierreConvexoReal.add(list.get(7));
		cierreConvexoReal.add(list.get(5));
		cierreConvexoReal.add(list.get(0));
	}
	
	public void compruebaCierreConvexo(List<Punto> cierreConvexo) throws Exception
	{
		assertEquals(cierreConvexo.size(), cierreConvexoReal.size());
		for (Iterator iterator = cierreConvexo.iterator(); iterator.hasNext();) {
			Punto punto = (Punto) iterator.next();
			assertTrue(cierreConvexoReal.contains(punto));
		}
	}

	protected void intoDataList(int x, int y, List<Punto> lista) {
		Punto punto = new Punto();
		punto.setX(x);
		punto.setY(y);
		lista.add(punto);
	}

	protected void compruebaCierreConvexoPorAristas(List<Arista> conjuntoConvexo) {
		// TODO Auto-generated method stub
		assertEquals(conjuntoConvexo.size(), cierreConvexoReal.size());
		Arista a1 = null;
		Punto punto = null;
		Punto puntoSiguiente = null;
		for (int i = 0; i < cierreConvexoReal.size()-1; i++) {
			punto = cierreConvexoReal.get(i);
			puntoSiguiente = cierreConvexoReal.get(i +1);
			a1 = new Arista(punto, puntoSiguiente);
			assertTrue(conjuntoConvexo.contains(a1));			
		}
		a1 = new Arista(puntoSiguiente,cierreConvexoReal.get(0));
		assertTrue(conjuntoConvexo.contains(a1));								
	}
	
	

}
