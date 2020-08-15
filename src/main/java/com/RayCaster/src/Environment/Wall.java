package com.RayCaster.src.Environment;
import org.lwjgl.opengl.GL11;
public class Wall {
		public float x1;
		public float y1;
		public float x2; //direction vector x
		public float y2; //direction vector y
		public float Length; // for direction vector length
		public String texture; // stores the texture of wall
		public Wall(float x1_coord, float y1_coord, float length, float angle, String walltexture){
			this.x1 = x1_coord;
			this.y1 = y1_coord;
			this.Length = length;
			this.texture = walltexture;
			
			float angletoRadian = (float) (angle*(Math.PI/180));
			if(angle%90!=0){
				x2 = (float) Math.sqrt(Math.pow(length, 2)/(1 + Math.pow(Math.tan(angletoRadian), 2)));
				y2 = (float) Math.sqrt((Math.pow(length, 2)-Math.pow(x2, 2)));
			}
			else{
				if((angle/90)%2 != 0){
					this.x2 = 0;
					this.y2 = length;
				}
				else{
					this.x2 = length;
					this.y2 = 0;
				}
			}
			if(angle>90 && angle<181){
				this.x2 = -this.x2;
			}
			else{
				if(angle>180 && angle<271){
					this.x2 = -this.x2;
					this.y2 = -this.y2;
				}
				else{
					if(angle>270 && angle<361){
					this.y2 = -this.y2;
					}
				}
			}
		}
		private float normalise(float num){ // maps variable between 0 and 1 for openGL display max of 100
			float normal;
			normal = (num-0)/(10-0);
			return normal;
		}
		public void show(){
			GL11.glColor4f(1, 1, 1, 0);
			GL11.glVertex2d(normalise(x1), normalise(y1));
			GL11.glColor4f(1, 1, 1, 0);
			GL11.glVertex2d(normalise(x1) + normalise(x2), normalise(y1) + normalise(y2));
		}
}
