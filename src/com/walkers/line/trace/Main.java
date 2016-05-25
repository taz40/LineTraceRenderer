package com.walkers.line.trace;

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
	
	public static void main(String[] args){
		new Main();
	}
	
	Main(){
		init();
		run();
	}
	
	public void init(){
		running = true;
		map = new Map(8);
		player = new Player(2f,2f,0);
		window = new Window(800,600, "Fake 3D test");
	}
	
	public void run(){
		while(running){
			window.clear();
			angle = -focalLength/2;
			for(int collumn = 0; collumn < res; collumn++){
				int x = collumn;
				//angle = (float) Math.atan2(x, Math.toRadians(focalLength));
				angle += (1f/(float)res)*focalLength;
				Ray ray = map.cast((int)player.x, (int)player.y, (float)Math.toRadians(angle+player.direction), 100);
				if(ray.hitWall){
					//System.out.println(ray.distance);
					float z = ray.distance*(float)Math.cos(Math.toRadians(angle));
					float wallHeight = height/z;
					int y = (int)(300-(wallHeight/2));
					for(int yo = 0; yo < wallHeight; yo++){
						if(yo+y >= 0 && yo+y < 600){
							window.pixels[x + (y+yo) * 800] = 0xffff0000;
						}
					}
				}
			}
			player.direction += .2;
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
