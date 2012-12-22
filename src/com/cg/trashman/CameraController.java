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

	public CameraController() {
		pX = 0;
		pY = 0;
		pZ = 0;
		r = 0;
		rX = 1;
		rY = 1;
		rZ = 1;
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
		if (event.getKeyCode() == KeyEvent.VK_W) {
			pZ += pSpeed;
		} 
		if (event.getKeyCode() == KeyEvent.VK_S) {
			pZ -= pSpeed;
		} 
		if (event.getKeyCode() == KeyEvent.VK_D) {
			pX -= pSpeed;
		}
		if (event.getKeyCode() == KeyEvent.VK_SPACE){
			pY -= pSpeed;
		}
		if( event.getKeyCode() == KeyEvent.VK_SHIFT){
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
		}else if(event.getKeyCode() == KeyEvent.VK_LEFT){
			// Press Left
			r -= rSpeed;
			rX = 0;
			rY = 1;
			rZ = 0;
		}else if(event.getKeyCode() == KeyEvent.VK_RIGHT){
			// Press Right
			r += rSpeed;
			rX = 0;
			rY = 1;
			rZ = 0;
		}
	}

	public void keyReleased(KeyEvent event) {
	}

	public void keyTyped(KeyEvent event) {

	}
}
