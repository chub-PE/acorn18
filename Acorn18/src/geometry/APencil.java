package geometry;

import java.util.ArrayList;

public class APencil
{
	public APoint last_point = null;
	
	private ArrayList<AShape> shapes = new ArrayList<AShape>();
		
	public void addShape(AShape shape)
	{
		shapes.add(shape);
	}
	
	public boolean isDrawnAlready(AShape shape)
	{
		for (AShape s : shapes)
		{
			if (s.equals(shape))
			{
				return true;
			}
		}
		return false;
	}
	
}
