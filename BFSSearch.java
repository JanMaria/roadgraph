package roadgraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
import geography.RoadSegment;

/**Exemplification of concrete implementation of search algorithm. It uses bfs search algorithm.
 * 
 * @author Jan Prokop
 *
 */
public class BFSSearch extends SearchAlgorithm{
	
	
	public BFSSearch(MapGraph mg) {
		super(mg);
	}
	
	//bfs version of search algorithm without a consumer
	@Override
	public List<GeographicPoint> search(GeographicPoint start, GeographicPoint goal) {
		Consumer<GeographicPoint> temp = (x) -> {};
        return search(start, goal, temp);
		
	}
	
	//bfs version of search algorithm with a consumer
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
		for (RoadSegment rs : graph.getGraph().get(node)) {
			neighbors.add(rs.getOtherPoint(node));
		}
		return neighbors;
	}
	
	/*private BFSSearch(MapGraph mg) {
		algorithm = this;
		graph = mg;
	}
	
	
	public final void makeInstance() {
		chooseAlgorithm();
	}
	
	public static void chooseAlgorithm(MapGraph mg) {
		new BFSSearch(mg);
	}*/

}
