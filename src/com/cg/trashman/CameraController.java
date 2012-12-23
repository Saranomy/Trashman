package com.cg.trashman;

import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import java.awt.event.KeyEvent;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

public class CameraController {
	
	private GL2 gl;
	private GLU glu;
	private float pX;
	private float pY;
	private float pZ;
	private float pSpeed = 0.4f;
	private float rSpeed = 0.2f;
	private float r;
	private float rX;
	private float rY;
	private float rZ;
	
	private static final float START_CAMERA_X = -11.0f;
	private static final float START_CAMERA_Y = -19.2f;
	private static final float START_CAMERA_Z = -2.0f;
	private static final float START_ROT = 32.59f;
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
			pX += pSpeed;
		} 
		else if (event.getKeyCode() == KeyEvent.VK_D) {
			pX -= pSpeed;
		}
		
		if (event.getKeyCode() == KeyEvent.VK_W) {
			pZ += pSpeed;
		} 
		else if (event.getKeyCode() == KeyEvent.VK_S) {
			pZ -= pSpeed;
		} 
		
		if (event.getKeyCode() == KeyEvent.VK_SPACE){
			pY -= pSpeed;
		}
		else if( event.getKeyCode() == KeyEvent.VK_SHIFT){
			pY += pSpeed;
		}
		
		if (event.getKeyCode() == KeyEvent.VK_UP){
			// Press UP
			r -= rSpeed;
			rX = 1;
			rY = 0;
			rZ = 0;
		}else if(event.getKeyCode() == KeyEvent.VK_DOWN){
			// Press Down
			r += rSpeed;
			rX = 1;
			rY = 0;
			rZ = 0;
		}
	}

	public void keyReleased(KeyEvent event) {
	}

	public void keyTyped(KeyEvent event) {

	}
}
