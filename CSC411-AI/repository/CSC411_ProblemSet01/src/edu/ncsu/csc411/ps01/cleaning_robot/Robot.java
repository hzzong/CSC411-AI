package edu.ncsu.csc411.ps01.cleaning_robot;

/**
 * Represents a simple-reflex agent cleaning a particular room. At all times the
 * robot has a direction in the environment. The robot only has one sensor - the
 * status of all its neighboring tiles, including the one it is currently on
 * (located at [1][1]).
 *
 * Your task is to modify the getAction method below to produce better coverage.
 */

public class Robot {
    private final Environment env;
    private int               posX;
    private int               posY;
    private int               lastX;
    private int               lastY;

    /**
     * Initializes a Robot on a specific tile in the environment. The robot
     * facing to the right.
     */
    public Robot ( final Environment env ) {
        this.env = env;
        this.posX = 0;
        this.posY = 0;
        this.lastX = -1;
        this.lastY = -1;
    }

    public int getPosX () {
        return posX;
    }

    public int getPosY () {
        return posY;
    }

    public void incPosX () {
        posX++;
    }

    public void decPosX () {
        posX--;
    }

    public void incPosY () {
        posY++;
    }

    public void decPosY () {
        posY--;
    }

    /**
     * Simulate the passage of a single time-step. At each time-step, the Robot
     * decides whether to clean the current tile or move tiles.
     */
    public Action getAction () {
        // Gets a 3x3 array of tile statuses, centered around
        // the robot.
        final Tile[][] tiles = env.getNeighborTiles( this );
        // tiles[1][1] is the tile the robot is currently
        // standing on.
        if ( tiles[1][1].getStatus() == TileStatus.DIRTY ) {
            return Action.CLEAN;
        }

        // Modify the code below to get better coverage.
        // System.out.println( posX + " " + posY );
        // System.out.println( tiles[1][2].getStatus().toString() );

        if ( tiles[1][0].getStatus() != TileStatus.IMPASSABLE && tiles[1][0].getStatus() == TileStatus.DIRTY ) {
            lastX = -1;
            lastY = -1;
            return Action.MOVE_UP;
        }
        else if ( tiles[2][1].getStatus() != TileStatus.IMPASSABLE && tiles[2][1].getStatus() == TileStatus.DIRTY ) {
            lastX = -1;
            lastY = -1;
            return Action.MOVE_RIGHT;
        }
        else if ( tiles[0][1].getStatus() != TileStatus.IMPASSABLE && tiles[0][1].getStatus() == TileStatus.DIRTY ) {
            lastX = -1;
            lastY = -1;
            return Action.MOVE_LEFT;
        }
        else if ( tiles[1][2].getStatus() != TileStatus.IMPASSABLE && tiles[1][2].getStatus() == TileStatus.DIRTY ) {
            lastX = -1;
            lastY = -1;
            return Action.MOVE_DOWN;
        }
        else if ( tiles[1][2].getStatus() != TileStatus.IMPASSABLE && ( lastX != 1 && lastY != 0 ) ) {
            lastX = 1;
            lastY = 2;
            return Action.MOVE_DOWN;
        }
        else if ( tiles[2][1].getStatus() != TileStatus.IMPASSABLE && ( lastX != 0 && lastY != 1 ) ) {
            lastX = 2;
            lastY = 1;
            return Action.MOVE_RIGHT;
        }
        else if ( tiles[0][1].getStatus() != TileStatus.IMPASSABLE && ( lastX != 2 && lastY != 1 ) ) {
            lastX = 0;
            lastY = 1;
            return Action.MOVE_LEFT;
        }
        else if ( tiles[1][0].getStatus() != TileStatus.IMPASSABLE && ( lastX != 1 && lastY != 2 ) ) {
            lastX = 1;
            lastY = 0;
            return Action.MOVE_UP;
        }
        lastX = -1;
        lastY = -1;
        return Action.DO_NOTHING;
    }
}
