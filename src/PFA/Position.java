package PFA;

public class Position {

	public int x;
	public int y;
	public Position(){

	}
	public Position(int abscisse, int ordonne) {
		super();
		this.x = abscisse;
		this.y = ordonne;
	}

	public float Distance(Position B){
		return (float)Math.sqrt(Math.pow(this.x -B.x,2)+Math.pow(this.y -B.y,2));
	}




}
