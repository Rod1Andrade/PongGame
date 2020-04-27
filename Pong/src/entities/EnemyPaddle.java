package entities;

import java.awt.Color;
import java.awt.Graphics;

import app.Game;

/**
 * EnemyPaddle: Classe que representa o paddle
 * inimigo do jogo.
 * 
 * A sua jogabilidade eh baseada nas posicoes
 * da bola.
 * */
public final class EnemyPaddle extends GameObject {

	public static final double SPEED = 1.25;
	
	public Color color;
	public double Dificult = 0.6;
	private double dy;
	
	public EnemyPaddle(int x, int y, int width, int height, Color color) {
		super(x, y, width, height);
		
		this.color = color;
	}

	@Override
	public void colision(int x1, int y1, int x2, int y2) {
		// Colisao dos paddles com as bordas
		if (y1 >= y2 - height) {
			this.y = y2 - height;
		}

		if (y1 < 0) {
			this.y = 0;
		}
	}

	/**
	 * O paddle segue os movimentos da bola
	 * depois que a posicao da bola passa da 
	 * metade da tela.
	 * */
	public void update(Ball ball) {
		// Momento da reacao
		if(ball.x > Game.WIDTH / 2)
			y += dy;
		dy = ball.dy * Dificult;
	}
	
	@Override
	public void update(boolean[] keys) {

	}

	@Override
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, width, height);
	}

}
