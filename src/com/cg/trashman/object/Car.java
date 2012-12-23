package com.cg.trashman.object;

import static javax.media.opengl.GL2.GL_QUADS;
import javax.media.opengl.GL2;
import com.cg.trashman.ISimpleObject;

public class Car implements ISimpleObject {
	private float pX;
	private float pZ;
	private float abstractX;
	private float abstractZ;
	private float pSpeed = 0.1f;
	private static final float size = 0.4f;

	public Car() {
		pX = 0f;
		pZ = 0f;
		abstractX = pX;
		abstractZ = pZ;
	}

	public void updateMazePosition(int row, int col) {
		abstractX = row * 2f;
		abstractZ = col * 2f;
	}

	@Override
	public void update(GL2 gl, Object arg) {
		pX += pSpeed;
		pZ += pSpeed;
		render(gl);
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
		//gl.glTranslatef(pX, 0, 0); // translate right and into the
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
}
