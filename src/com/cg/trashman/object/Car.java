package com.cg.trashman.object;

import static javax.media.opengl.GL2.GL_QUADS;

import java.awt.event.KeyEvent;

import javax.media.opengl.GL2;
import com.cg.trashman.ISimpleObject;

public class Car implements ISimpleObject {
	private float pX;
	private float pZ;
	private float desX;
	private float desZ;
	private float pSpeed = 0f;
	private float pAcc = 0.07f;
	private static final float size = 0.4f;

	public Car() {
		pX = 0f;
		pZ = 0f;
		desX = pX;
		desZ = pZ;
	}

	public void updateMazePosition(int row, int col) {
		desX = row * 2f;
		desZ = col * 2f;
	}

	public void addMazePositionX(int dX) {
		desX += dX * 2f;
	}

	public void addMazePositionZ(int dZ) {
		desZ += dZ * 2f;
	}

	@Override
	public void update(GL2 gl, Object arg) {
		render(gl);
		
		if (this.desX == pX && this.desZ == pZ) {
			pSpeed = 0;
			return;
		}
		this.pX += Math.signum(desX - pX) * pSpeed;
		if (Math.abs((this.pX - this.desX) * 1000f) / 1000f < pSpeed) {
			this.pX = this.desX;
		}
		this.pZ += Math.signum(desZ - pZ) * pSpeed;
		if (Math.abs((this.pZ - this.desZ) * 1000f) / 1000f < pSpeed) {
			this.pZ = this.desZ;
		}
		pSpeed += pAcc;
	}

	public float getX() {
		return pX;
	}

	public float getZ() {
		return pZ;
	}

	private void render(GL2 gl) {
		// ----- Render the Color Cube -----
		gl.glLoadIdentity(); // reset the current model-view matrix
		// gl.glTranslatef(pX, 0, 0); // translate right and into the
		// screen
		gl.glTranslatef(0, 0, -pZ);
		gl.glTranslatef(pX, 0, 0);
		gl.glBegin(GL_QUADS); // of the color cube

		// Top-face
		gl.glColor3f(0.0f, size, 0.0f); // green
		gl.glVertex3f(size, size, -size);
		gl.glVertex3f(-size, size, -size);
		gl.glVertex3f(-size, size, size);
		gl.glVertex3f(size, size, size);

		// Bottom-face
		gl.glColor3f(size, 0.5f, 0.0f); // orange
		gl.glVertex3f(size, -size, size);
		gl.glVertex3f(-size, -size, size);
		gl.glVertex3f(-size, -size, -size);
		gl.glVertex3f(size, -size, -size);

		// Front-face
		gl.glColor3f(size, 0.0f, 0.0f); // red
		gl.glVertex3f(size, size, size);
		gl.glVertex3f(-size, size, size);
		gl.glVertex3f(-size, -size, size);
		gl.glVertex3f(size, -size, size);

		// Back-face
		gl.glColor3f(size, size, 0.0f); // yellow
		gl.glVertex3f(size, -size, -size);
		gl.glVertex3f(-size, -size, -size);
		gl.glVertex3f(-size, size, -size);
		gl.glVertex3f(size, size, -size);

		// Left-face
		gl.glColor3f(0.0f, 0.0f, size); // blue
		gl.glVertex3f(-size, size, size);
		gl.glVertex3f(-size, size, -size);
		gl.glVertex3f(-size, -size, -size);
		gl.glVertex3f(-size, -size, size);

		// Right-face
		gl.glColor3f(size, 0.0f, size); // magenta
		gl.glVertex3f(size, size, -size);
		gl.glVertex3f(size, size, size);
		gl.glVertex3f(size, -size, size);
		gl.glVertex3f(size, -size, -size);

		gl.glEnd(); // of the color cube
	}

	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_I) {
			addMazePositionZ(1);
		}
		if (event.getKeyCode() == KeyEvent.VK_K) {
			addMazePositionZ(-1);
		}
		if (event.getKeyCode() == KeyEvent.VK_J) {
			addMazePositionX(-1);
		}
		if (event.getKeyCode() == KeyEvent.VK_L) {
			addMazePositionX(1);
		}
	}

	public void keyReleased(KeyEvent event) {
	}

	public void keyTyped(KeyEvent event) {

	}
}
