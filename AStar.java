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
		
		//Map<GeographicPoint, Double> distances = new HashMap<GeographicPoint, Double>();
		//graph.getMap().keySet().forEach(gp -> distances.put(gp, Double.POSITIVE_INFINITY));
		
		Comparator<GeographicPoint> byWeights = new Comparator<GeographicPoint>() {
			@Override
			public int compare(GeographicPoint gp1, GeographicPoint gp2) {
				
				Double weight1 = weight(gp1, goal);//distances.get(gp1) + gp1.distance(goal);
				Double weight2 = weight(gp2, goal);//distances.get(gp2) + gp2.distance(goal);
				/*System.out.println("new compare:" +
						"\n" + gp1 + "\t" + distances.get(gp1) + "\t" + gp1.distance(goal) +
						"\n" + gp2 + "\t" + distances.get(gp2) + "\t" + gp2.distance(goal) + 
						"\nweight1: " + weight1 + "\nweight2: " + weight2 
						);*/
				return weight1.compareTo(weight2);
			}
		};
		
		Queue<GeographicPoint> queue = new PriorityQueue<GeographicPoint>(
				byWeights
				//(gp1, gp2) -> distances.get(gp1).compareTo(distances.get(gp2))
				);
		
		GeographicPoint curr = null;
		Map<GeographicPoint, GeographicPoint> parents = new HashMap<GeographicPoint, GeographicPoint>();
		Set<GeographicPoint> visited = new HashSet<GeographicPoint>();
		
		enqueue(queue, distances, start, 0.0);
		System.out.println(start + "\t" + start.distance(start));
		
		
		while(!queue.isEmpty()) {
			curr = queue.poll();
			System.out.println(curr);
			nodeSearched.accept(curr);
			if (!visited.contains(curr)) {
				visited.add(curr);
				if (curr.equals(goal)) break;
				double dist = distances.get(curr); 
				for (RoadSegment rs : graph.getMap().get(curr)) {
					GeographicPoint gp = rs.getOtherPoint(curr);
					if (!visited.contains(gp)) {
						double newDist = dist+rs.getLength();
						double weight = gp.distance(goal);
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
	
	private void enqueue(Queue<GeographicPoint> queue, Map<GeographicPoint, Double> distances, GeographicPoint gp, double newDist) {
		distances.put(gp, newDist);
		queue.add(gp);
		
	}
	
	

}
