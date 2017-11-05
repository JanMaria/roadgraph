package roadgraph;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;


/**Abstract superclass serving as a State interface (see: State Design Pattern) for 
 * rout searching algorithms. 
 * 
 * @author Jan Prokop
 *
 */
public abstract class SearchAlgorithm {
	// graph representation of the map
	protected MapGraph graph;
	
	public SearchAlgorithm(MapGraph mg) {
		graph = mg;
	}
	
	/**
	 * This is a standard version of a rout finding method
	 * @param start The point on the map from which to start a rout
	 * @param goal The destination point
	 * @return The list of following steps from the start to the goal inclusively. 
	 */
	public List<GeographicPoint> search(GeographicPoint start, GeographicPoint goal) {
		Consumer<GeographicPoint> temp = (x) -> {};
        return search(start, goal, temp);
	}
	
	/**
	 * This is a version of a search method that would be used for visualization by GUI. 
	 */
	public abstract List<GeographicPoint> search(GeographicPoint start, GeographicPoint goal, 
			Consumer<GeographicPoint> nodeSearched);
	
	

}
