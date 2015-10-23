package draw;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class EllipsePattern extends APattern
{
	public EllipsePattern(double width, double height)
	{
		super(width, height);
	}

	@Override
	public Shape getShape(double x, double y)
	{
		return new Ellipse2D.Double(x, y, width, height);
	}

	@Override
	public String getType()
	{
		return APattern.TYPE_ELLIPSE;
	}

}
