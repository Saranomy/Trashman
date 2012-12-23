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
	private float xSpeed = 0.0f;
	private float ySpeed = 0.0f;
	private float zSpeed = 0.0f;
	private float rSpeed = 0.2f;
	private float r;
	private float rX;
	private float rY;
	private float rZ;
	
	private float desX;
	private float desY;
	private float desZ;
	
	private static final float START_CAMERA_X = -11.0f;
	private static final float START_CAMERA_Y = -19.2f;
	private static final float START_CAMERA_Z = -2.0f;
	private static final float START_ROT = 32.6f;
	private static final float START_ROT_X = 1.0f;
	private static final float START_ROT_Y = 0.0f;
	private static final float START_ROT_Z = 0.0f;
	private static final float CAMERA_ACC = 0.008f; // must < car speed

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

	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_A) {
			//pX += pSpeed;
			this.setDestination(pX + 2.0f, pY, pZ);
		} 
		else if (event.getKeyCode() == KeyEvent.VK_D) {
			//pX -= pSpeed;
			this.setDestination(pX - 2.0f, pY, pZ);
		}
		
		if (event.getKeyCode() == KeyEvent.VK_W) {
			//pZ += pSpeed;
			this.setDestination(pX, pY, pZ + 2.0f);
		} 
		else if (event.getKeyCode() == KeyEvent.VK_S) {
			//pZ -= pSpeed;
			this.setDestination(pX, pY, pZ - 2.0f);
		} 
		
		if (event.getKeyCode() == KeyEvent.VK_SPACE){
			//pY -= pSpeed;
			this.setDestination(pX, pY - 2.0f, pZ);
		}
		else if( event.getKeyCode() == KeyEvent.VK_SHIFT){
			//pY += pSpeed;
			this.setDestination(pX, pY + 2.0f, pZ);
		}
		
		if (event.getKeyCode() == KeyEvent.VK_UP){
			// Press UP
			//r -= rSpeed;
			//rX = 1;
			//rY = 0;
			//rZ = 0;
		}else if(event.getKeyCode() == KeyEvent.VK_DOWN){
			// Press Down
			//r += rSpeed;
			//rX = 1;
			//rY = 0;
			//rZ = 0;
		}
	}
	
	public void setDestination(float x,float y,float z){
		this.desX = x;
		this.desY = y;
		this.desZ = z;
	}
	
	public void update(){
		if( this.desX == pX && this.desY == pY && this.desZ == pZ){
			xSpeed = 0;
			ySpeed = 0;
			zSpeed = 0;
			return;
		}
		this.pX += Math.signum( desX - pX ) * xSpeed;
		xSpeed  += CAMERA_ACC; 
		if (Math.abs((this.pX - this.desX) * 1000f) / 1000f < xSpeed) {
			this.pX = this.desX;
		} 
		this.pY += Math.signum( desY - pY ) * ySpeed;
		ySpeed  += CAMERA_ACC;
		if (Math.abs((this.pY - this.desY) * 1000f) / 1000f < ySpeed) {
			this.pY = this.desY;
		} 
		this.pZ += Math.signum( desZ - pZ ) * zSpeed;
		zSpeed  += CAMERA_ACC;
		if (Math.abs((this.pZ - this.desZ) * 1000f) / 1000f < zSpeed) {
			this.pZ = this.desZ;
		} 

	}

	public void keyReleased(KeyEvent event) {
	}

	public void keyTyped(KeyEvent event) {

	}
}
