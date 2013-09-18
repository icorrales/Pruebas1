package dia.upm.cconvexo.algoritmos;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import dia.upm.cconvexo.gestores.GestorConjuntoConvexo;
import dia.upm.cconvexo.global.ComparadorAbscisas;
import dia.upm.cconvexo.model.Arista;
import dia.upm.cconvexo.model.Punto;

public class Incremental extends AbstractAlgoritmo {

	
	public final static String nombre = "Incremental";
	
	
//	ordenar ( {p(1),...,p(N)} , abs_crec_ord_crec(p,q) );
//	(* ordena los p(i) de menor a mayor abscisa *) 
//	(* los puntos con abscisas iguales los ordena de menor a mayor ordenada *) 
//	c_convexo := { p(1) };
//	vertice_derecho := p(1);
//	for  i:=2  to  N  do
//	v_sop_sup := soporte_superior ( c_convexo, vertice_derecho, p(i) );
//	v_sop_inf := soporte_inferior ( c_convexo, vertice_derecho, p(i) );
//	(* a continuaci�n se actualiza c_convexo con p(i) *) 
//	siguiente de v_sop_inf en c_convexo := p(i);
//	anterior de p(i) en c_convexo := v_sop_inf;
//	siguiente de p(i) en c_convexo := v_sop_sup;
//	anterior de v_sop_sup en c_convexo := p(i);
//	vertice_derecho := p(i)
//	(* p(i) es el v�rtice m�s a la derecha del nuevo cierre *) 
//	endfor
	public void start(int delay)
	{
		List<Punto> listaPuntos = GestorConjuntoConvexo.getInstancia().getListaPuntos();
		List<Punto> listaPuntosCopia = (List<Punto>) ((LinkedList<Punto>) listaPuntos).clone();
		ordenacion_abscisas(listaPuntosCopia);		
		Iterator<Punto> iterator = listaPuntosCopia.iterator();
		Punto vertice_derecho = iterator.next();
		assert vertice_derecho != null;
		List<Punto> c_convexo = new LinkedList<Punto>();
		c_convexo.add(vertice_derecho);
		for (Iterator iterator2=iterator;iterator2.hasNext();) {
			
			Punto punto = (Punto) iterator2.next();
			Punto v_sop_sup = soporte_superior(c_convexo,vertice_derecho,punto);
			Punto v_sop_inf = soporte_inferior(c_convexo,vertice_derecho,punto);
			int indice_v_sop_inf = c_convexo.indexOf(v_sop_inf);
			int copia_indice_v_sop_inf = indice_v_sop_inf; 
			int indice_v_sop_sup = c_convexo.indexOf(v_sop_sup);
			
			// Hay que borrar todos las aristas entre el vertice soporte inferior y el superior, pero
			// para ello hay q ordenar la lista de tal forma que el soporte superior siempre est� detras del inferior.
			while ( indice_v_sop_inf < indice_v_sop_sup)
			{
				Arista arista = new Arista(c_convexo.get(indice_v_sop_inf),c_convexo.get(indice_v_sop_inf +1));
				GestorConjuntoConvexo.getInstancia().borraArista(arista);
				if ( indice_v_sop_inf +1 != indice_v_sop_sup)
				{
					c_convexo.remove(indice_v_sop_inf +1);
				}
				indice_v_sop_inf++;
			}
			
			c_convexo.add(copia_indice_v_sop_inf +1, punto);			
			GestorConjuntoConvexo.getInstancia().anadeArista(new Arista(v_sop_inf,punto));
			GestorConjuntoConvexo.getInstancia().anadeArista(new Arista(punto,v_sop_sup));
			
			
			vertice_derecho = punto;			
		}
		
		
		
		
	}


public void ordenacion_abscisas(List<Punto> listaPuntosCopia) {
	Collections.sort(listaPuntosCopia,new ComparadorAbscisas());
}


public Punto soporte_superior(List<Punto> c_convexo,
			Punto vertice_derecho, Punto punto) {
	Punto puntoSoporte = funcion_soporte(c_convexo, vertice_derecho, punto, FunctionsGlobals.POSITIVA);
	assert puntoSoporte != null;
	return puntoSoporte;
	}


public Punto soporte_inferior(List<Punto> c_convexo,
			Punto vertice_derecho, Punto punto) {
		
		Punto puntoSoporte = funcion_soporte(c_convexo, vertice_derecho, punto, FunctionsGlobals.NEGATIVA);
		assert puntoSoporte != null;
		return puntoSoporte;
	}


/*function soporte_superior (cierre, vertice_derecho, pto_exterior) :
	(* Devuelve el v�rtice soporte superior del cierre respecto al punto exterior *)
	(* vertice_derecho es el v�rtice m�s a la derecha del cierre *)
	if  cierre tiene un solo punto  then
	return vertice_derecho
	elseif  cierre tiene dos puntos  then
	sig_derecho := siguiente de vertice_derecho en cierre;
	if orientacion(pto_exterior,vertice_derecho,sig_derecho)=POSITIVA then
	return vertice_derecho
	else
	return sig_derecho
	endif
	else (* cierre tiene tres o m�s v�rtices *) 
	v := vertice_derecho;
	sig_v := siguiente de v en cierre;
	while  orientacion(pto_exterior, v, sig_v) == POSITIVA  do
	v := sig_v;
	sig_v := siguiente de v en cierre;
	endwhile
	return v
	endif
*/
private Punto funcion_soporte (List<Punto> c_convexo, Punto vertice_derecho,
		Punto punto, int ejeGiro) {
	// TODO Auto-generated method stub
	assert c_convexo != null;
	assert vertice_derecho != null;
	assert punto != null;
	assert vertice_derecho.getX() <= punto.getX();
	assert c_convexo.contains(vertice_derecho);
	
	if (c_convexo.size() == 1)
	{
		return vertice_derecho;
	}
	else if (c_convexo.size() == 2)
	{
		
		Punto siguiente_v_der = siguiente(vertice_derecho, c_convexo);		
		if (orientation(punto, vertice_derecho, siguiente_v_der)== ejeGiro)
		{
			return vertice_derecho;
		}
		else
		{
			return siguiente_v_der;
		}
			
	}
	else {// c_convexo.size >2
		Punto v = vertice_derecho;
		Punto siguiente_v = siguiente(vertice_derecho, c_convexo);
		while (orientation(punto, v, siguiente_v) == ejeGiro)
		{
			v = siguiente_v;
			siguiente_v = siguiente(v, c_convexo);
		}
		return v;
	}
}
}