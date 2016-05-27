package com.walkers.line.trace;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Map {
	public int width;
	public int height;
	float playerStartX;
	float playerStartY;
	float playerStartDir;
	String mapName;
	//String nextLevel;
	//int textureWidth = 32;
	public int[] map;
	Main main;
	
	public Map(String name, int width, int height){
		this.width = width;
		this.height = height;
		map = new int[width*height];
		playerStartX = 1.5f;
		playerStartY = 1.5f;
		playerStartDir = 0;
	}
	
	public Map(InputStream inputStream, Main main){
		this.main = main;
		loadLevel(inputStream);
		//Random rand = new Random();
	}
	
	public void loadLevel(InputStream inputStream){
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			mapName = reader.readLine();
			int width = Integer.parseInt(reader.readLine());
			int height = Integer.parseInt(reader.readLine());
			playerStartX = Float.parseFloat(reader.readLine());
			playerStartY = Float.parseFloat(reader.readLine());
			playerStartDir = Float.parseFloat(reader.readLine());
			map = new int[width*height];
			for(int y = 0; y < height; y++){
				String line = reader.readLine();
				String tiles[] = line.split(",");
				if(tiles.length != width)
					System.err.println("Width of map data not same as width of map!");
				for(int x = 0; x < width; x++){
					map[x + y * width] = Integer.parseInt(tiles[x]);
				}
			}
			//nextLevel = reader.readLine();
			reader.close();
			this.width = width;
			this.height = height;
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	Ray cast(float x, float y, float angle, float range){
		float rayDirY = (float)Math.sin(angle);
		float rayDirX = (float)Math.cos(angle);
		//int destX = (int)Math.ceil(x + (run * range));
		//int destY = (int)Math.ceil(y + (rise * range));
		
		int mapX = (int)x;
		int mapY = (int)y;
		
		float sideDistX;
		float sideDistY;
		
		float deltaDistX = (float)Math.sqrt(1 + (rayDirY * rayDirY) / (rayDirX * rayDirX));
		float deltaDistY = (float)Math.sqrt(1 + (rayDirX * rayDirX) / (rayDirY * rayDirY));
		float perpWallDist;
		
		int stepX;
		int stepY;
		
		boolean hit = false;
		int side = 0;
		
		if(rayDirX < 0){
			stepX = -1; 
			sideDistX = (x - mapX) * deltaDistX;
	      }
	      else
	      {
	        stepX = 1;
	        sideDistX = (mapX + 1.0f - x) * deltaDistX;
	      }
	      if (rayDirY < 0)
	      {
	        stepY = -1;
	        sideDistY = (y - mapY) * deltaDistY;
	      }
	      else
	      {
	        stepY = 1;
	        sideDistY = (mapY + 1.0f - y) * deltaDistY;
	      }
	      
	      //perform DDA
	      while (!hit)
	      {
	        //jump to next map square, OR in x-direction, OR in y-direction
	        if (sideDistX < sideDistY)
	        {
	          sideDistX += deltaDistX;
	          mapX += stepX;
	          side = 0;
	        }
	        else
	        {
	          sideDistY += deltaDistY;
	          mapY += stepY;
	          side = 1;
	        }
	        //Check if ray has hit a wall
	        if (mapX < width && mapX >= 0 && mapY < height && mapY >= 0 && map[mapX + mapY * width] != 0) hit = true;
	      } 
	      
	      if (side == 0) perpWallDist = ((float)mapX - x + (1 - stepX) / 2) / rayDirX;
	      else           perpWallDist = ((float)mapY - y + (1 - stepY) / 2) / rayDirY;
	      
	      //calculate value of wallX
	      float wallX; //where exactly the wall was hit
	      if (side == 0) wallX = y + perpWallDist * rayDirY;
	      else           wallX = x + perpWallDist * rayDirX;
	      wallX -= (int)Math.floor((wallX));

	      Ray ray = new Ray();
	      ray.texIndex = map[mapX + mapY * width]-1;
	      int textureWidth = TextureManager.getTexture(ray.texIndex).getWidth();
	      //x coordinate on the texture
	      int texX = (int)(wallX * (float)textureWidth);
	      if(side == 0 && rayDirX < 0) texX = textureWidth - texX - 1;
	      if(side == 1 && rayDirY > 0) texX = textureWidth - texX - 1;
	      
	      ray.hitWall = true;
	      ray.distance = perpWallDist;
	      if(ray.distance > range){
	    	  return new Ray();
	      }
	      ray.texX = texX;
	      ray.x = mapX;
	      ray.y = mapY;
	      switch(map[mapX + mapY * width]){
	      case 1:
	    	  ray.color = 0xffff0000;
	    	  break;
	      case 2:
	    	  ray.color = 0xff00ff00;
	    	  break;
	      case 3:
	    	  ray.color = 0xff0000ff;
	    	  break;
	      case 4:
	    	  ray.color = 0xffff00ff;
	    	  break;
	      case 5:
	    	  ray.color = 0xffffff00;
	    	  break;
	      }
	      return ray;
	      
	}
	
	public void updateTile(int tileIndex, int x, int y){
		
	}
	
	public void useTile(int tileIndex, int x, int y){
		if(tileIndex == 2 || tileIndex == 3){
			System.out.println("using door");
			map[x + y * width] = 0;
		}else if(tileIndex == 4){
			//loadLevel(Map.class.getResourceAsStream("/Maps/"+nextLevel));
			//main.player = new Player(playerStartX, playerStartY, playerStartDir, this);
		}
	}

}
