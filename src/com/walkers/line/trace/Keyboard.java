package com.walkers.line.trace;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	
	public static boolean[] keys = new boolean[1024];

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static boolean getKey(int code){
		return keys[code];
	}

}
