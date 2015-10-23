package ui.sceneryeditor;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

//CONTAINER: SceneryCanvas

public class SceneryCanvasMouseListener extends MouseAdapter
{
	private SceneryCanvas canvas;
	private Point drag_offset = null; //the drag offset is used to compute the position of a dragged item
	private JPanel drag;
	
	public SceneryCanvasMouseListener(SceneryCanvas canvas)
	{
	    this.canvas = canvas;
	}
	
	@Override
	public void mousePressed(MouseEvent me)
	{
		try
		{
			drag = (JPanel) canvas.getComponentAt(me.getPoint());
			drag_offset = new Point(drag.getLocation().x - me.getX(), drag.getLocation().y - me.getY());
			canvas.selectObject(drag.getName());
		}
		catch (Exception ex)
		{
			return;
		}

	}
	
	@Override
	public void mouseReleased(MouseEvent me)
	{
		drag = null;
		drag_offset = null;
	}
	
	@Override 
	public void mouseDragged(MouseEvent me)
	{
		try
		{
			if (drag == null || drag_offset == null)
			{
				mousePressed(me);
			}
			drag.setLocation(me.getX() + drag_offset.x, me.getY() + drag_offset.y);
		}
		catch (Exception ex)
		{
			return;
		}
	}
	
}