package dia.upm.cconvexo.algoritmos;

import java.util.Iterator;
import java.util.List;

import dia.upm.cconvexo.gestores.GestorConjuntoConvexo;
import dia.upm.cconvexo.gestores.GestorFranjas;
import dia.upm.cconvexo.model.Punto;

public abstract class AproximacionGeneral extends AbstractAlgoritmo {

	protected int k_franjas = 0;

	public AproximacionGeneral() {
		super();
	}

	@Override
	public void start(int delay) {
		// TODO Auto-generated method stub
		List<Punto> listPuntos = GestorConjuntoConvexo.getInstancia().getListaPuntos();
		GestorFranjas.getInstancia().reset();
		assert listPuntos != null;
		assert k_franjas != 0;
		Punto x_min = this.busquedaPuntoMenorAbscisa(listPuntos);
		Punto x_max = this.busquedaPuntoMayorAbscisa(listPuntos);
		GestorFranjas.getInstancia().setPuntoMin(x_min);
		GestorFranjas.getInstancia().setPuntoMax(x_max);
		GestorFranjas.getInstancia().setNumeroFranjas(k_franjas);
		double anchura_franja = Math.floor((x_max.getX() - x_min.getX()) / this.k_franjas);
		// dibuja_franjas (anchura_franja)
		List<Punto> muestraordenada = this.calculaMuestra(listPuntos);
		// calcular cierre convexo de Andrew.
		Andrew algoritmo = new Andrew();
		algoritmo.algoritmoAndrew(0,muestraordenada);
		
	}

	public int getK_franjas() {
		return k_franjas;
	}
	
	public abstract List<Punto> calculaMuestra(List<Punto> listPuntos);

	public void setK_franjas(int k_franjas) {
		this.k_franjas = k_franjas;
	}

	protected void escogeMaximosFranjas(List<Punto> listPuntos) {
		int franja;
		// Se Introducen los puntos en las franjas atendiendo a su posición.
		for (Iterator<Punto> iterator = listPuntos.iterator(); iterator.hasNext();) {
			Punto punto =  iterator.next();
			franja = GestorFranjas.getInstancia().getFranja(punto);
			List<Punto> listFranja = GestorFranjas.getInstancia().getListaFranjas().get(franja);
			// Ahora revisamos si el punto es de los que hay que mirar en la franja. Al final nos quedamos con solo dos puntos por franja
			if (listFranja.isEmpty())
			{
				listFranja.add(punto);
				listFranja.add(punto);				
			}
			else
			{
				if (punto.getY()>listFranja.get(0).getY())
				{
					listFranja.set(0, punto);
				}
				
				if (punto.getY()<listFranja.get(1).getY())
				{
					listFranja.set(1, punto);
				}
			}
			
		}
	}

}