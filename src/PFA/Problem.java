package PFA;


import java.util.ArrayList;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;
import java.lang.*; 
import java.io.*; 

//import com.sun.org.apache.xpath.internal.functions.FuncBoolean;

public class Problem {


    public  ArrayList<Switch> switchs;
    public  ArrayList<Controller> controllers;
    public  ArrayList<Link> links;
    public  ArrayList<Position> positions;

    public int packetSize;
    public int maxLatency;//in milli seconds
    public int averageControllerProcessing;//in milli seconds
    public int propagationVelocity;
   // public static Problem me;
    public Problem(){}
    
    
    public int getClosestPositionToSwitch(int switchIndex) {
    	Switch s=switchs.get(switchIndex);
		int closestPositionIndex=0;
		Position closestPosition=positions.get(closestPositionIndex);
		for (int j = 0; j < positions.size(); j++) {
			Position newCondidat=positions.get(j);
			float distance1=s.position.Distance(closestPosition);
			float distance2=s.position.Distance(newCondidat);
			if(distance2<distance1) {
				closestPositionIndex=j;
				closestPosition=newCondidat;
			}
		}
		return closestPositionIndex;
    }

    
    public int[] inventory() {
    	int[] inv=new int[controllers.size()];
		for (int j = 0; j < inv.length; j++) {
			inv[j]=controllers.get(j).numberOfControllers;
		}
		return inv;
    }
    public int nbrOfControllers() {
    	int[] inventory=inventory();
    	int nbrControllers=0;
    	for (int i = 0; i < inventory.length; i++) {
			nbrControllers+=inventory[i];
		}
    	return nbrControllers;
    }
    
    public Integer[] positionsSortedByClosestToSwitch(Integer[] positions,int switchIndex) {
    	Comparator<Integer> comp=new PositionComparator(this, switchs.get(switchIndex).position);
    	Integer[] sortedPosition=positions.clone();
    	Arrays.sort(sortedPosition, comp);
    	return sortedPosition;
    }
    public Integer[] switchesSortedByMinimalIsolation(int[] positions) {
    	
    	Comparator<Integer> comp=new SwitchComparator(this,positions);
    	Integer[] sortedSwitchs=getFirstSequence(switchs.size());
    	Arrays.sort(sortedSwitchs, comp);
    	return sortedSwitchs;
    }
    public static Integer[] getFirstSequence(int n) {
    	Integer[] sequence=new Integer[n];
    	for (int i = 0; i < sequence.length; i++) {
			sequence[i]=i;
			
		}
    	return sequence;
    }
    
    
    
    public int[] closestPositionPerSwichs() {
    	int[] closestpositionPerSwitch=new int[switchs.size()];
    	for (int i = 0; i < closestpositionPerSwitch.length; i++) {
			closestpositionPerSwitch[i]=getClosestPositionToSwitch(i);
		}
    	return closestpositionPerSwitch;
    }
    public int getMinimalLinkforSwitch(int switchIndex) {
    	int minimalLink=0;
    	Switch s=switchs.get(switchIndex);
    	Link link=links.get(minimalLink);
    	while(s.unmatchedPackets*packetSize> Solution.MbpsToBytes(link.bandwidth)) {
    		minimalLink++;
    		link=links.get(minimalLink);
    	}
    	return minimalLink;
    }
    
    
    public int cheapestControllerWithMinimalProcessing(int minimalProcessing) {
    	int cheapestControllerIndex=0;
    	Controller cheapestController=controllers.get(cheapestControllerIndex);
    	for (int i = 0; i < controllers.size(); i++) {
    		Controller c=controllers.get(i);
    		if(c.processing>=minimalProcessing) {
    			if (c.cost<cheapestController.cost) {
    				cheapestControllerIndex=i;
    				cheapestController=c;
					
				}
    		}
			
		}
    	if ((cheapestController).processing>=minimalProcessing) {
        	return cheapestControllerIndex;

		}else {
			return -1;
		}
    	
    	
    }
    

}
