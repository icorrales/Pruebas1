package dia.upm.cconvexo.gestores;

import java.util.HashMap;
import java.util.Map;

import dia.upm.cconvexo.algoritmos.Andrew;
import dia.upm.cconvexo.algoritmos.BusquedaAristas;
import dia.upm.cconvexo.algoritmos.DivideYVencerasPreord;
import dia.upm.cconvexo.algoritmos.EliminacionPtosInteriores;
import dia.upm.cconvexo.algoritmos.GrahamNuevo;
import dia.upm.cconvexo.algoritmos.Jarvis;
import dia.upm.cconvexo.algoritmos.QuickHullNuevo;
import dia.upm.cconvexo.interfaces.IAlgoritmoHullConvex;

public class GestorAlgoritmos {

	public static final int TEDIRECTA = 0;
	public static final int TERETARDO = 1;
	public static final int TESTEPTOSTEP = 2;
	public static final int DEFAULTDELAY = 200;
	
	private static GestorAlgoritmos instancia = null;
	private Map<String, IAlgoritmoHullConvex> mapaAlgoritmos = null;
	private int executiontype = TEDIRECTA;
	private int delay = DEFAULTDELAY;
	
	



	public int getExecutiontype() {
		return executiontype;
	}



	public void setExecutiontype(int executiontype) {
		this.executiontype = executiontype;
	}



	public int getDelay() {
		return delay;
	}



	public void setDelay(int delay) {
		this.delay = delay;
	}



	private GestorAlgoritmos() {
		// TODO Auto-generated constructor stub
		mapaAlgoritmos = new HashMap<String, IAlgoritmoHullConvex>();
		initAlgoritmos();
	}
	
	
	
	private void initAlgoritmos() {
		// TODO Auto-generated method stub
		mapaAlgoritmos.put(Jarvis.nombre, new Jarvis());
		mapaAlgoritmos.put(BusquedaAristas.nombre, new BusquedaAristas());
		mapaAlgoritmos.put(EliminacionPtosInteriores.nombre,new EliminacionPtosInteriores());
		mapaAlgoritmos.put(GrahamNuevo.nombre, new GrahamNuevo());
		mapaAlgoritmos.put(Andrew.nombre, new Andrew());
		mapaAlgoritmos.put(DivideYVencerasPreord.nombre, new DivideYVencerasPreord());
		mapaAlgoritmos.put(QuickHullNuevo.nombre, new QuickHullNuevo());
	}



	public static GestorAlgoritmos getInstancia ()
	{
		if (instancia == null)
		{
			instancia = new GestorAlgoritmos();			
		}
		assert instancia != null;
		return instancia;
	}
	
	public void setAlgoritmo(String nombre,IAlgoritmoHullConvex algoritmo)
	{
		assert algoritmo != null;
		assert mapaAlgoritmos.containsKey(nombre) == false;
		mapaAlgoritmos.put(nombre, algoritmo);
		
	}
	
	public IAlgoritmoHullConvex getAlgoritmo(String nombre)
	{
		assert mapaAlgoritmos.containsKey(nombre);
		return mapaAlgoritmos.get(nombre);
	}

}
