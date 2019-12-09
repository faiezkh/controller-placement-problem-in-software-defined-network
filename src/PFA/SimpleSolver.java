package PFA;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

//import com.sun.xml.internal.ws.api.pipe.NextAction;

import javafx.util.Pair;

public class SimpleSolver extends Solver {

	public SimpleSolver(Problem problem) {
		super(problem);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Solution Solve(Problem problem) {
		Solution bestSolution=null;
		int nbrControllersTotal=problem.nbrOfControllers();
		int maxCost=Integer.MAX_VALUE;
		//possible positions that each controller can be installed to
		int[] possiblePositions=new int[problem.positions.size()];
		//each element contains the index of the position
		for (int j = 0; j < possiblePositions.length; j++) {
			possiblePositions[j]=j;
		}

		//i marks the number of controllers
		
		for (int n = 1; n < nbrControllersTotal; n++) {
			int[] inventory=problem.inventory();
			//get all the possible combinations of i positions of all the possible positions
			List<int[]> allCombinations=Combination.getCombinations(possiblePositions, n);
			//get all the possible controllers sets in a sorted order with respect to the inventory
			List<int[]> allControllersPosibilities=Combination.getAllSortedSequencesWithLimits(0, problem.controllers.size(),n,inventory,  null, 0);
			for (int[] controllers : allControllersPosibilities) {
				
				int cost=getCostOfControlers(controllers);
				//for each possible combination j
				for (int j = 0; j < allCombinations.size(); j++) {
					//creating a new solution
					Solution newSolution=new Solution(problem);
					//get the selected combination j
					int[] combination=allCombinations.get(j);
					//for each controller of index k
					for (int k = 0; k < combination.length; k++) {
						//we set its position to the kth element in the combination j
						newSolution.controllerInPosition[combination[k]]=controllers[k];
					}
					//the capacity available for each controller so far
					int[] controllersPacketsCapacity=new int[n];
					//for each controller of index controllers[k] we set its processing value
					for (int k = 0; k < controllersPacketsCapacity.length; k++) {
						controllersPacketsCapacity[k]=problem.controllers.get(controllers[k]).processing;
					}
					//the ports capacity available for each controller so far
					int[] controllersPortsCapacity=new int[n];
					//for each controller of index controllers[k] we set its ports number
					for (int k = 0; k < controllersPortsCapacity.length; k++) {
						controllersPortsCapacity[k]=problem.controllers.get(controllers[k]).ports-(n-1);
					}
					Integer[] sortedCombination=new Integer[n];
					sortedCombination = Arrays.stream( combination ).boxed().toArray( Integer[]::new );
					boolean noSolution=false;
					//for each switch of index k
					Integer[] sortedSwitchs=problem.switchesSortedByMinimalIsolation(combination);
					for (int k = 0; k < problem.switchs.size(); k++) {
						Switch s=problem.switchs.get(sortedSwitchs[k]);
						sortedCombination=problem.positionsSortedByClosestToSwitch(sortedCombination, sortedSwitchs[k]);
						for (int k2 = 0; k2 < sortedCombination.length; k2++) {
							//check if the controller in the closest position can handle the switch
							int pos=sortedCombination[k2];
							int cont=-1;
							for (int l = 0; l < combination.length; l++) {
								if (combination[l]==pos) {
									cont = l;
									break;
								}
							}
							//check if the specified controller has enough capacity and ports to handle the switch
							if(controllersPacketsCapacity[cont]>=s.unmatchedPackets&& controllersPortsCapacity[cont]>0) {
								newSolution.positionAndLinkPerSwitch[sortedSwitchs[k]]=new Pair<Integer, Integer>(pos, problem.getMinimalLinkforSwitch(sortedSwitchs[k]));
								controllersPacketsCapacity[cont]-=s.unmatchedPackets;
								controllersPortsCapacity[cont]-=1;
								break;
							}
						}
						if(newSolution.positionAndLinkPerSwitch[sortedSwitchs[k]]==null) {
							noSolution=true;
							break;
						}
						
						
					}
					//if we didn't get blocked by an unsufficient controller capacity
					if (!noSolution) {
						// we calculate the solution with the cheapest linking possible even if invalid
						for (int k = 0; k < newSolution.controllerInPosition.length; k++) {
							for (int k2 = k+1; k2 < newSolution.controllerInPosition.length; k2++) {
								if(newSolution.controllerInPosition[k]>-1&&newSolution.controllerInPosition[k2]>-1) {
									newSolution.interControllersLinks[k][k2]=0;
								}
								
							}
							
						}
						int totalCost=newSolution.TotalCost();
						//if the new solution is cheaper then the bestSolution (or its the first one)
						if (totalCost<maxCost) {
							//check if the solution is valid
							if (newSolution.IsValidSolution()) {
								//we found the new best solution
								bestSolution=newSolution;
								maxCost=totalCost;
								
							}else {
								int maximalLink=problem.links.size()-1;
								//trying to increase link quality without overcoming the cost
								for (int k = 0; k < newSolution.positionAndLinkPerSwitch.length; k++) {
									int pos=newSolution.positionAndLinkPerSwitch[k].getKey();
									newSolution.positionAndLinkPerSwitch[k]=new Pair<Integer, Integer>(pos,maximalLink);
									totalCost=newSolution.TotalCost();
									if (totalCost>=maxCost) {
										break;
									}
									if (newSolution.IsValidSolution()) {
										bestSolution=newSolution;
										maxCost=totalCost;

									}
								}
							}
						}
					}
				}
			}
			
			boolean tryAgain=true;
			while(tryAgain) {
				tryAgain=false;
				//the cost of the initial controllers
				
				
			}
			
		}
		return bestSolution;
	}
	
	
	
	public int getCostOfControlers(int[] controllers) {
		int cost=0;
		for (int i = 0; i < controllers.length; i++) {
			cost+=problem.controllers.get(controllers[i]).cost;
		}
		return cost;
	}
	public boolean getCheaperCombination(int[] controllers,int[] inventory) {
		//check for inventory constraint
		int nonZeroIndex=-1;
		for (int i = 0; i < controllers.length; i++) {
			if (controllers[i]!=0) {
				nonZeroIndex=i;
				break;
			}
		}// 0 0 1 1 4 4
		if (nonZeroIndex==-1) {
			return false;
		}else {
			int nextCheapest=controllers[nonZeroIndex]-1;
			while ( nextCheapest>-1&&inventory[nextCheapest]<1 ) {
				nextCheapest--;
			}
			if (nextCheapest<0) {
				return false;
			}			
			inventory[controllers[nonZeroIndex]]++;
			controllers[nonZeroIndex]=nextCheapest;
			inventory[nextCheapest]--;
			if (nonZeroIndex>0) {
				while ( nextCheapest>-1&&inventory[nextCheapest]<1) {
					nextCheapest--;
				}
				if (nextCheapest<0) {
					return false;
				}
				inventory[controllers[nonZeroIndex-1]]++;
				controllers[nonZeroIndex-1]=nextCheapest;
				inventory[nextCheapest]--;
			}
			return true;
		}
		
	}

}
