package com.cg.trashman;

import static javax.media.opengl.GL.GL_COLOR_BUFFER_BIT;
import static javax.media.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static javax.media.opengl.GL.GL_LINEAR;
import static javax.media.opengl.GL.GL_TEXTURE_2D;
import static javax.media.opengl.GL.GL_TEXTURE_MAG_FILTER;
import static javax.media.opengl.GL.GL_TEXTURE_MIN_FILTER;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLException;
import javax.media.opengl.glu.GLU;

import com.cg.trashman.object.Car;
import com.cg.trashman.object.Cube;
import com.cg.trashman.object.Maze;
import com.cg.trashman.object.Pyramid;
import com.cg.trashman.object.Trash;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class GameScene implements IScene {
	private Cube cube;
	private Pyramid pyramid;
	private CameraController cameraController;
	private Maze maze;
	private Car car;
	private List<Trash> trashes;
	private Texture[] textures;
	private GL2 gl;
	private GLU glu;
	private MainGLCanvas mainGLCanvas;

	private TextRenderer textRenderer;
	
	private int frame_counter = 0;
	private int FPS = 60;

	public GameScene() {

	}

	@Override
	public void init(GL2 gl, GLU glu, MainGLCanvas mainGLCanvas) {
		this.gl = gl;
		this.glu = glu;
		this.mainGLCanvas = mainGLCanvas;

		/* load texture */
		try {
			textures = new Texture[16];
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

			gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

		} catch (GLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// init text
		textRenderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 30));

		initComponent();
		// Set up CameraController before using it
		cameraController.setGL(gl, glu);
	}

	public void initComponent() {
		cube = new Cube();
		pyramid = new Pyramid();
		cameraController = new CameraController();
		maze = MazeGenerator.createMaze(19, 19, 0.4f, textures);
		trashes = TrashGenerator.create(maze.getGrid(), textures);
		car = new Car(maze.getGrid(), textures, trashes);
	}

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

		// update trash
		for (Trash t : trashes) {
			t.update(gl, null);
		}

		// Update Camera Parameter
		float[] t = cameraController.getTranslation();
		float[] r = cameraController.getRotation();

		cameraController.update();
		cameraController.setDestination(-car.getX(), -19, -10 + car.getZ());

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

		// draw text
		textRenderer.beginRendering(drawable.getWidth(), drawable.getHeight());
		textRenderer.setColor(1f, 1f, 1f, 1f);
		textRenderer.draw("Score: " + car.getScore(), 100, 100);
		textRenderer.endRendering();
		
		frame_counter++;
		if( frame_counter == 2 * FPS ){
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
