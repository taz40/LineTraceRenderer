package com.walkers.line.trace.DevTools.LevelEditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.sun.glass.events.KeyEvent;
import com.walkers.line.trace.Keyboard;
import com.walkers.line.trace.Map;
import com.walkers.line.trace.Mouse;
import com.walkers.line.trace.TextureManager;
import com.walkers.line.trace.Window;

public class LevelEditor implements Runnable, ActionListener{
	
	Window window;
	boolean running = true;
	Thread renderThread;
	Map map;
	boolean clickDetected = false;
	long lastTime;
	float xOffset;
	float yOffset;
	float moveSpeed = 200;
	
	public static void main(String[] args){
		new LevelEditor();
	}
	
	public LevelEditor(){
		window = new Window(800,600,"Level Editor");
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		menuBar.add(menu);
		JMenuItem item = new JMenuItem("New");
		item.setActionCommand("New");
		item.addActionListener(this);
		menu.add(item);
		item = new JMenuItem("Open");
		item.setActionCommand("Open");
		item.addActionListener(this);
		menu.add(item);
		item = new JMenuItem("Save");
		item.setActionCommand("Save");
		item.addActionListener(this);
		menu.add(item);
		window.frame.setJMenuBar(menuBar);
		window.frame.pack();
		renderThread = new Thread(this, "Render Thread");
		renderThread.start();
	}
	
	public void run(){
		lastTime = System.nanoTime();
		while(running){
			long now = System.nanoTime();
			float deltaTime = (lastTime - now)/1e9f;
			lastTime = now;
			window.clear();
			Graphics g = window.image.getGraphics();
			if(map != null){
				for(int x = 0; x < map.width; x++){
					for(int y = 0; y < map.height; y++){
						if(map.map[x + y * map.width] != 0){
							BufferedImage image = TextureManager.getTexture(map.map[x + y * map.width]-1);
							g.drawImage(image, (x*32)+(int)xOffset, (y*32)+(int)yOffset, ((x+1)*32)+(int)xOffset, ((y+1)*32)+(int)yOffset, 0, 0, image.getWidth(), image.getHeight(), null);
						}else{
							g.setColor(Color.white);
							g.fillRect((x*32)+(int)xOffset, (y*32)+(int)yOffset, 32, 32);
						}
					}
				}
				int tileX = (Mouse.mouseX-(int)xOffset)/32;
				int tileY = (Mouse.mouseY-(int)yOffset)/32;
				if(tileX >= 0 && tileX < map.width && tileY >= 0 && tileY < map.height){
					g.setColor(new Color(0xff00ff00));
					g.drawRect(tileX*32+(int)xOffset, tileY*32+(int)yOffset, 32, 32);
				}
				if(Mouse.button == 1 && !clickDetected){
					clickDetected = true;
					if(tileX >= 0 && tileX < map.width && tileY >= 0 && tileY < map.height){
						map.map[tileX + tileY * map.width]++;
						if(map.map[tileX + tileY * map.width] > 4){
							map.map[tileX + tileY * map.width] = 0;
						}
					}
				}else if(Mouse.button == 0 && clickDetected){
					clickDetected = false;
				}
				if(Keyboard.getKey(KeyEvent.VK_W)){
					yOffset -= moveSpeed * deltaTime;
				}
				if(Keyboard.getKey(KeyEvent.VK_S)){
					yOffset += moveSpeed * deltaTime;
				}
				if(Keyboard.getKey(KeyEvent.VK_A)){
					xOffset -= moveSpeed * deltaTime;
				}
				if(Keyboard.getKey(KeyEvent.VK_D)){
					xOffset += moveSpeed * deltaTime;
				}
			}
			window.render();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("New")){
			String name = (String) JOptionPane.showInputDialog(
                    window.frame,
                    "Name the level",
                    "Name",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "newLevel");
			
			
			int width = Integer.parseInt((String) JOptionPane.showInputDialog(
                    window.frame,
                    "Width",
                    "Width",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "16"));
			
			int height = Integer.parseInt((String) JOptionPane.showInputDialog(
                    window.frame,
                    "Height",
                    "Height",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "16"));
			
			map = new Map(name, width, height);
		}
	}

}
