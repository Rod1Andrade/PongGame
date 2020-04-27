package gfx;

import java.awt.Canvas;

import javax.swing.JFrame;
/**
 * Modela uma janela com base em um Canvas
 * */
public class Window extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public Window (Canvas canvas, String title) {
		
		setTitle(title);
		add(canvas);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
	}

}
