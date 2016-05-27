package com.walkers.line.trace;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class TextureManager {
	
	static ArrayList<BufferedImage> textures = new ArrayList<BufferedImage>();
	
	static{
		try {
			textures.add(ImageIO.read(TextureManager.class.getResource("/Textures/WallTexture.png")));
			textures.add(ImageIO.read(TextureManager.class.getResource("/Textures/FrontDoor.png")));
			textures.add(ImageIO.read(TextureManager.class.getResource("/Textures/Door.png")));
			textures.add(ImageIO.read(TextureManager.class.getResource("/Textures/Door.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static BufferedImage getTexture(int index){
		return textures.get(index);
	}

}
