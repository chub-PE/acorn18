package draw;

import geometry.AVector;

import java.awt.Shape;

public class VectorPattern extends APattern //TODO test
{
	
	private AVector vector;
	
	public VectorPattern(int width, int height, AVector vector)
	{
		super(width, height);
	}

	@Override
	public Shape getShape(double x, double y)
	{
		return vector.toPath2D();
	}

	@Override
	public String getType()
	{
		return null;
	}
}
