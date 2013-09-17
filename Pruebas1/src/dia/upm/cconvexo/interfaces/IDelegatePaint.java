package dia.upm.cconvexo.interfaces;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import dia.upm.cconvexo.model.Arista;
import dia.upm.cconvexo.model.Punto;

public interface IDelegatePaint {

	public void paint(JComponent component, Graphics2D graphic);
	public void paintPuntos(Graphics2D graphic);
	public void paintPunto(Punto pto);
	public void paintArista(Arista a1);
	public void paintArista(Arista a1, Color color);
	public void borraRecta(Arista a1);
	public void borraPuntos();
	public void borraPuntoSubconjunto(Punto p);
}
