package ui.sceneryeditor;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

//CONTAINER: SceneryEditorFrame

public class ToolsSidebar extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JCheckBox show_all_layers = null;
	private JSpinner layer_select = null;
	private JButton undo = null;
	private JButton redo = null;
	private JButton new_object = null;
	private JButton import_object = null;
	private JButton bg_color = null;
	private JButton export = null;

	private SceneryEditorFrame main = null;
		
	public ToolsSidebar(SceneryEditorFrame main)
	{
		this.main = main;
		
		//setting the container up
		this.setBorder(BorderFactory.createTitledBorder("Project Tools"));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		//declaring components
		layer_select = new JSpinner();
		show_all_layers = new JCheckBox("Show All");
		undo = new JButton("Undo");
		redo = new JButton("Redo");
		new_object = new JButton("New Object");
		import_object = new JButton("Import Object");
		bg_color = new JButton("Background Color");
		export = new JButton("Export");
		
		//setting default values to components
		setShowAll(true);
		
		//setting preferred size
		layer_select.setPreferredSize(new Dimension (main.defCompSize().width / 2, main.defCompSize().height));
		show_all_layers.setPreferredSize(new Dimension (main.defCompSize().width / 2, main.defCompSize().height));
		undo.setPreferredSize(new Dimension (main.defCompSize().width / 2, main.defCompSize().height));
		redo.setPreferredSize(new Dimension (main.defCompSize().width / 2, main.defCompSize().height));		
		new_object.setPreferredSize(main.defCompSize());
		import_object.setPreferredSize(main.defCompSize());
		bg_color.setPreferredSize(main.defCompSize());
		export.setPreferredSize(main.defCompSize());

		//declaring enclosing panels for components
		JPanel layer_select_panel = new JPanel();
		JPanel undo_redo_panel = new JPanel();
		JPanel new_object_panel = new JPanel();
		JPanel import_object_panel = new JPanel();
		JPanel bg_color_panel = new JPanel();
		JPanel export_panel = new JPanel();

		//adding components to enclosing panels 
		layer_select_panel.add(layer_select);
		layer_select_panel.add(show_all_layers);
		undo_redo_panel.add(undo);
		undo_redo_panel.add(redo);
		new_object_panel.add(new_object);
		import_object_panel.add(import_object);
		bg_color_panel.add(bg_color);
		export_panel.add(export);

		//setting borders for enclosing panels 
		layer_select_panel.setBorder(BorderFactory.createTitledBorder("Layer Select"));

		//adding enclosing panels to container panel
		this.add(layer_select_panel);
		this.add(undo_redo_panel);
		this.add(new_object_panel);
		this.add(import_object_panel);
		this.add(bg_color_panel);
		this.add(export_panel);
		
		createActionListeners();
	}
	
	public void setShowAll(boolean value) //FIXME do some other shit
	{
		show_all_layers.setSelected(value);
		layer_select.setEnabled(!value);
	}
	
	private void createActionListeners()
	{
		layer_select.addChangeListener(new ChangeListener()
		{
	        @Override
	        public void stateChanged(ChangeEvent e)
	        {
	        	if ((Integer)layer_select.getValue() > -1)
	        	{
		        	main.canvas().selectLayer((Integer)layer_select.getValue());
	        	}
	        	else
	        	{
	        		layer_select.setValue(0);
	        	}
	        }
	    });

	}
	
}
