package dia.upm.cconvexo.algoritmos;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import dia.upm.cconvexo.gestores.GestorConjuntoConvexo;
import dia.upm.cconvexo.model.Punto;

public class AproximacionInferior extends AbstractAlgoritmo {

	private int k_franjas = 0;
	
	@Override
	public void start(int delay) {
		// TODO Auto-generated method stub
		List<Punto> listPuntos = GestorConjuntoConvexo.getInstancia().getListaPuntos();
		assert listPuntos != null;
		assert k_franjas != 0;
		Punto x_min = this.busquedaPuntoMenorAbscisa(listPuntos);
		Punto x_max = this.busquedaPuntoMayorAbscisa(listPuntos);
		double anchura_franja = Math.floor((x_max.getX() - x_min.getX()) / this.k_franjas);
		// dibuja_frajas ()
		List<Punto> muestraordenada = calculaMuestra(listPuntos, anchura_franja,x_min,x_max);
		
		
		
	}
	
	private List<Punto> calculaMuestra(List<Punto> listPuntos,
			double anchura_franja, Punto x_min, Punto x_max) {
		// TODO Auto-generated method stub
		assert listPuntos != null;
		assert x_max != null && x_min != null;
		int franja = 0;
		List<Punto> muestra = new LinkedList<Punto>();
		List<List<Punto>> listaFranjas = new LinkedList<List<Punto>>();
		for (Iterator iterator = listPuntos.iterator(); iterator.hasNext();) {
			Punto punto = (Punto) iterator.next();
			if (punto.equals(x_min))
			{
				franja = 0;
			}
			else
			{
				franja = (int) Math.floor((punto.getX()-x_min.getX())/k_franjas);
				
			}
			List<Punto> listFranja = listaFranjas.get(franja);
			if (listFranja == null)
			{
				listFranja = new LinkedList<Punto>();
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
		for (Iterator iterator = listaFranjas.iterator(); iterator.hasNext();) {
			List<Punto> list = (List<Punto>) iterator.next();
			if (list.get(0).getX()< list.get(1).getX())
			{
				muestra.add(list.get(0));
				muestra.add(list.get(1));
			}
			else if (list.get(0).equals(list.get(1)))
			{
				muestra.add(list.get(0));
			}
			else
			{
				muestra.add(list.get(1));
				muestra.add(list.get(0));
			}
			
		}
		
		return muestra;
	}

	public AproximacionInferior()
	{
		
	}

	public int getK_franjas() {
		return k_franjas;
	}

	public void setK_franjas(int k_franjas) {
		this.k_franjas = k_franjas;
	}
	
	

	
	

}
