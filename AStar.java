package roadgraph;

import java.util.Comparator;
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

/** Implementation of the A* search algorithm. This is an algorithm to find the shortest 
 * rout on a map from one point to another. The advantage of this algorithm over more 
 * traditional ones is that it doesn't search in every direction from the starting point
 * until it finds the destination point, but instead tries to keep the desirable direction. 
 * It makes this algorithm a relatively fast one. 
 * 
 * @author Jan Prokop
 *
 */
public class AStar extends SearchAlgorithm {
	
	private Map<GeographicPoint, Double> distances = new HashMap<GeographicPoint, Double>();

	public AStar(MapGraph mg) {
		super(mg);
		graph.getMap().keySet().forEach(gp -> distances.put(gp, Double.POSITIVE_INFINITY));
	}
	
	private Double weight (GeographicPoint gp, GeographicPoint goal) {
		return distances.get(gp) + gp.distance(goal);
	}

	@Override
	public List<GeographicPoint> search(GeographicPoint start, GeographicPoint goal,
			Consumer<GeographicPoint> nodeSearched) {
		
		List <GeographicPoint> path = new LinkedList<GeographicPoint>();
		
		Comparator<GeographicPoint> byWeights = new Comparator<GeographicPoint>() {
			@Override
			public int compare(GeographicPoint gp1, GeographicPoint gp2) {
				
				Double weight1 = weight(gp1, goal);
				Double weight2 = weight(gp2, goal);
				
				return weight1.compareTo(weight2);
			}
		};
		
		Queue<GeographicPoint> queue = new PriorityQueue<GeographicPoint>(byWeights);
		
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
						if (distances.get(gp) > (newDist)) {
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
		} path.add(0, curr);
		
		return path;
	}
	
	//Private helper function used to enqueue a node into priority queue
	private void enqueue(
			Queue<GeographicPoint> queue, Map<GeographicPoint, Double> distances, 
			GeographicPoint gp, double newDist
			) {
		distances.put(gp, newDist);
		queue.add(gp);
	}
	
	

}
