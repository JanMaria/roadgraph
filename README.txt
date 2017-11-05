This is (mostly) my code written as an assignment during UCSD MOOC on Coursera. It is not meant to work by itself - it's just a piece of the broader application written by MOOC's team. The code illustrates the use of state pattern among other things.  

Class: MapGraph
This class represents a graph of geographic locations which are intersections between roads. Importantly it implements the methods to search for routs between two chosen locations. 

Class: SearchAlgorithm
Abstract class that serves as an interface between the calling class (MapGraph) and particular searching algorithm (bfs, Dijkstra, A*). With that design MapGraph object doesn’t care which algorithm is used. Instead it just requests searching for the path from one point to the other. Which algorithm performs that search is based on concrete implementation of SearchAlgorithm class instead of a list of conditionals (again: State Design Pattern).

BFSSearch, Dijkstra, AStar
They all extend SearchAlgorithm. They implement different searching algorithms (breadth first search, dijkstra's and A* respectively). 
