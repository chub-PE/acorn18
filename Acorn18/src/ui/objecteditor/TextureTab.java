package ui.objecteditor;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.RNJ;
import draw.AObject;
import draw.APattern;

//CONTAINER: ObjectEditorPanel

public class TextureTab extends JTabbedPane
{
	private static final long serialVersionUID = 1L;
	private static final String TABNAME_NEW_LAYER = "+";
	private static final String TABNAME_ALL_LAYER = "texture";
	private static final String TABNAME_DEFAULT = "pattern";
	
	private int number_of_tabs = 0;
	private int selected_tab = 0;
	
	private JPanel all_layers_panel = null;
	private JLabel all_layers_label = null;
	
	private ArrayList<TextureCanvas> tabs = new ArrayList<TextureCanvas>();
	
	private AObject object = null;
	private ObjectEditorFrame main;

	public TextureTab(ObjectEditorFrame main, AObject object)
	{
		this.object = object;
		this.main = main;
		
		all_layers_panel = new JPanel();
		all_layers_label = new JLabel();
		all_layers_panel.add(all_layers_label);
		
		this.insertTab(TABNAME_NEW_LAYER, null, null, "Create New Layer", 0);
		this.insertTab(TABNAME_ALL_LAYER, null, all_layers_panel, "All Layers", 0);

		for (APattern ap : object.getCanvas().getPatterns())
		{
			addTab(ap);
		}
		addListeners();
	}
	
	public void addTab(APattern pattern)
	{
		TextureCanvas new_tab = new TextureCanvas(main, pattern, this, number_of_tabs);
		this.insertTab(TABNAME_DEFAULT, null, new_tab,
				RNJ.random(), number_of_tabs);
		tabs.add(number_of_tabs, new_tab);
		changeSelection(number_of_tabs);
		number_of_tabs++;
		refreshTextureTab();
	}
	
	public void changeSelection(int value)
	{
		selected_tab = value;
		this.setSelectedIndex(selected_tab);
	}
	
	public AObject getObject()
	{
		return object;
	}
	
	public void removeTab(int index)
	{
		this.remove(index);
		tabs.remove(index);
		refactorTabs(index);
		number_of_tabs--;
		changeSelection(number_of_tabs);
	}
	
	public void refactorTabs(int index)
	{
		for (int i = index; i < tabs.size(); i++)
		{
			tabs.get(i).moveIndex(-1);
		}
	}
		
	public void refreshTextureTab()
	{
		ImageIcon icon = new ImageIcon(object.getCanvas().drawTexture(main.getSize().height,
				main.getSize().height));
		
		all_layers_label.setIcon(icon);
		all_layers_panel.setSize(icon.getIconWidth(), icon.getIconHeight());
	}
	
	
	private void addListeners()
	{
		this.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				//if the ADD NEW TAB is clicked
				if (TextureTab.this.getSelectedIndex() == number_of_tabs + 1)
				{
					addTab(object.getCanvas().createPattern(number_of_tabs));
					changeSelection(number_of_tabs - 1);
				}
				else
				{
					selected_tab = TextureTab.this.getSelectedIndex();
				}
				if (TextureTab.this.getSelectedIndex() == number_of_tabs)
				{
					refreshTextureTab();
				}
			}
			
		});
	}
}
