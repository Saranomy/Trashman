package com.cg.trashman;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.cg.trashman.object.Trash;

public class TrashGenerator {
	public static List<Trash> create(boolean[][] grid) {
		Random random = new Random();
		List<Trash> trashes = new ArrayList<Trash>();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if(!grid[i][j] && random.nextFloat() < 0.5) {
					trashes.add(new Trash(i,j));
				}
			}
		}
		return trashes;
	}
}
