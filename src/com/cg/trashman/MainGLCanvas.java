package com.cg.trashman;

import static javax.media.opengl.GL.GL_DEPTH_TEST;
import static javax.media.opengl.GL.GL_LEQUAL;
import static javax.media.opengl.GL.GL_LINEAR;
import static javax.media.opengl.GL.GL_NICEST;
import static javax.media.opengl.GL.GL_TEXTURE_2D;
import static javax.media.opengl.GL.GL_TEXTURE_MAG_FILTER;
import static javax.media.opengl.GL.GL_TEXTURE_MIN_FILTER;
import static javax.media.opengl.GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_AMBIENT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SMOOTH;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLException;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

/**
 * JOGL 2.0 Example 2: Rotating 3D Shapes (GLCanvas)
 */
@SuppressWarnings("serial")
public class MainGLCanvas extends GLCanvas implements GLEventListener,
		KeyListener {
	private List<IScene> scenes;
	private IScene currentScene;
	public Texture[] textures;

	// Define constants for the top-level container
	private static String TITLE = "Trashman Alpha 0.1.0"; // window's
															// title
	private static final int CANVAS_WIDTH = 800; // width of the drawable
	private static final int CANVAS_HEIGHT = 600; // height of the drawable
	private static final int FPS = 60; // animator's target frames per second

	/** The entry main() method to setup the top-level container and animator */
	public static void main(String[] args) {
		// Run the GUI codes in the event-dispatching thread for thread safety
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// Create the OpenGL rendering canvas
				GLCanvas canvas = new MainGLCanvas();
				canvas.setPreferredSize(new Dimension(CANVAS_WIDTH,
						CANVAS_HEIGHT));

				// Create a animator that drives canvas' display() at the
				// specified FPS.
				final FPSAnimator animator = new FPSAnimator(canvas, FPS, true);

				// Create the top-level container
				final JFrame frame = new JFrame(); // Swing's JFrame or AWT's
													// Frame
				frame.getContentPane().add(canvas);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setResizable(false);
				frame.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						// Use a dedicate thread to run the stop() to ensure
						// that the
						// animator stops before program exits.
						new Thread() {
							@Override
							public void run() {
								if (animator.isStarted())
									animator.stop();
								System.exit(0);
							}
						}.start();
					}
				});
				frame.setTitle(TITLE);
				frame.pack();
				frame.setVisible(true);
				animator.start(); // start the animation loop
			}
		});
	}

	// Setup OpenGL Graphics Renderer

	private GLU glu; // for the GL Utility

	/** Constructor to setup the GUI for this Component */
	public MainGLCanvas() {
		this.addGLEventListener(this);
		this.addKeyListener(this);
	}

	// ------ Implement methods declared in GLEventListener ------

	/**
	 * Called back immediately after the OpenGL context is initialized. Can be
	 * used to perform one-time initialization. Run only once.
	 */
	@Override
	public void init(GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2(); // get the OpenGL graphics context
		glu = new GLU(); // get GL Utilities

		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // set background (clear) color
		gl.glClearDepth(1.0f); // set clear depth value to farthest
		gl.glEnable(GL_DEPTH_TEST); // enables depth testing
		gl.glEnable(GL2.GL_POLYGON_SMOOTH);
		gl.glDepthFunc(GL_LEQUAL); // the type of depth test to do
		gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST); // best
																// perspective
																// correction
		gl.glShadeModel(GL_SMOOTH); // blends colors nicely, and smoothes out
									// lighting

		// Set up the lighting for light named GL_LIGHT1
		float[] LightAmbient = { 0f, 0f, 0f, 0.5f };
		gl.glLightfv(GL2.GL_LIGHT0, GL_AMBIENT, LightAmbient, 0);
		gl.glLightfv(GL2.GL_LIGHT1, GL_AMBIENT, LightAmbient, 1);
		gl.glLightfv(GL2.GL_LIGHT2, GL_AMBIENT, LightAmbient, 2);
		gl.glLightfv(GL2.GL_LIGHT3, GL_AMBIENT, LightAmbient, 3);
		gl.glEnable(GL2.GL_LIGHT0);
		gl.glEnable(GL2.GL_LIGHT1);
		gl.glEnable(GL2.GL_LIGHT2);
		gl.glEnable(GL2.GL_LIGHT3);

		/* fog config */
		// Set up fog mode
		// float[] fogColor = { 0.0f, 0.0f, 0.5f, 1.0f };
		// gl.glFogfv(GL2.GL_FOG_COLOR, fogColor, 0); // set fog color
		// gl.glFogf(GL2.GL_FOG_DENSITY, 0.04f); // how dense will the fog be
		// gl.glHint(GL2.GL_FOG_HINT, GL2.GL_DONT_CARE); // fog hint value
		// gl.glFogf(GL2.GL_FOG_START, 1.0f); // fog start depth
		// gl.glFogf(GL2.GL_FOG_END, 20.0f); // fog end depth
		// gl.glEnable(GL2.GL_FOG); // enables GL_FOG
		// gl.glFogi(GL2.GL_FOG_MODE, GL2.GL_EXP) ;

		/* load texture */
		try {
			textures = new Texture[19];
			// buildings
			textures[0] = TextureIO.newTexture(getClass().getClassLoader()
					.getResource("img/building.png"), false, ".png");
			textures[1] = TextureIO.newTexture(getClass().getClassLoader()
					.getResource("img/building2.png"), false, ".png");
			textures[2] = TextureIO.newTexture(getClass().getClassLoader()
					.getResource("img/building3.png"), false, ".png");
			textures[3] = TextureIO.newTexture(getClass().getClassLoader()
					.getResource("img/building4.png"), false, ".png");
			textures[4] = TextureIO.newTexture(getClass().getClassLoader()
					.getResource("img/building5.png"), false, ".png");
			// roofs
			textures[5] = TextureIO.newTexture(getClass().getClassLoader()
					.getResource("img/roof.png"), false, ".png");
			textures[6] = TextureIO.newTexture(getClass().getClassLoader()
					.getResource("img/roof2.png"), false, ".png");
			textures[7] = TextureIO.newTexture(getClass().getClassLoader()
					.getResource("img/roof3.png"), false, ".png");
			textures[8] = TextureIO.newTexture(getClass().getClassLoader()
					.getResource("img/roof4.png"), false, ".png");
			textures[9] = TextureIO.newTexture(getClass().getClassLoader()
					.getResource("img/roof5.png"), false, ".png");
			// road
			textures[10] = TextureIO.newTexture(getClass().getClassLoader()
					.getResource("img/road.png"), false, ".png");
			// car
			textures[11] = TextureIO.newTexture(getClass().getClassLoader()
					.getResource("img/carSide.png"), false, ".png");
			textures[12] = TextureIO.newTexture(getClass().getClassLoader()
					.getResource("img/carFront.png"), false, ".png");
			textures[13] = TextureIO.newTexture(getClass().getClassLoader()
					.getResource("img/carBack.png"), false, ".png");
			textures[14] = TextureIO.newTexture(getClass().getClassLoader()
					.getResource("img/carTop.png"), false, ".png");
			// trash
			textures[15] = TextureIO.newTexture(getClass().getClassLoader()
					.getResource("img/trash.png"), false, ".png");
			textures[16] = TextureIO.newTexture(getClass().getClassLoader()
					.getResource("img/trash2.png"), false, ".png");
			textures[17] = TextureIO.newTexture(getClass().getClassLoader()
					.getResource("img/trash3.png"), false, ".png");
			textures[18] = TextureIO.newTexture(getClass().getClassLoader()
					.getResource("img/trash4.png"), false, ".png");

			gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

		} catch (GLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		/***
		 * SCENE CREATOR
		 */
		scenes = new ArrayList<IScene>();
		scenes.add(new MenuScene());
		scenes.add(new GameScene());
		scenes.add(new LeaderboardScene());
		currentScene = scenes.get(0);

		/***
		 * INIT ALL SCENES
		 */
		for (IScene scene : scenes) {
			scene.init(gl, glu, this);
		}
	}

	/**
	 * Call-back handler for window re-size event. Also called when the drawable
	 * is first set to visible.
	 */
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		currentScene.reshape(drawable, x, y, width, height);
	}

	/**
	 * Called back by the animator to perform rendering.
	 */
	@Override
	public void display(GLAutoDrawable drawable) {
		currentScene.display(drawable);
	}

	/**
	 * Called back before the OpenGL context is destroyed. Release resource such
	 * as buffers.
	 */
	@Override
	public void dispose(GLAutoDrawable drawable) {
	}

	@Override
	public void keyPressed(KeyEvent event) {
		currentScene.keyPressed(event);
	}

	@Override
	public void keyReleased(KeyEvent event) {
		currentScene.keyReleased(event);
	}

	@Override
	public void keyTyped(KeyEvent event) {
		currentScene.keyTyped(event);
	}

	public void setScene(IScene scene) {
		currentScene = scene;
	}

	public void setScene(int number) {
		currentScene = scenes.get(number);
		currentScene.refresh();
	}

}