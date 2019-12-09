package PFA;



public abstract class Solver {

	public  Problem problem;
	public  Solver(Problem problem){
		this.problem=problem;
	}


	abstract  public Solution Solve(Problem problem);

}
