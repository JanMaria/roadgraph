package roadgraph;

import java.awt.geom.Point2D.Double;


@SuppressWarnings("serial")
public class GeographicPoint extends Double implements Comparable<GeographicPoint>{
	private double distanceFromStart = java.lang.Double.POSITIVE_INFINITY;
	
	public GeographicPoint(double latitude, double longitude)
	{
		super(latitude, longitude);
	}
	
	/**
	 * Calculates the geographic distance in km between this point and 
	 * the other point. 
	 * @param other
	 * @return The distance between this lat, lon point and the other point
	 */
	public double distance(GeographicPoint other)
	{
		return getDist(this.getX(), this.getY(),
                other.getX(), other.getY());     
	}
	
    
    private double getDist(double lat1, double lon1, double lat2, double lon2)
    {
    	int R = 6373; // radius of the earth in kilometres
    	double lat1rad = Math.toRadians(lat1);
    	double lat2rad = Math.toRadians(lat2);
    	double deltaLat = Math.toRadians(lat2-lat1);
    	double deltaLon = Math.toRadians(lon2-lon1);

    	double a = Math.sin(deltaLat/2) * Math.sin(deltaLat/2) +
    	        Math.cos(lat1rad) * Math.cos(lat2rad) *
    	        Math.sin(deltaLon/2) * Math.sin(deltaLon/2);
    	double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

    	double d = R * c;
    	return d;
    }
    
    private void setDistance(double dist) {
    	distanceFromStart = dist;
    }
    
    /** this method should allow to use call to that method as an argument 
     * to other function (see: Dijkstra.search(...))
     */
    public GeographicPoint setDistanceFromStart(double dist) {
    	setDistance(dist);
    	return this;
    }
    
    public double getDistanceFromStart() {
    	return distanceFromStart;
    }
    
    public String toString()
    {
    	return "Lat: " + getX() + ", Lon: " + getY();
    }

	@Override
	public int compareTo(GeographicPoint other) {
		return (int) Math.round(this.distanceFromStart - other.getDistanceFromStart());
	}
	
	
}

