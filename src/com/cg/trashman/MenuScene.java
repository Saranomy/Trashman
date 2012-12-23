package com.cg.trashman;

import java.awt.event.KeyEvent;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

public class MenuScene implements IScene {
	private GL2 gl;
	private GLU glu;
	private MainGLCanvas mainGLCanvas;

	public MenuScene() {

	}

	@Override
	public void init(GL2 gl, GLU glu, MainGLCanvas mainGLCanvas) {
		this.gl = gl;
		this.glu = glu;
		this.mainGLCanvas = mainGLCanvas;

	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void display(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

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

}
