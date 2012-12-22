package com.cg.trashman;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		// implement GLRenderer
		GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);
		GLCanvas glcanvas = new GLCanvas(capabilities);
		Renderer renderer = new Renderer();
		glcanvas.addGLEventListener(renderer);
		glcanvas.addMouseMotionListener(renderer);
		glcanvas.addMouseListener(renderer);
		glcanvas.addKeyListener(renderer);
		glcanvas.setSize(800, 600);

		// start jframe
		JFrame frame = new JFrame("Trashman Alpha 0.1.0");
		frame.getContentPane().add(glcanvas);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(frame.getContentPane().getPreferredSize());
		frame.setVisible(true);
	}
}