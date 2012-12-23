package com.cg.trashman;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.cg.trashman.object.Trash;
import com.jogamp.opengl.util.texture.Texture;

public class TrashGenerator {
	public static List<Trash> create(boolean[][] grid, Texture[] textures) {
		Random random = new Random();
		List<Trash> trashes = new ArrayList<Trash>();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (!grid[i][j] && random.nextFloat() < 0.2) {
					trashes.add(new Trash(i, j, textures));
				}
			}
		}
		return trashes;
	}
	public static Trash newTrash(boolean[][] grid,List<Trash> trashes, Texture[] textures){
		Random r = new Random(System.currentTimeMillis());
		while( true ){
			int i = r.nextInt(grid.length);
			int j = r.nextInt(grid[0].length);
			if (!grid[i][j] && !hasTrashAt(i,j,trashes) ) {
				return new Trash(i,j,textures);
			}
		}
		//return null;
	}
	private static boolean hasTrashAt(int row,int col,List<Trash> trashes){
		for( Trash t : trashes ){
			if( t.getRow() == row && t.getCol() == col )
				return true;
		}
		return false;
	}
}
