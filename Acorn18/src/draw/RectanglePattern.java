package draw;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

public class RectanglePattern extends APattern
{
	public RectanglePattern(double width, double height)
	{
		super(width, height);
	}

	@Override
	public Shape getShape(double x, double y)
	{
		return new Rectangle2D.Double(x, y, width, height);
	}

	@Override
	public String getType()
	{
		return APattern.TYPE_RECTANGLE;
	}
}
