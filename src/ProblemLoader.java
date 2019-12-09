import PFA.*;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ProblemLoader {



    public static void main(String[] args){
        Problem problem=new Problem();
        ObjectMapper mapper = new ObjectMapper();
        try {
            problem = mapper.readValue(new File("problem.json"), Problem.class);
            //testing the result

        }catch (Exception e){
            System.out.print(e.getMessage());
            System.out.print("error reading values from file");
        }
        ObjectMapper mapperObj = new ObjectMapper();
        try {
            String problemString=mapperObj.writeValueAsString(problem);
            System.out.print(problemString);

        }catch (Exception e){
            System.out.print("error writing values to object values from file");

        }



    }

}
