package ui.sceneryeditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

//MAIN FRAME

public class SceneryEditorFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	public static final String TITLE = "Acorn18 Scenery Editor 0 pre-alpha";
	
	private SceneryCanvas scenery_canvas = null;
	private ToolsSidebar tools_sidebar = null;
	private CurrentSidebar current_sidebar = null;
	
	public SceneryEditorFrame()
	{		
		super(TITLE);
		try {
	        SwingUtilities.invokeAndWait(new Runnable()
	        {
	            public void run()
	            {
	            	Dimension src_size = Toolkit.getDefaultToolkit().getScreenSize();
	        		SceneryEditorFrame.this.setSize((int)(src_size.width / 1.1), (int)(src_size.height / 1.1));
	        		SceneryEditorFrame.this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        		SceneryEditorFrame.this.setVisible(true);
	        		
	        		JPanel sidebars_panel = new JPanel();
	        		SceneryEditorFrame.this.getContentPane().setLayout(new BorderLayout());
	        		sidebars_panel.setLayout(new BoxLayout(sidebars_panel, BoxLayout.Y_AXIS));
	        		
	        		scenery_canvas = new SceneryCanvas(SceneryEditorFrame.this);
	        		tools_sidebar = new ToolsSidebar(SceneryEditorFrame.this);
	        		current_sidebar = new CurrentSidebar(SceneryEditorFrame.this);
	        		
	        		sidebars_panel.add(tools_sidebar);
	        		sidebars_panel.add(current_sidebar);
	        		SceneryEditorFrame.this.getContentPane().add(scenery_canvas, BorderLayout.CENTER);
	        		SceneryEditorFrame.this.getContentPane().add(sidebars_panel, BorderLayout.EAST);
	        		
	        		addListeners();
	        		scenery_canvas.test();
	        		update();
	            }
	        });
	    }
		catch (Exception e)
		{
			e.printStackTrace();
	    }
	}
	
	public void update()
	{
		SwingUtilities.updateComponentTreeUI(this);
	}

	public Dimension getSize()
	{
		return new Dimension(this.getBounds().width, this.getBounds().height);
	}

	private void addListeners()
	{
		this.addWindowFocusListener(new WindowFocusListener()
		{

			@Override
			public void windowGainedFocus(WindowEvent arg0)
			{
				update();
			}
			@Override
			public void windowLostFocus(WindowEvent arg0)
			{
		
			}

		});
	}
	
	
	public CurrentSidebar current()
	{
		return current_sidebar;
	}

	public SceneryCanvas canvas()
	{
		return scenery_canvas;
	}
	
	public ToolsSidebar tools()
	{
		return tools_sidebar;
	}
	
	/**
	 * Returns a recommended size for a sidebar element, for a unified look*/
	public Dimension defCompSize()
	{
		return new Dimension ((int) (getSize().getWidth() / 6), 50);
	}
	
}
