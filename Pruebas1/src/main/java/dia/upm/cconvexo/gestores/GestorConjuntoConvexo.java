package dia.upm.cconvexo.gestores;




//import java.awt.Color;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import dia.upm.cconvexo.global.Triangulo;
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

    public void borraSubconjuntoArista()
    {
        this.subconjuntoArista.clear();
        for (int i = 0; i < listaListener.size(); i++) {


            IDelegatePaint delegate = listaListener.get(i);
            delegate.paintPuntos();
        }

    }

    static GestorConjuntoConvexo instancia = null;
	private List<Punto> listaPuntos = null;
	private List<Arista> conjuntoConvexo = null;
	private List<Punto> subconjuntoPuntos = null;
    private List<Arista>subconjuntoArista = null;
	private List<IDelegatePaint> listaListener = null;
    private List<Punto> puntosGeograficos = null;

    public boolean isOrdenaAngularmente() {
        return ordenaAngularmente;
    }

    public void setOrdenaAngularmente(boolean ordenaAngularmente) {
        this.ordenaAngularmente = ordenaAngularmente;
    }

    private boolean ordenaAngularmente = false;
	
	
	private GestorConjuntoConvexo() {
		// TODO Auto-generated constructor stub
		listaListener = new LinkedList<IDelegatePaint>();
		conjuntoConvexo = new LinkedList<Arista>();
        listaPuntos = new LinkedList<Punto>();
        subconjuntoArista = new LinkedList<Arista>();
        puntosGeograficos = new LinkedList<Punto>();
        subconjuntoPuntos = new LinkedList<Punto>();
		
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
        initGestor();
		for (Iterator<IDelegatePaint> iterator = listaListener.iterator(); iterator.hasNext();) {
			IDelegatePaint delegate = iterator.next();
			delegate.paintPuntos();
			
		}
		
	}

    public void initGestor() {
        this.conjuntoConvexo.clear();
        this.subconjuntoArista.clear();
        this.puntosGeograficos.clear();
        this.ordenaAngularmente = false;
    }

    public void borraListaPuntos()
	{
		listaPuntos = new LinkedList<Punto>();
		conjuntoConvexo.clear();
        for (IDelegatePaint delegate : listaListener) {
            delegate.borraPuntos();
            duerme();

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
            for (IDelegatePaint delegate : listaListener) {
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
			Arista a1 = iterator.next();

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

    private void duerme() {

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Punto> getPuntosGeograficos() {
        return puntosGeograficos;
    }

    public List<Punto> getSubconjuntoPuntos() {
        return subconjuntoPuntos;
    }

    public void anadaPuntoSubconjunto(Punto punto) {
        assert punto != null;
        assert subconjuntoPuntos != null;
        assert ! subconjuntoPuntos.contains(punto);
        subconjuntoPuntos.add(punto);
        for (Iterator<IDelegatePaint> iterator = listaListener.iterator(); iterator.hasNext();) {
            IDelegatePaint delegate = iterator.next();
            delegate.paintPuntos();

        }
    }

    public void borraPuntoSubconjunto(Punto punto) {
        assert punto != null;
        assert subconjuntoPuntos != null;
        subconjuntoPuntos.remove(punto);
        for (Iterator<IDelegatePaint> iterator = listaListener.iterator(); iterator.hasNext();) {
            IDelegatePaint delegate = iterator.next();
            delegate.paintPuntos();

        }
    }

    public void anadeTrianguloTmp(Triangulo t1) {
        Arista a1 = new Arista(t1.getPunto1(),t1.getPunto2());
        if ( ! subconjuntoArista.contains(a1)) {
            subconjuntoArista.add(a1);
        }
        Arista a2 = new Arista(t1.getPunto2(),t1.getPunto3());
        if ( ! subconjuntoArista.contains(a2)) {
            subconjuntoArista.add(a2);
        }
        Arista a3 = new Arista(t1.getPunto1(),t1.getPunto3());
        if ( ! subconjuntoArista.contains(a3)) {
            subconjuntoArista.add(a3);
        }

        for (int i = 0; i < listaListener.size(); i++) {


            IDelegatePaint delegate = listaListener.get(i);
            delegate.paintArista(a1);
        }
    }

    public void borraTrianguloTmp(Triangulo t1) {
        Arista a1 = new Arista(t1.getPunto1(),t1.getPunto2());
        subconjuntoArista.remove(a1);
        subconjuntoArista.remove(new Arista(t1.getPunto2(), t1.getPunto3()));
        subconjuntoArista.remove(new Arista(t1.getPunto1(), t1.getPunto3()));
        for (int i = 0; i < listaListener.size(); i++) {


            IDelegatePaint delegate = listaListener.get(i);
            delegate.borraRecta(a1);
        }
    }
}
