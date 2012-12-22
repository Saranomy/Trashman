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
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {

				// ----- Render the Color Cube -----
				gl.glLoadIdentity(); // reset the current model-view matrix
				gl.glTranslatef(1.6f * i, 0f, -7.0f * j);

				gl.glBegin(GL_QUADS); // of the color cube

				// Top-face
				Random random = new Random(i);
				gl.glColor3f(random.nextFloat(), random.nextFloat(),
						random.nextFloat()); // green
				gl.glVertex3f(1.0f, 1.0f, -1.0f);
				gl.glVertex3f(-1.0f, 1.0f, -1.0f);
				gl.glVertex3f(-1.0f, 1.0f, 1.0f);
				gl.glVertex3f(1.0f, 1.0f, 1.0f);
				gl.glEnd();
			}
		}
	}

}
