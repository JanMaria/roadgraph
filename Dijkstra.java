package roadgraph;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.function.Consumer;


public class Dijkstra extends SearchAlgorithm{

	public Dijkstra(MapGraph mg) {
		super(mg);
		System.out.println("________________________\nnewDijkstra\n______________________");
	}

	

	@Override
	public List<GeographicPoint> search(GeographicPoint start, GeographicPoint goal,
			Consumer<GeographicPoint> nodeSearched) {
		List <GeographicPoint> path = new LinkedList<GeographicPoint>();
		
		Queue<GeographicPoint> queue = new PriorityQueue<GeographicPoint>();
		
		GeographicPoint curr = null;
		HashMap<GeographicPoint, GeographicPoint> visitedParents = new HashMap<GeographicPoint, GeographicPoint>();
		
		queue.add(start.setDistanceFromStart(0));
		//visitedParents.put(start, null);
		
		while(!queue.isEmpty()) {
			visitedParents.put(queue.peek(), curr);
			curr = queue.poll();
			if (curr.equals(goal)) break;
//?????????????nodeSearched.accept(curr);
			//for (GeographicPoint gp : getNeighbors(curr))
			
			for (RoadSegment rs : graph.getGraph().get(curr)) {
				GeographicPoint gp = rs.getOtherPoint(curr); 
				if (!visitedParents.keySet().contains(gp)) {
					queue.add(gp.setDistanceFromStart(curr.getDistanceFromStart() + rs.getLength()));
					//System.out.println(curr + "\t" + curr.getDistanceFromStart() + "\t" + gp + "\t" + gp.getDistanceFromStart());
					//visitedParents.put(gp, curr);
				}
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

}
