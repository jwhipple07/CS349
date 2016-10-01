package etchaSketch;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DrawFrame extends JFrame {
	private static final long serialVersionUID = 8466349198537151401L;

	public DrawFrame() {
		setTitle("Java Graphics");
		setSize(600, 400);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		Container contentPane = getContentPane();

		MyCanvas drawingCanvas = new MyCanvas();
		contentPane.add(drawingCanvas, BorderLayout.CENTER);
		
		JButton clear = new JButton("Clear");
		JPanel buttonHolder = new JPanel();
		buttonHolder.add(clear);
		contentPane.add(buttonHolder, BorderLayout.SOUTH);

		clear.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//Do nothing
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				clear.setEnabled(false);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				clear.setEnabled(true);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				//clear the drawing on canvas
				drawingCanvas.clear();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				//Do nothing
			}
		});
	}
}
