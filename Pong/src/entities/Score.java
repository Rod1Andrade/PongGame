package entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
/**
 * Classe para lidar com a logica de placar do jogo
 * */
public class Score {
	
	private int score;

	private int x;
	private int y;

	private Font font;
	private Color color;

	public Score(int x, int y, Font font, Color color) {
		this.x = x;
		this.y = y;
		
	
		this.font = font;
		this.color = color;

		score = 0;
	}
	
	public void update() {
		score++;
	}

	public void render(Graphics g) {
		g.setColor(color);
		g.setFont(font);
		g.drawString(String.format("%d", score), x , y);
	}
	
	public void reset() {
		score = 0;
	}
	
	public int getScore() {
		return score;
	}

}
