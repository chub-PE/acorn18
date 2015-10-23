package draw;

import geometry.AVector;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

//ACanvas contains layers of simple patterns (APatterns) and can render them to a bufferedimage
public class ACanvas
{
	public static final int TEMPLATE_NOTEXTURE = 1;

	public static final Color TRANSPARENT = new Color(0, 0, 0, 0);
	public static final Color PURPLE = new Color(150, 100, 200);
	public static final ACanvas NO_TEXTURE = new ACanvas(20, 20);
	
	private ArrayList<APattern> patterns = new ArrayList<APattern>();
	private int width, height;
	private Color background_color = TRANSPARENT;
	
	/**
	 * Contains a list of patterns, in a specific order. <br/>
	 * Has methods to print them on a monochrome image in a specific order to produce textures. <br/>
	 * The drawObject method takes an AVector object, and using it as outlines it creates a cut-out image of the texture.
	 * @param width width of canvas
	 * @param height height of canvas*/
	public ACanvas(int width, int height)
	{
		this.width = width;
		this.height = height;
	}
	
	//TODO decide if this constructor is necessary
	ACanvas(int template)
	{
		this(200, 200);
		switch (template)
		{
			case AObject.OBJECT_TEMPLATE_NOTEXTURE :
				background_color = PURPLE;
				return;
		}
	}

	
	/**
	 *Renders the texture to a custom size image.
	 *@param width width of the return image
	 *@param height height of the return image*/
	public BufferedImage drawTexture(int width, int height)
	{
		BufferedImage re = ACanvas.newMonochrome(width, height, background_color);
		for (int i = 0; i < patterns.size(); i++)
		{
			patterns.get(i).drawTo(re);
		}
		return re;
	}
	
	/**
	 *Renders the texture to a custom size image.
	 *Use getSize() and setSize() to modify.*/
	public BufferedImage drawTexture()
	{
		return drawTexture(width, height);
	}
	
	/**
	 *Creates a cutout of the texture, using the AVector as outlines.
	 *@param outlines the silhouette of the cutout*/
	public BufferedImage drawObject(AVector outlines)
	{
		//FIXME NOT WORKING
		Path2D path = outlines.toPath2D();
		int re_width = (int)(path.getBounds().width + outlines.getUpperLeftmost().x);
		int re_height = (int)(path.getBounds().height + outlines.getUpperLeftmost().y);
		//TODO delete print
		//System.out.println("path bounds width: " + path.getBounds().width);
		//System.out.println("path bounds height: " + path.getBounds().height);
		BufferedImage object = ACanvas.newTransparent(re_width, re_height);		
		BufferedImage texture = this.drawTexture(re_width, re_height);

		Graphics2D tg = texture.createGraphics();
		Graphics2D og = object.createGraphics();
		
		og.setColor(Color.BLACK);
		og.fill(path);
		
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.DST_IN, 1);
		tg.setComposite(ac);
		tg.drawImage(object, 0, 0, null);
		
		tg.dispose();
		og.dispose();
		
		return object;
	}
	
	/**
	 * Set the default size of the canvas.
	 * @param width width of the canvas
	 * @param height height of the canvas*/
	public void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Set the background color of the canvas.*/
	public void setBackgroundColor(Color color)
	{
		background_color = color;
	}
	
	/**
	 * Returns the default size of canvas*/
	public Rectangle getSize()
	{
		return new Rectangle(width, height);
	}
	
	/**
	 * Returns the background color.*/
	public Color getBackgroundColor()
	{
		return background_color;
	}

	
	/**
	 * Creates a new blank BufferedImage. </br>
	 * For internal use.
	 * @param width width of image
	 * @param height height of image
	 * @param background color of the BufferedImage*/
	private static BufferedImage newMonochrome(int width, int height, Color background)
	{
		BufferedImage return_value = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = return_value.createGraphics();
		g2d.setColor(background);
		g2d.fillRect(0, 0, width, height);
		g2d.dispose();
		return return_value;
	}
	
	/**
	 * Creates a new blank BufferedImage with transparent background.
	 * @param width width of image
	 * @param height height of image
	 * */
	private static BufferedImage newTransparent(int width, int height)
	{
		return newMonochrome(width, height, TRANSPARENT);
	}
	
	/**
	 * Returns all the APattern objects contained in this canvas
	 * @see APattern*/
	public APattern[] getPatterns()
	{
		return patterns.toArray(new APattern[patterns.size()]);
	}
	
	/**
	 * Creates a new default pattern, and adds it to this canvas.*/
	public APattern createPattern(int index)
	{
		APattern re = new EllipsePattern(50, 50);
		patterns.add(index, re);
		return re;
	}
	
	/**
	 * Adds a pattern to this canvas.
	 * @see APattern*/
	public void addPattern(APattern pattern)
	{
		patterns.add(pattern);
	}
	
	
	
	/**Replaces APattern object at index with a new one.
	 * @param pattern new pattern
	 * @param index index of change
	 * @see APattern*/
	public void replacePattern(APattern pattern, int index)
	{
		if (index > patterns.size())
		{
			return;
		}
		patterns.remove(index);
		patterns.add(index, pattern);
	}
	
	/**
	 * Removes specified pattern from the canvas.
	 * Returns false, if specified pattern wasn't on the canvas.
	 * @param pattern APattern to be removed
	 * @see APattern*/
	public boolean removePattern(APattern pattern)
	{
		return patterns.remove(pattern);
	}
	
	/**
	 * Removes pattern from the canvas at index. Does not make sure if index is valid.
	 * @param index index of removal.*/
	public void removePattern(int index)
	{
		//TODO if index invalid does it throw up?
		patterns.remove(index);
	}
}
