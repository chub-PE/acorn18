package geometry;

import java.awt.geom.Path2D;

public abstract class AShape
{
	public static final int TYPE_CURVE = 1;
	public static final int TYPE_ELLIPSE = 2;
	public static final int TYPE_RECTANGLE = 3;	
	public static final int TYPE_TRIANGLE = 4;
	
	@SuppressWarnings("unused")
	private int type;
	
	protected APoint origin = null;
	protected APoint destination = null;
	
	protected APoint origin_control = null;
	protected APoint destination_control = null;
	
	protected boolean origin_control_active = false;
	protected boolean destination_control_active = false;
	
	protected AShape(APoint origin, APoint destination, int type)
	{
		this.type = type;
		this.origin = origin;
		this.destination = destination;
		origin_control = new APoint(origin.x, origin.y, APoint.TYPE_HELPER, 0);
		destination_control = new APoint(destination.x, destination.y, APoint.TYPE_HELPER, 1);
	}
	
	public abstract Path2D.Double drawTo(Path2D.Double original, APencil pencil);
	
	public static AShape shapeFactory(APoint origin, APoint destination, int type) //TODO should widen the variety as it comes
	{
		switch (type)
		{
		case TYPE_CURVE:
			return new ACurve(origin, destination);
		case TYPE_ELLIPSE:
			return null; // TODO something
		case TYPE_RECTANGLE:
			return null; // TODO something
		case TYPE_TRIANGLE:
			return null; // TODO something
		default:
			return null;
		}
	}
	
	public APoint getOrigin()
	{
		return origin;
	}
	
	public void removeSelfReferences()
	{
		if (origin != null)
			origin.removeShape(this);
		if (destination != null)
			destination.removeShape(this);
		if (origin_control != null)
			origin_control.removeShape(this);
		if (destination_control != null)
			destination_control.removeShape(this);
	}
	
	public APoint getDestination()
	{
		return destination;
	}
		
	public APoint getOriginControl()
	{
		return origin_control;
	}
	
	public APoint getDestinationControl()
	{
		return destination_control;
	}
	
	public void setOriginControl(int x, int y)
	{
		origin_control.setPosition(x, y);
		setOriginControlActive(true);
	}
	
	public void setDestinationControl(int x, int y)
	{
		destination_control.setPosition(x, y);
		setDestinationControlActive(true);
	}
	
	public void setOriginControlActive(boolean value)
	{
		origin_control_active = value;
	}
	
	public void setDestinationControlActive(boolean value)
	{
		destination_control_active = value;
	}
	
	protected void pencilHandler(APencil pencil)
	{
		pencil.last_point = this.getDestination();
		pencil.addShape(this);
	}
	
	public boolean isBroken(APencil pencil)
	{
		if (pencil.last_point != null)
			if (pencil.last_point.equals(this.getOrigin()))
				return false;
		return true;
	}
}
