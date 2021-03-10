package edu.ncsu.csc411.ps02.searching_robot;

import java.util.ArrayList;

public class RunSimulation {
    private final Environment      env;
    private final ArrayList<Robot> robots;
    private final int              numRobots;
    private int                    timesteps;
    private final int              timestepsStop;
    private boolean                goalMet;

    public RunSimulation () {
        this.env = new Environment();
        this.env.setTarget( 9, 9 );
        numRobots = 1;
        robots = new ArrayList<Robot>();
        for ( int i = 0; i < numRobots; i++ ) {
            final Robot robot = new Robot( env );
            robots.add( robot );
        }
        // number of time steps since the beginning
        this.timesteps = 0;
        // number of time steps before stopping simulation
        this.timestepsStop = 200;
        this.goalMet = false;
    }

    public void run () {
        while ( timesteps < timestepsStop ) {
            updateEnvironment();
            if ( timesteps == timestepsStop ) {
                break;
            }
            for ( final Robot robot : robots ) {
                if ( env.goalConditionMet( robot ) ) {
                    goalMet = true;
                    break;
                }
            }
            if ( goalMet ) {
                break;
            }
        }
        printPerformanceMeasure();
    }

    public double getTimesteps () {
        return timesteps;
    }

    public void printPerformanceMeasure () {
        System.out.printf( "Simulation Completed in %d timesteps\n", timesteps );
        System.out.println( "Goal Condition Met: " + goalMet );
    }

    // Gets the new state of the world after robot actions
    public void updateEnvironment () {
        timesteps++;
        for ( final Robot robot : robots ) {
            final Action action = robot.getAction();
            final int row = robot.getPosRow();
            final int col = robot.getPosCol();
            switch ( action ) {
                case MOVE_DOWN:
                    if ( env.validPos( row + 1, col ) ) {
                        robot.incPosRow();
                    }
                    break;
                case MOVE_LEFT:
                    if ( env.validPos( row, col - 1 ) ) {
                        robot.decPosCol();
                    }
                    break;
                case MOVE_RIGHT:
                    if ( env.validPos( row, col + 1 ) ) {
                        robot.incPosCol();
                    }
                    break;
                case MOVE_UP:
                    if ( env.validPos( row - 1, col ) ) {
                        robot.decPosRow();
                    }
                    break;
                case DO_NOTHING: // pass to default
                default:
                    break;
            }
        }
    }

    public static void main ( final String[] args ) {
        final RunSimulation sim = new RunSimulation();
        sim.run();
    }
}
