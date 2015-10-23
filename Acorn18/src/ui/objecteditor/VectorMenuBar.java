package ui.objecteditor;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JMenuBar;

//CONTAINER: VectorTab

public class VectorMenuBar extends JMenuBar
{
	private static final long serialVersionUID = 1L;
	
	private JButton add_point;
	private JButton remove_point;
	private JButton insert_line;
	private JButton insert_ellipse;
	private JButton insert_rectangle;
	private JButton insert_triangle;
	private JCheckBox show_control_points;
	private JCheckBox show_points;
	
	private ObjectEditorFrame main;

	public VectorMenuBar(ObjectEditorFrame main)
	{
		this.main = main;
		FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
		layout.setVgap(0);
		layout.setHgap(0);
		this.setLayout(layout);

		add_point = new JButton("New Point");
		remove_point = new JButton("Remove Point");
		insert_line = new JButton("Insert Line");
		insert_ellipse = new JButton("Insert Ellipse");
		insert_rectangle = new JButton("Insert Rectangle");
		insert_triangle = new JButton("Insert Triangle");
		show_control_points = new JCheckBox("Show Control Points");
		show_points = new JCheckBox("Show Points");

		this.add(add_point);
		this.add(remove_point);
		this.add(insert_line);
		this.add(insert_ellipse);
		this.add(insert_rectangle);
		this.add(insert_triangle);
		this.add(show_control_points);
		this.add(show_points);
	}
	
	ObjectEditorFrame getMain()
	{
		return main;
	}
}
