package PFA;

import java.util.Comparator;

public class PositionComparator implements Comparator<Integer> {
	Problem problem;
	Position position;
	
	public PositionComparator(Problem problem, Position position) {
		this.problem=problem;
		this.position=position;
	}

	@Override
	public int compare(Integer a, Integer b) {
		Position pa=problem.positions.get(a);
		Position pb=problem.positions.get(b);
		return (int)(pa.Distance(position)-pb.Distance(position));
	}

}
