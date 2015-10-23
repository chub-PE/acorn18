package ui.sceneryeditor;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ui.objecteditor.ObjectEditorFrame;

//CONTAINER: SceneryEditorFrame

public class CurrentSidebar extends JPanel
{
	private static final long serialVersionUID = 1L;
	private static final String NO_SELECTION_VALUE = "<none>";
	
	private JComboBox<String> object_list = null;
	private JSpinner object_pos_x = null;
	private JSpinner object_pos_y = null;
	private JSpinner object_pos_z = null;
	private JSpinner object_ratio_x = null;
	private JSpinner object_ratio_y = null;
	private JButton snap_wizzard = null; // for snapping against other objects
	private JButton edit_object = null; // for editing in another window
	private SceneryEditorFrame main = null;
	
	public CurrentSidebar(SceneryEditorFrame main)
	{
		this.main = main;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createTitledBorder("Current Object"));

		//declaring components
		object_list = new JComboBox<String>();
		object_pos_x = new JSpinner();
		object_pos_y = new JSpinner();
		object_pos_z = new JSpinner();
		object_ratio_x = new JSpinner();
		object_ratio_y = new JSpinner();
		snap_wizzard = new JButton("Snap Wizzard");
		edit_object = new JButton("Edit Object");

		//setting default values to components
		object_list.addItem(NO_SELECTION_VALUE);
		object_pos_x.setBorder(BorderFactory.createTitledBorder("x"));
		object_pos_y.setBorder(BorderFactory.createTitledBorder("y"));
		object_pos_z.setBorder(BorderFactory.createTitledBorder("z"));
		object_ratio_x.setBorder(BorderFactory.createTitledBorder("x"));
		object_ratio_y.setBorder(BorderFactory.createTitledBorder("y"));
		deselectObject(); // disables unaccessable components
		
		//setting preferred size
		object_list.setPreferredSize(main.defCompSize());
		object_pos_x.setPreferredSize(new Dimension(main.defCompSize().width / 3, main.defCompSize().height));
		object_pos_y.setPreferredSize(new Dimension(main.defCompSize().width / 3, main.defCompSize().height));
		object_pos_z.setPreferredSize(new Dimension(main.defCompSize().width / 3, main.defCompSize().height));
		object_ratio_x.setPreferredSize(new Dimension(main.defCompSize().width / 2, main.defCompSize().height));
		object_ratio_y.setPreferredSize(new Dimension(main.defCompSize().width / 2, main.defCompSize().height));
		snap_wizzard.setPreferredSize(main.defCompSize());
		edit_object.setPreferredSize(main.defCompSize());
		
		//declaring enclosing panels for components
		JPanel object_list_container = new JPanel();
		JPanel object_position_container = new JPanel();
		JPanel object_ratio_container = new JPanel();
		JPanel snap_wizzard_container = new JPanel();
		JPanel edit_object_container = new JPanel();
		
		//adding components to enclosing panels 
		object_list_container.add(object_list);
		object_position_container.add(object_pos_x);
		object_position_container.add(object_pos_y);
		object_position_container.add(object_pos_z);
		object_ratio_container.add(object_ratio_x);
		object_ratio_container.add(object_ratio_y);
		snap_wizzard_container.add(snap_wizzard);
		edit_object_container.add(edit_object);

		//setting borders for enclosing panels 
		object_list_container.setBorder(BorderFactory.createTitledBorder("Object List"));
		object_position_container.setBorder(BorderFactory.createTitledBorder("Object Position"));
		object_ratio_container.setBorder(BorderFactory.createTitledBorder("Object Size Ratio"));
		
		//adding enclosing panels to container panel
		this.add(object_list_container);
		this.add(object_position_container);
		this.add(object_ratio_container);
		this.add(snap_wizzard_container);
		this.add(edit_object_container);
		
		createActionListeners();
		
	}
	
	public String getCurrentObjectName()
	{
		return object_list.getSelectedItem().toString();
	}
	
	public void addNewObject(String name)
	{
		object_list.addItem(name);
	}
	
	public void selectObject()
	{
		if (!object_list.getSelectedItem().toString().equals(main.canvas().getSelectedObject().getName()))
		{
			object_list.setSelectedItem(main.canvas().getSelectedObject().getName());
		}
		
		refreshSelected();
		
		object_pos_x.setEnabled(true);
		object_pos_y.setEnabled(true);
		object_pos_z.setEnabled(true);
		object_ratio_x.setEnabled(true);
		object_ratio_y.setEnabled(true);
		snap_wizzard.setEnabled(true);
		edit_object.setEnabled(true);		
	}
	
	public void deselectObject()
	{
		object_pos_x.setEnabled(false);
		object_pos_y.setEnabled(false);
		object_pos_z.setEnabled(false);
		object_ratio_x.setEnabled(false);
		object_ratio_y.setEnabled(false);
		snap_wizzard.setEnabled(false);
		edit_object.setEnabled(false);
	}
	
	public void refreshSelected()
	{
		if (main.canvas().getSelectedObject() != null)
		{
			object_pos_x.setValue(main.canvas().getSelectedObject().getPanel().getLocation().x);
			object_pos_y.setValue(main.canvas().getSelectedObject().getPanel().getLocation().y);
			object_pos_z.setValue(main.canvas().getSelectedObject().getLayer());
			object_ratio_x.setValue(main.canvas().getSelectedObject().getSizeRatioX());
			object_ratio_y.setValue(main.canvas().getSelectedObject().getSizeRatioY());
		}
	}
	
	private void createActionListeners()
	{
		object_list.addActionListener(new ActionListener ()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	if (main.canvas() != null)
		    	{
		    		//if the value selected was no selection
		    		if (object_list.getSelectedItem().toString().equals(NO_SELECTION_VALUE))
		    		{
		    			main.canvas().deselectObject();
		    			deselectObject();
		    		}
		    		else
		    		{
		    			main.canvas().selectObject(object_list.getSelectedItem().toString());
		    		}
		    	}
		    }
		});
		
		object_pos_x.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				Point p = new Point ((Integer)object_pos_x.getValue(),
						main.canvas().getSelectedObject().getPanel().getLocation().y);
				main.canvas().getSelectedObject().getPanel().setLocation(p);
			}
		});
		
		object_pos_y.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				Point p = new Point (main.canvas().getSelectedObject().getPanel().getLocation().x,
						(Integer)object_pos_y.getValue());
				main.canvas().getSelectedObject().getPanel().setLocation(p);
			}
		});
		
		object_pos_z.addChangeListener(new ChangeListener()
		{

			@Override
			public void stateChanged(ChangeEvent e)
			{
				int new_value = (Integer)object_pos_z.getValue();
	        	if (new_value > -1)
	        	{
	        		main.canvas().changeLayerOfSelected(new_value);
	        	}
	        	else
	        	{
	        		object_pos_z.setValue(0);
	        	}
				
			}
			
		});
		
		object_ratio_x.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				//if there is no object selected, the ratio cannot be changed
				if(main.canvas().getSelectedObject() == null)
				{
					return;
				}
				try
				{
					int value = (int) object_ratio_x.getValue();
					if (value < 0)
					{
						throw new Exception();
					}
					else
					{
						main.canvas().getSelectedObject().setSizeRatioX(value);
					}
				}
				catch(Exception ex)
				{
					//if the value entered was invalid, it displays the original object size ratio
					object_ratio_x.setValue(main.canvas().getSelectedObject().getSizeRatioX());
				}
			}
			
		});
		
		object_ratio_y.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				//if there is no object selected, the ratio cannot be changed
				if(main.canvas().getSelectedObject() == null)
				{
					return;
				}
				try
				{
					int value = (int) object_ratio_y.getValue();
					if (value < 0)
					{
						throw new Exception();
					}
					else
					{
						main.canvas().getSelectedObject().setSizeRatioY(value);
					}
				}
				catch(Exception ex)
				{
					//if the value entered was invalid, it displays the original object size ratio
					object_ratio_y.setValue(main.canvas().getSelectedObject().getSizeRatioY());
				}
			}
			
		});
		
		edit_object.addActionListener(new ActionListener ()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	new ObjectEditorFrame(main.canvas().getSelectedObject());
		    }
		});
	}
	
}

