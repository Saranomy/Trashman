
public class Maze {
	
	public boolean grid[][];
	private int row;
	private int col;
	
	public Maze(int row,int col){
		grid = new boolean[row][col];
		for(int i = 0 ;  i < row ; i++){
			for(int j = 0 ; j < col ; j++){
				grid[i][j] = false;
			}
		}
		this.row = row;
		this.col = col;
	}
	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public void setWall(int row,int col){
		grid[row][col] = true;
	}
	
	public boolean[][] getMap(){
		return grid;
	}
	
}
