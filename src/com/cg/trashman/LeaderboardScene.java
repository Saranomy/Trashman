package com.cg.trashman;

import static javax.media.opengl.GL.GL_COLOR_BUFFER_BIT;
import static javax.media.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static javax.media.opengl.GL2.GL_QUADS;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;

public class LeaderboardScene implements IScene {
	private GL2 gl;
	private GLU glu;
	private MainGLCanvas mainGLCanvas;
	private Texture[] textures;
	private float textureTop;
	private float textureBottom;
	private float textureLeft;
	private float textureRight;

	private float angleCar;
	private TextRenderer textTitle;
	private TextRenderer textInfo;
	private Score score;

	public LeaderboardScene() {
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

		angleCar = 0f;
		textTitle = new TextRenderer(new Font("SansSerif", Font.BOLD, 90));
		textInfo = new TextRenderer(new Font("SansSerif", Font.BOLD, 40));
		score = Score.getInstance();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {

	}

	@Override
	public void display(GLAutoDrawable drawable) {
		gl.glMatrixMode(GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(45.0, 1.55f, 0.1, 100.0);
		gl.glMatrixMode(GL_MODELVIEW);

		GL2 gl = drawable.getGL().getGL2(); // get the OpenGL 2 graphics context
		gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		gl.glColor3f(1f, 1f, 1f);

		// draw many trashes
		drawTrash(15, 0.3f, -1.8f, -7f);
		drawTrash(16, 0.3f, -0.6f, -7f);
		drawTrash(17, 0.3f, 0.6f, -7f);
		drawTrash(18, 0.3f, 1.8f, -7f);
		angleCar += 0.4f;

		// draw title
		String str = "You got";
		textTitle.beginRendering(drawable.getWidth(), drawable.getHeight());
		textTitle.setColor(1f, 1f, 1f, 1f);
		Rectangle2D textBox = textTitle.getBounds(str);
		textTitle.draw(str, 400 - ((int) textBox.getWidth() / 2), 460);
		textTitle.endRendering();

		// draw score
		str = String.format("$%04d", score.getScore());
		textTitle.beginRendering(drawable.getWidth(), drawable.getHeight());
		textTitle.setColor(1f, 1f, 0f, 1f);
		textBox = textTitle.getBounds(str);
		textTitle.draw(str, 400 - ((int) textBox.getWidth() / 2), 360);
		textTitle.endRendering();

		// draw info
		str = "Press Enter to Continue";
		textInfo.beginRendering(drawable.getWidth(), drawable.getHeight());
		textInfo.setColor(1f, 1f, 1f, 1f);
		textBox = textInfo.getBounds(str);
		textInfo.draw(str, 400 - ((int) textBox.getWidth() / 2), 150);
		textInfo.endRendering();
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {

	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_ENTER) {
			mainGLCanvas.setScene(0);
			score.reset();
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {

	}

	@Override
	public void keyTyped(KeyEvent event) {

	}

	private void drawTrash(int trashId, float size, float x, float z) {
		// ----- Render the Color Cube -----
		gl.glLoadIdentity(); // reset the current model-view matrix
		// gl.glTranslatef(pX, 0, 0); // translate right and into the
		// screen

		gl.glTranslatef(x, -0.4f, z);
		gl.glRotatef(angleCar, 0f, 1f, 1f);

		// trash texture
		textures[trashId].enable(gl);
		textures[trashId].bind(gl);

		gl.glBegin(GL_QUADS);
		// Front Face
		gl.glTexCoord2f(textureLeft, textureBottom);
		gl.glVertex3f(-size, -size, size); // bottom-left of the
											// texture and quad
		gl.glTexCoord2f(textureRight, textureBottom);
		gl.glVertex3f(size, -size, size); // bottom-right of the
											// texture and quad
		gl.glTexCoord2f(textureRight, textureTop);
		gl.glVertex3f(size, size, size); // top-right of the texture
											// and quad
		gl.glTexCoord2f(textureLeft, textureTop);
		gl.glVertex3f(-size, size, size); // top-left of the texture
											// and quad

		// Back Face
		gl.glTexCoord2f(textureRight, textureBottom);
		gl.glVertex3f(-size, -size, -size);
		gl.glTexCoord2f(textureRight, textureTop);
		gl.glVertex3f(-size, size, -size);
		gl.glTexCoord2f(textureLeft, textureTop);
		gl.glVertex3f(size, size, -size);
		gl.glTexCoord2f(textureLeft, textureBottom);
		gl.glVertex3f(size, -size, -size);

		// Top Face
		gl.glTexCoord2f(textureLeft, textureTop);
		gl.glVertex3f(-size, size, -size);
		gl.glTexCoord2f(textureLeft, textureBottom);
		gl.glVertex3f(-size, size, size);
		gl.glTexCoord2f(textureRight, textureBottom);
		gl.glVertex3f(size, size, size);
		gl.glTexCoord2f(textureRight, textureTop);
		gl.glVertex3f(size, size, -size);

		// Bottom Face
		gl.glTexCoord2f(textureRight, textureTop);
		gl.glVertex3f(-size, -size, -size);
		gl.glTexCoord2f(textureLeft, textureTop);
		gl.glVertex3f(size, -size, -size);
		gl.glTexCoord2f(textureLeft, textureBottom);
		gl.glVertex3f(size, -size, size);
		gl.glTexCoord2f(textureRight, textureBottom);
		gl.glVertex3f(-size, -size, size);

		// Right face
		gl.glTexCoord2f(textureRight, textureBottom);
		gl.glVertex3f(size, -size, -size);
		gl.glTexCoord2f(textureRight, textureTop);
		gl.glVertex3f(size, size, -size);
		gl.glTexCoord2f(textureLeft, textureTop);
		gl.glVertex3f(size, size, size);
		gl.glTexCoord2f(textureLeft, textureBottom);
		gl.glVertex3f(size, -size, size);

		// Left Face
		gl.glTexCoord2f(textureLeft, textureBottom);
		gl.glVertex3f(-size, -size, -size);
		gl.glTexCoord2f(textureRight, textureBottom);
		gl.glVertex3f(-size, -size, size);
		gl.glTexCoord2f(textureRight, textureTop);
		gl.glVertex3f(-size, size, size);
		gl.glTexCoord2f(textureLeft, textureTop);
		gl.glVertex3f(-size, size, -size);

		gl.glEnd();
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}

}
