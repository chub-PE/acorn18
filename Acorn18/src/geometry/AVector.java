package geometry;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import draw.AObject;

public class AVector
{
	private ArrayList<APoint> points = new ArrayList<APoint>();
	private int current_index = 0;
	
	public AVector()
	{
		points.add(new APoint(0, 0, APoint.TYPE_ORIGO, current_index));
		current_index++;
	}
	

	public AVector(int template)
	{
		this();
		switch (template)
		{
		case AObject.OBJECT_TEMPLATE_NOTEXTURE:
			loadObjectTemplateNotexture();			
			break;
		default :
			break;
		}
	}

	public Path2D.Double toPath2D()
	{
		Path2D.Double shape = new Path2D.Double();
		System.out.println();
		APencil pencil = new APencil();
		for (int i = 0; i < points.size(); i++)
		{	
			points.get(i).drawTo(shape, pencil);
		}
		return shape;
	}
	
	public APoint newPoint(int x, int y)
	{
		APoint re = new APoint(x, y, APoint.TYPE_REGULAR, current_index);
		current_index++;
		points.add(re);
		return re;
	}
	
	public APoint newPoint()
	{
		return newPoint(0, 0);
	}
		
	public boolean removePoint(APoint point)
	{
		for (int i = 0; i < points.size(); i++)
		{
			if (points.get(i).equals(point))
			{
				points.remove(i);
				return true;
			}
		}
		return false;
	}

	public APoint setOrigo(int x, int y)
	{
		points.get(0).setPosition(x, y);
		return points.get(0);
	}
	
	public APoint getOrigo()
	{
		return points.get(0);
	}

	public APoint getPoint(int index)
	{
		return points.get(index);
	}
	
	/**
	 * Returns the upper leftmost point of this vector*/
	public Point2D.Double getUpperLeftmost()
	{
		double x = getOrigo().x;
		double y = getOrigo().y;
	
		for (APoint p : points)
		{
			if (p.x < x)
			{
				x = p.x;
			}
			if (p.y < y)
			{
				y = p.y;
			}
		}
		
		return new Point2D.Double(x, y);
	}
	
	private void loadObjectTemplateNotexture()
	{
		APoint p0 = setOrigo(0, 0);
		APoint p1 = newPoint(200, 0);
		APoint p2 = newPoint(200, 200);
		APoint p3 = newPoint(0, 200);
		
		APoint.newShape(p0, p1, AShape.TYPE_CURVE);
		APoint.newShape(p1, p2, AShape.TYPE_CURVE);
		APoint.newShape(p2, p3, AShape.TYPE_CURVE);
		APoint.newShape(p3, p0, AShape.TYPE_CURVE);

	}
	
	public APoint[] getPoints()
	{
		return points.toArray(new APoint[points.size()]);
	}
	
}
