package xmlDrawer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.JFrame;
/**
 * Code for generating a screen with all of the arcs and lines drawn for HackThisSite.org's programming challenge 4
 * @author Zach
 *
 */
public class LinesComponent extends JComponent{

	private static class Line{
		final int x1; 
		final int y1;
		final int x2;
		final int y2;   
		final Color color;

		public Line(int x1, int y1, int x2, int y2, Color color) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
			this.color = color;
		}               
	}

	private static class Arc{
		final int x;
		final int y;
		final int width;
		final int height;
		final int startAngle;
		final int arcAngle;
		final Color color;

		public Arc(int x, int y, int width, int height, int startAngle,int arcAngle, Color color) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height=height;
			this.startAngle = startAngle;
			this.arcAngle = arcAngle;
			this.color = color;
		}               
	}
	
	private final LinkedList<Line> lines = new LinkedList<Line>();
	private final LinkedList<Arc> arcs = new LinkedList<Arc>();
	
	public void addLine(int x1, int x2, int x3, int x4) {
		addLine(x1, x2, x3, x4, Color.black);
	}

	public void addLine(int x1, int y1, int x2, int y2, Color color) {
		x1 = x1;
		x2 = x2;
		y1 = 1000-y1;
		y2=1000-y2;
		lines.add(new Line(x1,y1,x2,y2, color));        
		repaint();
	}

	public void clearLines() {
		lines.clear();
		repaint();
	}
	
	public void addArc(int xCenter, int radius, int arcStart, int yCenter, int arcExtend, Color color){
		int x;
		int y;
		int width;
		int height;
		int startAngle;
		int arcAngle;
		x = (int) (xCenter - radius*Math.pow(2, 1/2));
		System.out.println("x: "+x);
		y = (int) (1000 - yCenter - radius*Math.pow(2, 1/2));
		width = 2*radius;
		height = 2*radius;
		startAngle = arcStart;
		arcAngle = arcExtend;
		System.out.println("Adding Arc("+x+","+y+","+width+","+height+","+startAngle+","+arcAngle+","+color+")");
		arcs.add(new Arc(x,y,width,height,startAngle,arcAngle, color));
		repaint();
	}
	public void addArcDirectly(int x, int y, int width, int height, int startAngle, int arcAngle, Color color){
		arcs.add(new Arc(x,y,width,height,startAngle,arcAngle, color));
		repaint();
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Line line : lines) {
			g.setColor(line.color);
			g.drawLine(line.x1, line.y1, line.x2, line.y2);
			
		}
		for(Arc arc:arcs){
			g.setColor(arc.color);
			g.drawArc(arc.x, arc.y, arc.width, arc.height, arc.startAngle, arc.arcAngle);
		}
	}
	public static void main(String[] args){
		JFrame testFrame = new JFrame();
		testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		LinesComponent comp = new LinesComponent();
		comp.setPreferredSize(new Dimension(1000, 1000));
		comp.setBackground(Color.black);
		testFrame.getContentPane().add(comp, BorderLayout.CENTER);
		testFrame.setBackground(Color.black);
		testFrame.pack();
		testFrame.setVisible(true);
		comp.addLine(0, 0, 250, 250, Color.black);
		comp.addLine(250,250,500,500, Color.red);
		comp.addLine(500,500,750,750, Color.green);
		comp.addLine(750, 750, 1000, 1000, Color.blue);
		
		comp.addLine(0, 1000, 250, 750, Color.black);
		comp.addLine(250,750,500,500, Color.red);
		comp.addLine(500,500,750,250, Color.green);
		comp.addLine(750, 250, 1000, 000, Color.blue);
		//comp.addArc(xCenter, radius, arcStart, yCenter, arcExtend, color);
		comp.addArc(500, 450, 0, 500, 360, Color.black);
		comp.addArc(50, 50, 0, 50, 360, Color.blue);
		comp.addArc(50, 50, 0, 950, 360, Color.red);
		comp.addArc(950, 50, 0, 950, 360, Color.orange);
		comp.addArc(950, 50, 0, 50, 360, Color.green);
	}
}