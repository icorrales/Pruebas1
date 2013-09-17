package dia.upm.cconvexo.model;

import java.awt.Point;
import java.awt.geom.Point2D;

public class Punto extends Point2D {

	public double x;
	public double y;
	
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = - y;
	}
	public Punto()
	{
		
	}
	public Punto(Point b) {
		// TODO Auto-generated constructor stub
		this.x = b.x;
		this.y = - b.y;
	}

	@Override
	public double getX() {
		// TODO Auto-generated method stub
		
		return x;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return y;
	}

	@Override
	public void setLocation(double arg0, double arg1) {
		// TODO Auto-generated method stub
		x = arg0;
		y = - arg1;

	}
	
	@Override
	public String toString()
	{
		return "("+ x + "," + y+")-";
	}
	
	@Override
	public boolean equals(Object o)
	{
		assert o != null;
		assert o instanceof Punto;
		
		Punto p = (Punto) o;
		return p.getX() == x && p.getY() == y; 
		
	}
	
	public Point getPoint()
	{
		
		
		return new Point((int)this.x,Math.abs((int)this.y));
	}

}
