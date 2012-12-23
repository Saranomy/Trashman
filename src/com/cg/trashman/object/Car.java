package com.cg.trashman.object;

import static javax.media.opengl.GL2.GL_QUADS;

import java.awt.event.KeyEvent;
import java.util.List;

import javax.media.opengl.GL2;
import com.cg.trashman.ISimpleObject;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;

public class Car implements ISimpleObject {
	private float pX;
	private float pZ;
	private float desX;
	private float desZ;
	private float pSpeed = 0.08f;
	private float pDefaultSpeed = 0.1f;
	private Direction direction;
	private boolean[][] mazeGrid;
	private int gridX;
	private int gridZ;

	private Texture[] textures;
	private float textureTop;
	private float textureBottom;
	private float textureLeft;
	private float textureRight;
	private List<Trash> trashes;
	public int score;

	enum Direction {
		Stop, Up, Down, Left, Right
	}

	private static final float size = 0.8f;

	public Car(boolean[][] mazeGrid, Texture[] textures, List<Trash> trashes) {
		pX = 0f;
		pZ = 0f;
		desX = pX;
		desZ = pZ;
		direction = Direction.Stop;
		this.mazeGrid = mazeGrid;
		gridX = 0;
		gridZ = 0;

		this.textures = textures;
		TextureCoords textureCoords = textures[0].getImageTexCoords();
		textureTop = textureCoords.top();
		textureBottom = textureCoords.bottom();
		textureLeft = textureCoords.left();
		textureRight = textureCoords.right();

		this.trashes = trashes;
		score = 0;
	}

	public void updateMazePosition(int row, int col) {
		desX = row * 2f;
		desZ = col * 2f;
	}

	public void addMazePositionX(int dX) {
		// out of range
		if (gridX + dX >= mazeGrid.length || gridX + dX < 0) {
			direction = Direction.Stop;
			return;
		}
		if (mazeGrid[gridX + dX][gridZ]) {
			direction = Direction.Stop;
		} else {
			gridX += dX;
			desX += dX * 2f;

			// check trash collision
			updateTrashCollision();
		}
	}

	public void addMazePositionZ(int dZ) {
		// out of range
		if (gridZ + dZ >= mazeGrid[0].length || gridZ + dZ < 0) {
			direction = Direction.Stop;
			return;
		}
		if (mazeGrid[gridX][gridZ + dZ]) {
			direction = Direction.Stop;
		} else {
			gridZ += dZ;
			desZ += dZ * 2f;

			// check trash collision
			updateTrashCollision();
		}
	}

	@Override
	public void update(GL2 gl, Object arg) {
		render(gl);

		if (isStable()) {
			pSpeed = 0;

			// next move
			if (direction == Direction.Left) {
				addMazePositionX(-1);
			} else if (direction == Direction.Right) {
				addMazePositionX(1);
			} else if (direction == Direction.Up) {
				addMazePositionZ(1);
			} else if (direction == Direction.Down) {
				addMazePositionZ(-1);
			}
			return;
		}
		this.pX += Math.signum(desX - pX) * pSpeed;
		if (Math.abs((this.pX - this.desX) * 1000f) / 1000f < pSpeed) {
			this.pX = this.desX;
		}
		this.pZ += Math.signum(desZ - pZ) * pSpeed;
		if (Math.abs((this.pZ - this.desZ) * 1000f) / 1000f < pSpeed) {
			this.pZ = this.desZ;
		}
		pSpeed = pDefaultSpeed;
	}

	public float getX() {
		return pX;
	}

	public float getZ() {
		return pZ;
	}

	private void render(GL2 gl) {
		// ----- Render the Color Cube -----
		gl.glLoadIdentity(); // reset the current model-view matrix
		// gl.glTranslatef(pX, 0, 0); // translate right and into the
		// screen
		gl.glTranslatef(0, 0, -pZ);
		gl.glTranslatef(pX, 0, 0);
		if (direction == Direction.Up)
			gl.glRotatef(-90f, 0f, 1f, 0f);
		else if (direction == Direction.Down)
			gl.glRotatef(90f, 0f, 1f, 0f);
		else if (direction == Direction.Left)
			gl.glRotatef(0f, 0f, 1f, 0f);
		else if (direction == Direction.Right)
			gl.glRotatef(180f, 0f, 1f, 0f);

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

	public boolean isStable() {
		return pX == desX && pZ == desZ;
	}

	public int getScore() {
		return score;
	}

	public void updateTrashCollision() {
		for (int i = 0; i < trashes.size(); i++) {
			Trash t = trashes.get(i);
			if (t.getRow() == gridX && t.getCol() == gridZ) {
				score += 100;
				trashes.remove(t);
				i--;
			}
		}
	}

	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_W
				|| event.getKeyCode() == KeyEvent.VK_UP) {
			direction = Direction.Up;
		}
		if (event.getKeyCode() == KeyEvent.VK_S
				|| event.getKeyCode() == KeyEvent.VK_DOWN) {
			direction = Direction.Down;
		}
		if (event.getKeyCode() == KeyEvent.VK_A
				|| event.getKeyCode() == KeyEvent.VK_LEFT) {
			direction = Direction.Left;
		}
		if (event.getKeyCode() == KeyEvent.VK_D
				|| event.getKeyCode() == KeyEvent.VK_RIGHT) {
			direction = Direction.Right;
		}
	}

	public void keyReleased(KeyEvent event) {
	}

	public void keyTyped(KeyEvent event) {

	}
}
