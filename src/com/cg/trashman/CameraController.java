package com.cg.trashman;

import java.awt.event.KeyEvent;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

public class CameraController {
	
	private GL2 gl;
	private GLU glu;
	private float pX;
	private float pY;
	private float pZ;
	private float xSpeed = 0.1f;
	private float ySpeed = 0.1f;
	private float zSpeed = 0.1f;
	private float rSpeed = 0.2f;
	private float r;
	private float rX;
	private float rY;
	private float rZ;
	
	private float desX;
	private float desY;
	private float desZ;
	
	private int state;
	private float totalDistance;
	private float currentDistance;
	
	private static final int ACCELERATE = 1;
	private static final int DECELERATE = -1;
	
	private static final float START_CAMERA_X = -0.0f;
	private static final float START_CAMERA_Y = -14.2f;
	private static final float START_CAMERA_Z = -10.0f;
	private static final float START_ROT = 32.6f;
	private static final float START_ROT_X = 1.0f;
	private static final float START_ROT_Y = 0.0f;
	private static final float START_ROT_Z = 0.0f;

	public CameraController() { 
		pX = START_CAMERA_X;
		pY = START_CAMERA_Y;
		pZ = START_CAMERA_Z;
		r  = START_ROT;
		rX = START_ROT_X;
		rY = START_ROT_Y;
		rZ = START_ROT_Z;
		desX = pX;
		desY = pY;
		desZ = pZ;
	}

	public void setGL(GL2 gl, GLU glu) {
		this.gl = gl;
		this.glu = glu;
	}

	public float[] getTranslation() {
		return new float[] { pX, pY, pZ };
	}

	public float[] getRotation() {
		return new float[] { r, rX, rY, rZ };
	}
	
	public void setDestination(float x,float y,float z){

		desX = x;
		desY = y;
		desZ = z;
		
	}
	
	public void update(){
		
		if( this.desX == pX && this.desY == pY && this.desZ == pZ){
			return;
		}
		
		this.pX += Math.signum( desX - pX ) * xSpeed;
		this.pY += Math.signum( desY - pY ) * ySpeed;
		this.pZ += Math.signum( desZ - pZ ) * zSpeed;
		
		if (Math.abs((this.pX - this.desX) * 1000f) / 1000f < Math.abs(xSpeed)) {
			this.pX = this.desX;
		} 
		
		if (Math.abs((this.pY - this.desY) * 1000f) / 1000f < Math.abs(ySpeed)) {
			this.pY = this.desY;
		}
			
		if (Math.abs((this.pZ - this.desZ) * 1000f) / 1000f < Math.abs(zSpeed)) {
			this.pZ = this.desZ;
		}
	
	}
	
	public void keyPressed(KeyEvent event){
		
	}

	public void keyReleased(KeyEvent event) {
	}

	public void keyTyped(KeyEvent event) {

	}
}
