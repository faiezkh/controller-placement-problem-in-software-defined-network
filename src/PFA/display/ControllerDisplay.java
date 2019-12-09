package PFA.display;

import java.util.List;

import PFA.Controller;
import PFA.Position;

public class ControllerDisplay {
	
	public Position position;
	
	public int cost;
	public int ports;
	public String name;
	public int processing;
	public List<SwitchLinkDisplay> linkedSwitchs;

	
	public ControllerDisplay(Position position, Controller controller,
			List<SwitchLinkDisplay> switchs) {
		super();
		this.position = position;
		this.cost = controller.cost;
		this.ports = controller.ports;
		this.name = controller.name;
		this.processing = controller.processing;
		this.linkedSwitchs = switchs;
	}

	
	
	
	

}
