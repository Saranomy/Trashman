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
	public Texture texture;
	private float textureTop;
	private float textureBottom;
	private float textureLeft;
	private float textureRight;

	public Maze(int row, int col, Texture texture) {
		grid = new boolean[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				grid[i][j] = false;
			}
		}
		this.row = row;
		this.col = col;
		this.texture = texture;
		TextureCoords textureCoords = texture.getImageTexCoords();
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

					gl.glBegin(GL_QUADS); // of the color cube

					// Top-face
					float rand = 0.5f + (0.5f * random.nextFloat());
					//gl.glColor3f(rand, rand, rand); // green
					gl.glColor3f(1f, 1f, 1f);
					gl.glVertex3f(1.0f, 1.0f, -1.0f);
					gl.glVertex3f(-1.0f, 1.0f, -1.0f);
					gl.glVertex3f(-1.0f, 1.0f, 1.0f);
					gl.glVertex3f(1.0f, 1.0f, 1.0f);
					gl.glEnd();
				} else {
					gl.glLoadIdentity(); // reset the current model-view matrix
					gl.glTranslatef(2f * i, 0f, -2f * j);

					// Enables this texture's target in the current GL context's
					// state.
					texture.enable(gl); // same as
										// gl.glEnable(texture.getTarget());
					// gl.glTexEnvi(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE,
					// GL.GL_REPLACE);
					// Binds this texture to the current GL context.
					texture.bind(gl); // same as
										// gl.glBindTexture(texture.getTarget(),
										// texture.getTextureObject());

					gl.glBegin(GL_QUADS);
					gl.glColor3f(1f, 1f, 1f);
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

					// Top Face
					gl.glTexCoord2f(textureLeft, textureTop);
					gl.glVertex3f(-1.0f, 1.0f, -1.0f);
					gl.glTexCoord2f(textureLeft, textureBottom);
					gl.glVertex3f(-1.0f, 1.0f, 1.0f);
					gl.glTexCoord2f(textureRight, textureBottom);
					gl.glVertex3f(1.0f, 1.0f, 1.0f);
					gl.glTexCoord2f(textureRight, textureTop);
					gl.glVertex3f(1.0f, 1.0f, -1.0f);

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
