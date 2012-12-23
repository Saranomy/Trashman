package com.cg.trashman;

import java.awt.event.KeyEvent;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

public interface IScene {
	public void init(GL2 gl, GLU glu);

	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height);

	public void display(GLAutoDrawable drawable);

	public void dispose(GLAutoDrawable drawable);

	public void keyPressed(KeyEvent event);

	public void keyReleased(KeyEvent event);

	public void keyTyped(KeyEvent event);
}
