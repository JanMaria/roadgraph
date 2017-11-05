This is (mostly) my code written as an assignment during UCSD MOOC on Coursera.org 
It is not meant to work by itself - it's just a piece of the broader application 
written by MOOC's team. The code illustrates the use of state pattern among other things.  

Class: MapGraph
Modifications made to MapGraph (what and why): 6 searching methods deleted and substituted with just 2 much shorter ones (because all 6 of them are actually the same 2 methods but using different algorithms). Private member variable of type SearchAlgorithm added (look below) – which search algorithm (bfs, Dijkstra, ASearch) will be performed depends on the concrete implementation of abstract SearchAlgorithm class. ). The whole design is based on the State Design Pattern and is actually utterly redundant if we have just one search algorithm but knowing that there will be more of them it makes future code much simpler and understandable (although probably useless with online grader).

Class name: SearchAlgorithm
Purpose and description of class: Abstract class that serves as an interface between the calling class (MapGraph) and particular searching algorithm (bfs, Dijkstra, ASearch). With that design MapGraph object doesn’t care which algorithm is used. Instead it just requests searching for the path from one point to the other. Which algorithm performs that search is based on concrete implementation of SearchAlgorithm class instead of a list of conditionals (again: see State Design Pattern).

Class name: DFSSearch
Purpose and description of class: Concrete implementation of Abstract SearchAlgorithm. It uses dfs search algorithm. 

Overall Design Justification:
State Design Pattern – makes client class (i.e. MapGraph) much simpler and it allows us to get rid of a list of conditionals which would otherwise be needed to decide which algorithm to use. It makes use of OO principles such as Information Hiding and Polymorphism. It makes code simpler to understand and easier to maintain. 

