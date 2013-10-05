package dia.upm.cconvexo.algoritmos;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dia.upm.cconvexo.gestores.GestorConjuntoConvexo;
import dia.upm.cconvexo.global.ComparadorAngulos;
import dia.upm.cconvexo.model.Arista;
import dia.upm.cconvexo.model.Punto;

public abstract class DerivadosGraham extends AbstractAlgoritmo {

	public DerivadosGraham() {
		super();
	}

	protected void pintaAristas(int delay, List<Punto> cierreConvexo) {
		for (int i = 0; i < cierreConvexo.size() -1; i++) {
			
			Punto punto2 = (Punto) cierreConvexo.get(i);
			Punto punto3 = (Punto) cierreConvexo.get(i+1);
			GestorConjuntoConvexo.getInstancia().anadeArista(new Arista(punto2,punto3));
			
		 
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	protected void pintaOrdenacion(int delay, Punto puntoInterior, List<Punto> cierreConvexo) {
		for (int i = 0; i < cierreConvexo.size(); i++) {
							
			Punto punto2 = (Punto) cierreConvexo.get(i);
			GestorConjuntoConvexo.getInstancia().anadeArista(new Arista(puntoInterior,punto2));
			
		 
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	protected void scan_graham(Punto puntoInterior, List<Punto> cierreConvexo) {
		Punto verticeInicio = puntoInterior;
		Punto v = puntoInterior;
		while (siguiente(v,cierreConvexo) != verticeInicio) {
			Punto siguiente_V = siguiente(v, cierreConvexo);					
			Punto sig_sig_V =  siguiente(siguiente_V,cierreConvexo);
            Arista a1 = new Arista(v,siguiente_V);
            Arista a2 = new Arista(v,sig_sig_V);
            GestorConjuntoConvexo.getInstancia().anadeAristaTmp(a1);
            GestorConjuntoConvexo.getInstancia().anadeAristaTmp(a2);
			if (orientation(v, siguiente_V, sig_sig_V) == FunctionsGlobals.POSITIVA)
			{
				v = siguiente(v, cierreConvexo);
			}
			else
			{
				if (v != verticeInicio)
				{
					cierreConvexo.remove(siguiente_V);
					int indice_v = cierreConvexo.indexOf(v);
					v = cierreConvexo.get(indice_v-1);
				}
			}
            GestorConjuntoConvexo.getInstancia().borraAristaTmp(a1);
            GestorConjuntoConvexo.getInstancia().borraAristaTmp(a2);
		}
	}

	public void ordenarAngularmente(List<Punto> cierreConvexo, Punto puntoInterior) {
		// TODO Auto-generated method stub
		Comparator<Punto> comparador = new ComparadorAngulos(puntoInterior);
		Collections.sort(cierreConvexo,comparador);
	}

}