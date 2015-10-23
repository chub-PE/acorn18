package ui.objecteditor;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import draw.AObject;

//MAIN FRAME

public class ObjectEditorFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	public static final String TITLE = "Acorn18 Object Editor";
	private ObjectEditorPanel object_editor_panel;
	
	public ObjectEditorFrame(AObject object)
	{
		super(TITLE + " - Object '" + object.getName() + "'");
    	Dimension src = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize((int)(src.width / 1.1), (int)(src.height / 1.1));
		this.setVisible(true);
		object_editor_panel = new ObjectEditorPanel(ObjectEditorFrame.this, object);
		this.add(object_editor_panel);
	
		update();
	}
	

	public void update()
	{
		SwingUtilities.updateComponentTreeUI(this);
	}

	public Dimension getSize()
	{
		return new Dimension(this.getBounds().width, this.getBounds().height);
	}
	
	public ObjectEditorPanel getOEPanel()
	{
		return object_editor_panel;
	}
	
	/**
	 * Returns a recommended size for a sidebar element, for a unified look*/
	public Dimension defCompSize()
	{
		return new Dimension ((int) (getSize().getWidth() / 6), 50);
	}
	
	public ObjectEditorPanel mainPanel()
	{
		return object_editor_panel;
	}

}
