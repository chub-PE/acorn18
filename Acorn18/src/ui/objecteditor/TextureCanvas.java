package ui.objecteditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.RNJ;
import ui.helper.DoubleSpinner;
import draw.AObject;
import draw.APattern;

//CONTAINER: TextureTab

public class TextureCanvas extends JPanel
{	
	private static final long serialVersionUID = 1L;

	private int index;
	
	private JPanel label_panel = null;
	private JLabel label = null;
	private APattern pattern = null;
	private JMenuBar menu_bar = null;
	
	private JComboBox<String> select_type = null;
	private JSpinner line_width = null;
	private JSpinner motif_width = null;
	private JSpinner motif_height = null;
	private DoubleSpinner x_offset = null;
	private DoubleSpinner y_offset = null;
	private DoubleSpinner rotation_angle = null;
	private JButton background_color = null;
	private JButton line_color = null;
	private JButton delete_layer = null;
	private ObjectEditorFrame main;
	
	private TextureTab texture;
	private AObject object;
	
	public TextureCanvas(ObjectEditorFrame main, APattern pattern, TextureTab texture, int index)
	{
		this.main = main;
		this.pattern = pattern;
		this.texture = texture;
		this.index = index;
		this.object = texture.getObject();
		
		//creating new components
		label_panel = new JPanel();
		label = new JLabel();
		menu_bar = new JMenuBar();
		select_type = new JComboBox<String>(APattern.PATTERN_TYPES);
		line_width = new JSpinner();
		motif_width = new JSpinner();
		motif_height = new JSpinner();
		x_offset = new DoubleSpinner();
		y_offset = new DoubleSpinner();
		rotation_angle = new DoubleSpinner();
		background_color = new JButton("Background Color");
		line_color = new JButton("Line Color");
		delete_layer = new JButton("Delete Layer");
		
		//setting default values to components
		refreshFields();
		select_type.setBorder(BorderFactory.createTitledBorder("Select Type"));
		line_width.setBorder(BorderFactory.createTitledBorder("Line Width (int)"));
		motif_width.setBorder(BorderFactory.createTitledBorder("Motif Width (int)"));
		motif_height.setBorder(BorderFactory.createTitledBorder("Motif Height (int)"));
		x_offset.setBorder(BorderFactory.createTitledBorder("X Offset Multiplier (double)"));
		y_offset.setBorder(BorderFactory.createTitledBorder("Y Offset Multiplier (double)"));
		rotation_angle.setBorder(BorderFactory.createTitledBorder("Rotation Angle (double)"));
		
		//adding components to containers
		menu_bar.add(select_type);
		menu_bar.add(line_width);
		menu_bar.add(motif_width);
		menu_bar.add(motif_height);
		menu_bar.add(x_offset);
		menu_bar.add(y_offset);
		menu_bar.add(rotation_angle);
		menu_bar.add(background_color);
		menu_bar.add(line_color);
		menu_bar.add(delete_layer);
		label_panel.add(label);
		this.setLayout(new BorderLayout());
		this.add(label_panel);
		this.add(menu_bar, BorderLayout.NORTH);
		
		refreshPicture();
		addListeners();
	}
	
	public int getIndex()
	{
		return index;
	}
	
	public void moveIndex(int add)
	{
		index = index + add;
	}

	public void refreshFields()
	{
		select_type.setSelectedItem(pattern.getType());
		line_width.setValue((double)pattern.getLineWidth());
		motif_width.setValue(pattern.getWidth());
		motif_height.setValue(pattern.getHeight());
		x_offset.setValue(pattern.getHorizontalOM());;
		y_offset.setValue(pattern.getVerticalOM());;
		rotation_angle.setValue(pattern.getRotation());
		background_color.setBackground(object.getCanvas().getBackgroundColor());
		line_color.setBackground(pattern.getLineColor());
	}
	
	public void refreshPicture()
	{
		Dimension image_size = new Dimension(main.getSize().height, main.getSize().height);		
		label.setIcon(new ImageIcon(pattern.draw(image_size, object.getCanvas().getBackgroundColor())));
		this.setSize(image_size);
	}
	
	public void deleteLayer()
	{
		Object[] options =
		{
				"Yes",
                "No"
        };
		int n = JOptionPane.showOptionDialog(menu_bar,
		"Are you sure you want to delete this layer?",
		RNJ.random(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
		null, options, options[1]);
		
		switch (n)
		{
			case 0:
				object.getCanvas().removePattern(pattern);
				texture.removeTab(index);
				texture.refreshTextureTab();
				return;
			default:
				return;
		}
	}
	
	
	private void addListeners()
	{
		select_type.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				pattern = APattern.patternFactory((String)select_type.getSelectedItem(),
						pattern);
				object.getCanvas().replacePattern(pattern, index);
				refreshPicture();
			}
		});
				
		line_width.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				if ((int)line_width.getValue() < 1)
				{
					line_width.setValue(1);
				}
				pattern.setLineWidth((int)line_width.getValue());
				refreshPicture();
				
			}
		});
		
		motif_width.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				if ((int)motif_width.getValue() < 1)
				{
					motif_width.setValue(1);
				}
				pattern.setWidth((int)motif_width.getValue());
				refreshPicture();
			}
		});
		
		motif_height.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				if ((int)motif_height.getValue() < 1)
				{
					motif_height.setValue(1);
				}
				pattern.setHeight((int)motif_height.getValue());
				refreshPicture();
			}
		});
		
		x_offset.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				if(x_offset.getDouble() < 0)
				{
					x_offset.setValue((double)0);
				}
				pattern.setXOffset(x_offset.getDouble());
				refreshPicture();
			}
		});
		
		y_offset.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				if(y_offset.getDouble() < 0)
				{
					y_offset.setValue((double)0);
				}
				System.out.println(y_offset.getDouble());
				pattern.setYOffset(y_offset.getDouble());
				refreshPicture();
			}
		});
		
		rotation_angle.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				if (rotation_angle.getDouble() >= 360d)
				{
					rotation_angle.setValue((double)0);
				}
				if (rotation_angle.getDouble() <= -360d)
				{
					rotation_angle.setValue((double)0);
				}
				pattern.setRotation(rotation_angle.getDouble());
				refreshPicture();
			}
		});
		
		line_color.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Color color = JColorChooser.showDialog(menu_bar,
						"Choose line color", pattern.getLineColor());
				if (color != null)
				{
					pattern.setLineColor(color);
					refreshPicture();
					refreshFields();
				}
			}
		});

		background_color.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Color color = JColorChooser.showDialog(menu_bar,
						"Choose background color", object.getCanvas().getBackgroundColor());
				if (color != null)
				{
					object.getCanvas().setBackgroundColor(color);
					refreshPicture();
					refreshFields();
				}
			}
		});
		
		delete_layer.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				deleteLayer();
			}
		});		
	}
}
