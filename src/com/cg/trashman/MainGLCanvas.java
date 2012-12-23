package com.cg.trashman;

import static javax.media.opengl.GL.GL_COLOR_BUFFER_BIT;
import static javax.media.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static javax.media.opengl.GL.GL_DEPTH_TEST;
import static javax.media.opengl.GL.GL_LEQUAL;
import static javax.media.opengl.GL.GL_NICEST;
import static javax.media.opengl.GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SMOOTH;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.cg.trashman.object.Car;
import com.cg.trashman.object.Cube;
import com.cg.trashman.object.Maze;
import com.cg.trashman.object.Pyramid;
import com.jogamp.opengl.util.FPSAnimator;
// GL constants
// GL2 constants

/**
 * JOGL 2.0 Example 2: Rotating 3D Shapes (GLCanvas)
 */
@SuppressWarnings("serial")
public class MainGLCanvas extends GLCanvas implements GLEventListener,
		KeyListener {

	static {
		GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);
		capabilities.setNumSamples(2); // enable anti aliasing - just as a
										// example
		capabilities.setSampleBuffers(true);
	}

	private Cube cube;
	private Pyramid pyramid;
	private CameraController cameraController;
	private Maze maze;
	private Car car;

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

		/* fog config */
		// Set up fog mode
		// float[] fogColor = { 0.0f, 0.0f, 0.0f, 1.0f };
		// gl.glFogfv(GL_FOG_COLOR, fogColor, 0); // set fog color
		// gl.glFogf(GL_FOG_DENSITY, 0.04f); // how dense will the fog be
		// gl.glHint(GL_FOG_HINT, GL_DONT_CARE); // fog hint value
		// gl.glFogf(GL_FOG_START, 1.0f); // fog start depth
		// gl.glFogf(GL_FOG_END, 20.0f); // fog end depth
		// gl.glEnable(GL_FOG); // enables GL_FOG
		// gl.glFogi(GL_FOG_MODE, GL_EXP);

		initComponent();
		// Set up CameraController before using it
		cameraController.setGL(gl, glu);
	}

	public void initComponent() {
		cube = new Cube();
		pyramid = new Pyramid();
		cameraController = new CameraController();
		maze = MazeGenerator.createMaze(19, 19);
		car = new Car();
	}

	/**
	 * Call-back handler for window re-size event. Also called when the drawable
	 * is first set to visible.
	 */
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		GL2 gl = drawable.getGL().getGL2(); // get the OpenGL 2 graphics context

		if (height == 0)
			height = 1; // prevent divide by zero
		float aspect = (float) width / height;

		// Set the view port (display area) to cover the entire window
		gl.glViewport(0, 0, width, height);

		// Setup perspective projection, with aspect ratio matches viewport
		gl.glMatrixMode(GL_PROJECTION); // choose projection matrix
		gl.glLoadIdentity(); // reset projection matrix

		glu.gluPerspective(45.0, aspect, 0.1, 100.0); // fovy, aspect,
														// zNear,zFar

		// Enable the model-view transform
		gl.glMatrixMode(GL_MODELVIEW);
		gl.glLoadIdentity(); // reset
	}

	/**
	 * Called back by the animator to perform rendering.
	 */
	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2(); // get the OpenGL 2 graphics context
		gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear color
																// and depth
																// buffers

		pyramid.update(gl, null);
		cube.update(gl, null);
		maze.update(gl, null);
		car.update(gl, null);

		// Update Camera Parameter
		float[] t = cameraController.getTranslation();
		float[] r = cameraController.getRotation();

		cameraController.update();

		cameraController.setDestination(-car.getX(), -19, -5 + car.getZ());

		// Update camera translation
		gl.glMatrixMode(GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(45.0, 1.55f, 0.1, 100.0);
		// Do translation
		gl.glRotatef(-1 * r[0], -1 * r[1], -1 * r[2], -1 * r[3]);
		gl.glTranslatef(t[0], t[1], t[2]);

		// Do rotation
		gl.glTranslatef(-1 * t[0], -1 * t[1], -1 * t[2]);
		gl.glRotatef(r[0], r[1], r[2], r[3]);
		gl.glTranslatef(t[0], t[1], t[2]);
		gl.glMatrixMode(GL_MODELVIEW);
		gl.glLoadIdentity();

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
		cameraController.keyPressed(event);
	}

	@Override
	public void keyReleased(KeyEvent event) {
		cameraController.keyReleased(event);
	}

	@Override
	public void keyTyped(KeyEvent event) {
		cameraController.keyTyped(event);
	}
}