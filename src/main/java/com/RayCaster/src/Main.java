package com.RayCaster.src;
import java.io.IOException;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import com.RayCaster.src.Environment.*;
import com.RayCaster.src.Player.Player;

public class Main {
	
	private static long window;
	private static Player player1;
	private static Map map1;
	private static Texture[] textures = new Texture[2]; //place holder for all textures
	
	public static void init() throws IOException{
		player1 = new Player(0,0);
		map1 = new Map();
		
		if(!GLFW.glfwInit()){
			throw new IllegalStateException("failed to initialise"); // if failed to initialize
		}
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		window = GLFW.glfwCreateWindow(800, 800, "3DRayCaster", 0, 0); //creates window. monitor param for full screen and share param for multiple monitors
		
		if(window == 0){
			throw new IllegalStateException("failed to create Raycaster"); // if window failed to load
		}
		GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor()); //gets the specs of the monitor
		GLFW.glfwSetWindowPos(window, ((videoMode.width() + 800)/2), ((videoMode.height() + 800)/2)); // set window position on screen
		GLFW.glfwShowWindow(window); // show the window, make visible
		GLFW.glfwMakeContextCurrent(window);
		GL.createCapabilities();
		textures = Texture.renderTextures(textures); //load all the textures into list
	}
	
	public static void loop(){
		while(!GLFW.glfwWindowShouldClose(window)){
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GLFW.glfwPollEvents();
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT); // clear pixels to black
			player1.updatePlayerPerspective(textures, window, map1);
			GL11.glEnd(); // end processing
			GLFW.glfwSwapBuffers(window); // there are two buffers for opengl one is being processes and other is being displayed, this swaps them at end of frame
		}
	}
	
	public static void main(String[] args) throws IOException{
		
		init();
		loop();
		
		GLFW.glfwTerminate();
		System.out.println("Terminated");
	}
}
