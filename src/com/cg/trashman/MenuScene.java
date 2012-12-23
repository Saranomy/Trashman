package com.cg.trashman;

import static javax.media.opengl.GL2.GL_QUADS;

import java.awt.event.KeyEvent;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;

public class MenuScene implements IScene {
	private GL2 gl;
	private GLU glu;
	private MainGLCanvas mainGLCanvas;
	private Texture[] textures;
	private float textureTop;
	private float textureBottom;
	private float textureLeft;
	private float textureRight;

	public MenuScene() {

	}

	@Override
	public void init(GL2 gl, GLU glu, MainGLCanvas mainGLCanvas) {
		this.gl = gl;
		this.glu = glu;
		this.mainGLCanvas = mainGLCanvas;
		textures = mainGLCanvas.textures;
		TextureCoords textureCoords = textures[0].getImageTexCoords();
		textureTop = textureCoords.top();
		textureBottom = textureCoords.bottom();
		textureLeft = textureCoords.left();
		textureRight = textureCoords.right();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
	}

	@Override
	public void display(GLAutoDrawable drawable) {
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_ENTER) {
			mainGLCanvas.setScene(1);
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent event) {
		// TODO Auto-generated method stub

	}

	private void drawBigCar() {
		float size = 1f;
		// ----- Render the Color Cube -----
		gl.glLoadIdentity(); // reset the current model-view matrix
		// gl.glTranslatef(pX, 0, 0); // translate right and into the
		// screen
		gl.glTranslatef(-1.5f, 0.0f, -6.0f);
		// side car (for front face)
		textures[11].enable(gl);
		textures[11].bind(gl);
		gl.glBegin(GL_QUADS);
		// Front Face
		gl.glTexCoord2f(textureLeft, textureBottom);
		gl.glVertex3f(-size, -0.5f * size, 0.5f * size);
		gl.glTexCoord2f(textureRight, textureBottom);
		gl.glVertex3f(size, -0.5f * size, 0.5f * size);
		gl.glTexCoord2f(textureRight, textureTop);
		gl.glVertex3f(size, 0.5f * size, 0.5f * size);
		gl.glTexCoord2f(textureLeft, textureTop);
		gl.glVertex3f(-size, 0.5f * size, 0.5f * size);
		gl.glEnd();

		// inverse front face
		textures[11].enable(gl);
		textures[11].bind(gl);
		gl.glBegin(GL_QUADS);
		// Back Face
		gl.glTexCoord2f(textureLeft, textureBottom);
		gl.glVertex3f(-size, -0.5f * size, -0.5f * size);
		gl.glTexCoord2f(textureLeft, textureTop);
		gl.glVertex3f(-size, 0.5f * size, -0.5f * size);
		gl.glTexCoord2f(textureRight, textureTop);
		gl.glVertex3f(size, 0.5f * size, -0.5f * size);
		gl.glTexCoord2f(textureRight, textureBottom);
		gl.glVertex3f(size, -0.5f * size, -0.5f * size);
		gl.glEnd();

		// top car (for Top face)
		textures[14].enable(gl);
		textures[14].bind(gl);
		gl.glBegin(GL_QUADS);
		// Top Face
		gl.glTexCoord2f(textureLeft, textureTop);
		gl.glVertex3f(-size, 0.5f * size, -0.5f * size);
		gl.glTexCoord2f(textureLeft, textureBottom);
		gl.glVertex3f(-size, 0.5f * size, 0.5f * size);
		gl.glTexCoord2f(textureRight, textureBottom);
		gl.glVertex3f(size, 0.5f * size, 0.5f * size);
		gl.glTexCoord2f(textureRight, textureTop);
		gl.glVertex3f(size, 0.5f * size, -0.5f * size);
		gl.glEnd();

		// carBack
		textures[13].enable(gl);
		textures[13].bind(gl);
		gl.glBegin(GL_QUADS);
		// Right face
		gl.glTexCoord2f(textureRight, textureBottom);
		gl.glVertex3f(size, -0.5f * size, -0.5f * size);
		gl.glTexCoord2f(textureRight, textureTop);
		gl.glVertex3f(size, 0.5f * size, -0.5f * size);
		gl.glTexCoord2f(textureLeft, textureTop);
		gl.glVertex3f(size, 0.5f * size, 0.5f * size);
		gl.glTexCoord2f(textureLeft, textureBottom);
		gl.glVertex3f(size, -0.5f * size, 0.5f * size);
		gl.glEnd();

		// carFront
		textures[12].enable(gl);
		textures[12].bind(gl);
		gl.glBegin(GL_QUADS);
		// Left Face
		gl.glTexCoord2f(textureLeft, textureBottom);
		gl.glVertex3f(-size, -0.5f * size, -0.5f * size);
		gl.glTexCoord2f(textureRight, textureBottom);
		gl.glVertex3f(-size, -0.5f * size, 0.5f * size);
		gl.glTexCoord2f(textureRight, textureTop);
		gl.glVertex3f(-size, 0.5f * size, 0.5f * size);
		gl.glTexCoord2f(textureLeft, textureTop);
		gl.glVertex3f(-size, 0.5f * size, -0.5f * size);
		gl.glEnd();
	}

}
