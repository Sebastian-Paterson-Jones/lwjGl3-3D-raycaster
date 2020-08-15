package com.RayCaster.src.Environment;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;

public class Texture {
	private int id;
	private int width;
	private int height;
	
	public Texture(String filename) throws IOException{
		
		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		IntBuffer comp = BufferUtils.createIntBuffer(1);
		
		ByteBuffer data = STBImage.stbi_load(filename, width, height, comp, 4);
		if(data == null){
			throw new IOException(STBImage.stbi_failure_reason());
		}
				
		id = GL11.glGenTextures();
		this.width = width.get();
		this.height = height.get();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, this.width, this.height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data);
		STBImage.stbi_image_free(data);
	}
	public void bind(){
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}
	public static Texture[] renderTextures(Texture[] texture) throws IOException{
		texture[0] = new Texture("Java/My_RayCaster/src/main/java/com/RayCaster/src/res/texture.jpg");
		texture[1] = new Texture("Java/My_RayCaster/src/main/java/com/RayCaster/src/res/texture.jpg");
		return texture;
	}
}
