package roadgraph;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import java.util.Set;
import java.util.function.Consumer;

import util.GraphLoader;

/**
 * @author UCSD MOOC development team and Jan Prokop
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	//private variable mapping every node (vertex) to the Set of outgoing roads. The Set structure is used 
	//to make sure that one road will not be added twice. 
	private HashMap <GeographicPoint, Set<RoadSegment>> nodes;
	//Private variable that makes use of so called State Design Pattern
	private SearchAlgorithm algorithm;// = new BFSSearch(this);
	
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		nodes = new HashMap<GeographicPoint, Set<RoadSegment>>();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		return nodes.size();
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		return nodes.keySet();
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		int edges = 0;
		for (GeographicPoint gp : nodes.keySet())
			edges += nodes.get(gp).size();
		return edges;
	}
	
	/**
	 * Method useful for debuging and to get access to the graph structure from outside this class
	 * @return HashMap representation of the graph
	 */
	public HashMap<GeographicPoint, Set<RoadSegment>> getMap() {
		return nodes;
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		if (location == null)
			return false;
		//in order to shorten the code next method makes use of the side product of putIfAbsent method
		boolean ret = (nodes.putIfAbsent(location, new HashSet<RoadSegment>()) == null) ? true : false;
		return ret;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {
		if (!nodes.containsKey(from) || !nodes.containsKey(to) || length < 0 || from == null 
				|| to == null || roadName == null || roadType == null) {
			throw (new IllegalArgumentException());
		}
		
		nodes.get(from).add(new RoadSegment(from, to, new ArrayList<GeographicPoint>(), roadName, roadType, length));
	}
	
	
	/**This method allows user to change search algorithm (possible algorithms are bfs, 
	 * dijkstra [not implemented jet], aStar [not implemented jet]).  
	 * 
	 * @param newAlgorithm subclass of SearchAlgorithm interface. In other words - concrete implementation 
	 * of particular search algorithm.   
	 */
	public void changeAlgorithm(SearchAlgorithm newAlgorithm) {
		algorithm = newAlgorithm;
	}
	
	/** Find the path from start to goal. It uses different algorithms based on 
	 * private state variable called algorithm.
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal) or null if no route was found. 
	 */
	public List<GeographicPoint> findRout(GeographicPoint start, GeographicPoint goal) {
		return algorithm.search(start, goal);
	}
	
	/** Find the path from start to goal. It uses different algorithms based on 
	 * private state variable called algorithm.
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal) or null if no route was found. 
	 */
	public List<GeographicPoint> findRout(GeographicPoint start, GeographicPoint goal, 
			Consumer<GeographicPoint> nodeSearched) {
		return algorithm.search(start, goal, nodeSearched);
	}
	
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal, 
			Consumer<GeographicPoint> nodeSearched) {
		changeAlgorithm(new Dijkstra(this));
		return algorithm.search(start, goal, nodeSearched);
	}
	
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		return dijkstra(start, goal, m -> {});
	}
	
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal, 
			Consumer<GeographicPoint> nodeSearched) {
		changeAlgorithm(new AStar(this));
		return algorithm.search(start, goal, nodeSearched);
	}
	
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		return aStarSearch(start, goal, m -> {});
	}
	
	public static void main (String... args) {
		String a = "a";
		String b = "b";
		
		System.out.println(a.compareTo(b));
	}
	
	
}