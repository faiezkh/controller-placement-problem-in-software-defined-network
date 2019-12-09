package PFA;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javafx.print.Collation;
import javafx.util.Pair;

public class Solution {




    public int[] controllerInPosition;
    public Pair<Integer,Integer>[] positionAndLinkPerSwitch;
    public int[][] interControllersLinks;

    public Problem problem;

     int dim_Switchs;
     int dim_Controllers;
     int dim_Links;
    int dim_Positions;

    public Solution(Problem problem){
        this.problem =problem;
        this.dim_Switchs =problem.switchs.size();
        this.dim_Controllers =problem.controllers.size();
        this.dim_Links =problem.links.size();
        this.dim_Positions =problem.positions.size();
        controllerInPosition =new int[dim_Positions];
        positionAndLinkPerSwitch =new Pair[dim_Switchs];
        interControllersLinks =new int[dim_Positions][dim_Positions];
        
        for (int i = 0; i < controllerInPosition.length; i++) {
			controllerInPosition[i]=-1;
		}
        for (int i = 0; i < dim_Positions; i++) {
			for (int j = i+1; j < dim_Positions; j++) {
				interControllersLinks[i][j]=-1;
			}
		}
    }
    
    
    
    public List<Integer>[] getSwitchsPerPosition() {
    	List<Integer>[] switchsPerPosition=new List[dim_Positions];
    	for (int i = 0; i < switchsPerPosition.length; i++) {
			switchsPerPosition[i]=new ArrayList<Integer>();
			
		}
    	for (int i = 0; i < positionAndLinkPerSwitch.length; i++) {
			int positionIndex =positionAndLinkPerSwitch[i].getKey();
			switchsPerPosition[positionIndex].add(i);
		}
    	return switchsPerPosition;
   }
    public int getControllersCount() {
    	int count=0;
    	for (int i = 0; i < controllerInPosition.length; i++) {
			int controllerIndex =controllerInPosition[i];
			if(controllerIndex>-1) {
				count++;
			}
		}
    	return count;
    }

    private int ControllersCost(){
        int Cc=0;
        for (int i = 0; i< dim_Positions; ++i){
            if(controllerInPosition[i]>-1){
                int C_index= controllerInPosition[i];
                Controller controller= problem.controllers.get(C_index);
                Cc+=controller.cost;
            }
        }
		return Cc;
    }
    
    private int InterControllersLinksCost() {
    	int Ct=0;
    	for(int i = 0; i< dim_Positions; ++i) {
    		for(int j = i+1; j< dim_Positions; ++j) {
    			if(interControllersLinks[i][j]>-1) {
    				int L_index= interControllersLinks[i][j];
    			    Link liaison= problem.links.get(L_index);
    			    Ct+=liaison.Cost(problem.positions.get(i), problem.positions.get(j));
    		}
    	}
    }
		return Ct;
 }
    private int SwitchControllerLinkingCost() {
    	int Cl=0;
    	for(int i = 0; i< dim_Switchs; ++i) {
            Pair<Integer,Integer> pair= positionAndLinkPerSwitch[i];
            int L_index=pair.getValue();
            int controller_index=pair.getKey();
            Link liaison= problem.links.get(L_index);
            Cl+=liaison.Cost(problem.switchs.get(i).position, problem.positions.get(controller_index));

    	}
		return Cl;
    }
    public  int TotalCost(){
        return ControllersCost()+InterControllersLinksCost()+SwitchControllerLinkingCost();
    }
    public boolean PortsConstraint(){
        for (int i = 0; i< dim_Positions; ++i){
            int links=0;
            int controllerIndex= controllerInPosition[i];
            if (controllerIndex>-1) {
                Controller controller = problem.controllers.get(controllerIndex);
                for (int j = 0; j < dim_Positions; j++) {
                    if (interControllersLinks[i][j] > -1) {
                        links++;
                    }
                }
                for (int k = 0; k < dim_Switchs; k++) {
                    int controllerIndex2= positionAndLinkPerSwitch[k].getKey();
                    if (controllerIndex==controllerIndex2){
                        links++;
                    }
                }
                int count=getControllersCount();
                //to correct...
                if (links +count-1> controller.ports) {
                    return false;
                }
            }
        }
        return true;
    }
    public boolean SwitchLinkUnicity(){
        for (int i = 0; i < dim_Switchs; i++) {
            if (positionAndLinkPerSwitch[i]==null){
                return false;
            }
        }
        return true;
    }

    public boolean ControllerProcess(){
        for (int i = 0; i < dim_Positions; i++) {
            int controllerIndex= controllerInPosition[i];
            if(controllerIndex>-1){
                Controller controller=problem.controllers.get(controllerIndex);//this is the mistake
                int unwantedSwitchLookups=0;
                for (int k = 0; k < dim_Switchs; k++) {
                    if(positionAndLinkPerSwitch[k].getKey()==i){
                        unwantedSwitchLookups+=problem.switchs.get(k).unmatchedPackets;
                    }
                }
                if (controller.processing <unwantedSwitchLookups){
                    return false;
                }
            }

        }
        return true;
    }
    public boolean InventoryConstraint() {
    	int[] controlersUsedPerCategory=new int[dim_Controllers];
    	for (int i = 0; i < controlersUsedPerCategory.length; i++) {
			controlersUsedPerCategory[i]=0;
		}
    	for (int j = 0; j < controllerInPosition.length; j++) {
			if(controllerInPosition[j]>-1) {
				int controllerIndex=controllerInPosition[j];
				controlersUsedPerCategory[controllerIndex]++;
			}
		}
    	for (int i = 0; i < controlersUsedPerCategory.length; i++) {
			if(controlersUsedPerCategory[i]>problem.controllers.get(i).numberOfControllers)
				return false;
		}
    	return true;
    }
    

    public boolean LinkBandwith(){
        for (int i = 0; i < dim_Switchs; i++) {
            Switch s=problem.switchs.get(i);
            Link l=problem.links.get(positionAndLinkPerSwitch[i].getValue());
            if (PacketsToBytes(s.unmatchedPackets)> MbpsToBytes(l.bandwidth)){
                return false;
            }

        }
        return true;

    }
    //to verify
    public boolean TransitionDelay(){

        int tran=0,prop=0,proc=0;
        for (int i = 0; i < dim_Switchs; i++) {
            Switch s=problem.switchs.get(i);
            int pIndex=(positionAndLinkPerSwitch[i].getKey());
            Position p=problem.positions.get(pIndex);
            Controller c=problem.controllers.get(controllerInPosition[pIndex]);
            Link l=problem.links.get(positionAndLinkPerSwitch[i].getValue());
            tran+= PacketsToBytes(s.unmatchedPackets)*1000/ MbpsToBytes(l.bandwidth);
            prop+=p.Distance(s.position)*1000/(l.propagationSpeed);
        }
        for (int i = 0; i < dim_Positions; i++) {
            int cIndex= controllerInPosition[i];
            if (cIndex>-1){
                Controller c=problem.controllers.get(cIndex);
                proc+=problem.averageControllerProcessing;

            }
        }
        return (2*tran+2*prop+proc<=problem.maxLatency);
    }
    
    
    public boolean InterControllerTopology(){
        for (int i = 0; i < dim_Positions; i++) {
            for (int j = i+1; j < dim_Positions; j++) {
                if (controllerInPosition[i]>-1&& controllerInPosition[j]>-1){
                    if (interControllersLinks[i][j]==-1){
                        return false;
                    }
                }

            }

        }
        return true;
    }
    public boolean IsValidSolution() {
    	return ControllerProcess()&&InterControllerTopology()&&InventoryConstraint()&&LinkBandwith()&&PortsConstraint()&&SwitchLinkUnicity()&&TransitionDelay();
    }

    public  int PacketsToBytes(int a){
        return a*problem.packetSize;
    }
    public static int MbpsToBytes(int a){
        return a*(1024*1024);
    }

}
    
    
    
