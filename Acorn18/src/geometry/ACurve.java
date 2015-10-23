package geometry;
import java.awt.geom.Path2D;

/**
 * Acorn Curve is an implementation of AShape, that represents a Bezier curve in an AVector.
 * The AShape control points represent the Bezier control points.
 * */
public class ACurve extends AShape
{
	public ACurve (APoint origin, APoint destination)
	{
		super(origin, destination, TYPE_CURVE);
	}
	
	@Override
	public Path2D.Double drawTo(Path2D.Double original, APencil pencil)
	{	
		if (isBroken(pencil))
		{
			original.moveTo(getOrigin().x, getOrigin().y);
			System.out.println("moved to x: " + getOrigin().x + " y: " + getOrigin().y);
		}
		if (origin_control_active || destination_control_active)
		{
			original.curveTo(getOriginControl().x, getOriginControl().y,
					getDestinationControl().x, getDestinationControl().y, getDestination().x, getDestination().y);
			System.out.println("curved to x: " + getDestination().x + " y: " + getDestination().y);
		}
		else
		{
			original.lineTo(getDestination().x, getDestination().y);
			System.out.println("lined to x: " + getDestination().x + " y: " + getDestination().y);
		}
		pencilHandler(pencil);		
		return original;
	}
	
	@Override
	public APoint getOriginControl()
	{
		if (origin_control != null)
			return origin_control;
		else
			return origin;
	}
	
	@Override
	public APoint getDestinationControl()
	{
		if (destination_control != null)
			return destination_control;
		else
			return destination;
	}
}
