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
	private float r;
	private float rX;
	private float rY;
	private float rZ;
	private float cX;
	private float cY;
	private float cZ;

	public CameraController() {
		pX = 0;
		pY = 0;
		pZ = 0;
		r = 0;
		rX = 1;
		rY = 1;
		rZ = 1;
		cX = 0;
		cY = 0;
		cZ = 0;
	}

	public void setGL(GL2 gl, GLU glu) {
		this.gl = gl;
		this.glu = glu;
	}

	public float[] getTranslation() {
		return new float[] { pX, pY, pZ };
	}

	public float[] getRotation() {
		return new float[] { r, cX, cY, cZ };
	}

	public void moveCamera(float dx, float dy) {
		pX += dx;
		pY += dy;
		// Enable the model-view transform
		gl.glMatrixMode(GL_PROJECTION); // choose projection matrix
		gl.glLoadIdentity(); // reset projection matrix
		glu.gluPerspective(45.0, 1.55f, 0.1, 100.0);
		// Enable the model-view transform
		// gl.glTranslatef(pX, pY, 0);
		gl.glRotatef(pX, 0f, 20f, 1f);
		gl.glMatrixMode(GL_MODELVIEW);
		gl.glLoadIdentity(); // reset
	}

	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_A) {
			r = rY;
			rY += 1;
			cX = 0;
			cY = 1;
			cZ = 0;
		} else if (event.getKeyCode() == KeyEvent.VK_W) {
			r = rX;
			rX += 1;
			cX = 1;
			cY = 0;
			cZ = 0;
		} else if (event.getKeyCode() == KeyEvent.VK_S) {
			r = rX;
			rX -= 1;
			cX = -1;
			cY = 0;
			cZ = 0;
		} else if (event.getKeyCode() == KeyEvent.VK_D) {
			r = rY;
			rY -= 1;
			cX = 0;
			cY = -1;
			cZ = 0;
		}
	}

	public void keyReleased(KeyEvent event) {
	}

	public void keyTyped(KeyEvent event) {

	}
}
