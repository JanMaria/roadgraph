package roadgraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import geography.*;

/**This is the implementation of the Dijkstra Search Algorithm
 * that guarantee to find the shortest rout from one location
 * on the map to another.  
 * 
 * @author Jan Prokop
 *
 */
public class Dijkstra extends SearchAlgorithm{

	public Dijkstra(MapGraph mg) {
		super(mg);
	}

	

	@Override
	public List<GeographicPoint> search(GeographicPoint start, GeographicPoint goal,
			Consumer<GeographicPoint> nodeSearched) {
		
		List <GeographicPoint> path = new LinkedList<GeographicPoint>();
		
		Map<GeographicPoint, Double> distances = new HashMap<GeographicPoint, Double>();
		graph.getMap().keySet().forEach(gp -> distances.put(gp, Double.POSITIVE_INFINITY));
		
		Queue<GeographicPoint> queue = new PriorityQueue<GeographicPoint>(
				(gp1, gp2) -> distances.get(gp1).compareTo(distances.get(gp2))
				);
		
		GeographicPoint curr = null;
		Map<GeographicPoint, GeographicPoint> parents = new HashMap<GeographicPoint, GeographicPoint>();
		Set<GeographicPoint> visited = new HashSet<GeographicPoint>();
		
		enqueue(queue, distances, start, 0.0);
		
		while(!queue.isEmpty()) {
			curr = queue.poll();
			nodeSearched.accept(curr);
			if (!visited.contains(curr)) {
				visited.add(curr);
				if (curr.equals(goal)) break;
				double dist = distances.get(curr); 
				for (RoadSegment rs : graph.getMap().get(curr)) {
					GeographicPoint gp = rs.getOtherPoint(curr);
					if (!visited.contains(gp)) {
						double newDist = dist+rs.getLength();
						if (distances.get(gp) > (dist + rs.getLength())) {
							enqueue(queue, distances, gp, newDist);
							parents.put(gp,curr);
						}
					}
				}
			}
		}
		
		//this would mean that no path was found
		if (!curr.equals(goal)){
			return null;
		}
		while (curr != start){
			path.add(0, curr);
			curr = parents.get(curr);
		} path.add(curr);
		
		return path;
	}
	
	//private helper method
	private void enqueue(Queue<GeographicPoint> queue, Map<GeographicPoint, Double> distances, GeographicPoint gp, double newDist) {
		queue.add(gp);
		distances.put(gp, newDist);
	}
	

}
