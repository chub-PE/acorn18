package main;

import geometry.APoint;
import geometry.AShape;
import geometry.AVector;

import java.awt.Color;

import ui.objecteditor.ObjectEditorFrame;
import ui.sceneryeditor.SceneryEditorFrame;
import draw.ACanvas;
import draw.AObject;
import draw.EllipsePattern;
import draw.LinePattern;
import draw.RectanglePattern;

public class Control
{
	public static void main(String[] args)
	{
		new SceneryEditorFrame();
		//new OEMainFrame(objectTest());
	}
	
	public static AObject objectTest()
	{
		int width = 500, height = 500;
		EllipsePattern pattern = new EllipsePattern(40, 40);
		pattern.setOffset(1, 1);
		pattern.setLineColor(Color.RED);
		pattern.setLineWidth(3);
		
		LinePattern pattern2 = new LinePattern(10);
		pattern2.setLineColor(Color.GREEN);
		pattern2.setLineWidth(2);
		
		EllipsePattern pattern3 = new EllipsePattern(20, 40);
		pattern3.setOffset(4, 1);
		pattern3.setLineColor(Color.CYAN);
		pattern3.setLineWidth(2);
		
		RectanglePattern pattern4 = new RectanglePattern(20, 40);
		pattern4.setOffset(4, 1);
		pattern4.setLineColor(Color.ORANGE);
		pattern4.setLineWidth(2);
				
		ACanvas canvas = new ACanvas(width, height);
		canvas.setBackgroundColor(Color.PINK);
		canvas.addPattern(pattern);
		canvas.addPattern(pattern2);
		canvas.addPattern(pattern3);
		canvas.addPattern(pattern4);
		
		AVector vector = new AVector();
		
		APoint p0 = vector.getOrigo();
		APoint p1 = vector.newPoint(500, 0);
		APoint p2 = vector.newPoint(250, 500);
		
		APoint.newShape(p0, p1, AShape.TYPE_CURVE);
		APoint.newShape(p1, p2, AShape.TYPE_CURVE);
		APoint.newShape(p2, p0, AShape.TYPE_CURVE);
				
		return new AObject(canvas, vector, "little buckshot");		
	}
}
