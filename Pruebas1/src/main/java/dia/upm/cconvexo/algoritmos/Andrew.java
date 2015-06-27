package dia.upm.cconvexo.algoritmos;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import dia.upm.cconvexo.R;
import dia.upm.cconvexo.android.gestores.GestorMensajes;
import dia.upm.cconvexo.gestores.GestorConjuntoConvexo;
import dia.upm.cconvexo.global.ComparadorAbscisas;
import dia.upm.cconvexo.model.Arista;
import dia.upm.cconvexo.model.Punto;

public class Andrew extends DerivadosGraham {

	public final static String nombre = GestorMensajes.getInstancia().getResourceString(R.string.andrew);

	
	public Andrew() {
		// TODO Auto-generated constructor stub
        footnote = "sdfootnote106anc";
	}
    	
	
	public void start(int delay) {
		
		List<Punto> listaPuntos = GestorConjuntoConvexo.getInstancia().getListaPuntos();			
		algoritmoAndrew(delay, listaPuntos);
		
	}


	public void algoritmoAndrew(int delay, List<Punto> listaPuntos) {
        assert listaPuntos != null && listaPuntos.size() > 0;
		if (listaPuntos.size() == 1)
		{
		}
		else
		{			
			List<Punto> listaPuntosCopia = new LinkedList<Punto>(listaPuntos);			
			Collections.sort(listaPuntosCopia, new ComparadorAbscisas());
			List<Punto> subconjuntoInferior = new LinkedList<Punto>();
			List<Punto> subconjuntoSuperior = new LinkedList<Punto>();
			divideLista(listaPuntosCopia,subconjuntoInferior,subconjuntoSuperior);
			Punto pIzquierda = listaPuntosCopia.get(0);
			Punto pDerecha = listaPuntosCopia.get(listaPuntosCopia.size()-1);
            GestorMensajes.getInstancia().addMessage(R.string.andrew_c_1);
            GestorConjuntoConvexo.getInstancia().anadePuntoGrafico(pIzquierda);

            GestorConjuntoConvexo.getInstancia().anadePuntoGrafico(pDerecha);
            GestorMensajes.getInstancia().addMessage(R.string.andrew_c_2);
            GestorConjuntoConvexo.getInstancia().anadeAristaTmp(new Arista(pIzquierda, pDerecha));

			if (subconjuntoSuperior.size() > 0)
			{
                GestorMensajes.getInstancia().addMessage(R.string.andrew_c_3);
				scanSubconjunto(delay, subconjuntoSuperior, pIzquierda,
						pDerecha);
			}
			
			if (subconjuntoInferior.size() > 0)
			{
                GestorMensajes.getInstancia().addMessage(R.string.andrew_c_4);
				scanSubconjunto(delay, subconjuntoInferior, 
						pDerecha,pIzquierda);
			}

            if (subconjuntoInferior.size() == 0 || subconjuntoSuperior.size() == 0)
            {
                GestorConjuntoConvexo.getInstancia().anadeArista(new Arista(pIzquierda, pDerecha));
            }
		}
	}


	public void scanSubconjunto(int delay, List<Punto> subconjunto,
			Punto pIzquierda, Punto pCentro) {
		subconjunto.add(pIzquierda);
		subconjunto.add(pCentro);
		ordenarAngularmente(subconjunto, pCentro);
		scan_graham(pCentro,subconjunto);
		pintaAristas(delay, subconjunto);
	}

	public void divideLista(List<Punto> listaPuntosCopia,
			List<Punto> subconjuntoInferior, List<Punto> subconjuntoSuperior) {
		
		assert listaPuntosCopia != null && listaPuntosCopia.size() > 1;
		assert subconjuntoInferior != null;
		assert subconjuntoSuperior != null;
		Punto pIzquierda = listaPuntosCopia.get(0);
		Punto pDerecha = listaPuntosCopia.get(listaPuntosCopia.size()-1);
		for (int i = 1; i < listaPuntosCopia.size() - 1; i++) {
			int orientation = orientation(pIzquierda, pDerecha, listaPuntosCopia.get(i)); 
			if (orientation == FunctionsGlobals.POSITIVA)
			{
				subconjuntoSuperior.add(listaPuntosCopia.get(i));
			}
			else if  (orientation == FunctionsGlobals.NEGATIVA)
			{
				subconjuntoInferior.add(listaPuntosCopia.get(i));
			}		
		}
		
	}
}
