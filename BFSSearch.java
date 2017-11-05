package roadgraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import geography.*;



/**This class is an implementation of the Breadth First Search algorithm. 
 * It finds the rout from one point on the map to another with the 
 * minimal number of steps (nodes). It doesn't guarantee to find the shortest path.  
 * 
 * @author Jan Prokop
 *
 */
public class BFSSearch extends SearchAlgorithm{
	
	
	public BFSSearch(MapGraph mg) {
		super(mg);
	}
	
	@Override
	public List<GeographicPoint> search(GeographicPoint start, GeographicPoint goal,
			Consumer<GeographicPoint> nodeSearched) {
		List <GeographicPoint> path = new LinkedList<GeographicPoint>();
		LinkedList<GeographicPoint> queue = new LinkedList<GeographicPoint>();
		GeographicPoint curr = null;
		HashMap<GeographicPoint, GeographicPoint> visitedParents = new HashMap<GeographicPoint, GeographicPoint>();
		queue.add(start);
		visitedParents.put(start, null);
		
		while(!queue.isEmpty()) {
			curr = queue.poll();
			if (curr.equals(goal)) break;
			nodeSearched.accept(curr);
			for (GeographicPoint gp : getNeighbors(curr)) 
				if (!visitedParents.keySet().contains(gp)) {
					queue.add(gp);
					visitedParents.put(gp, curr);
				}
		}
		//this would mean that no path was found
		if (!curr.equals(goal)){
			return null;
		}
		while (curr != null){
			path.add(0, curr);
			curr = visitedParents.get(curr);
		}
		
		return path;
	}
	
	/** Helper function: finds the locations you can directly go to from provided location. 
	 * Return value is Set in order to assure (for simplicity) that there will be no 
	 *  
	 * @param node location you provide
	 * @return HashSet of nodes with direct (one step) access from provided location
	 */
	private Set<GeographicPoint> getNeighbors(GeographicPoint node) {
		HashSet<GeographicPoint> neighbors = new HashSet<GeographicPoint>();
		for (RoadSegment rs : graph.getMap().get(node)) {
			neighbors.add(rs.getOtherPoint(node));
		}
		return neighbors;
	}

}
