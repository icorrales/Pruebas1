package dia.upm.cconvexo.gestores;


import android.graphics.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;




import dia.upm.cconvexo.interfaces.IDelegatePaint;
import dia.upm.cconvexo.model.Arista;
import dia.upm.cconvexo.model.Punto;

public class GestorConjuntoConvexo {

    public List<Arista> getSubconjuntoArista() {
        return subconjuntoArista;
    }

    public void setSubconjuntoArista(List<Arista> subconjuntoArista) {
        this.subconjuntoArista = subconjuntoArista;
    }

    static GestorConjuntoConvexo instancia = null;
	private List<Punto> listaPuntos = null;
	private List<Arista> conjuntoConvexo = null;
	private List<Punto> subconjuntoPuntos = null;
    private List<Arista>subconjuntoArista = null;
	private List<IDelegatePaint> listaListener = null;
	
	
	private GestorConjuntoConvexo() {
		// TODO Auto-generated constructor stub
		listaListener = new LinkedList<IDelegatePaint>();
		conjuntoConvexo = new LinkedList<Arista>();
        listaPuntos = new LinkedList<Punto>();
        subconjuntoArista = new LinkedList<Arista>();
		
	}
	
	public static GestorConjuntoConvexo getInstancia ()
	{
		if (instancia == null)
		{
			instancia = new GestorConjuntoConvexo();			
		}
		assert instancia != null;
		return instancia;
	}

	public List<Punto> getListaPuntos() {
		return listaPuntos;
	}

	public void setListaPuntos(List<Punto> listaPuntos) {
		assert listaPuntos != null;
		assert listaListener != null;
		this.listaPuntos = listaPuntos;
		for (Iterator<IDelegatePaint> iterator = listaListener.iterator(); iterator.hasNext();) {
			IDelegatePaint delegate = iterator.next();
			delegate.paintPuntos();
			
		}
		
	}
	
	public void borraListaPuntos()
	{
		listaPuntos.clear();
		conjuntoConvexo.clear();
		for (Iterator<IDelegatePaint> iterator = listaListener.iterator(); iterator.hasNext();) {
			IDelegatePaint delegate = iterator.next();
			delegate.borraPuntos();
			
		}
	}
	
	public void anadeArista(Arista a1)
	{
		assert conjuntoConvexo != null;
		assert a1 != null;
		assert a1.getDestino() != null && a1.getOrigen() != null;
		if ( ! conjuntoConvexo.contains(a1))
		{
			conjuntoConvexo.add(a1);
			for (Iterator<IDelegatePaint> iterator = listaListener.iterator(); iterator.hasNext();) {
				IDelegatePaint delegate = iterator.next();
				delegate.paintArista(a1);			
			}
		}
	}
	
	
	public void borraArista(Arista a1)
	{
		assert conjuntoConvexo != null;
		assert a1 != null;
		assert a1.getDestino() != null && a1.getOrigen() != null;				
//		assert conjuntoConvexo.contains(a1);
		if (conjuntoConvexo.contains(a1))
		{	
		conjuntoConvexo.remove(a1);
		for (Iterator<IDelegatePaint> iterator = listaListener.iterator(); iterator.hasNext();) {
			IDelegatePaint delegate = iterator.next();
			delegate.borraRecta(a1);			
		}
		}
	}
	
	public void addListener (IDelegatePaint delegate) 
	{
		assert delegate != null;
		listaListener.add(delegate);
	}

	public void removeListener(IDelegatePaint delegate)
	{
		assert delegate != null;
		listaListener.remove(delegate);
	}

	public List<Arista> getConjuntoConvexo() {
		// TODO Auto-generated method stub
		return this.conjuntoConvexo;
	}
	
	public void setSubconjuntoPuntos(List<Punto> lista)
	{
		assert lista != null;
		subconjuntoPuntos = lista;
	}
	
	public void borrarPuntoSubconjunto(Punto p)
	{
		assert p != null;
		for (Iterator<IDelegatePaint> iterator = listaListener.iterator(); iterator.hasNext();) {
			IDelegatePaint delegate = iterator.next();
			delegate.borraPuntoSubconjunto(p);			
		}
	}

	public void anadePuntoGrafico(Punto puntoInterior) {
		assert puntoInterior != null;
		
		for (Iterator<IDelegatePaint> iterator = listaListener.iterator(); iterator.hasNext();) {
			IDelegatePaint delegate = iterator.next();
			delegate.paintPuntos();
			
		}
		
	}

	public void pintaCierreConvexo() {
		// TODO Auto-generated method stub
		for (Iterator<Arista> iterator = conjuntoConvexo.iterator(); iterator.hasNext();) {
			Arista a1 = (Arista) iterator.next();
			
			for (int i = 0; i < listaListener.size(); i++) {
				
			
				IDelegatePaint delegate = listaListener.get(i);
				  delegate.paintArista(a1, Color.RED);
			}
		


		}
		
		
	}

    public void anadeAristaTmp(Arista a1)
    {
        subconjuntoArista.add(a1);
        for (int i = 0; i < listaListener.size(); i++) {


            IDelegatePaint delegate = listaListener.get(i);
            delegate.paintArista(a1);
        }
    }

    public void borraAristaTmp(Arista a1)
    {
        subconjuntoArista.remove(a1);
        for (int i = 0; i < listaListener.size(); i++) {


            IDelegatePaint delegate = listaListener.get(i);
            delegate.paintArista(a1);
        }
    }

}
