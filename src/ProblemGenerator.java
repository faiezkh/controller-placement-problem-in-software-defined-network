import PFA.*;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.PrintWriter;
import java.util.ArrayList;

public class ProblemGenerator {



    public static void main(String[] args){
        Problem problem=new Problem();
        Controller c=new Controller(1200,3,"Class A",24 ,20);
        Switch s=new Switch();
        s.unmatchedPackets=4;
        s.position =new Position(1,1);
        Position p=new Position(2,2);
        Link l=new Link(100,1,210000000);
        problem.switchs =new ArrayList<>();
        problem.controllers =new ArrayList<>();
        problem.positions =new ArrayList<>();
        problem.links =new ArrayList<>();
        problem.switchs.add(s);
        problem.positions.add(p);
        problem.controllers.add(c);
        problem.links.add(l);


        ObjectMapper mapperObj = new ObjectMapper();
        try {
            String problemString=mapperObj.writeValueAsString(problem);
            //System.out.print(problemString);
            PrintWriter out = new PrintWriter("problem.json");
            out.println(problemString);
            out.close();


        }catch (Exception e){

        }



    }

}
