import org.apache.commons.math3.optim.*;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import java.util.ArrayList;
import java.util.Collection;


public class ZongCharlie_ProblemSet03 {
	
	public static double[] linearProgram_Coffee() {
		
		//Function 1 for the milk calculation
		LinearObjectiveFunction f1 = new LinearObjectiveFunction(new double[] {1, 1}, 0);
		
		//Constraints for the milks
		Collection<LinearConstraint> constraints = new ArrayList<LinearConstraint>();
		
		constraints.add(new LinearConstraint(new double[] {1.25, 2}, Relationship.LEQ, 5000));
		
		constraints.add(new LinearConstraint(new double[] {1, 1}, Relationship.LEQ, 3625));
		
		constraints.add(new LinearConstraint(new double[] {0, 4.8}, 0,  Relationship.EQ,  new double[] {1, 0}, 0));
				
		PointValuePair allmilk = null;
		
		LinearConstraintSet constraintSet = new LinearConstraintSet(constraints);
		
		try {
			allmilk = new SimplexSolver().optimize(f1, constraintSet, GoalType.MAXIMIZE,
					new NonNegativeConstraint(true));
		} catch (Exception e) {
			System.out.println("No solution fulfilling the constraints can be found.");
		}
		
		double smilk =  allmilk.getPoint()[0];
		double fmilk =  allmilk.getPoint()[1];
		
		
		LinearObjectiveFunction f2 = new LinearObjectiveFunction(new double[] {2.6, 1.3, 3.0}, 0);
		
		Collection<LinearConstraint> constraints2 = new ArrayList<LinearConstraint>();
		//Caffee1 + Caffee2 + Caffee3 <= 2000 oz
		constraints2.add(new LinearConstraint(new double[] {2, 2, 2}, Relationship.LEQ, 2000));
		
		constraints2.add(new LinearConstraint(new double[] {6, 2, 0}, Relationship.LEQ, smilk));
		
		constraints2.add(new LinearConstraint(new double[] {0, 2, 4}, Relationship.LEQ, fmilk));
		
		
		PointValuePair solution = null;
		LinearConstraintSet constraintSet2 = new LinearConstraintSet(constraints2);
		
		try {
			solution = new SimplexSolver().optimize(f2, constraintSet2, GoalType.MAXIMIZE,
					new NonNegativeConstraint(true));
		} catch (Exception e) {
			System.out.println("No solution fulfilling the constraints can be found.");
		}
		

		double[] result =  {solution.getValue(), solution.getPoint()[0],solution.getPoint()[1], solution.getPoint()[2]};
		System.out.println(result[0] + " " + result[1] + " " + result[2] + " " + result[3]);
		return result;
		
	}

	public static double[] linearProgram_Shoes() {
		LinearObjectiveFunction f = new LinearObjectiveFunction(new double[] {20, 30, 25, 5}, 0);
		
		Collection<LinearConstraint> constraints = new ArrayList<LinearConstraint>();
		constraints.add(new LinearConstraint(new double[] {2, 2.8, 1.2, 0.8}, Relationship.LEQ, 1500));
		
		constraints.add(new LinearConstraint(new double[] {0, 1.2, 2, 0}, Relationship.LEQ, 200));
		
		constraints.add(new LinearConstraint(new double[] {0, 0, 0, 1.5}, Relationship.LEQ, 500));
		
		constraints.add(new LinearConstraint(new double[] {1, 0, 0, 0}, Relationship.GEQ, 10));
		
		constraints.add(new LinearConstraint(new double[] {0, 1, 0, 0}, Relationship.GEQ, 10));
		
		constraints.add(new LinearConstraint(new double[] {0, 0, 1, 0}, Relationship.GEQ, 10));
		
		constraints.add(new LinearConstraint(new double[] {0, 0, 0, 1}, Relationship.GEQ, 10));
		
		constraints.add(new LinearConstraint(new double[] {1, 0, 0, 0}, 0, Relationship.EQ, new double[] {0, 2, 0, 0}, 0));
		
//		constraints.add(new LinearConstraint(new double[] {20, 30, 25, 5}, Relationship.EQ, 12416.67));
		
		PointValuePair solution = null;
		LinearConstraintSet constraintSet = new LinearConstraintSet(constraints);
		
		try {
			solution = new SimplexSolver().optimize(f, constraintSet, GoalType.MAXIMIZE,
					new NonNegativeConstraint(true));
		} catch (Exception e) {
			System.out.println("No solution fulfilling the constraints can be found.");
		}
		

		double[] result =  {solution.getValue(), solution.getPoint()[0],solution.getPoint()[1], solution.getPoint()[2], solution.getPoint()[3]};
		System.out.println(result[0] + " " + result[1] + " " + result[2] + " " + result[3] + " " + result[4]);
		return result;
		
	}

	public static double[] linearProgram_Bakery() {
		LinearObjectiveFunction f = new LinearObjectiveFunction(new double[] {1, 2, 0.25, 0.75}, 0);
		
		Collection<LinearConstraint> constraints = new ArrayList<LinearConstraint>();
		constraints.add(new LinearConstraint(new double[] {0.05, 0.15, 0.05, 0.15}, Relationship.LEQ, 130));
		
		constraints.add(new LinearConstraint(new double[] {0.2, 0.25, 0.08, 0.3}, Relationship.LEQ, 300));
		
		constraints.add(new LinearConstraint(new double[] {0.25, 0.1, 0.2, 0.05}, Relationship.LEQ, 180));
		
		constraints.add(new LinearConstraint(new double[] {0.1, 0.05, 0.15, 0.12}, Relationship.LEQ, 160));
		
		constraints.add(new LinearConstraint(new double[] {0.02, 0.01, 0.15, 0.05}, Relationship.LEQ, 100));
		
		constraints.add(new LinearConstraint(new double[] {1, 0, 0, 0}, Relationship.GEQ, 100));
		
		constraints.add(new LinearConstraint(new double[] {0, 1, 0, 0}, Relationship.GEQ, 100));
		
		constraints.add(new LinearConstraint(new double[] {0, 0, 1, 0}, Relationship.GEQ, 100));
		
		constraints.add(new LinearConstraint(new double[] {0, 0, 0, 1}, Relationship.GEQ, 100));
		
		constraints.add(new LinearConstraint(new double[] {1, 0, 0, 0}, Relationship.LEQ, 500));

		constraints.add(new LinearConstraint(new double[] {0, 1, 0, 0}, Relationship.LEQ, 500));
		
		constraints.add(new LinearConstraint(new double[] {0, 0, 1, 0}, Relationship.LEQ, 500));
		
		constraints.add(new LinearConstraint(new double[] {0, 0, 0, 1}, Relationship.LEQ, 500));
		
		constraints.add(new LinearConstraint(new double[] {0, 0, 1, 0}, 0, Relationship.EQ, new double[] {0, 2, 0, 0}, 0));
		
		constraints.add(new LinearConstraint(new double[] {0, 0, 0, 1}, 0, Relationship.EQ, new double[] {3, 0, 0, 0}, 0));
		
//		constraints.add(new LinearConstraint(new double[] {1, 2, 0.25, 0.75}, Relationship.EQ, 1062));
		
		PointValuePair solution = null;
		LinearConstraintSet constraintSet = new LinearConstraintSet(constraints);
		
		try {
			solution = new SimplexSolver().optimize(f, constraintSet, GoalType.MAXIMIZE,
					new NonNegativeConstraint(true));
		} catch (Exception e) {
			System.out.println("No solution fulfilling the constraints can be found.");
		}
		

		double[] result =  {solution.getValue(), solution.getPoint()[0],solution.getPoint()[1], solution.getPoint()[2], solution.getPoint()[3]};
		System.out.println(result[0] + " " + result[1] + " " + result[2] + " " + result[3] + " " + result[4]);
		return result;
		
	}
	
	
	public static void main(String[] args) {
//		linearProgram_Coffee();
		
		linearProgram_Bakery() ;
		
		linearProgram_Shoes();
	}
}
