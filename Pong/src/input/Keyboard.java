package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/**
 * Handler para entradas do teclado
 * */
public class Keyboard implements KeyListener {

	private boolean[] key = new boolean[120];
	
	public boolean ENTER;
	public boolean ESC;
	
	public boolean SPACE;
	
	public void update() {
		ENTER = key[KeyEvent.VK_ENTER];
		ESC = key[KeyEvent.VK_ESCAPE];
		
		SPACE = key[KeyEvent.VK_SPACE];
	}
	
	public boolean[] getKeys() {
		return this.key;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		key[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		key[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
