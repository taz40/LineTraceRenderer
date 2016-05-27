package com.walkers.line.trace;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Main {
	
	boolean running = false;
	int res = 800;
	int height = 600;
	float focalLength = 45;
	float range = 4;
	Map map;
	Player player;
	public static Window window;
	float angle = 0;
	long lastTime;
	BufferedImage testImage;
	int frames;
	float timer;
	int fps;
	
	public static void main(String[] args){
		new Main();
	}
	
	Main(){
		init();
		run();
	}
	
	public void init(){
		try {
			testImage = ImageIO.read(this.getClass().getResource("/Textures/test.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		running = true;
		map = new Map(Main.class.getResourceAsStream("/Maps/TestMap.map"), this);
		player = new Player(map.playerStartX, map.playerStartY,map.playerStartDir, map);
		window = new Window(res,height, "Fake 3D test");
	}
	
	public void run(){
		lastTime = System.nanoTime();
		while(running){
			window.clear();
			angle = -focalLength/2;
			Graphics g = window.image.getGraphics();
			for(int collumn = 0; collumn < res; collumn++){
				int x = collumn;
				//angle = (float) Math.atan2(x, Math.toRadians(focalLength));
				angle += (1f/(float)res)*focalLength;
				Ray ray = map.cast(player.x, player.y, (float)Math.toRadians(angle+player.direction), 100);
				if(ray.hitWall){
					//System.out.println(ray.distance);
					float z = ray.distance*(float)Math.cos(Math.toRadians(angle));
					int wallHeight = (int) (height*1.4/z);
					int y = (int)((height/2)-(wallHeight/2))+player.yOffset;
					for(int y1 = 0; y1 < y; y1++){
						if(y1 >= 0 && y1 < height){
							window.pixels[x + (y1) * res] = 0xff7c7c7c;
						}
					}
//					for(int yo = 0; yo < wallHeight; yo++){
//						if(yo+y >= 0 && yo+y < height){
//							window.pixels[x + (y+yo) * res] = ray.color;
//						}
//					}
					BufferedImage image = TextureManager.getTexture(ray.texIndex);
					g.drawImage(image, x, y, x+1, y+wallHeight, ray.texX, 0, ray.texX+1, image.getHeight(), null);
					//g.drawImage(testImage, 10, 10, null);
					//System.out.println(ray.texX);
					for(int y1 = y+wallHeight; y1 < height; y1++){
						if(y1 >= 0 && y1 < height){
							window.pixels[x + (y1) * res] = 0xff3c3c3c;
						}
					}
				}
			}
			for(int y = 0; y < map.height; y++){
				for(int x = 0; x < map.width; x++){
					map.updateTile(map.map[x + y * map.width], x, y);
				}
			}
			
			long now = System.nanoTime();
			float deltaTime = (float)(now - lastTime)/1e9f;
			lastTime = now;
			player.update(deltaTime);
			frames++;
			if(timer < 1){
				timer += deltaTime;
			}else{
				timer = 0;
				fps = frames;
				frames = 0;
			}
			//System.out.println(fps);
//			for(int y = 0; y < 8; y++){
//				for(int x = 0; x < 8; x++){
//					for(int px = 0; px < 30; px++){
//						for(int py = 0; py < 30; py++){
//							if(map.map[x + y * 8]){
//								window.pixels[(px+(x*30)) + (py+(y*30)) * 800] = 0xffff0000;
//							}
//						}
//					}
//				}
//			}
//			angle = -focalLength/2;
//			Ray ray = map.cast((int)player.x*30, (int)player.y*30, (float)Math.toRadians(angle+player.direction), 100);
//			angle = focalLength/2;
//			ray = map.cast((int)player.x*30, (int)player.y*30, (float)Math.toRadians(angle+player.direction), 100);
			window.render();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
