package com.walkers.line.trace;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public class Window extends Canvas {
	
	JFrame frame;
	int width, height;
	String title;
	BufferedImage image;
	public int[] pixels;
	
	public Window(int width, int height, String title){
		frame = new JFrame(title);
		frame.setSize(width, height);
		this.width = width;
		this.height = height;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		this.setPreferredSize(new Dimension(width, height));
		frame.add(this);
		frame.pack();
		frame.setVisible(true);
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	}
	
	public void render(){
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(2);
			return;
		}
		//System.out.println("print");
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);
		g.drawImage(image, 0, 0, width, height,null);
		g.dispose();
		bs.show();
		
	}
	
	public void paintComponent(Graphics g1){
		
	}
	
	public void clear(){
		for(int i = 0; i < pixels.length; i++){
			pixels[i] = 0;
		}
	}
	
}
