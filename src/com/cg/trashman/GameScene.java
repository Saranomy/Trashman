package com.cg.trashman;

import static javax.media.opengl.GL.GL_COLOR_BUFFER_BIT;
import static javax.media.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import com.cg.trashman.object.Car;
import com.cg.trashman.object.Maze;
import com.cg.trashman.object.Trash;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.texture.Texture;

public class GameScene implements IScene {
	private CameraController cameraController;
	private Maze maze;
	private Car car;
	private List<Trash> trashes;
	private Texture[] textures;
	private CountDownTimer timer;

	private GL2 gl;
	private GLU glu;
	private MainGLCanvas mainGLCanvas;

	private TextRenderer textRenderer;

	private int frame_counter = 0;
	private int FPS = 60;

	public GameScene() {

	}

	@Override
	public void refresh() {
		initComponent();
	}

	@Override
	public void init(GL2 gl, GLU glu, MainGLCanvas mainGLCanvas) {
		this.gl = gl;
		this.glu = glu;
		this.mainGLCanvas = mainGLCanvas;
		textures = mainGLCanvas.textures;

		// init text
		textRenderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 30));

		initComponent();
		// Set up CameraController before using it
		cameraController.setGL(gl, glu);
	}

	public void initComponent() {
		cameraController = new CameraController();
		maze = MazeGenerator.createMaze(19, 19, 0.4f, textures);
		trashes = TrashGenerator.create(maze.getGrid(), textures);
		car = new Car(maze.getGrid(), textures, trashes);
		timer = new CountDownTimer();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
	}

	@Override
	public void display(GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2(); // get the OpenGL 2 graphics context
		gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear color
																// and depth
																// buffers
		maze.update(gl, null);
		car.update(gl, null);

		// update trash
		for (Trash t : trashes) {
			t.update(gl, null);
		}

		// Update Camera Parameter
		float[] t = cameraController.getTranslation();
		float[] r = cameraController.getRotation();

		cameraController.update();
		cameraController.setDestination(-car.getX(), -14.2f, -10f + car.getZ());

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

		// draw a background of text
		
		// draw score
		textRenderer.beginRendering(drawable.getWidth(), drawable.getHeight());
		textRenderer.setColor(1f, 1f, 1f, 1f);
		textRenderer.draw("Score: " + car.getScore(), 50, 70);
		textRenderer.endRendering();

		// draw time
		textRenderer.beginRendering(drawable.getWidth(), drawable.getHeight());
		textRenderer.setColor(1f, 1f, 1f, 1f);
		textRenderer.draw("Time: " + timer.getTime(), 50, 40);
		textRenderer.endRendering();
		// move to leaderboard if finish
		if (timer.getTime() <= 5) {
			mainGLCanvas.setScene(2);
		}

		frame_counter++;
		if (frame_counter == FPS) {
			frame_counter = 0;
			trashes.add(TrashGenerator.newTrash(maze.grid, trashes, textures));
		}
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent event) {
		cameraController.keyPressed(event);
		car.keyPressed(event);
	}

	@Override
	public void keyReleased(KeyEvent event) {
		cameraController.keyReleased(event);
		car.keyReleased(event);
	}

	@Override
	public void keyTyped(KeyEvent event) {
		cameraController.keyTyped(event);
		car.keyTyped(event);
	}

}
