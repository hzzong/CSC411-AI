package edu.ncsu.csc411.ps01.cleaning_robot;

import java.util.ArrayList;

public class RunSimulation {
	private Environment env;
	private ArrayList<Robot> robots;
	private int numRobots;
	
	public RunSimulation() {
		this.env = new Environment();
		numRobots = 1;
		robots = new ArrayList<Robot>(); 
		for(int i = 0; i < numRobots; i++) {
			Robot robot = new Robot(env);
			robots.add(robot);
		}
	}
	
	public void run() {
		for (int i = 0; i < 200; i++) {
			updateEnvironment();
			if (env.getNumCleanedTiles() == env.getNumTiles()) {
				break;
			}
		}
		printPerformanceMeasure();
	}
	
	public double getPerformanceMeasure() {
		int cleanTiles = env.getNumCleanedTiles();
		int totalTiles = env.getNumTiles();
        return (1.0*cleanTiles)/totalTiles;
	}
	
	public void printPerformanceMeasure() {
		int cleanTiles = env.getNumCleanedTiles();
		int totalTiles = env.getNumTiles();
		System.out.println("Simulation Complete");
        System.out.println("Total Tiles:\t" + totalTiles);
        System.out.println("Clean Tiles:\t" + cleanTiles);
        System.out.printf("%% Cleaned:\t%.2f%%\n",(100.0*cleanTiles/totalTiles));
	}

	public void updateEnvironment() {
		for(Robot robot : robots) {
			Action action = robot.getAction();
			int x = robot.getPosX();
			int y = robot.getPosY();
			switch(action) {
            case CLEAN:
                env.cleanTile(x, y);
                break;
            case MOVE_DOWN:
                if (env.validPos(x, y+1))
                    robot.incPosY();
                break;
            case MOVE_LEFT:
                if (env.validPos(x-1, y))
                    robot.decPosX();
                break;
            case MOVE_RIGHT:
                if (env.validPos(x+1, y))
                    robot.incPosX();
                break;
            case MOVE_UP:
                if (env.validPos(x, y-1))
                    robot.decPosY();
                break;
            case DO_NOTHING: // pass to default
            default:
                break;
			}
		}
	}
	
	public static void main(String[] args) {
		RunSimulation sim = new RunSimulation();
		sim.run();
    }
}