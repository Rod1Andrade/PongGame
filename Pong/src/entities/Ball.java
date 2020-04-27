package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import app.Game;
/**
 * Classe que representa uma bola
 * para o jogo Pong
 * */
public class Ball extends GameObject {

	public static double SPEED = 0.75;
	private double dx = 3;
	public double dy = 3;
	
	public Color color;
	
	public Ball(int x, int y, int width, int height) {
		super(x, y, width, height);
		
		color = new Color(255,255,255);
	}
	
	public void colision(GameObject object) {

		Rectangle bounds = new Rectangle(object.x, object.y, object.width, object.height);
		Rectangle ball = new Rectangle((int)(x + (dx * SPEED)), (int)(y + (dy * SPEED)), width, height);
		
		if(bounds.intersects(ball)) {
			dx *= -1;
		}
	}
	
	@Override
	public void colision(int x1, int y1, int x2, int y2) {
		
		if(y < y1)
			dy *= -1;
		
		if(y > y2 - height)
			dy *= -1;
		
		
		if(x <= x1) {
			Game.score2.update();
			x = x2 / 2;
			y = y2 / 2;
			dx *= -1;
		}
		
		if(x > x2) {
			Game.score1.update();
			x = x2 / 2;
			y = y2 / 2;
			dx *= -1;
		}
		
	}

	public void update() {
		x += dx * SPEED;
		y += dy * SPEED;
	}
	
	@Override
	void update(boolean[] keys) {
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(color);
		g.fillOval(x, y, width, height);
	}

}
