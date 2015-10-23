package draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * APattern is an abstract superclass for patterns, can be used with ACanvas to create a texture.*/
public abstract class APattern // TODO MAKE IT SO ROTATION IS POSSIBLE
{
	public static final String TYPE_ELLIPSE = "Ellipse Pattern";
	public static final String TYPE_LINE = "Line Pattern";
	public static final String TYPE_RECTANGLE = "Rectangle Pattern";

	public static final String[] PATTERN_TYPES =
	{
		TYPE_ELLIPSE,
		TYPE_LINE,
		TYPE_RECTANGLE,
	};
	
	protected Color line_color = Color.BLACK;
	protected int line_width = 1;
	protected double height, width;
	protected double rotation = 0;

	/**
	 * Horizontal and vertical offset multiplier.
	 * The distance between the basic elements in a pattern is computed
	 * by multiplying the height or width (horizontal, vertical) of the basic element with the OM.*/
	protected double x_offset = 2, y_offset = 2;
	
	public APattern(double width, double height)
	{
		this.width = width;
		this.height = height;
	}
	
	public static APattern patternFactory(String type, double width, double height,
			double offset_x, double offset_y, double rotation, Color line_color, int line_width)
	{
		APattern re;
		switch(type)
		{
			case TYPE_ELLIPSE:
				re = new EllipsePattern(width, height);
				break;
			case TYPE_LINE:
				re = new LinePattern(width, height);
				break;
			case TYPE_RECTANGLE:
				re = new RectanglePattern(width, height);
				break;
			default :
				return null;
		}
		re.setXOffset(offset_x);
		re.setYOffset(offset_y);
		re.setRotation(rotation);
		re.setLineColor(line_color);
		re.setLineWidth(line_width);
		return re;
	}
	
	public static APattern patternFactory(String type, APattern dat)
	{
		return patternFactory(type, dat.getWidth(), dat.getHeight(), dat.getHorizontalOM(),
				dat.getVerticalOM(), dat.getRotation(), dat.getLineColor(), dat.getLineWidth());
	}

	public abstract String getType();
	
	/**
	 * Returns a basic element of the pattern.*/
	public abstract Shape getShape(double x, double y);

	/**
	 * Draws the pattern to target image, then returns it.*/
	public BufferedImage drawTo(BufferedImage target)
	{
		Rectangle rotated_area = calculateRotatedArea(target);
		Graphics2D g = preDraw(target, rotated_area);

        //draw to the rotated image
		g.setColor(line_color);
		g.setStroke(new BasicStroke(line_width));		
		double curr_altitude = (height / 2) * - y_offset;
		while (curr_altitude < rotated_area.getHeight()) //pattern is drawn to the entire area of the rotated image
		{
			double curr_latitude = (width / 2) * - x_offset;
			while (curr_latitude < rotated_area.getWidth())
			{
				g.draw(getShape(curr_altitude, curr_latitude));
				curr_latitude = curr_latitude + ((width / 2) * x_offset);
			}
			curr_altitude = curr_altitude + ((height / 2)* y_offset);
		}
		
		afterDraw(target, g);
		//restore the original rotation of the image
		return target;
	}
	
	protected Rectangle calculateRotatedArea(BufferedImage target)
	{
		double rads = Math.toRadians(rotation);
		double sin = Math.abs(Math.sin(rads));
		double cos = Math.abs(Math.cos(rads));
		int new_width = (int) Math.floor(target.getWidth() * cos + target.getHeight() * sin);
		int new_height = (int) Math.floor(target.getHeight() * cos + target.getWidth() * sin);
		return new Rectangle(new_width, new_height);
	}
	
	protected Graphics2D preDraw(BufferedImage target, Rectangle rotated_area)
	{
		Graphics2D re = target.createGraphics();

		//calculate middle of picture
        int focal_x = target.getWidth()/2;
        int focal_y = target.getHeight()/2;

        //rotate and position the original image in negative direction
        AffineTransform at = new AffineTransform();
        at.rotate(Math.toRadians(rotation), focal_x, focal_y);
        //compensates for the distance between the rotated and not rotated image
        at.translate(-((rotated_area.getWidth() - target.getWidth()) / 2),
        		- ((rotated_area.getHeight() - target.getHeight()) / 2));
        re.setTransform(at);
        return re;
	}
	
	protected void afterDraw(BufferedImage target, Graphics2D g)
	{
        int focal_x = target.getWidth()/2;
        int focal_y = target.getHeight()/2;
        AffineTransform at2 = new AffineTransform();
        at2.rotate(Math.toRadians(-rotation), focal_x, focal_y);
        g.setTransform(at2);
		g.dispose();
	}
	
	public BufferedImage draw(Dimension dim, Color background)
	{
		BufferedImage re = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = re.createGraphics();
		g.setColor(background);
		g.fillRect(0, 0, re.getWidth(), re.getHeight());
		g.dispose();
		return drawTo(re);
	}
	
	/**
	 * Set the offset multiplier to values*/
	public void setOffset(double horizontal, double vertical)
	{
		x_offset = horizontal;
		y_offset = vertical;
	}
	
	/**
	 * Set horizontal offset multiplier to value*/
	public void setXOffset(double value)
	{
		if (value == 0)
		{
			return;
		}
		x_offset = value;
	}
	
	/**
	 * Set vertical offset multiplier to value*/
	public void setYOffset(double value)
	{
		if (value == 0)
		{
			return;
		}
		y_offset = value;
	}
	
	/**
	 * Set line width to value*/
	public void setLineWidth(int value)
	{
		line_width = value;
	}
	
	/**
	 * Set line color to value*/
	public void setLineColor(Color value)
	{
		line_color = value;
	}
	
	/**
	 * Set the rotation to value*/
	public void setRotation(double value)
	{
		rotation = value;
	}
	
	public void setWidth(int value)
	{
		this.width = value;
	}
	
	public void setHeight(int value)
	{
		this.height = value;
	}
	
	/**
	 * Return current rotation*/
	public double getRotation()
	{
		return rotation;
	}
	
	/**
	 * Return current line color.*/
	public Color getLineColor()
	{
		return line_color;
	}
	
	/**
	 * Return current line width*/
	public int getLineWidth()
	{
		return line_width;
	}
	
	/**
	 * Return current horizontal offset multiplier*/
	public double getHorizontalOM()
	{
		return x_offset;
	}
	
	/**
	 * Returns current vertical offset multiplier*/
	public double getVerticalOM()
	{
		return y_offset;
	}
	
	/**
	 * Returns width*/
	public double getWidth()
	{
		return width;
	}
	
	
	/**
	 * Returns height*/
	public double getHeight()
	{
		return height;
	}
}
