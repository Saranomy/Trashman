package com.cg.trashman;

import static javax.media.opengl.GL.GL_COLOR_BUFFER_BIT;
import static javax.media.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static javax.media.opengl.GL.GL_TRIANGLES;
import static javax.media.opengl.GL2.GL_QUADS;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

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
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2(); // get the OpenGL 2 graphics context
		gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear color
																// and depth
																// buffers
		gl.glLoadIdentity(); // reset the model-view matrix

		// ----- Render the Pyramid -----
		gl.glLoadIdentity(); // reset the model-view matrix
		gl.glTranslatef(-1.5f, 0.0f, -6.0f); // translate left and into the
												// screen
		// gl.glRotatef(anglePyramid, 0.1f, 1.0f, -0.1f); // rotate about the
		// y-axis

		gl.glBegin(GL_TRIANGLES); // of the pyramid

		// Font-face triangle
		gl.glColor3f(1.0f, 0.0f, 0.0f); // Red
		gl.glVertex3f(0.0f, 1.0f, 0.0f);
		gl.glColor3f(0.0f, 1.0f, 0.0f); // Green
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);
		gl.glColor3f(0.0f, 0.0f, 1.0f); // Blue
		gl.glVertex3f(1.0f, -1.0f, 1.0f);

		// Right-face triangle
		gl.glColor3f(1.0f, 0.0f, 0.0f); // Red
		gl.glVertex3f(0.0f, 1.0f, 0.0f);
		gl.glColor3f(0.0f, 0.0f, 1.0f); // Blue
		gl.glVertex3f(1.0f, -1.0f, 1.0f);
		gl.glColor3f(0.0f, 1.0f, 0.0f); // Green
		gl.glVertex3f(1.0f, -1.0f, -1.0f);

		// Back-face triangle
		gl.glColor3f(1.0f, 0.0f, 0.0f); // Red
		gl.glVertex3f(0.0f, 1.0f, 0.0f);
		gl.glColor3f(0.0f, 1.0f, 0.0f); // Green
		gl.glVertex3f(1.0f, -1.0f, -1.0f);
		gl.glColor3f(0.0f, 0.0f, 1.0f); // Blue
		gl.glVertex3f(-1.0f, -1.0f, -1.0f);

		// Left-face triangle
		gl.glColor3f(1.0f, 0.0f, 0.0f); // Red
		gl.glVertex3f(0.0f, 1.0f, 0.0f);
		gl.glColor3f(0.0f, 0.0f, 1.0f); // Blue
		gl.glVertex3f(-1.0f, -1.0f, -1.0f);
		gl.glColor3f(0.0f, 1.0f, 0.0f); // Green
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);

		gl.glEnd(); // of the pyramid

		// ----- Render the Color Cube -----
		gl.glLoadIdentity(); // reset the current model-view matrix
		gl.glTranslatef(1.5f, 0.0f, -7.0f); // translate right and into the
											// screen
		// gl.glRotatef(angleCube, 1.0f, 1.0f, 1.0f); // rotate about the x, y
		// and z-axes

		gl.glBegin(GL_QUADS); // of the color cube

		// Top-face
		gl.glColor3f(0.0f, 1.0f, 0.0f); // green
		gl.glVertex3f(1.0f, 1.0f, -1.0f);
		gl.glVertex3f(-1.0f, 1.0f, -1.0f);
		gl.glVertex3f(-1.0f, 1.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, 1.0f);

		// Bottom-face
		gl.glColor3f(1.0f, 0.5f, 0.0f); // orange
		gl.glVertex3f(1.0f, -1.0f, 1.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);
		gl.glVertex3f(-1.0f, -1.0f, -1.0f);
		gl.glVertex3f(1.0f, -1.0f, -1.0f);

		// Front-face
		gl.glColor3f(1.0f, 0.0f, 0.0f); // red
		gl.glVertex3f(1.0f, 1.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, 1.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);
		gl.glVertex3f(1.0f, -1.0f, 1.0f);

		// Back-face
		gl.glColor3f(1.0f, 1.0f, 0.0f); // yellow
		gl.glVertex3f(1.0f, -1.0f, -1.0f);
		gl.glVertex3f(-1.0f, -1.0f, -1.0f);
		gl.glVertex3f(-1.0f, 1.0f, -1.0f);
		gl.glVertex3f(1.0f, 1.0f, -1.0f);

		// Left-face
		gl.glColor3f(0.0f, 0.0f, 1.0f); // blue
		gl.glVertex3f(-1.0f, 1.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, -1.0f);
		gl.glVertex3f(-1.0f, -1.0f, -1.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);

		// Right-face
		gl.glColor3f(1.0f, 0.0f, 1.0f); // violet
		gl.glVertex3f(1.0f, 1.0f, -1.0f);
		gl.glVertex3f(1.0f, 1.0f, 1.0f);
		gl.glVertex3f(1.0f, -1.0f, 1.0f);
		gl.glVertex3f(1.0f, -1.0f, -1.0f);

		gl.glEnd(); // of the color cube
		
		gl.glMatrixMode(GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(45.0, 1.55f, 0.1, 100.0);
		gl.glMatrixMode(GL_MODELVIEW);
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
