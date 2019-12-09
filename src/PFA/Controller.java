package PFA;

public class Controller {
	public int cost;
	public int ports;
	public String name;
	public int processing;

	//the number of controiller devices per category
	//remember: an instance of 'Controller' symbolize a category of controller devices
	public int numberOfControllers;
	public Controller(){

	}
	public Controller(int price, int nombre_port_physique, String type_controller,
					  int nombre_request_traitee, int nombre_instances_controlleur) {
		super();
		this.cost = price;
		this.ports = nombre_port_physique;
		this.name = type_controller;
		this.processing = nombre_request_traitee;
		this.numberOfControllers =nombre_instances_controlleur;
	}



	

}
