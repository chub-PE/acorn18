package ui.objecteditor;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import draw.AObject;

//CONTAINER: ObjectEditorPanel

public class VectorTab extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JPanel side_panel = null;
	
	//CONTAINS
	private VectorMenuBar vector_menu_bar = null;
	private VectorCanvas vector_canvas = null;
	private VectorSelectionSidebar vector_selection_sidebar = null;
	private VectorElementsSidebar vector_elements_sidebar = null;
	private ObjectEditorFrame main = null;

	public VectorTab(ObjectEditorFrame main, AObject object)
	{
		this.main = main;
		this.setLayout(new BorderLayout());
		
		vector_menu_bar = new VectorMenuBar(main);
		vector_selection_sidebar = new VectorSelectionSidebar(main);
		vector_elements_sidebar = new VectorElementsSidebar(main);
		vector_canvas = new VectorCanvas(main, object);
		side_panel = new JPanel();
		side_panel.setLayout(new BoxLayout(side_panel, BoxLayout.Y_AXIS));
		side_panel.add(vector_selection_sidebar);
		side_panel.add(vector_elements_sidebar);
		
		this.add(vector_menu_bar, BorderLayout.NORTH);
		this.add(side_panel, BorderLayout.EAST);
		this.add(vector_canvas, BorderLayout.CENTER);
	}

	public ObjectEditorFrame getMainFrame()
	{
		return main;
	}
	
	public VectorMenuBar getMenuBar()
	{
		return vector_menu_bar;
	}
	
	public VectorSelectionSidebar getSelectionSidebar()
	{
		return vector_selection_sidebar;
	}
	
	public VectorElementsSidebar getElementManager()
	{
		return vector_elements_sidebar;
	}
	
	public VectorCanvas getVectorCanvas()
	{
		return vector_canvas;
	}
	
}
