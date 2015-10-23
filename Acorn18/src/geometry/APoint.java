package geometry;

import java.awt.geom.Path2D;
import java.util.ArrayList;

public class APoint
{	
	public static final int TYPE_ORIGO = 0;
	public static final int TYPE_REGULAR = 1;
	public static final int TYPE_HELPER = 2;
	
	public double x, y;
	
	private ArrayList<AShape> shapes = new ArrayList<AShape>();
	private int type;
	private int index;
	
	public APoint(double x, double y, int type, int index)
	{
		this.x = x;
		this.y = y;
		this.type = type;
		this.index = index;
	}
	
	public static AShape newShape(APoint A, APoint B, int type)
	{
		AShape re;
		if (A.getIndex() < B.getIndex())
			re = AShape.shapeFactory(A, B, type);
		else
			re = AShape.shapeFactory(B, A, type);
		A.shapes.add(re);
		B.shapes.add(re);
		return re;
	}
	
	public Path2D.Double drawTo(Path2D.Double path, APencil pencil)
	{
		for (AShape s : shapes)
		{
			if (s.getOrigin().equals(this) && !pencil.isDrawnAlready(s))
			{
				s.drawTo(path, pencil);
				pencil.last_point.drawTo(path, pencil);
			}
		}
		return path;
	}
	
	public boolean removeShape (AShape shape)
	{
		for (int i = 0; i < shapes.size(); i++)
		{
			if (shapes.get(i).equals(shape))
			{
				shapes.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public void setPosition(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public int getIndex()
	{
		return index;
	}
		
	public int getType()
	{
		return type;
	}
	
	public void removeSelfReferences()
	{
		for (AShape shape : shapes)
		{
			shape.removeSelfReferences();
		}
	}
	
	
}
