package roadgraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import geography.GeographicPoint;
import geography.RoadSegment;

public class Node {
	private GeographicPoint loc;
	private HashMap<GeographicPoint, RoadSegment> neighbors = new HashMap<GeographicPoint, RoadSegment>();
	
	public Node(GeographicPoint newNode) {
		loc = newNode;
	}
	
	public Node(double lat, double lon) {
		loc = new GeographicPoint(lat, lon);
	}
	
	public boolean addNeighbor(RoadSegment toNeighbor) {
		if (toNeighbor == null) 
			return false;
		boolean ret = (neighbors.putIfAbsent(toNeighbor.getOtherPoint(loc), toNeighbor) == null) ? true : false;
		return ret; 
	}
	
	public List<Node> getNeighbors() {
		return new ArrayList<Node>(neighbors.keySet());
	}
	

}
