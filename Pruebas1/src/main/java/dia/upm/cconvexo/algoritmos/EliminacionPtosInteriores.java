package dia.upm.cconvexo.algoritmos;

import java.util.LinkedList;
import java.util.List;

import dia.upm.cconvexo.gestores.GestorConjuntoConvexo;
import dia.upm.cconvexo.model.Punto;

public class EliminacionPtosInteriores extends AbstractAlgoritmo {
	
	public final static String nombre = "PtosInteriores";

	public EliminacionPtosInteriores() {
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public void start(int delay)
	{
	
/*	c_convexo := { p(1), ..., p(N) }; (* inicializa la lista circular con los puntos de entrada *) 
	if N>=3 then (* si N<3 no hay que hacer nada *) */
		List<Punto> cierreConvexo = GestorConjuntoConvexo.getInstancia().getListaPuntos();
		List<Punto> subconjuntoConvexo = (List<Punto>) ((LinkedList<Punto>) GestorConjuntoConvexo.getInstancia().getListaPuntos()).clone();
		assert cierreConvexo != null;
		assert subconjuntoConvexo != null;
		int N = cierreConvexo.size();
		if (N>=3)
		{
			
		

/* 
	for i:=1 to N-2 do
	for j:=i+1 to N-1 do
	for k:=j+1 to N do
	if orientacion(p(i),p(j),p(k))=LINEA then
	pto_inter := punto intermedio de p(i),p(j),p(k);
	eliminar pto_inter de la lista c_convexo
	endif
	pto:= primer punto de c_convexo;
	repeat
	if pto != p(i) and pto != p(j) and pto != p(k) then
	if pto es interior al tri�ngulo formado por p(i),p(j),p(k) then
	eliminar pto de la lista c_convexo
	endif
	endif
	pto := siguiente punto de pto en la lista c_convexo
	until c_convexo se haya recorrido entera
	endfor
	endfor
	endfor
	*/
	for (int i = 0; i < N - 2; i++) {
		
		for (int j = i+1; j < N - 1; j++) {
			
			for (int k = 0; k < N; k++) {
				
				if (this.orientation(cierreConvexo.get(i), cierreConvexo.get(j), cierreConvexo.get(k)) == FunctionsGlobals.LINEA)
				{
					
				}
				for (int k2 = 0; k2 < subconjuntoConvexo.size(); k2++) {
					Punto pto = subconjuntoConvexo.get(k2);
					if (pto != cierreConvexo.get(i) && pto != cierreConvexo.get(j) && pto != cierreConvexo.get(k))
					{
						if (this.estaEnTri(cierreConvexo.get(i), cierreConvexo.get(j), cierreConvexo.get(k), pto))
						{
							subconjuntoConvexo.remove(pto);
							GestorConjuntoConvexo.getInstancia().borrarPuntoSubconjunto(pto);
						}
					}					
				}
				
			}
			
		}
		
	}	
	
	}
	/*
	(* Ahora hay que ordenar angularmente c_convexo *) 
	origen_ordenacion := centroide de tres puntos cualquiera de c_convexo;
	ordenar ( c_convexo, comparar_angulos(p,q,origen_ordenacion) );
	endif*/
		
	}

}
