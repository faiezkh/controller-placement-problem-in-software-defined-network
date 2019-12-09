package PFA.display;

import java.util.ArrayList;
import java.util.List;

import PFA.Controller;
import PFA.Link;
import PFA.Position;
import PFA.Solution;
import PFA.Switch;
import javafx.util.Pair;

public class SolutionDisplay {
	
	public List<ControllerDisplay> controllers;
	
	public SolutionDisplay(Solution solution) {
		controllers=new ArrayList<ControllerDisplay>();
		
		for (int i = 0; i < solution.controllerInPosition.length; i++) {
			int controllerIndex=solution.controllerInPosition[i];
			if (controllerIndex>-1) {
				Position p=solution.problem.positions.get(i);
				Controller controller=solution.problem.controllers.get(controllerIndex);
				List<SwitchLinkDisplay> switchsAndLinks=new ArrayList<SwitchLinkDisplay>();
				for (int j = 0; j < solution.positionAndLinkPerSwitch.length; j++) {
					Pair<Integer,Integer> pair=solution.positionAndLinkPerSwitch[j];
					if (pair.getKey()==i) {
						Switch s=solution.problem.switchs.get(j);
						Link l=solution.problem.links.get(pair.getValue());
						switchsAndLinks.add(new SwitchLinkDisplay(s, l));
					}
				}
				controllers.add(new ControllerDisplay(p, controller, switchsAndLinks));
			}
		}
	}

}
