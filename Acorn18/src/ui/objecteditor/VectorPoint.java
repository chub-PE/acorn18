package ui.objecteditor;

import geometry.APoint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VectorPoint extends JPanel
{

	private static final long serialVersionUID = 1L;
	
	private static final int LINE_WIDTH = 3;
	private static final Rectangle ICON_DIM = new Rectangle(30, 30);
	private static final Color PURPLE = new Color(180, 140, 240, 1);
	private static final ImageIcon ORIGO_ICON_DEFAULT = generateIcon(Color.BLUE, true);
	private static final ImageIcon ORIGO_ICON_SELECT = generateIcon(Color.RED, true);
	private static final ImageIcon REGULAR_ICON_DEFAULT = generateIcon(Color.BLUE, false);
	private static final ImageIcon REGULAR_ICON_SELECT = generateIcon(Color.RED, false);
	private static final ImageIcon CONTROL_ICON_DEFAULT = generateIcon(PURPLE, false);
	private static final ImageIcon CONTROL_ICON_SELECT = generateIcon(Color.GREEN, false);

	private APoint point = null;
	private Point offset = new Point(0, 0);
	private boolean selected = false;
	private ObjectEditorFrame main = null;
	JLabel label;
	
	public VectorPoint(ObjectEditorFrame main, APoint point)
	{
		this.point = point;
		this.main = main;
		
		label = new JLabel();
		updateIcon();
		FlowLayout layout = new FlowLayout();		
		layout.setVgap(0);
		layout.setHgap(0);
	
		setLayout(layout);
		add(label);
		setSize(new Dimension(ICON_DIM.width, ICON_DIM.height));
		setLocation(point.getX(), point.getY());
		
		//oe_point.setBackground(TRANSPARENT);
		setOpaque(false);

	}
	
	public void setSelected(boolean value)
	{
		selected = value;
		updateIcon();
	}
	
	public boolean getSelected()
	{
		return selected;
	}
	
	public void updateIcon()
	{
		label.setIcon(getIcon());
	}
	
	public void setLocation(double x, double y)
	{
		super.setLocation((int)x, (int)y);
		point.setPosition(x, y);
	}
	
	@Override
	public void setLocation(int x, int y)
	{
		setLocation((double)x, (double)y);
	}

	public APoint getPoint()
	{
		return point;
	}
	
	private ImageIcon getIcon()
	{
		switch (point.getType())
		{
			case APoint.TYPE_ORIGO:
				if (selected)
					return ORIGO_ICON_SELECT;
				else
					return ORIGO_ICON_DEFAULT;
			case APoint.TYPE_HELPER:
				if (selected)
					return CONTROL_ICON_SELECT;
				else
					return CONTROL_ICON_DEFAULT;
			case APoint.TYPE_REGULAR:
				if (selected)
					return REGULAR_ICON_SELECT;
				else
					return REGULAR_ICON_DEFAULT;
			default:
				return null;
		}
	}
	
	private static ImageIcon generateIcon(Color color, boolean origo)
	{		
		BufferedImage img = new BufferedImage(ICON_DIM.width, ICON_DIM.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();
		BasicStroke bs = new BasicStroke(LINE_WIDTH);		
		g.setColor(color);
		g.setStroke(bs);
		
		Point mid = new Point(ICON_DIM.width/2, ICON_DIM.height/2);
		Point outer_rad = new Point(ICON_DIM.width - LINE_WIDTH, ICON_DIM.height - LINE_WIDTH);
		Point outer_mid = new Point(mid.x - (outer_rad.x / 2), mid.y - (outer_rad.y / 2));
		Ellipse2D outer = new Ellipse2D.Double(outer_mid.x, outer_mid.y, outer_rad.x, outer_rad.y);
		g.draw(outer);
		
		if (origo)
		{
			Point inner_rad = new Point(outer_rad.x / 2, outer_rad.y / 2);
			Point inner_mid = new Point(mid.x - (inner_rad.x / 2), mid.y - (inner_rad.y / 2));
			Ellipse2D inner = new Ellipse2D.Double(inner_mid.x, inner_mid.y, inner_rad.x, inner_rad.y);
			g.draw(inner);
		}
		
		bs = new BasicStroke(1);
		g.setStroke(bs);
		Line2D line1 = new Line2D.Double(new Point(0, 0), new Point(ICON_DIM.width, ICON_DIM.height));
		Line2D line2 = new Line2D.Double(new Point(0, ICON_DIM.height), new Point(ICON_DIM.width, 0));
		g.draw(line1);
		g.draw(line2);
		
		g.dispose();
		return new ImageIcon(img);
	}

	
}
