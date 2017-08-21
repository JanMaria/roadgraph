package roadgraph;

import java.util.List;
import java.util.function.Consumer;

import geography.GeographicPoint;


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
	
	public abstract List<GeographicPoint> search(GeographicPoint start, GeographicPoint goal);
	public abstract List<GeographicPoint> search(GeographicPoint start, GeographicPoint goal, 
			Consumer<GeographicPoint> nodeSearched);

}
