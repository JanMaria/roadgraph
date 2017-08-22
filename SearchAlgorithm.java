package roadgraph;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;


/**Abstract superclass serving as a State interface (see State Design Pattern). 
 * 
 * @author Jan Prokop
 *
 */
public abstract class SearchAlgorithm {
	protected MapGraph graph;
	
	public SearchAlgorithm(MapGraph mg) {
		graph = mg;
	}
	
	public List<GeographicPoint> search(GeographicPoint start, GeographicPoint goal) {
		Consumer<GeographicPoint> temp = (x) -> {};
        return search(start, goal, temp);
	}
	
	public abstract List<GeographicPoint> search(GeographicPoint start, GeographicPoint goal, 
			Consumer<GeographicPoint> nodeSearched);
	
	

}
