package ui.objecteditor;

import geometry.APoint;
import geometry.AShape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import draw.AObject;

//CONTAINER: VectorTab

public class VectorCanvas extends JLayeredPane
{
	private static final long serialVersionUID = 1L;
	public static final Color TRANSPARENT = new Color(0, 0, 0, 0);
	
	private ArrayList<VectorPoint> selected = new ArrayList<VectorPoint>();
	private AObject object;
	private ObjectEditorFrame main = null;

	public VectorCanvas(ObjectEditorFrame main, AObject object)
	{
		this.main = main;
		this.setBackground(Color.WHITE);
		this.setOpaque(true);
		this.object = object;
		
		VectorCanvasMouseListener vcml = new VectorCanvasMouseListener(this);
		this.addMouseMotionListener(vcml);
		this.addMouseListener(vcml);
				
		for(APoint p : object.getVector().getPoints())
		{
			registerPoint(p);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(object.draw(), 0, 0, null);
	}
	

	private void registerPoint(APoint point)
	{
		VectorPoint oe_point = new VectorPoint(main, point);
		add(oe_point);
		revalidate();
		repaint();
	}
	
	public void updateCanvas()
	{
		revalidate();
		repaint();
	}
	
	private Point getMid()
	{
		return new Point(this.getSize().width / 2, this.getSize().height / 2);
	}
	
	ObjectEditorFrame getMain()
	{
		return main;
	}
	
	public void select(VectorPoint point)
	{
		point.setSelected(true);
		selected.add(point);
	}
	
	public void deselectAll()
	{
		for (VectorPoint v : selected)
		{
			v.setSelected(false);
		}
		selected.clear();
	}
	
	public boolean deselect(VectorPoint point)
	{
		point.setSelected(false);
		return selected.remove(point);
	}
	
	/**
	 * Tries deselecting point. If point was not selected, it gets selected.*/
	public void dynamicallySelect(VectorPoint point)
	{
		if(!deselect(point))
		{
			select(point);
		}
	}
	
	public void addPoint()
	{
		addPoint(getMid().x, getMid().y);
	}
	
	public APoint addPoint(int x, int y)
	{
		APoint re = object.getVector().newPoint(x, y);
		registerPoint(re);
		return re;
	}
	
	public void addEllipse()
	{
		addEllipse(addPoint(1, 1), addPoint(getMid().x, getMid().y));
	}
	
	public void addEllipse(APoint A)
	{
		addEllipse(A, addPoint(getMid().x, getMid().y));
	}
	
	public void addEllipse(APoint A, APoint B)
	{
		APoint.newShape(A, B, AShape.TYPE_ELLIPSE);
	}
	
	public void addRectangle()
	{
		addRectangle(addPoint(1, 1), addPoint(getMid().x, getMid().y));
	}
	
	public void addRectangle(APoint A)
	{
		addRectangle(A, addPoint(getMid().x, getMid().y));
	}
	
	public void addRectangle(APoint A, APoint B)
	{
		APoint.newShape(A, B, AShape.TYPE_RECTANGLE);		
	}
	
	public VectorSelectionSidebar getSelectionSidebar()
	{
		return main.getOEPanel().getVectorTab().getSelectionSidebar();
	}
	
}
