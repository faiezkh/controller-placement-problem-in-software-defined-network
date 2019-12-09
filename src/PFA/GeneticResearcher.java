package PFA;


import java.util.Collection;
import java.util.function.Function;

public class GeneticResearcher<T> {


     public T Research(T init, Function<T, Collection<T>> neigborhood,Function<T,Float> score,int maxIterations,Float targetScore){

         T best=init;
         float bestScore=score.apply(init);
         int iteration=0;
         while (iteration<maxIterations && bestScore>targetScore){
        	 iteration++;
             Collection<T> neighbors=neigborhood.apply(best);
             for (T neighbor:neighbors) {
                 float nscore=score.apply(neighbor);
                 if((nscore<bestScore)) {
                     best = neighbor;
                     bestScore = nscore;
                 }
             }
         }
         return  best;
    }
}
