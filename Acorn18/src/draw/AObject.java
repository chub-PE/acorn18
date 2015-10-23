package draw;

import geometry.AVector;

import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

//AObject contains an outline (AVector) a texture (ACanvas), a JPanel and a JLabel
//it is used directly by the GUI
public class AObject
{
	//why is this here?
	public static final int OBJECT_TEMPLATE_NOTEXTURE = 1;
	
	private ACanvas canvas;
	private AVector vector;
	private JPanel panel;
	private JLabel label;
	
	private int layer = 0;
	private int size_ratio_x = 100;
	private int size_ratio_y = 100;
	
	private String name;
	
	/**
	 * Convinience object for GUI use, wrapping an outline (AVector) and a texture (ACanvas).
	 * The draw() method returns a cutout from the texture in the shape of the outlines.<br/>
	 * Has built-in swing components: a JLabel, that contains the image, and a JPanel, that contains the JLabel.
	 * Contains height and width size ratio precentage getters and setters. <br/>
	 * @param canvas the texture object
	 * @param vector the outline object
	 * @param name should be a unique identifier that can be used to reference this object. Not used internally.
	 * @see ACanvas
	 * @see AVector
	 * */
	public AObject(ACanvas canvas, AVector vector, String name)
	{
		this.canvas = canvas;
		this.vector = vector;
		this.name = name;
		this.panel = new JPanel();
		this.label = new JLabel();
		
		FlowLayout layout = new FlowLayout();
		
		//name should be a unique identifier, that can be used to track down this object
		panel.setName(name);
		label.setName(name);
		
		layout.setHgap(0);
		layout.setVgap(0);
		panel.setLayout(layout);
		panel.setLocation(0, 0);
		panel.setOpaque(false);
		
		panel.add(label);
		refreshImage();
	}
	
	/**
	 * Renders the contents of the object to a BufferedImage.
	 * @see BufferedImage*/
	public BufferedImage draw()
	{
		try
		{
			BufferedImage img = canvas.drawObject(vector);
			Rectangle new_dim = new Rectangle((img.getWidth() / 100) * size_ratio_x, (img.getHeight() / 100) * size_ratio_y);
			Image temp = img.getScaledInstance(new_dim.width, new_dim.height, Image.SCALE_SMOOTH);
			BufferedImage re = new BufferedImage(new_dim.width, new_dim.height, BufferedImage.TYPE_INT_ARGB);
		    Graphics2D g2d = re.createGraphics();
		    g2d.drawImage(temp, 0, 0, null);
		    g2d.dispose();
		    return re;
		}
		catch (IllegalArgumentException ex)
		{
			return new ACanvas(OBJECT_TEMPLATE_NOTEXTURE).drawObject(new AVector(OBJECT_TEMPLATE_NOTEXTURE));
		}

	}

	
	/**
	 * Returns a JLabel component that contains the rendered image.*/
	public JLabel getLabel()
	{
		return label;
	}
	
	/**
	 * Returns the JPanel component, that contains a JLabel with the rendered image.*/
	public JPanel getPanel()
	{
		return panel;
	}
	
	/**
	 * Returns the canvas object.*/
	public ACanvas getCanvas()
	{
		return canvas;
	}
	
	/**
	 * Returns the vector object.*/
	public AVector getVector()
	{
		return vector;
	}
	
	/**
	 * Returns the identifier of the object (the name of JLabel and JPanel components)*/
	public String getName()
	{
		return name;
	}
	
	/**
	 * Returns the layer on which the image should be rendered.<br/>
	 * This value is not used internally by the object*/
	public int getLayer()
	{
		return layer;
	}

	/**
	 * Returns the width size ratio precentage*/
	public int getSizeRatioX()
	{
		return size_ratio_x;
	}
	
	/**
	 * Retuns the height size ratio precentage*/
	public int getSizeRatioY()
	{
		return size_ratio_y;
	}
	
	/**
	 * Sets the size ratio precentage of the image. This data is used internally when rendering.
	 * @param x width size ratio
	 * @param y height size ratio*/
	public void setSizeRatio(int x, int y)
	{
		size_ratio_x = x;
		size_ratio_y = y;
		refreshImage();
	}
	
	/**
	 * Sets the width size ratio of the image. This data is used internally, when rendering.
	 * @param value width size ratio precentage*/
	public void setSizeRatioX(int value)
	{
		size_ratio_x = value;
		refreshImage();
	}
	
	/**
	 * Sets the height size ratio of the image. This data is used internally, when rendering.
	 * @param value height size ratio precentage*/
	public void setSizeRatioY(int value)
	{
		size_ratio_y = value;
		refreshImage();
	}
	
	/**
	 * Sets the layer on which the image should be rendered.
	 * This value is not used by the object directly.*/
	public void setLayer(int value)
	{
		layer = value;
	}
	
	/**
	 * Revalidates, repaints, and resizes (if needed) the swing components with the current version of the image.*/
	public void refreshImage()
	{
		ImageIcon pic = new ImageIcon(draw());
		label.setIcon(pic);
		panel.setSize(pic.getIconWidth(), pic.getIconHeight());
		label.revalidate();
		label.repaint();
	}
}
