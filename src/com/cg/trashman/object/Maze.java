package com.cg.trashman.object;

import static javax.media.opengl.GL2.GL_QUADS;

import java.util.Random;

import javax.media.opengl.GL2;

import com.cg.trashman.ISimpleObject;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;

public class Maze implements ISimpleObject {

	public boolean grid[][];
	private int row;
	private int col;
	public Texture[] textures;
	private float textureTop;
	private float textureBottom;
	private float textureLeft;
	private float textureRight;

	public Maze(int row, int col, Texture[] textures) {
		grid = new boolean[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				grid[i][j] = false;
			}
		}
		this.row = row;
		this.col = col;
		this.textures = textures;
		TextureCoords textureCoords = textures[0].getImageTexCoords();
		textureTop = textureCoords.top();
		textureBottom = textureCoords.bottom();
		textureLeft = textureCoords.left();
		textureRight = textureCoords.right();
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

	public boolean[][] getGrid() {
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
				if (!grid[i][j]) {
					gl.glLoadIdentity(); // reset the current model-view matrix
					gl.glTranslatef(2f * i, -2f, -2f * j);
					gl.glColor3f(1f, 1f, 1f);
					// road
					textures[10].enable(gl);
					textures[10].bind(gl);

					gl.glBegin(GL_QUADS); // of the color cube

					// Top-face
					gl.glTexCoord2f(textureLeft, textureTop);
					gl.glVertex3f(1.0f, 1.0f, -1.0f);
					gl.glTexCoord2f(textureLeft, textureBottom);
					gl.glVertex3f(-1.0f, 1.0f, -1.0f);
					gl.glTexCoord2f(textureRight, textureBottom);
					gl.glVertex3f(-1.0f, 1.0f, 1.0f);
					gl.glTexCoord2f(textureRight, textureTop);
					gl.glVertex3f(1.0f, 1.0f, 1.0f);
					gl.glEnd();
				} else {
					// random building id (0,4)
					int buildingId = random.nextInt(4);
					// random roof id (5,9)
					int roofId = 5 + buildingId;
					
					gl.glLoadIdentity(); // reset the current model-view matrix
					gl.glTranslatef(2f * i, 0f, -2f * j);

					// building (0,4)
					textures[buildingId].enable(gl);
					textures[buildingId].bind(gl);

					gl.glBegin(GL_QUADS);
					// Front Face
					gl.glTexCoord2f(textureLeft, textureBottom);
					gl.glVertex3f(-1.0f, -1.0f, 1.0f); // bottom-left of the
														// texture and quad
					gl.glTexCoord2f(textureRight, textureBottom);
					gl.glVertex3f(1.0f, -1.0f, 1.0f); // bottom-right of the
														// texture and quad
					gl.glTexCoord2f(textureRight, textureTop);
					gl.glVertex3f(1.0f, 1.0f, 1.0f); // top-right of the texture
														// and quad
					gl.glTexCoord2f(textureLeft, textureTop);
					gl.glVertex3f(-1.0f, 1.0f, 1.0f); // top-left of the texture
														// and quad

					// Back Face
					gl.glTexCoord2f(textureRight, textureBottom);
					gl.glVertex3f(-1.0f, -1.0f, -1.0f);
					gl.glTexCoord2f(textureRight, textureTop);
					gl.glVertex3f(-1.0f, 1.0f, -1.0f);
					gl.glTexCoord2f(textureLeft, textureTop);
					gl.glVertex3f(1.0f, 1.0f, -1.0f);
					gl.glTexCoord2f(textureLeft, textureBottom);
					gl.glVertex3f(1.0f, -1.0f, -1.0f);

					gl.glEnd();
					// roof (5,9)
					textures[roofId].enable(gl);
					textures[roofId].bind(gl);
					gl.glBegin(GL_QUADS);

					// Top Face
					gl.glTexCoord2f(textureLeft, textureTop);
					gl.glVertex3f(-1.0f, 1.0f, -1.0f);
					gl.glTexCoord2f(textureLeft, textureBottom);
					gl.glVertex3f(-1.0f, 1.0f, 1.0f);
					gl.glTexCoord2f(textureRight, textureBottom);
					gl.glVertex3f(1.0f, 1.0f, 1.0f);
					gl.glTexCoord2f(textureRight, textureTop);
					gl.glVertex3f(1.0f, 1.0f, -1.0f);

					gl.glEnd();
					// go back to building (0,4)
					textures[buildingId].enable(gl);
					textures[buildingId].bind(gl);
					gl.glBegin(GL_QUADS);

					// Bottom Face
					gl.glTexCoord2f(textureRight, textureTop);
					gl.glVertex3f(-1.0f, -1.0f, -1.0f);
					gl.glTexCoord2f(textureLeft, textureTop);
					gl.glVertex3f(1.0f, -1.0f, -1.0f);
					gl.glTexCoord2f(textureLeft, textureBottom);
					gl.glVertex3f(1.0f, -1.0f, 1.0f);
					gl.glTexCoord2f(textureRight, textureBottom);
					gl.glVertex3f(-1.0f, -1.0f, 1.0f);

					// Right face
					gl.glTexCoord2f(textureRight, textureBottom);
					gl.glVertex3f(1.0f, -1.0f, -1.0f);
					gl.glTexCoord2f(textureRight, textureTop);
					gl.glVertex3f(1.0f, 1.0f, -1.0f);
					gl.glTexCoord2f(textureLeft, textureTop);
					gl.glVertex3f(1.0f, 1.0f, 1.0f);
					gl.glTexCoord2f(textureLeft, textureBottom);
					gl.glVertex3f(1.0f, -1.0f, 1.0f);

					// Left Face
					gl.glTexCoord2f(textureLeft, textureBottom);
					gl.glVertex3f(-1.0f, -1.0f, -1.0f);
					gl.glTexCoord2f(textureRight, textureBottom);
					gl.glVertex3f(-1.0f, -1.0f, 1.0f);
					gl.glTexCoord2f(textureRight, textureTop);
					gl.glVertex3f(-1.0f, 1.0f, 1.0f);
					gl.glTexCoord2f(textureLeft, textureTop);
					gl.glVertex3f(-1.0f, 1.0f, -1.0f);

					gl.glEnd();
				}
			}
		}
	}

}
