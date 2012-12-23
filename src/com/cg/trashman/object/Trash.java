package com.cg.trashman.object;

import static javax.media.opengl.GL2.GL_QUADS;

import javax.media.opengl.GL2;

import com.cg.trashman.ISimpleObject;

public class Trash implements ISimpleObject {
	private static final float size = 0.2f;
	private int row;
	private int col;

	public Trash(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	@Override
	public void update(GL2 gl, Object arg) {
		// TODO Auto-generated method stub
		render(gl);
	}

	private void render(GL2 gl) {
		// ----- Render the Color Cube -----
		gl.glLoadIdentity(); // reset the current model-view matrix
		// gl.glTranslatef(pX, 0, 0); // translate right and into the
		// screen
		gl.glTranslatef(0, 0, -col * 2f);
		gl.glTranslatef(row * 2f, 0, 0);
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
