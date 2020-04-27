package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 * Paddle: Barras verticas do jogo.
 * Para movimentar as barras foram definidias as
 * teclas W e S.
 * */
public final class Paddle extends GameObject {

	public static final int SPEED = 2;
	public Color color;
	
	public Paddle(int x, int y, int width, int height, Color color) {
		super(x, y, width, height);
		this.color = color;
	}

	@Override
	public void colision(int x1, int y1, int x2, int y2) {
		
		// Colisao dos paddles com as bordas
		if(y1 >= y2 - height) {
			this.y = y2 - height; 
		}
		
		if(y1 < 0) {
			this.y = 0;
		}
				
	}
	
	@Override
	public void update(boolean[] keys) {
		if(keys[KeyEvent.VK_W])
			y -= SPEED;
		if(keys[KeyEvent.VK_S])
			y += SPEED;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, width, height);
	}

}
