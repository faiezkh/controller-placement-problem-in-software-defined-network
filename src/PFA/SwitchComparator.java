package PFA;

import java.util.Comparator;

public class SwitchComparator implements Comparator<Integer>{
	
	Problem problem;
	int[] positions;
	public SwitchComparator(Problem problem, int[] positions) {
		this.problem=problem;
		this.positions=positions;
	}

	@Override
	public int compare(Integer i1, Integer i2) {
		
		
		Integer d1=Integer.MAX_VALUE,d2=Integer.MAX_VALUE;
		Switch s1=problem.switchs.get(i1),s2=problem.switchs.get(i2);
		for (int i = 0; i < positions.length; i++) {
			
			Position p=problem.positions.get(positions[i]);
			d1=Math.min(d1, (int)p.Distance(s1.position));
			d2=Math.min(d2, (int)p.Distance(s2.position));
			
		}
		return Integer.compare(d1, d2);
	}

}
