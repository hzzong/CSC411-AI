package edu.ncsu.csc411.ps01.cleaning_robot;

/**
 * The world in which this simulation exists. As a base
 * world, this produces a 10x10 room of DIRTY tiles. In addition,
 * 20% of the room is covered with "walls" (tiles marked as IMPASSABLE).
 * 
 * This object will allow the agent to explore the world and is how
 * the agent will retrieve information about neighboring tiles.
 * DO NOT MODIFY.
 * @author Adam Gaweda
 */
public class Environment {
	private Tile[][] tiles;
	private int rows, cols;
	
	public Environment() { this(10,10); }
	public Environment(int rows, int columns) {
		// Add 2 to create exterior walls
		this.rows = rows;
		this.cols = columns;
		tiles = new Tile[rows][cols];
		for(int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				tiles[i][j] = new Tile(TileStatus.DIRTY);
			}
		}
		int numWalls = (int)(Math.random() * (rows*columns*.2));
		for(int i = 0; i < numWalls; i++) {
			int x = (int)(Math.random() * rows);
			int y = (int)(Math.random() * cols);
			tiles[x][y] = new Tile(TileStatus.IMPASSABLE);
		}
	}
	
	/* Traditional Getters */
	public Tile[][] getTiles() { return tiles; }
	public int getRows() { return this.rows; }
	public int getCols() { return this.cols; }
	
	/*
	 * Returns a 3x3 array of tile statuses, centered
	 * around the agent (which is located at index [1][1]).
	 */
	public Tile[][] getNeighborTiles(Robot robot) {
		Tile[][] neighbors = new Tile[3][3];
		int roboX = robot.getPosX();
		int roboY = robot.getPosY();
		int currentX = 0;
		int currentY = 0;
		
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				int X = roboX + i;
				int Y = roboY + j;
				if (X < 0 || X > rows-1 || Y < 0 || Y > cols-1) {
					neighbors[currentX][currentY] = new Tile(TileStatus.IMPASSABLE);
				} else {
					neighbors[currentX][currentY] = tiles[X][Y];
				}
				currentY++;
				currentY = currentY % 3;
			}
			currentX++;
			currentX = currentX % 3;
		}
		
		return neighbors;
	}
	
	/* Cleans the tile at coordinate [x][y] */
	public void cleanTile(int x, int y) {
		tiles[x][y].cleanTile();		
	}
	
	/* Counts number of cleaned tiles */
	public int getNumCleanedTiles() {
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (this.tiles[i][j].getStatus() == TileStatus.CLEAN)
                    count++;
            }
        }
        return count;
    }
	
	/* Counts number of tiles that are not walls */
	public int getNumTiles() {
		int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (this.tiles[i][j].getStatus() != TileStatus.IMPASSABLE)
                    count++;
            }
        }
        return count;
    }
	
	/* Returns a ratio of the number of cleaned tiles */
	public double getPerformanceMeasure() {
		return (1.0 * getNumCleanedTiles()) / getNumTiles(); 
	}

	/* Determines if a particular [x][y] coordinate is within
	 * the boundaries of the environment. This is a rudimentary
	 * "collision detection" to ensure the agent does not walk
	 * outside the world (or through walls).
	 */
	public boolean validPos(int x, int y) {
	    return x >= 0 && x < cols && y >= 0 && y < rows &&
	    		tiles[x][y].getStatus() != TileStatus.IMPASSABLE;
	}
}
