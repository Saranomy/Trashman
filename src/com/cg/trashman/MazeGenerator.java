package com.cg.trashman;

import java.util.Random;

import com.cg.trashman.object.Maze;
import com.jogamp.opengl.util.texture.Texture;

public class MazeGenerator {

	private static Random random;

	static {
		if (random == null) {
			random = new Random(System.currentTimeMillis());
		}
	}

	public static Maze createMaze(int row, int col, float density,
			Texture[] textures) {

		Maze maze = new Maze(row, col, textures);

		// Label every intersection so that we can tell when they're all
		// connected
		int[][] groups = new int[row][col];
		int group = 1;

		for (int i = 1; i < row - 1; i += 2) {
			for (int j = 1; j < col; j += 2) {
				groups[i][j] = group;
				group++;
			}
		}

		int wall_count = (int) (row / 2) * (int) (col / 2);

		while (true) {
			int x = random.nextInt(row);
			int y = random.nextInt(col);

			// System.out.printf("%d %d\n",x,y );

			int dir = random.nextInt(4);
			int dx, dy;

			if (dir == 0) {
				dx = -1;
				dy = 0;
			} else if (dir == 1) {
				dx = 1;
				dy = 0;
			} else if (dir == 2) {
				dx = 0;
				dy = -1;
			} else {
				dx = 0;
				dy = 1;
			}

			int mx = x + dx;
			int my = y + dy;

			if (maze.grid[x][y])
				continue;

			if (x + 2 * dx >= row || x + 2 * dx < 0)
				continue;
			if (y + 2 * dy >= col || y + 2 * dy < 0)
				continue;

			if (groups[x + (2 * dx)][y + (2 * dy)] == 0)
				continue;

			int id = groups[x][y];
			int oldid = groups[x + (2 * dx)][y + (2 * dy)];

			if (id == oldid)
				continue;

			for (int i = 1; i < row - 1; i += 2) {
				for (int j = 1; j < col - 1; j += 2) {
					if (groups[i][j] == oldid)
						groups[i][j] = id;
				}
			}

			maze.grid[mx][my] = true;
			wall_count++;

			if ((float) wall_count / (float) (row * col) >= density)
				break;

		}

		for (int i = 1; i < row - 1; i += 2) {
			for (int j = 1; j < col; j += 2) {
				maze.grid[i][j] = true;
			}
		}

		return maze;
	}

}
