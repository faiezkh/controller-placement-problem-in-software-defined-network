import java.io.File;
import java.io.PrintWriter;

import org.codehaus.jackson.map.ObjectMapper;

import PFA.Problem;
import PFA.SimpleSolver;
import PFA.Solution;
import PFA.Solver;
import PFA.display.SolutionDisplay;

public class ProblemSolver {

	public static void main(String[] args) {
		Problem problem=new Problem();
        ObjectMapper mapper = new ObjectMapper();
        try {
            problem = mapper.readValue(new File("problem1.json"), Problem.class);
            //testing the result

        }catch (Exception e){
            System.out.print(e.getMessage());
            System.out.print("error reading values from file");
        }
		Solver solver =new  SimpleSolver(problem);
		Solution solution=solver.Solve(problem);
        SolutionDisplay sd=new SolutionDisplay(solution);

        try {
            String solutionString=mapper.writeValueAsString(sd);
            System.out.print(solutionString);
            PrintWriter out = new PrintWriter("solution1.json");
            out.println(solutionString);
            out.close();


        }catch (Exception e){
            System.out.print(e.getMessage());

        }
		

	}

}
