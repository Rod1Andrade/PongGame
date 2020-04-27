package entities;

import java.awt.Graphics;

/**
 * Game Object: Classe abstrata para modelar
 * objetos de jogo
 * */
public abstract class GameObject {

	public int width;
	public int height;
	public int x;
	public int y;
	
	public GameObject(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		
		this.width = width;
		this.height = height;
		
	}
	
	/**
	 * Detecta a coslisao entre os pontos:
	 * (x1, y1) e (x2, y2)
	 * */
	abstract void colision(int x1, int y1, int x2, int y2);
	
	/**
	 * Funcao para atualizar os estado do objeto
	 * */
	abstract void update(boolean[] keys);
	
	/**
	 * Funcao para renderizar os graficos do objeto
	 * */
	abstract void render(Graphics g);
	
}
