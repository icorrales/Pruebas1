package dia.upm.cconvexo.algoritmos;

import java.util.LinkedList;
import java.util.List;

import dia.upm.cconvexo.R;
import dia.upm.cconvexo.android.gestores.GestorMensajes;
import dia.upm.cconvexo.gestores.GestorConjuntoConvexo;
import dia.upm.cconvexo.model.Arista;
import dia.upm.cconvexo.model.Punto;

public class GrahamNuevo extends DerivadosGraham {
	
	public final static String nombre = GestorMensajes.getInstancia().getResourceString(R.string.graham);

    public GrahamNuevo()
    {
        footnote = "sdfootnote96anc";
    }

	@SuppressWarnings("unchecked")
	public void start(int delay) {
		// TODO Auto-generated method stub
		List<Punto> listaPuntos = GestorConjuntoConvexo.getInstancia().getListaPuntos();
		if (listaPuntos.size() > 1)
		{			
			if (alineados (listaPuntos))
			{
				// pinta los dos vertices mas alejados.
			}
			else // siempre llegaran aqui siendo 3 o mas puntos.
			{
//				Punto puntoInterior = centroide(listaPuntos.get(0), listaPuntos.get(1), listaPuntos.get(2));
				Punto puntoInterior = busquedaPuntoMenorOrdenada(listaPuntos);
				GestorConjuntoConvexo.getInstancia().anadePuntoGrafico(puntoInterior);
                GestorMensajes.getInstancia().addMessage(R.string.graham_c_4);
				List<Punto> cierreConvexo = (LinkedList<Punto>)((LinkedList<Punto>) listaPuntos).clone();

                ordenarAngularmente(cierreConvexo, puntoInterior);
                GestorMensajes.getInstancia().addMessage(R.string.graham_c_5);
                pintaOrdenacion(delay, puntoInterior, cierreConvexo);
				scan_graham(puntoInterior, cierreConvexo);
                List<Arista> conjuntoConvexo = GestorConjuntoConvexo.getInstancia().getConjuntoConvexo();
                Punto pfinal = conjuntoConvexo.get(conjuntoConvexo.size() -1 ).getDestino();
                Arista arista_final = new Arista(puntoInterior, pfinal);
                GestorMensajes.getInstancia().addMessage(R.string.graham_c_6);
                GestorConjuntoConvexo.getInstancia().anadeArista(arista_final);
				GestorConjuntoConvexo.getInstancia().borraSubconjuntoArista();
			}
		}
	}

	public boolean alineados(List<Punto> listaPuntos) {
		// TODO Auto-generated method stub
		return false;
	}

}
