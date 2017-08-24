package roadgraph;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;


public class Dijkstra extends SearchAlgorithm{

	public Dijkstra(MapGraph mg) {
		super(mg);
		System.out.println("NEW GRAPH!!!!!!!!!!!!!!!");
		for (GeographicPoint gp : graph.getGraph().keySet()) {
			System.out.println (graph.getGraph().get(gp));
		}
	}

	

	@Override
	public List<GeographicPoint> search(GeographicPoint start, GeographicPoint goal,
			Consumer<GeographicPoint> nodeSearched) {
		List <GeographicPoint> path = new LinkedList<GeographicPoint>();
		
		Queue<GeographicPoint> queue = new PriorityQueue<GeographicPoint>();
		
		GeographicPoint curr = null;
		Map<GeographicPoint, GeographicPoint> parents = new HashMap<GeographicPoint, GeographicPoint>();
		Set<GeographicPoint> visited = new HashSet<GeographicPoint>();
		
		queue.add(start.setDistanceFromStart(0));
		parents.put(start, null);
		
		
		while(!queue.isEmpty()) {
			curr = queue.poll();
			if (!visited.contains(curr)) {
				visited.add(curr);
				if (curr.equals(goal)) break;
				double dist = curr.getDistanceFromStart();
				for (RoadSegment rs : graph.getGraph().get(curr)) {
					GeographicPoint gp = rs.getOtherPoint(curr);
					if (!visited.contains(gp)) {
						if (gp.getDistanceFromStart() > dist+rs.getLength()) {
							gp.setDistanceFromStart(dist+rs.getLength());
							parents.put(gp,curr);
							queue.add((GeographicPoint) gp.clone());
						}
					}
				}
			}
				
			
		}
		
		
		/*while(!queue.isEmpty()) {
			curr = queue.poll();
			if (curr.equals(goal)) break;
			visited.add(curr);
			double dist = curr.getDistanceFromStart();
			for (RoadSegment rs : graph.getGraph().get(curr)) {
				GeographicPoint gp = rs.getOtherPoint(curr);
				if (!visited.contains(gp)) {
					if (gp.getDistanceFromStart() > dist+rs.getLength()) {
						gp.setDistanceFromStart(dist+rs.getLength());
						parents.put(gp,curr);
						queue.add(gp);
					}
				}
			}
				
			
		}*/
		//this would mean that no path was found
		if (!curr.equals(goal)){
			return null;
		}
		while (curr != null){
			path.add(0, curr);
			curr = parents.get(curr);
		}
		
		return path;
	}

}
