package ui.objecteditor;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

//CONTAINER: VectorTab

public class VectorElementsSidebar extends JPanel
{
	private static final long serialVersionUID = 1L;
	public static final String TITLE = "Elements Manager";
	
	private DefaultListModel<String> list_of_all_points_model = null;
	private DefaultListModel<String> list_of_all_shapes_model = null;
	private JList<String> list_all_of_points = null;
	private JButton remove_point = null;
	private JList<String> list_all_of_shapes = null;
	private JButton remove_shape = null;
	
	private ObjectEditorFrame main = null;

	public VectorElementsSidebar(ObjectEditorFrame main)
	{
		this.main = main;
		this.setBorder(BorderFactory.createTitledBorder(TITLE));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		//create components
		list_of_all_points_model = new DefaultListModel<String>();
		list_of_all_shapes_model = new DefaultListModel<String>();
		list_all_of_points = new JList<>(list_of_all_points_model);
		remove_point = new JButton("Remove Point");
		list_all_of_shapes = new JList<>(list_of_all_shapes_model);
		remove_shape = new JButton("Remove Shape");
		
		//create container panels
		JScrollPane list_of_all_points_panel = new JScrollPane(list_all_of_points);
		JPanel remove_point_panel = new JPanel();
		JScrollPane list_of_all_shapes_panel = new JScrollPane(list_all_of_shapes);
		JPanel remove_shape_panel = new JPanel();
		
		//set defaults for components
		list_of_all_points_panel.setBorder(BorderFactory.createTitledBorder("List of Points"));
		remove_point.setPreferredSize(main.defCompSize());
		list_of_all_shapes_panel.setBorder(BorderFactory.createTitledBorder("List of Shapes"));
		remove_shape.setPreferredSize(main.defCompSize());
		
		//add components to containers
		remove_point_panel.add(remove_point);
		remove_shape_panel.add(remove_shape);
		this.add(list_of_all_points_panel);
		this.add(remove_point_panel);
		this.add(list_of_all_shapes_panel);
		this.add(remove_shape_panel);
	}	
	
	ObjectEditorFrame getMain()
	{
		return main;
	}
}
