package com.RayCaster.src.Player;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.RayCaster.src.Environment.*;

public class Player{
	public Ray[] rays = new Ray[500];
	public List<List<Float>> points = new ArrayList(); //these two lists are parallel
	public List<Wall> walls = new ArrayList();
	private float FOV = 30;
	float xPos;
	float yPos;
	float zPos;
	public Player(float x, float y){
		this.zPos = 0;
		this.xPos = x;
		this.yPos = y;
		Setup();
	}
	public void Setup(){
		for(int x=0;x<rays.length;x++){
			float angle = x*FOV/rays.length;
			rays[x] = new Ray(xPos,yPos,angle);
			rays[x].Posx = 2*((float)x/(float)rays.length)-1;
		}
	}
	public void updatePlayerPerspective(Texture[] textures, long window, Map map){ //must be called after calculateColissions()
		int stateLookLeft = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT);
		int stateLookRight = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_RIGHT);
		int stateDown = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_S);
		int stateUp = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_W);
		int stateRight = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_D);
		int stateLeft = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_A);
		int stateSprint = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT_SHIFT);
		points.clear();
		walls.clear();
		for(int x=0; x<rays.length;x++){
			if(stateRight == GLFW.GLFW_PRESS){
				rays[x].x1 += rays[rays.length/2].y2*0.03;
				rays[x].y1 += -rays[rays.length/2].x2*0.03;
			}
			if(stateLeft == GLFW.GLFW_PRESS){
				rays[x].x1 += -rays[rays.length/2].y2*0.03;
				rays[x].y1 += rays[rays.length/2].x2*0.03;
			}
			if(stateLookLeft == GLFW.GLFW_PRESS){
				rays[x].angle += 1;
				rays[x].calculateVector();
			}
			if(stateDown == GLFW.GLFW_PRESS){
				rays[x].y1 -= rays[rays.length/2].y2*0.05;
				rays[x].x1 -= rays[rays.length/2].x2*0.05;
				
			}
			if(stateLookRight == GLFW.GLFW_PRESS){
				rays[x].angle -= 1;
				rays[x].calculateVector();
			}
			if(stateUp == GLFW.GLFW_PRESS && stateSprint == GLFW.GLFW_PRESS){
				rays[x].y1 += rays[rays.length/2].y2*0.1;
				rays[x].x1 += rays[rays.length/2].x2*0.1;
			}
			else{
				if(stateUp == GLFW.GLFW_PRESS){
				rays[x].y1 += rays[rays.length/2].y2*0.05;
				rays[x].x1 += rays[rays.length/2].x2*0.05;
				}
			}
			if(rays[x].x1 != rays[0].x1 || rays[x].y1 != rays[x].y1){
				rays[x].x1 = rays[0].x1;
				rays[x].y1 = rays[0].y1;
			}
			
			rays[x].calculateCollission(map.walls); //update the intersections point of ray before calculations
			
			if(rays[x].wall != null){
				if(points.size()==0){
					points.add(new ArrayList());
					walls.add(rays[x].wall);
					points.get(0).add(rays[x].Posx);				//index 0 is the start position on window
					points.get(0).add(rays[x].multipleWall);//index 1 is the start position on wall as U
					points.get(0).add(rays[x].distance);	//index 2 is the distance to wall of start pos
					points.get(0).add(rays[x].Posx);				//index 3 is the end position on window
					points.get(0).add(rays[x].multipleWall);//index 4 is the end position on wall as U
					points.get(0).add(rays[x].distance);	//index 5 is the distance to wall for end xpos
				}
				else{
					if(walls.contains(rays[x].wall)){
						points.get(walls.indexOf(rays[x].wall)).set(3, rays[x].Posx); //index 3 to new end pos
						points.get(walls.indexOf(rays[x].wall)).set(4, rays[x].multipleWall);
						points.get(walls.indexOf(rays[x].wall)).set(5, rays[x].distance);
					}
					else{
						points.add(new ArrayList());
						walls.add(rays[x].wall);
						points.get(points.size()-1).add(rays[x].Posx);				//index 0 is the start position on window
						points.get(points.size()-1).add(rays[x].multipleWall);//index 1 is the start position on wall as U
						points.get(points.size()-1).add(rays[x].distance);	//index 2 is the distance to wall of start pos
						points.get(points.size()-1).add(rays[x].Posx);				//index 3 is the end position on window
						points.get(points.size()-1).add(rays[x].multipleWall);//index 4 is the end position on wall as U
						points.get(points.size()-1).add(rays[x].distance);	//index 5 is the distance to wall for end xpos
					}
				}
			}
		}
		//3d mapping is done here
		for(int x=0; x<points.size();x++){
			textures[1].bind();
			GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(points.get(x).get(1), 0); //bottomleft
				GL11.glVertex2f(-points.get(x).get(0), 25/points.get(x).get(2)); //topleft
				GL11.glTexCoord2f(points.get(x).get(4), 0); //bottonright
				GL11.glVertex2f(-points.get(x).get(3), 25/points.get(x).get(5)); //topright
				GL11.glTexCoord2f(points.get(x).get(4), 1); //topright
				GL11.glVertex2f(-points.get(x).get(3), -25/points.get(x).get(5)); //bottomright
				GL11.glTexCoord2f(points.get(x).get(1), 1); //topleft
				GL11.glVertex2f(-points.get(x).get(0), -25/points.get(x).get(2)); //bottonleft
		}
	}
}
