package com.cg.trashman.object;

import static javax.media.opengl.GL2.GL_QUADS;

import java.util.Random;

import javax.media.opengl.GL2;

import com.cg.trashman.ISimpleObject;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;

public class Trash implements ISimpleObject {
	private static final float size = 0.4f;
	private int row;
	private int col;
	private Texture[] textures;
	private float textureTop;
	private float textureBottom;
	private float textureLeft;
	private float textureRight;
	private float rotation;
	private int textureId;
	private int score;

	private float angle;

	public Trash(int row, int col, Texture[] textures) {
		this.row = row;
		this.col = col;
		this.textures = textures;
		TextureCoords textureCoords = textures[0].getImageTexCoords();
		textureTop = textureCoords.top();
		textureBottom = textureCoords.bottom();
		textureLeft = textureCoords.left();
		textureRight = textureCoords.right();

		rotation = new Random().nextFloat() * 360f;
		textureId = 15 + new Random().nextInt(4);
		score = 50 + new Random().nextInt(50);
		angle = 0f;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public int getScore() {
		return score;
	}

	@Override
	public void update(GL2 gl, Object arg) {
		angle += 0.3f;
		render(gl);
	}

	private void render(GL2 gl) {
		// ----- Render the Color Cube -----
		gl.glLoadIdentity(); // reset the current model-view matrix
		// gl.glTranslatef(pX, 0, 0); // translate right and into the
		// screen

		gl.glTranslatef(0, 0, -col * 2f);
		gl.glTranslatef(row * 2f, 0, 0);
		gl.glRotatef(rotation + angle, 0f, 1f, 0f);
		// trash texture
		textures[textureId].enable(gl);
		textures[textureId].bind(gl);

		gl.glBegin(GL_QUADS);
		// Front Face
		gl.glTexCoord2f(textureLeft, textureBottom);
		gl.glVertex3f(-size, -size, size); // bottom-left of the
											// texture and quad
		gl.glTexCoord2f(textureRight, textureBottom);
		gl.glVertex3f(size, -size, size); // bottom-right of the
											// texture and quad
		gl.glTexCoord2f(textureRight, textureTop);
		gl.glVertex3f(size, size, size); // top-right of the texture
											// and quad
		gl.glTexCoord2f(textureLeft, textureTop);
		gl.glVertex3f(-size, size, size); // top-left of the texture
											// and quad

		// Back Face
		gl.glTexCoord2f(textureRight, textureBottom);
		gl.glVertex3f(-size, -size, -size);
		gl.glTexCoord2f(textureRight, textureTop);
		gl.glVertex3f(-size, size, -size);
		gl.glTexCoord2f(textureLeft, textureTop);
		gl.glVertex3f(size, size, -size);
		gl.glTexCoord2f(textureLeft, textureBottom);
		gl.glVertex3f(size, -size, -size);

		// Top Face
		gl.glTexCoord2f(textureLeft, textureTop);
		gl.glVertex3f(-size, size, -size);
		gl.glTexCoord2f(textureLeft, textureBottom);
		gl.glVertex3f(-size, size, size);
		gl.glTexCoord2f(textureRight, textureBottom);
		gl.glVertex3f(size, size, size);
		gl.glTexCoord2f(textureRight, textureTop);
		gl.glVertex3f(size, size, -size);

		// Bottom Face
		gl.glTexCoord2f(textureRight, textureTop);
		gl.glVertex3f(-size, -size, -size);
		gl.glTexCoord2f(textureLeft, textureTop);
		gl.glVertex3f(size, -size, -size);
		gl.glTexCoord2f(textureLeft, textureBottom);
		gl.glVertex3f(size, -size, size);
		gl.glTexCoord2f(textureRight, textureBottom);
		gl.glVertex3f(-size, -size, size);

		// Right face
		gl.glTexCoord2f(textureRight, textureBottom);
		gl.glVertex3f(size, -size, -size);
		gl.glTexCoord2f(textureRight, textureTop);
		gl.glVertex3f(size, size, -size);
		gl.glTexCoord2f(textureLeft, textureTop);
		gl.glVertex3f(size, size, size);
		gl.glTexCoord2f(textureLeft, textureBottom);
		gl.glVertex3f(size, -size, size);

		// Left Face
		gl.glTexCoord2f(textureLeft, textureBottom);
		gl.glVertex3f(-size, -size, -size);
		gl.glTexCoord2f(textureRight, textureBottom);
		gl.glVertex3f(-size, -size, size);
		gl.glTexCoord2f(textureRight, textureTop);
		gl.glVertex3f(-size, size, size);
		gl.glTexCoord2f(textureLeft, textureTop);
		gl.glVertex3f(-size, size, -size);

		gl.glEnd();
	}

}
