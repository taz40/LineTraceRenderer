package com.walkers.line.trace;

import java.util.Random;

public class Map {
	int size;
	boolean[] map = {
			true,true,true,true,true,true,true,true,
			true,false,false,false,false,false,false,true,
			true,false,false,false,false,false,false,true,
			true,false,false,false,false,false,false,true,
			true,false,false,true,false,false,false,true,
			true,false,false,false,false,false,false,true,
			true,false,false,false,false,false,false,true,
			true,true,true,true,true,true,true,true
	};
	
	Map(int size){
		this.size = size;
		//map = new boolean[size*size];
		Random rand = new Random();
	}
	
	Ray cast(int x, int y, float angle, int range){
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
	        if (map[mapX + mapY * size]) hit = true;
	      } 
	      
	      if (side == 0) perpWallDist = (mapX - x + (1 - stepX) / 2) / rayDirX;
	      else           perpWallDist = (mapY - y + (1 - stepY) / 2) / rayDirY;
	      
	      Ray ray = new Ray();
	      ray.hitWall = true;
	      ray.distance = perpWallDist;
	      return ray;
	      
	}

}
