package ui.objecteditor;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import draw.AObject;

//CONTAINER: ObjectEditorFrame

public class ObjectEditorPanel extends JTabbedPane
{
	private static final long serialVersionUID = 1L;
	
	private VectorTab vector_tab = null;
	private TextureTab texture_tab = null;
	private AObject object = null;
	private ObjectEditorFrame main = null;
	
	public ObjectEditorPanel(ObjectEditorFrame main, AObject object)
	{
		this.main = main;
		this.object = object;
		
		vector_tab = new VectorTab(main, object);
		texture_tab = new TextureTab(main, object);

		this.addTab("Texture", texture_tab);
		this.addTab("Outline", vector_tab);
		
	}
	
	public ObjectEditorFrame getMain()
	{
		return main;
	}
	
	public AObject getObject()
	{
		return object;
	}
	
	public VectorTab getVectorTab()
	{
		return vector_tab;
	}
	
	public TextureTab getTextureTab()
	{
		return texture_tab;
	}
}
