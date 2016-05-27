package com.walkers.line.trace;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputListener;

public class Mouse implements MouseMotionListener, MouseInputListener, MouseWheelListener {
	
	public static int button = 0;
	public static int mouseX;
	public static int mouseY;
	public static int mouseClickX;
	public static int mouseClickY;
	public static int mouseDragX;
	public static int mouseDragY;

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		button = e.getButton();
		mouseClickX = e.getX();
		mouseClickY = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		button = 0;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseDragX = e.getX();
		mouseDragY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseX = e.getX();
		mouseY = e.getY();
	}
	
}
