package com.RayCaster.src.Environment;

import java.io.IOException;

public class Map {
	public Wall[] walls = new Wall[8];
	public Map() throws IOException{
		walls[0] = new Wall(-2,1,3,0, "texture.png");
		walls[1] = new Wall(1,1,3,90, "texture.png");
		walls[2] = new Wall(-2,1,3,90,"texture.png");
		walls[3] = new Wall(-2,2,3,0, "texture.png");
		walls[4] = new Wall(-20,20,5,0, "texture.png");
		walls[5] = new Wall(20,20,5,90, "texture.png");
		walls[6] = new Wall(-20,20,5,90,"texture.png");
		walls[7] = new Wall(-10,10,5,0, "texture.png");
	}
}
