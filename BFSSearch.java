package roadgraph;

public class BFSSearch extends SearchAlgorithm{
	
	private BFSSearch(MapGraph mg) {
		algorithm = this;
		graph = mg;
	}
	
	
	/*public final void makeInstance() {
		chooseAlgorithm();
	}*/
	
	public static void chooseAlgorithm(MapGraph mg) {
		new BFSSearch(mg);
	}

}
