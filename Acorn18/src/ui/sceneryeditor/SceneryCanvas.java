package ui.sceneryeditor;

import geometry.APoint;
import geometry.AShape;
import geometry.AVector;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import draw.ACanvas;
import draw.AObject;
import draw.EllipsePattern;
import draw.LinePattern;
import draw.RectanglePattern;

//CONTAINER: SceneryEditorFrame

public class SceneryCanvas extends JLayeredPane
{	
	private ArrayList<Component> foregrounded = new ArrayList<Component>();
	private Map<String, AObject> objects = new HashMap<String, AObject>();
	private int selected_layer = 0;
	private AObject selected_object = null;
	SceneryEditorFrame main = null;
	
	public SceneryCanvas(SceneryEditorFrame main)
	{
		this.main = main;
		this.setBackground(Color.WHITE);
		this.setOpaque(true);
		
		SceneryCanvasMouseListener scml = new SceneryCanvasMouseListener(this);
		Dimension w_size = main.getSize();
		this.addMouseMotionListener(scml);
		this.addMouseListener(scml);
		this.setPreferredSize(new Dimension ((w_size.width / 16) * 13, w_size.height));
		this.setBorder(new LineBorder(Color.GRAY));
	}

	public void deselectObject()
	{
		if (selected_object != null)
		{
			selected_object.getPanel().setBorder(new EmptyBorder(0, 0, 0, 0));
			this.setLayer(selected_object.getPanel(), selected_object.getLayer());
			selected_object = null;
		}
	}
	
	public void selectObject(String name)
	{
		AObject ob = objects.get(name);		
		if (ob == null)
		{
			return;
		}
		deselectObject();
		selected_object = ob;
		ob.getPanel().setBorder(new LineBorder(Color.BLACK));
		this.setLayer(ob.getPanel(), JLayeredPane.DRAG_LAYER);
		main.current().selectObject();
		refreshSelected();
	}
	
	public void refreshSelected()
	{
		if(selected_object != null)
		{
			main.current().refreshSelected();
			selected_object.refreshImage();
		}
	}
	
	public void addObject(AObject object)
	{
		//associating the new object with its name
		objects.put(object.getName(), object);
		main.current().addNewObject(object.getName());
		
		this.add(object.getPanel());
		this.revalidate();
		this.repaint();
	}
	
	public void removeObject(String name)
	{
		objects.remove(name);
	}
	
	//TODO test
	public void selectLayer(Integer layer)
	{
		deselectLayer();
		this.selected_layer = layer;
		Component[] comps = this.getComponents();
		foregrounded = new ArrayList<Component>();
	
		for (Component c : comps)
		{
			if (this.getLayer(c) == layer)
			{
				foregrounded.add(c);
				this.setLayer(c, layer);
			}
		}
	}
	
	public void changeLayerOfSelected(Integer value)
	{
		selected_object.setLayer(value);
		this.setLayer(selected_object.getPanel(), value);
	}
	
	public String[] getObjectNames()
	{
		return objects.keySet().toArray(new String[objects.keySet().size()]);
	}
	
	public AObject getSelectedObject()
	{
		return selected_object;
	}
	
	//TODO test
	public void deselectLayer()
	{
		for (Component c : foregrounded)
		{
			this.setLayer(c, selected_layer);
		}
	}
	
	public void test()
	{
		int width = 500, height = 500;
		EllipsePattern pattern = new EllipsePattern(40, 40);
		pattern.setOffset(1, 1);
		pattern.setLineColor(Color.RED);
		pattern.setLineWidth(3);
		
		LinePattern pattern2 = new LinePattern(10);
		pattern2.setLineColor(Color.GREEN);
		pattern2.setLineWidth(2);
		
		EllipsePattern pattern3 = new EllipsePattern(20, 40);
		pattern3.setOffset(4, 1);
		pattern3.setLineColor(Color.CYAN);
		pattern3.setLineWidth(2);
		
		RectanglePattern pattern4 = new RectanglePattern(20, 40);
		pattern4.setOffset(4, 1);
		pattern4.setLineColor(Color.ORANGE);
		pattern4.setLineWidth(2);
				
		ACanvas canvas = new ACanvas(width, height);
		canvas.setBackgroundColor(Color.PINK);
		canvas.addPattern(pattern);
		canvas.addPattern(pattern2);
		canvas.addPattern(pattern3);
		canvas.addPattern(pattern4);
		
		AVector vector = new AVector();
		
		APoint p0 = vector.getOrigo();
		APoint p1 = vector.newPoint(525, 0);
		APoint p2 = vector.newPoint(250, 500);
		
		AShape s1 = APoint.newShape(p0, p1, AShape.TYPE_CURVE);
		AShape s2 = APoint.newShape(p1, p2, AShape.TYPE_CURVE);
		AShape s3 = APoint.newShape(p2, p0, AShape.TYPE_CURVE);
				
		AObject ob = new AObject(canvas, vector, "abstract");
		AObject ob2 = new AObject(canvas, vector, "gegmachine");
		
		
		BufferedImage bi = new BufferedImage(900, 900, BufferedImage.TYPE_INT_ARGB);
		
		addObject(ob);
		addObject(ob2);

	}
}
