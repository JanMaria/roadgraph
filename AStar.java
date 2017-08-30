package roadgraph;

import java.util.List;
import java.util.function.Consumer;

public class AStar extends SearchAlgorithm {

	public AStar(MapGraph mg) {
		super(mg);
	}

	@Override
	public List<GeographicPoint> search(GeographicPoint start, GeographicPoint goal,
			Consumer<GeographicPoint> nodeSearched) {
		
		return null;
	}
	
	

}
