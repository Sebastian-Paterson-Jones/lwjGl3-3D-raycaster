package com.RayCaster.src.Player;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.GL11;
import com.RayCaster.src.Environment.Wall;

public class Ray {
	public float x1;
	public float y1;
	public float x2; //direction vector x
	public float y2; //direction vector y
	public float pointx; //point of collision x
	public float pointy; //point of collision y
	public float angle;
	public float Posx;
	public float multipleRay;
	public float multipleWall;
	public float distance;
	public Wall wall; //hold the wall the line intersects with;
	int VECTOR_LENGTH = 3; // just a constant for direction vector length
	
	public Ray(float x1_coord, float y1_coord, float angle){
		this.x1 = x1_coord;
		this.y1 = y1_coord;
		this.angle = angle;
		calculateVector();
		
	}
	public void calculateVector(){
		if(angle>360){
			angle -= 360;
		}
		else{
			if(angle<0){
				angle = 360 + angle;
			}
		}
		double angletoRadian = angle*(Math.PI/180);
		if(angle%90!=0){
			x2 = (float) Math.sqrt(Math.pow(VECTOR_LENGTH, 2)/(1 + Math.pow(Math.tan(angletoRadian), 2)));
			y2 = (float) Math.sqrt((Math.pow(VECTOR_LENGTH, 2)-Math.pow(x2, 2)));
		}
		else{
			if((angle/90)%2 != 0){
				this.x2 = 0;
				this.y2 = VECTOR_LENGTH;
			}
			else{
				this.x2 = VECTOR_LENGTH;
				this.y2 = 0;
			}
		}
		if(angle>=90 && angle<180){
			this.x2 = -this.x2;
		}
		else{
			if(angle>=180 && angle<270){
				this.x2 = -this.x2;
				this.y2 = -this.y2;
			}
			else{
				if(angle>=270 && angle<360){
				this.y2 = -this.y2;
				}
			}
		}
	}
	private float normalise(float num){ // maps variable between 0 and 1 for openGL display, max of 10 for now
		float normal;
		normal = (num)/(10);
		return normal;
	}
	public void calculateCollission(Wall[] walls){
		List<Float> rayCollision = new ArrayList<Float>(); //dynamic list for appending all vector multiplication values to get exact point of ray intersection
		List<Float> wallCollision = new ArrayList<Float>(); //dynamic list for appending all vector multiplication values to get exact point of wall intersection
		List<Wall> wall = new ArrayList<Wall>(); //dynamic list for appending all vector multiplication values to get exact point of wall intersection
		float t;
		float u;
		float denominator;
		float x1 = this.x1;
		float x2 = this.x2 + this.x1;
		float y1 = this.y1;
		float y2 = this.y1 + this.y2;
		for(int x=0;x<walls.length;x++){
			float x3 = walls[x].x1;
			float x4 = walls[x].x2 + walls[x].x1;
			float y3 = walls[x].y1;
			float y4 = walls[x].y2 + walls[x].y1;
			
			denominator = ((x1-x2)*(y3-y4))-((y1-y2)*(x3-x4));
			
			if(denominator != 0){
				t = ((x1-x3)*(y3-y4)-(y1-y3)*(x3-x4))/denominator;
				u = -((x1-x2)*(y1-y3)-(y1-y2)*(x1-x3))/denominator;
				if(t>0 && u<1 && u>0){
					rayCollision.add(t);
					wallCollision.add(u);
					wall.add(walls[x]);
				}
			}
		}
		if(!rayCollision.isEmpty()){
			multipleRay = rayCollision.get(0);
			multipleWall = wallCollision.get(0);
			this.wall = wall.get(0);
			for(int x=0;x<rayCollision.size();x++){
				if(rayCollision.get(x)<multipleRay){
					multipleRay = rayCollision.get(x);
					multipleWall = wallCollision.get(x);
					this.wall = wall.get(x);
				}
			}
			this.pointx = this.x2*multipleRay;
			this.pointy = this.y2*multipleRay;
			distance = (float) Math.sqrt(Math.pow((pointx-x1), 2) + Math.pow((pointy-y1), 2));
		}
		else{
			this.wall = null;
			this.multipleRay = 0;
			this.multipleWall = 0;
			this.pointx = this.x2*5; // remember to change
			this.pointy = this.y2*5; // remember to change
		}
	}
	public void show(){
		GL11.glColor4f(1, 1, 1, 0);
		GL11.glVertex2d(normalise(x1), normalise(y1));
		GL11.glColor4f(1, 1, 1, 0);
		GL11.glVertex2d(normalise(x1+pointx), normalise(y1+pointy));
	}
}
