package etchaSketch;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class MyCanvas extends JPanel implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 8002721722250729778L;
	private ArrayList<Point> points = new ArrayList<Point>();

	public MyCanvas() {
		addMouseMotionListener(this);
		addMouseListener(this);
	}

	public void clear() {
		points = new ArrayList<Point>();
		repaint();
	}

	// This method is responsible for painting the component.
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Point prevPoint = null;
		boolean newLineFlag = false;
		for (Point point : points) {

			if (point == null) {
				newLineFlag = true; //trigger a new line draw
				continue;
			}
			if (newLineFlag) {
				prevPoint = point; //store this current point as the next one's previous point
				newLineFlag = false; //turn off the flag
				continue;
			} else {
				g.drawLine(prevPoint.x, prevPoint.y, point.x, point.y);
				prevPoint = point;
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point newPoint = new Point();
		newPoint.setLocation(e.getX(), e.getY()); //latest location
		Point prevPoint = points.get(points.size() - 1); //location from end of points list
		points.add(newPoint);

		Graphics g = getGraphics();
		g.drawLine(prevPoint.x, prevPoint.y, newPoint.x, newPoint.y); //draw line
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		//do nothing
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//do nothing
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//do nothing
	}

	@Override
	public void mouseExited(MouseEvent e) {
		//do nothing
	}

	@Override
	public void mousePressed(MouseEvent e) {
		points.add(null); //null point means new line
		//add the coordinates of the mouse press
		Point startLocation = new Point(e.getX(), e.getY());
		points.add(startLocation);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//do nothing
	}
}
