package com.walkers.line.trace;

import com.sun.glass.events.KeyEvent;

public class Player {
	float x, y;
	float direction;
	float rotationalSpeed = 100;
	float upDownLookSpeed = 100;
	float movementSpeed = 2;
	Map map;
	int yOffset;
	
	Player(float x, float y, float direction, Map map){
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.map = map;
	}
	
	public void update(float deltaTime){
		if(Keyboard.getKey(KeyEvent.VK_LEFT)){
			direction -= rotationalSpeed * deltaTime;
		}
		
		if(Keyboard.getKey(KeyEvent.VK_RIGHT)){
			direction += rotationalSpeed * deltaTime;
		}
		
		if(Keyboard.getKey(KeyEvent.VK_W)){
			float dx = (float)Math.cos(Math.toRadians(direction));
			float dy = (float)Math.sin(Math.toRadians(direction));
			float x1 = x + dx * movementSpeed * deltaTime;
			float y1 = y + dy * movementSpeed * deltaTime;
			float offsetX = .2f;
			if(dx < 0){
				offsetX = -.2f;
			}else if(dx == 0){
				offsetX = 0;
			}
			
			float offsetY = .2f;
			if(dy < 0){
				offsetY = -.2f;
			}else if(dy == 0){
				offsetY = 0;
			}
			if(map.map[(int)(x1+offsetX) + (int)y * map.width] == 0){
				x = x1;
			}
			if(map.map[(int)x + (int)(y1+offsetY) * map.width] == 0){
				y = y1;
			}
		}
		
		if(Keyboard.getKey(KeyEvent.VK_S)){
			float dx = (float)Math.cos(Math.toRadians(direction));
			float dy = (float)Math.sin(Math.toRadians(direction));
			float x1 = x - dx * movementSpeed * deltaTime;
			float y1 = y - dy * movementSpeed * deltaTime;
			float offsetX = -.2f;
			if(dx < 0){
				offsetX = .2f;
			}else if(dx == 0){
				offsetX = 0;
			}
			
			float offsetY = -.2f;
			if(dy < 0){
				offsetY = .2f;
			}else if(dy == 0){
				offsetY = 0;
			}
			if(map.map[(int)(x1+offsetX) + (int)y * map.width] == 0){
				x = x1;
			}
			if(map.map[(int)x + (int)(y1+offsetY) * map.width] == 0){
				y = y1;
			}
		}
		
		if(Keyboard.getKey(KeyEvent.VK_D)){
			float dx = (float)Math.cos(Math.toRadians(direction+90));
			float dy = (float)Math.sin(Math.toRadians(direction+90));
			float x1 = x + dx * movementSpeed * deltaTime;
			float y1 = y + dy * movementSpeed * deltaTime;
			float offsetX = .2f;
			if(dx < 0){
				offsetX = -.2f;
			}else if(dx == 0){
				offsetX = 0;
			}
			
			float offsetY = .2f;
			if(dy < 0){
				offsetY = -.2f;
			}else if(dy == 0){
				offsetY = 0;
			}
			if(map.map[(int)(x1+offsetX) + (int)y * map.width] == 0){
				x = x1;
			}
			if(map.map[(int)x + (int)(y1+offsetY) * map.width] == 0){
				y = y1;
			}
		}
		
		if(Keyboard.getKey(KeyEvent.VK_A)){
			float dx = (float)Math.cos(Math.toRadians(direction+90));
			float dy = (float)Math.sin(Math.toRadians(direction+90));
			float x1 = x - dx * movementSpeed * deltaTime;
			float y1 = y - dy * movementSpeed * deltaTime;
			float offsetX = -.2f;
			if(dx < 0){
				offsetX = .2f;
			}else if(dx == 0){
				offsetX = 0;
			}
			
			float offsetY = -.2f;
			if(dy < 0){
				offsetY = .2f;
			}else if(dy == 0){
				offsetY = 0;
			}
			if(map.map[(int)(x1+offsetX) + (int)y * map.width] == 0){
				x = x1;
			}
			if(map.map[(int)x + (int)(y1+offsetY) * map.width] == 0){
				y = y1;
			}
		}
		
		if(Keyboard.getKey(KeyEvent.VK_E)){
			Keyboard.keys[KeyEvent.VK_E] = false;
			Ray ray = map.cast(x, y, (float)Math.toRadians(direction), 1.5f);
			if(ray.hitWall){
				map.useTile(ray.texIndex+1, ray.x, ray.y);
			}
		}
	}

}
