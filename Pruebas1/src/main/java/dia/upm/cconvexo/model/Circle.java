package dia.upm.cconvexo.model;

import java.util.Iterator;
import java.util.List;

/**
 * Created by ivan on 22/11/14.
 */
public class Circle {

    public double radius;
    public Punto centro;

    static final double TOL = 0.00000000001;

    public Circle( Punto p1, Punto p2, Punto p3)
    {
        double offset = Math.pow(p2.x,2) + Math.pow(p2.y,2);
        double bc =   ( Math.pow(p1.x,2) + Math.pow(p1.y,2) - offset )/2.0;
        double cd =   (offset - Math.pow(p3.x, 2) - Math.pow(p3.y, 2))/2.0;
        double det =  (p1.x - p2.x) * (p2.y - p3.y) - (p2.x - p3.x)* (p1.y - p2.y);

        if (Math.abs(det) < TOL) { throw new IllegalArgumentException("Yeah, lazy."); }

        double idet = 1/det;

        double centerx =  (bc * (p2.y - p3.y) - cd * (p1.y - p2.y)) * idet;
        double centery =  (cd * (p1.x - p2.x) - bc * (p2.x - p3.x)) * idet;
        double radius =
                Math.sqrt( Math.pow(p2.x - centerx,2) + Math.pow(p2.y-centery,2));

        centro = new Punto(centerx,centery);
        this.radius = radius;

    }

    public double area()
    {
        return Math.PI*Math.pow(radius,2);
    }

    public boolean contains(Punto p)
    {
        return (Math.abs(p.distance(centro)) <= radius + TOL);
    }


    public boolean contains(List<Punto> cconvexoPuntos) {

        boolean containsPoints = true;

        for (Iterator<Punto> iterator = cconvexoPuntos.iterator(); iterator.hasNext(); ) {
            Punto next = iterator.next();
            if (this.contains(next) == false)
            {
                containsPoints = false;
            }
        }
        return containsPoints;
    }

    public Circle(Punto p1, Punto p2) {
        assert p1 != null;
        assert p2 != null;
        initCircle2Points(p1,p2);
    }

    private void initCircle2Points(Punto p1, Punto p2) {
        double centro_x = 0.5*(p1.x + p2.x);
        double centro_y = 0.5*(p1.y + p2.y);
        centro = new Punto(centro_x,centro_y);
        radius = centro.distance(p1);
    }

}
