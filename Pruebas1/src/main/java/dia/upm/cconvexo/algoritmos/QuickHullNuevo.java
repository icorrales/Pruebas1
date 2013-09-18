package dia.upm.cconvexo.algoritmos;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import dia.upm.cconvexo.gestores.GestorConjuntoConvexo;
import dia.upm.cconvexo.global.Triangulo;
import dia.upm.cconvexo.model.Arista;
import dia.upm.cconvexo.model.Punto;

public class QuickHullNuevo extends AbstractAlgoritmo {
	
public final static String nombre = "QuickHull";
	
	public QuickHullNuevo()
	{
		
	}
	
	@Override
	public void start(int delay) {
		List<Punto> listaPuntos =GestorConjuntoConvexo.getInstancia().getListaPuntos();
		assert listaPuntos != null;
		if (listaPuntos.size() > 1)
		{
			Punto pMenorAbs = busquedaPuntoMenorAbscisa(listaPuntos);
			assert pMenorAbs != null;
			Punto q = pMenorAbs;
			for (Iterator<Punto> iterator = listaPuntos.iterator(); iterator.hasNext();) {
				Punto punto =  iterator.next();
				if (punto != pMenorAbs)
				{
					if (this.actualizar_min_angulo(pMenorAbs, punto, q))
					{
						q = punto;
					}
				}
				
			}
			Arista arista = new Arista(pMenorAbs,q);
			GestorConjuntoConvexo.getInstancia().anadeArista(arista);
			quickhull(listaPuntos,pMenorAbs,q);
		}
		
	}

	private void quickhull(List<Punto> listaPuntos, Punto menorAbs, Punto q) {
		assert listaPuntos != null;
		assert menorAbs != null && q != null;
		Punto h = menorAbs;
		for (Iterator<Punto> iterator = listaPuntos.iterator(); iterator.hasNext();) {
			Punto punto = iterator.next();
			Triangulo t1 = new Triangulo(punto,menorAbs,q);
			Triangulo t2 = new Triangulo(h,menorAbs,q);
			if (t1.area()>t2.area())
			{
				h= punto;
			}
			else if (t1.area() == t2.area())
			{
				if (orientation(menorAbs, h, punto) == FunctionsGlobals.POSITIVA)
				{
					h=punto;					
				}
			}			
		}
		if (h == menorAbs) { // Todos los p(i) están en la recta {p,q} 
			
				Arista arista = new Arista(menorAbs,q);
				GestorConjuntoConvexo.getInstancia().anadeArista(arista);
		}
		else 
			{	
			List<Punto> puntoDerecha = new LinkedList<Punto>();
			List<Punto> puntoIzquierda = new LinkedList<Punto>();
			
			for (Iterator<Punto> iterator = listaPuntos.iterator(); iterator
					.hasNext();) {
				Punto punto = iterator.next();
				if (orientation(menorAbs,h,punto) != FunctionsGlobals.NEGATIVA)
				{
					puntoDerecha.add(punto);
				}
				if (orientation(h,q,punto) != FunctionsGlobals.NEGATIVA)
				{
					puntoIzquierda.add(punto);
				}
			}
			quickhull(puntoDerecha, menorAbs, h);
			quickhull(puntoIzquierda, h, q);						
		}
		
		
		
	}

	private int area(Punto punto, Punto menorAbs, Punto q) {
		// TODO Auto-generated method stub
		return 0;
	}

}

