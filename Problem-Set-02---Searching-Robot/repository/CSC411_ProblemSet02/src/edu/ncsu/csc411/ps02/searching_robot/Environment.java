package edu.ncsu.csc411.ps02.searching_robot;

/**
 * The world in which this simulation exists. As a base world, this produces a
 * 10x10 room of tiles. In addition, 20% of the room is covered with "walls"
 * (tiles marked as IMPASSABLE).
 *
 * This object will allow the agent to explore the world and is how the agent
 * will retrieve information about the environment. DO NOT MODIFY.
 * 
 * @author Adam Gaweda
 */
public class Environment {
    private final Tile[][] tiles;
    private final int      rows, cols;
    private int            targetRow, targetCol;

    public Environment () {
        this( 10, 10 );
    }

    public Environment ( final int width, final int height ) {
        // Columns refer to the WIDTH of the environment
        // Rows refer to the HEIGHT of the environment
        this.cols = width;
        this.rows = height;
        tiles = new Tile[rows][cols];
        for ( int row = 0; row < rows; row++ ) {
            for ( int col = 0; col < cols; col++ ) {
                tiles[row][col] = new Tile( TileStatus.CLEAN );
            }
        }
        final int numWalls = (int) ( Math.random() * ( rows * cols * .2 ) );
        for ( int i = 0; i < numWalls; i++ ) {
            final int row = (int) ( Math.random() * rows );
            final int col = (int) ( Math.random() * cols );
            tiles[row][col] = new Tile( TileStatus.IMPASSABLE );
        }
    }

    /* Traditional Getters and Setters */
    public Tile[][] getTiles () {
        return tiles;
    }

    public int getRows () {
        return this.rows;
    }

    public int getCols () {
        return this.cols;
    }

    public void setTarget ( final int row, final int col ) {
        // Only set if it is a coordinate within the environment
        if ( row >= 0 && row < rows && col >= 0 && col < cols ) {
            tiles[targetRow][targetCol] = new Tile( TileStatus.CLEAN );
            targetRow = row;
            targetCol = col;
            tiles[targetRow][targetCol] = new Tile( TileStatus.TARGET );
        }
    }

    public int getTargetRow () {
        return this.targetRow;
    }

    public int getTargetCol () {
        return this.targetCol;
    }

    /*
     * Returns a the status of a tile at a given [row][col] coordinate
     */
    public TileStatus getTileStatus ( final int row, final int col ) {
        if ( row < 0 || row >= rows || col < 0 || col >= cols ) {
            return TileStatus.IMPASSABLE;
        }
        else {
            return tiles[row][col].getStatus();
        }
    }

    public void setTileStatuse ( final int row, final int col, final TileStatus status ) {
        // Only set if it is a coordinate within the environment
        if ( row >= 0 && row < rows && col >= 0 && col < cols ) {
            tiles[row][col] = new Tile( status );
        }
    }

    /* Counts number of tiles that are not walls */
    public int getNumTiles () {
        int count = 0;
        for ( int row = 0; row < rows; row++ ) {
            for ( int col = 0; col < cols; col++ ) {
                if ( this.tiles[row][col].getStatus() != TileStatus.IMPASSABLE ) {
                    count++;
                }
            }
        }
        return count;
    }

    /*
     * Determines if a particular [row][col] coordinate is within the boundaries
     * of the environment. This is a rudimentary "collision detection" to ensure
     * the agent does not walk outside the world (or through walls).
     */
    public boolean validPos ( final int row, final int col ) {
        return row >= 0 && row < rows && col >= 0 && col < cols && tiles[row][col].getStatus() != TileStatus.IMPASSABLE;
    }

    public boolean goalConditionMet ( final Robot robot ) {
        return robot.getPosRow() == targetRow && robot.getPosCol() == targetCol;
    }
}
