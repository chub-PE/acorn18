package ui.objecteditor;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VectorCanvasMouseListener extends MouseAdapter
{
	private VectorCanvas canvas;
	private Point drag_offset = null; //the drag offset is used to compute the position of a dragged item
	private VectorPoint drag;
	private boolean shift;
	
	public VectorCanvasMouseListener(VectorCanvas canvas)
	{
	    this.canvas = canvas;
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(
                new KeyEventDispatcher()
                {
                    public boolean dispatchKeyEvent(KeyEvent e)
                    {
                        shift = e.isShiftDown();
                        return false;
                    };
                });
	}
	
	@Override
	public void mousePressed(MouseEvent me)
	{
		try
		{
			drag = (VectorPoint) canvas.getComponentAt(me.getPoint());
			drag_offset = new Point(drag.getLocation().x - me.getX(), drag.getLocation().y - me.getY());
			canvas.getSelectionSidebar().updatePosition(drag.getPoint().x, drag.getPoint().y);
			if (shift) //if shift was held while clicking on a point, it gets dynamically selected
				canvas.dynamicallySelect(drag);
			else
			{
				canvas.deselectAll(); // all previously selected point gets deselected
				canvas.select(drag); //point gets selected
			}
		}
		catch (Exception ex)
		{
			if (!shift) // if shift wasnt held, and the click didn't hit any points, all gets deselected
				canvas.deselectAll();
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
			canvas.getSelectionSidebar().updatePosition(drag.getLocation().x, drag.getLocation().y);
			canvas.updateCanvas();
		}
		catch (Exception ex)
		{
			return;
		}
	}
	
}
