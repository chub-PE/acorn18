package draw;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

public class LinePattern extends APattern
{
	//height value is used only, when converting object into another instance of APattern
	public LinePattern(double width, double height)
	{
		super(width, height);
	}
	
	public LinePattern(double width)
	{
		this(width, 1);
	}

	// TODO TEST
	@Override 
	public BufferedImage drawTo(BufferedImage target)
	{
		Rectangle rotated_area = calculateRotatedArea(target);
		Graphics2D gra = preDraw(target, rotated_area);
		
        //draw to the rotated image
		gra.setColor(line_color);
		gra.setStroke(new BasicStroke(line_width));		
		double x = 0;
		while (x < rotated_area.getWidth())
		{
			gra.draw(getShape(x, rotated_area.getHeight()));
			x = x + (width / 2);
		}
		afterDraw(target, gra);
		return target;
	}
	
	@Override
	public String getType()
	{
		return APattern.TYPE_LINE;
	}
	
	@Override
	public Shape getShape(double x, double y)
	{
		return new Line2D.Double(x, 0, x, y);
	}

}
