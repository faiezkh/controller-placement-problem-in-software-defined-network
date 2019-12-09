package PFA;

public class Link {
	public int bandwidth;
	public float costPerMeter;
	public int type;
	public int propagationSpeed;
	public Link(){

	}
	public Link(int bande_passante, float prix_metre,int propagationSpeed) {
		super();
		this.bandwidth = bande_passante;
		this.costPerMeter = prix_metre;
		this.propagationSpeed = propagationSpeed;
	}

	public  int Cost(Position A, Position B){
		float distance=A.Distance(B);
		return (int)(distance* costPerMeter);

	}


}
