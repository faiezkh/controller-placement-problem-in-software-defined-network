package PFA;

import java.util.ArrayList;
import java.util.List;

public class Combination {
	 
	public static List<int[]>  getCombinations(int[]  input, int k){
	 
	
		List<int[]> subsets = new ArrayList<>();
	
		int[] s = new int[k];                  // here we'll keep indices 
		                                       // pointing to elements in input array
	
		if (k <= input.length) {
		    // first index sequence: 0, 1, 2, ...
		    for (int i = 0; (s[i] = i) < k - 1; i++);  
		    subsets.add(s);
		    for(;;) {
		        int i;
		        // find position of item that can be incremented
		        for (i = k - 1; i >= 0 && s[i] == input.length - k + i; i--); 
		        if (i < 0) {
		            break;
		        }
		        s[i]++;                    // increment this item
		        for (++i; i < k; i++) {    // fill up remaining items
		            s[i] = s[i - 1] + 1; 
		        }
		        subsets.add(s);
		    }
		}
		return subsets;
	}
	public static List<int[]>  getAllSortedSequencesWithLimits(int min, int max, int length,int[] limits,int[] sequence,int i){
		if (i==0) {
			sequence=new int[length];
		}
		List<int[]> result=new ArrayList<int[]>();
		if(i==length-1) {
			for (int j = min; j < max; j++) {
				if(limits[j]>0) {
					int[] rj=sequence.clone();
					rj[i]=j;
					result.add(rj);
				}
			}
			
		}else {
			for (int j = min; j < max; j++) {
				if(limits[j]>0) {
					int[] rj=sequence.clone();
					int[] lj=limits.clone();
					rj[i]=j;
					lj[j]-=1;
					result.addAll(getAllSortedSequencesWithLimits(j, max, length,lj, rj, i+1));
				}
				
			}
		}
		return result;
	}
	

	
}