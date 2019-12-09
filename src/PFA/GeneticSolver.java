package PFA;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import javafx.util.Pair;

public class GeneticSolver extends Solver{


    public GeneticSolver(Problem problem) {
        super(problem);
    }

    @Override
    public Solution Solve(Problem problem) {
        return null;
    }
    //Generate an approxiate solution that may be non valid
    public Solution InitialSolution() {
    	Solution solution=new Solution(problem);
		Random random=new Random();

    	for (int i = 0; i < problem.switchs.size(); i++) {
    		int closestPoition=problem.getClosestPositionToSwitch(i);
    		solution.positionAndLinkPerSwitch[i]=new Pair<Integer, Integer>(closestPoition,random.nextInt(problem.links.size()));
		}
    	List<Integer>[] switchsPerPosition=solution.getSwitchsPerPosition();
    	for (int i = 0; i < switchsPerPosition.length; i++) {
			List<Integer> switchsLinked=switchsPerPosition[i];
			if (!switchsLinked.isEmpty()) {
				int unmatchedlookups=0;
				for (int j = 0; j < switchsLinked.size(); j++) {
					Switch s=problem.switchs.get(j);
					unmatchedlookups+=s.unmatchedPackets;
				}
				int bestController=problem.cheapestControllerWithMinimalProcessing(unmatchedlookups);
				//check if no controller is found or the inventory is exceded TO DO...
				solution.controllerInPosition[i]=bestController;
				

			}
		}
    	
    	
    	for (int i = 0; i < solution.dim_Positions; i++) {
            for (int j = i+1; j < solution.dim_Positions; j++) {
                if (solution.controllerInPosition[i]>-1&& solution.controllerInPosition[j]>-1){
                    solution.interControllersLinks[i][j]=random.nextInt(solution.dim_Links);
                    
                }

            }
    	}
    	return solution;
    }


}
