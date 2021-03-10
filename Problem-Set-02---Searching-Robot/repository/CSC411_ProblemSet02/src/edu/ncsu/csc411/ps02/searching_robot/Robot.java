package edu.ncsu.csc411.ps02.searching_robot;

import java.util.ArrayList;

/**
 * Represents an intelligent agent moving through a particular room. The robot
 * only has one sensor - the ability to get the status of any tile in the
 * environment through the command env.getTileStatus(row, col).
 *
 * Your task is to modify the getAction method below so that is reached the
 * TARGET POSITION with a minimal number of steps. There is only one (1) target
 * position, which you can locate using env.getTargetRow() and
 * env.getTargetCol()
 */

public class Robot {
    private final Environment     env;
    private int                   posRow;
    private int                   posCol;

    private final Node            ini;

    private final Node            tar;

    private final ArrayList<Node> closedList;

    private Action                lastDirection;

    /**
     * Initializes a Robot on a specific tile in the environment.
     */
    public Robot ( final Environment env ) {
        this( env, 0, 0 );
    }

    public Robot ( final Environment env, final int posRow, final int posCol ) {
        this.env = env;
        this.posRow = posRow;
        this.posCol = posCol;
        closedList = new ArrayList<Node>();
        ini = new Node( 0, 0 );
        tar = new Node( env.getTargetRow(), env.getTargetCol() );
        ini.setF( getDistance( ini, tar ) );
        ini.setG( 0 );
        ini.setH( getDistance( ini, tar ) );
        closedList.add( ini );
        lastDirection = null;
    }

    public int getPosRow () {
        return posRow;
    }

    public int getPosCol () {
        return posCol;
    }

    public void incPosRow () {
        posRow++;
    }

    public void decPosRow () {
        posRow--;
    }

    public void incPosCol () {
        posCol++;
    }

    public void decPosCol () {
        posCol--;
    }

    /**
     * Simulate the passage of a single time-step. At each time-step, the Robot
     * decides which direction to move.
     */
    public Action getAction () {
        // There is no getNeighborTiles this time, but you
        // can get a tile's status with
        final TileStatus status = env.getTileStatus( posRow, posCol );

        // You are STRONGLY encouraged to design a search tree
        // rather than arbitrarily moving through the environment.
        if ( status == TileStatus.TARGET ) {
            return Action.DO_NOTHING;
        }

        // if ( open.size() > 0 ) {
        // for ( int i = 1; i < open.size(); i++ ) {
        // if ( open.get( i ).getF() < n.getF() ) {
        // n = open.get( i );
        // }
        // }
        // }
        // open.remove( n );

        final Node current = new Node( posRow, posCol );
        current.setH( Integer.MAX_VALUE );
        current.children = getNodesArround( current );

        int h = current.getH();
        Action nextStep = Action.DO_NOTHING;
        int last = -1;
        for ( int i = 0; i < current.children.size(); i++ ) {
            if ( env.getTileStatus( current.children.get( i ).getRow(),
                    current.children.get( i ).getCol() ) == TileStatus.TARGET ) {
                return next( current, current.children.get( i ) );
                // if ( n.children.get( i ).getRow() < posRow ) {
                // return Action.MOVE_DOWN;
                // }
                // else if ( n.children.get( i ).getRow() > posRow ) {
                // return Action.MOVE_UP;
                // }
                // else if ( n.children.get( i ).getCol() < posCol ) {
                // return Action.MOVE_LEFT;
                // }
                // else if ( n.children.get( i ).getCol() > posCol ) {
                // return Action.MOVE_RIGHT;
                // }
            }
            if ( ( !containsC( current.children.get( i ).getRow(), current.children.get( i ).getCol() ) ) ) {
                final int cg = getDistance( ini, current.children.get( i ) );
                final int ch = getDistance( current.children.get( i ), tar );
                final int cf = cg + ch;
                if ( ch <= h ) {
                    current.children.get( i ).setF( cf );
                    current.children.get( i ).setG( cg );
                    current.children.get( i ).setH( ch );
                    last = i;
                    nextStep = next( current, current.children.get( i ) );
                    h = ch;
                    lastDirection = null;
                }

            }

        }
        if ( nextStep == Action.DO_NOTHING ) {
            for ( int j = 0; j < current.children.size(); j++ ) {
                final int dh = getDistance( current.children.get( j ), tar );
                if ( dh < h && next( current, current.children.get( j ) ) != lastDirection ) {
                    nextStep = next( current, current.children.get( j ) );
                    lastDirection = nextStep;
                    current.children.get( j ).setH( dh );
                }
            }
        }
        else {
            closedList.add( current.children.get( last ) );
        }

        return nextStep;
    }

    public Action next ( final Node cur, final Node nex ) {
        if ( nex.getRow() > cur.getRow() ) {
            return Action.MOVE_DOWN;
        }
        else if ( nex.getRow() < cur.getRow() ) {
            return Action.MOVE_UP;
        }
        else if ( nex.getCol() < cur.getCol() ) {
            return Action.MOVE_LEFT;
        }
        else if ( nex.getCol() > cur.getCol() ) {
            return Action.MOVE_RIGHT;
        }
        return Action.DO_NOTHING;
    }

    public boolean containsC ( final int row, final int col ) {
        for ( int i = 0; i < closedList.size(); i++ ) {
            if ( closedList.get( i ).getRow() == row && closedList.get( i ).getCol() == col ) {
                return true;
            }
        }
        return false;
    }

    // public Node containsO ( final int row, final int col ) {
    // for ( int i = 0; i < open.size(); i++ ) {
    // if ( open.get( i ).getRow() == row && closedList.get( i ).getCol() == col
    // ) {
    // return open.get( i );
    // }
    // }
    // return null;
    // }

    public ArrayList<Node> getNodesArround ( final Node n ) {
        final ArrayList<Node> arround = new ArrayList<Node>();
        if ( env.getTileStatus( n.getRow() - 1, n.getCol() ) != TileStatus.IMPASSABLE ) {
            final Node n1 = new Node( n.getRow() - 1, n.getCol() );
            n1.setParent( n );
            arround.add( n1 );
        }
        if ( env.getTileStatus( n.getRow() + 1, n.getCol() ) != TileStatus.IMPASSABLE ) {
            final Node n2 = new Node( n.getRow() + 1, n.getCol() );
            n2.setParent( n );
            arround.add( n2 );
        }
        if ( env.getTileStatus( n.getRow(), n.getCol() - 1 ) != TileStatus.IMPASSABLE ) {
            final Node n3 = new Node( n.getRow(), n.getCol() - 1 );
            n3.setParent( n );
            arround.add( n3 );
        }
        if ( env.getTileStatus( n.getRow(), n.getCol() + 1 ) != TileStatus.IMPASSABLE ) {
            final Node n4 = new Node( n.getRow(), n.getCol() + 1 );
            n4.setParent( n );
            arround.add( n4 );
        }
        return arround;
    }

    public int getDistance ( final Node n1, final Node n2 ) {
        return ( Math.abs( n1.getRow() - n2.getRow() ) + Math.abs( n1.getCol() - n2.getCol() ) );
    }

    class Node {

        private final int       col;

        private final int       row;

        private int             f;

        private int             g;

        private int             h;

        private ArrayList<Node> children;

        private Node            parent;

        public Node ( final int row, final int col ) {
            this.col = col;
            this.row = row;

            children = new ArrayList<Node>();
            f = Integer.MAX_VALUE;
            g = Integer.MAX_VALUE;
            h = Integer.MAX_VALUE;
            parent = null;
        }

        public int getRow () {
            return row;
        }

        public int getCol () {
            return col;
        }

        public int getF () {
            return f;
        }

        public void setF ( final int f ) {
            this.f = f;
        }

        public void setG ( final int g ) {
            this.g = g;
        }

        public int getG () {
            return g;
        }

        public void setH ( final int h ) {
            this.h = h;
        }

        public int getH () {
            return h;
        }

        public void setParent ( final Node parent ) {
            this.parent = parent;
        }

        public Node getParent () {
            return parent;
        }

        public void addChild ( final Node child ) {
            children.add( child );
        }
    }

}
