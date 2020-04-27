package app;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import entities.Ball;
import entities.EnemyPaddle;
import entities.Paddle;
import entities.Score;
import enums.GameState;
import gfx.Window;
import input.Keyboard;

/**
 * @author Rodrigo Andrade
 * Aluno de SI da Universidade Estadual de Goias
 * 
 * Reconstrucao do jogo pong utilizando as tecnicas de 
 * Orientacao a objetos.
 * */
public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 300;
	public static final int HEIGHT = WIDTH / 16 * 9;
	public static final int SCALE = 3;

	private volatile boolean running;
	private Thread gameThread;

	private Window window;
	private Keyboard keyboard;

	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

	private Paddle paddle;
	private EnemyPaddle enemyPaddle;
	private Ball ball;

	public static Score score1;
	public static Score score2;
	private String winnerMessage;

	public static final int MAX_SCORE = 3;

	public static GameState gameState;

	public Game() {
		// Canvas configs
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		// Intancias
		window = new Window(this, "Pong game");
		keyboard = new Keyboard();
		gameState = GameState.STOP;

		// Pontos
		score1 = new Score(WIDTH / 2 - 20, 10, new Font("Serif", Font.BOLD, 10), Color.WHITE);
		score2 = new Score(WIDTH / 2 + 10, 10, new Font("Serif", Font.BOLD, 10), Color.WHITE);

		paddle = new Paddle(0, (HEIGHT / 2) - 50, 10, 50, Color.WHITE);

		enemyPaddle = new EnemyPaddle(WIDTH - 10, HEIGHT / 2, 10, 50, Color.WHITE);

		ball = new Ball((WIDTH / 2) - 10, (HEIGHT / 2) - 10, 10, 10);

		// Handlers
		this.addKeyListener(keyboard);
	}

	public synchronized void startGame() {
		running = true;
		gameThread = new Thread(this, "Game Thread");
		gameThread.start();
	}

	public synchronized void stopGame() {
		System.exit(0);
		try {
			gameThread.join();
		} catch (Exception e) {
			e.printStackTrace();
		}

		running = false;
	}

	public void winner(Graphics g) {
		score1.reset();
		score2.reset();

		ball.x = (WIDTH / 2) - 10;
		ball.y = (HEIGHT / 2) - 10;
		g.setColor(Color.WHITE);
		g.setFont(new Font("Serif", Font.BOLD, 10));
		g.drawString(winnerMessage, 100, 10);
	}

	public void tick() {

		keyboard.update();

		if (keyboard.ENTER)
			gameState = GameState.RUN;

		if (keyboard.ESC)
			stopGame();

		if (keyboard.SPACE)
			gameState = GameState.PAUSE;

		if (score1.getScore() == MAX_SCORE) {
			winnerMessage = "Player 1 WON";
			gameState = GameState.WINNER;
		}

		if (score2.getScore() == MAX_SCORE) {
			winnerMessage = "Player 2 WON";
			gameState = GameState.WINNER;
		}

		if (gameState != GameState.STOP && gameState != GameState.PAUSE && gameState != GameState.OVER) {
			paddle.update(keyboard.getKeys());
			paddle.colision(paddle.x, paddle.y, WIDTH, HEIGHT);

			enemyPaddle.colision(enemyPaddle.x, enemyPaddle.y, WIDTH, HEIGHT);
			enemyPaddle.update(ball);

			ball.update();
			ball.colision(0, 0, WIDTH - ball.width, HEIGHT);
			ball.colision(paddle);
			ball.colision(enemyPaddle);
		}
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();

		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = image.getGraphics();

		// Clear Screen
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		// TODO Colocar isso em um metodo para controlar o estado do jogo
		if (gameState == GameState.PAUSE) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Serif", Font.BOLD, 10));
			g.drawString("Press ENTER to continue", 80, 10);
		}
		if (gameState == GameState.WINNER) {
			winner(g);
		} else {
			score1.render(g);
			score2.render(g);
		}

		paddle.render(g);
		enemyPaddle.render(g);
		ball.render(g);

		g.dispose();

		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);

		bs.show();
	}

	@Override
	public void run() {

		long lastNanoTime = System.nanoTime();
		long lastMillisTime = System.currentTimeMillis();

		double nsPerTick = 1_000_000_000.0 / 60.0;
		double delta = 0;

		int frames = 0;
		int ticks = 0;

		while (running) {
			boolean renderNow = false;

			long instantNanoTime = System.nanoTime();
			long difference = instantNanoTime - lastNanoTime;

			delta += difference / nsPerTick;
			lastNanoTime = instantNanoTime;

			while (delta > 0) {
				tick();
				ticks++;
				delta--;
				renderNow = true;
			}

			if (renderNow) {
				render();
				frames++;
			}

			if (System.currentTimeMillis() - lastMillisTime > 1000) {
				lastMillisTime += 1000;

				// Debugger FPS and UPS
				window.setTitle(String.format("Pong | FPS: %d, UPS: %d", frames, ticks));

				frames = 0;
				ticks = 0;
			}
		}

		stopGame();

	}

	public static void main(String[] args) {
		new Game().startGame();
	}

}
