package roadgraph;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
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
	/*public List<GeographicPoint> findRout(GeographicPoint start, GeographicPoint goal) {
		return algorithm.search(start, goal);
	}*/
	
	/** Find the path from start to goal. It uses different algorithms based on 
	 * private state variable called algorithm.
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal) or null if no route was found. 
	 */
	/*public List<GeographicPoint> findRout(GeographicPoint start, GeographicPoint goal, 
			Consumer<GeographicPoint> nodeSearched) {
		return algorithm.search(start, goal, nodeSearched);
	}*/
	
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
		/*MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		
		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
		
		
		MapGraph testMap = new MapGraph();
		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		
		// A very simple test using real data
		testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		// A slightly more complex test using real data
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);*/
		
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);

		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);
	}
	
	
	
}