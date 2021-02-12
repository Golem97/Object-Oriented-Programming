Nathanaël Benichou
-----------------
Tz      342769130
-----------------



Ex1 Contain 3 Implementation :
---------------------------------------------------------------------------------------------------------------------------------------

1)NodeInfo   : Implementation of node_info.

2)WGraph_Ds   : Implementation of weighted_graph.

3)WGraph_Algo : Implementation of weighted_graph_algorithms

---------------------------------------------------------------------------------------------------------------------------------------


1)NodeInfo    (Inner Class of WGraph_Ds)
-----------

Constructor :
-------------
-Key               :Int      (Type)
_Tag               :Int      (Type)
_Information       :String   (Type)
_Collection        :HashMap  (Type)


NodeInfo is an implement class of node_info who's contain :
-----------------------------------------------------------

_getKey           Complexity O(1)
_getInfo          C          O(1)
_setInfo          C          O(1)
_getTag           C          O(1)
_setTag           C          O(1)

----------------------------------------------------------

Not overrides:
--------------

_removeT       C          O(1)




We are going to explains each methods of NodeInfo :
---------------------------------------------------

_getKey     : Return the key of the asked node.    

_removeT    : Remove a node after checks if the node is contain or not on the Collection.

_getInfo    : Return the info of my NodeInfo.

_setInfo    : To set the info of my NodeInfo.

_getTag     : Return the tag of my NodeInfo.

_setTag     : To set the Tag of my NodeInfo.

_ToString   : To print my NodeInfo

---------------------------------------------------------------------------------------------------------------------------------------


2)WGraph_DS
-----------

Importations:
-------------

 java.io.Serializable;
 java.util.Collection;
 java.util.HashMap; 
 java.util.Objects;


Constructor :
-------------

_Collection  (Nodes)        : HashMap (Type)
_Collection  (Edges)        : HashMap (Type)
_mc                         : In order to know the number of changes i operated in my graph.
_edges                     : In order to know how many edges i have in my graph.

---------------------------------------------------------------------------------------------------------------------------------------
In the Copy Constructor I copy all the edges and all the neighbor i will call the Copy Constructor in the method "Copy" of 

WGraph_Algo
---------------------------------------------------------------------------------------------------------------------------------------



WGraph_DS is an implement class of graph who's contain :
--------------------------------------------------------

_equals         Complexity O(1)
_hashCode       C          O(1)
_getNode        C          O(1)
_hasEdge        C          O(1)
_getEdge        C          O(1)
_addNode        C          O(1)
_connect        C          O(1)
_getV           C          O(1)
_getV(Id)       C          O(1)
_removeNode     C          O(1)
_removeEdge     C          O(1)
_nodeSize       C          O(1)
_edgeSize       C          O(1)
_getMC          C          O(1)


We are going to explains each methods of Graph_DS :
----------------------------------------------------

_equals             : In order to check equality between two nodes

_getNode            : In order to get a node from his Key

_hasEdge            : In order to checks if there is an edge between 2 nodes (Using hasNi from NodeInfo)

_getEdge            : In order to get an edge between two nodes.

_addNode            : In order to add a new node in my graph.(Checks if the node already exist & Increment the new change)

_connect            : In order to connect two nodes (Checks for the Node 1/(2): Existe /Is null /Have already Node 2/(1) has Neighbor)

_getV               : In order to get all the values of my Hash-map.

_getV (int)         : In order to get all the Neighbors of a Node with is ID (Using).
 
_removeNode         : In order to remove a Node of my graph (Checks if the node is contain in my graph).  
                    : For each neighbors of the node removed  we remove the node of the Neighbor list. (Using removeEdge)
                    : Also Decrement the Integer edges for all edges i removed 

_removeEdge         : In order to remove the connection between two Node.
                    : Checks if the Collection Actually exist.
                    : Remove Node1 of the list of neighbor of Node2 and the same from Node2 to Node1.
                    : Decrement the edge i removed.
                    : Increment the change.

_nodeSize           : In order to know how many Node in the graph Return the size of my Collection.

_edgeSize           : In order to know how many connections there is in my graph (Return all the edges i incremented).

_getMC              : In order to know how many changes i did in my graph (Just return the counter i incremented after all changes).

---------------------------------------------------------------------------------------------------------------------------------------

3) WGraph_Algo
--------------

Importations:
-------------

 java.io.File;
 java.io.FileInputStream;
 java.io.FileOutputStream;
 java.io.IOException;
 java.io.ObjectInputStream;
 java.io.ObjectOutputStream;
 java.io.Serializable;    
 java.util.*;




Constructor :
-------------
 
_weighted_graph 
 
_Collection       : HashSet (Type)


WGraph_Algo is an implement class of weighted_graph_algorithm who's contain :
-----------------------------------------------------------------------------

_compare             Complexity    ----
_init                C             ----
_getGraph            C             ----
_copy                C             ----
_isConnected         C             ---- 
_shortestPath        C             ----
_shortestPathDist    C             ----
_save                C             ---- 
_load                C             ----



Methodes not Overreedde :
------------------------

_BreadthFirstSearch  
_chek_Visit
_InitTag
_Dijkstra
_Djikstra_graphNi




We are going to explains each methods of WGraph_Algo :
-------------------------------------------------------


_init                : In order to Init the graph on which this set of algorithms operates on.

_copy                : Creat a Deep copy of my graph , each Node and each neighbor of them
                     : Using the copy constructor of WGraphDS
                     : The copy constructor of WGraphDs copy all nodes one by one the pointer in the memory will not be the same that the nodes copied
                     : The copy constructor of WGraphDs copy all edges one by one the pointer in the memory will not be the same that the edge copied
                     

_isConnected         : Checks if the Graph is connected => If Each Node can be reach from any Node of My graph 
                     : Checking all the path possible using The BFS Algorithm 
                     : BFS Use Check_Visite In order to verify if all the node of my graph was Visited 
                     : Using the Tag of Each Node if there is a Node with a Tag (0) So the node wasn't visited and the graph is not    : connnected

_shortestPath        : In order to know the shortest Path between two Node (Using Dijkstra)  
  
  _Dijkstra          : Declaration of a priority Queue  (Using the Comparator)
                     : First Initialise all the Tag to Infinity using InitTag
                     : Clear the visited list
                     : Add source node to my PQueue 
                     : Initialize distance from node to itself to 0
                     : Set Info of node to source
                     : While the PQueue is not empty
                     : Node u take the value of The node i removed from PQueue 
                     : Add u node to Visited List
                     : Calling Djikstra_graphNi giving Node u , Pqueue , Graph
     
   _InitTag          : Initialize all tag to infinit       

   _Djikstra_graphNi : Pass to all neighbor of u
                     : Take Value of the current node
                     : If the current node is not 'visited'
                     : Compare Distance and initialize Comparing Distance 
                     : Adding the current node into the visited List


_shortestPathDist    : Return the List of Shortestpath  (Calling Dijkstra)
                     : Using String (Split) To get all the value of my Shortest Path 

-save                : To Serialize the object  (Turn to octet the object)
 
_load                : To Copy My object Graph (Serialized) into a chosen File 


---------------------------------------------------------------------------------------------------------------------------------------


-----------------------------------------------------------------END README------------------------------------------------------------








