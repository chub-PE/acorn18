package ui.objecteditor;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

//CONTAINER: VectorTab

public class VectorSelectionSidebar extends JPanel
{
	public static final String TITLE = "Selection Manager";
	private static final long serialVersionUID = 1L;
	
	private DefaultListModel<String> list_of_shapes_model = null;
	private JList<String> list_of_shapes = null;
	private JButton remove_selected = null;
	private JButton new_line = null;
	private JButton new_ellipse = null;
	
	private ObjectEditorFrame main = null;
	
	public VectorSelectionSidebar(ObjectEditorFrame main)
	{
		this.main = main;
		this.setBorder(BorderFactory.createTitledBorder(TITLE));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		//create components
		list_of_shapes_model = new DefaultListModel<String>();
		list_of_shapes = new JList<>(list_of_shapes_model);
		remove_selected = new JButton("Remove Shape");
		new_line = new JButton("New Line");
		new_ellipse = new JButton("New Ellipse");
		
		//create container panels
		JScrollPane list_of_shapes_panel = new JScrollPane(list_of_shapes);
		JPanel remove_selected_panel = new JPanel();
		JPanel new_line_panel = new JPanel();
		JPanel new_ellipse_panel = new JPanel();
		
		//set defaults for components
		list_of_shapes_panel.setBorder(BorderFactory.createTitledBorder("Related Shapes"));
		remove_selected.setPreferredSize(main.defCompSize());
		new_line.setPreferredSize(main.defCompSize());
		new_ellipse.setPreferredSize(main.defCompSize());
		
		//add components to containers
		remove_selected_panel.add(remove_selected);
		new_line_panel.add(new_line);
		new_ellipse_panel.add(new_ellipse);
		this.add(list_of_shapes_panel);
		this.add(remove_selected_panel);
		this.add(new_line_panel);
		this.add(new_ellipse_panel);		
	}
	
	public void updatePosition(double x, double y)
	{
		remove_selected.setText("x: " + x);
		new_line.setText("y: " + y);
	}
	
	ObjectEditorFrame getMain()
	{
		return main;
	}
}
