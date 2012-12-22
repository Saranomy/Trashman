package com.cg.trashman.object;

import static javax.media.opengl.GL2.GL_QUADS;

import java.util.Random;

import javax.media.opengl.GL2;

import com.cg.trashman.ISimpleObject;

public class Maze implements ISimpleObject {

	public boolean grid[][];
	private int row;
	private int col;

	public Maze(int row, int col) {
		grid = new boolean[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				grid[i][j] = false;
			}
		}
		this.row = row;
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public void setWall(int row, int col) {
		grid[row][col] = true;
	}

	public boolean[][] getMap() {
		return grid;
	}

	@Override
	public void update(GL2 gl, Object arg) {
		render(gl);
	}

	private void render(GL2 gl) {
		Random random = new Random(0);
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (grid[i][j]) {
					gl.glLoadIdentity(); // reset the current model-view matrix
					gl.glTranslatef(2f * i, -2f, -2f * j);

					gl.glBegin(GL_QUADS); // of the color cube

					// Top-face
					float rand = 0.5f + (0.5f * random.nextFloat());
					gl.glColor3f(rand, rand, rand); // green
					gl.glVertex3f(1.0f, 1.0f, -1.0f);
					gl.glVertex3f(-1.0f, 1.0f, -1.0f);
					gl.glVertex3f(-1.0f, 1.0f, 1.0f);
					gl.glVertex3f(1.0f, 1.0f, 1.0f);
					gl.glEnd();
				} else {
					
					gl.glLoadIdentity(); // reset the current model-view matrix
					gl.glTranslatef(2f * i, 0f, -2f * j);

					gl.glBegin(GL_QUADS); // of the color cube

					// Top-face
					gl.glColor3f(0.3f, 0.3f, 0.3f); // green
					gl.glVertex3f(1.0f, 1.0f, -1.0f);
					gl.glVertex3f(-1.0f, 1.0f, -1.0f);
					gl.glVertex3f(-1.0f, 1.0f, 1.0f);
					gl.glVertex3f(1.0f, 1.0f, 1.0f);

					// Front-face
					gl.glColor3f(0.4f, 0.4f, 0.4f); // red
					gl.glVertex3f(1.0f, 1.0f, 1.0f);
					gl.glVertex3f(-1.0f, 1.0f, 1.0f);
					gl.glVertex3f(-1.0f, -1.0f, 1.0f);
					gl.glVertex3f(1.0f, -1.0f, 1.0f);

					// Back-face
					gl.glColor3f(0.5f, 0.5f, 0.5f); // yellow
					gl.glVertex3f(1.0f, -1.0f, -1.0f);
					gl.glVertex3f(-1.0f, -1.0f, -1.0f);
					gl.glVertex3f(-1.0f, 1.0f, -1.0f);
					gl.glVertex3f(1.0f, 1.0f, -1.0f);

					// Left-face
					//gl.glColor3f(0.0f, 0.0f, 1.0f); // blue
					gl.glVertex3f(-1.0f, 1.0f, 1.0f);
					gl.glVertex3f(-1.0f, 1.0f, -1.0f);
					gl.glVertex3f(-1.0f, -1.0f, -1.0f);
					gl.glVertex3f(-1.0f, -1.0f, 1.0f);

					// Right-face
					//gl.glColor3f(1.0f, 0.0f, 1.0f); // magenta
					gl.glVertex3f(1.0f, 1.0f, -1.0f);
					gl.glVertex3f(1.0f, 1.0f, 1.0f);
					gl.glVertex3f(1.0f, -1.0f, 1.0f);
					gl.glVertex3f(1.0f, -1.0f, -1.0f);
					
					gl.glEnd();
				}
			}
		}
	}

}
