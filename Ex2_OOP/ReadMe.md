Nathanael Benichou    &   Jordan Perez
--------------------------------------
Tz   :   342769130    &      336165733
--------------------------------------


Parti 1)
----------------------------------------------------------------------------------------------------------------------------------------


Ex2 Contains 5 Implementations :
----------------------------------------------------------------------------------------------------------------------------------------
1)NodeData     : Implementation of node_data.

2)EdgeData     : Implementation of edge_data.

3)Geolocation  : Implementation de geo_location.

2)DWGraph_Ds   : Implementation of directed_weighted_graph.

3)DWGraph_Algo : Implementation of directed_weighted_graph_algorithms

---------------------------------------------------------------------------------------------------------------------------------------


1)NodeData   
-----------

Constructor :
-------------
-Key               :Int      (Type)
_Tag               :Int      (Type)
_Information       :String   (Type)
_Weight            :Double   (Type)
_Collection        :HashMap  (Type)


NodeData is an implementation class of node_data interface which contains :
---------------------------------------------------------------------------

_getKey           Complexity O(1)
_getLocation      C          O(1)
_getWeight        C          O(1)
_getInfo          C          O(1)
_getTag           C          O(1)
_setInfo          C          O(1)
_setWeight        C          O(1)
_setLocation      C          0(1)
_setTag           C          O(1)
----------------------------------------------------------

Methods not Overriden:
----------------------
_toString

_getNi

_jsonNode    (Class to Serialize and Deserialize) 


We are going to explains each method of NodeData :
---------------------------------------------------

_getKey      : Return the key of the asked NodeData.    
      
_getWeight   : Return the Weight of this NodeData.

_getInfo     : Return the info of my NodeData.

_setInfo     : To set the info of my NodeData.

_getTag      : Return the tag of my NodeData.

_setTag      : To set the Tag of my NodeData.

_ToString    : To print my NodeData.

_getLocation : Returns the location of this NodeData.         


---------------------------------------------------------------------------------------------------------------------------------------

2)EdgeData   
-----------

Importations:
-------------

 import java.lang.reflect.Type;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Objects;

Constructor :
-------------
-Src               :Int      (Type)
-Dest              :Int      (Type)
_Tag               :Int      (Type)
_Information       :String   (Type)
_w                 :Double   (Type)


EdgeData is an implementation class of edge_data interface which contains :
---------------------------------------------------------------------------

_getSrc           Complexity O(1)
_getDest          C          O(1)
_getWeight        C          O(1)
_getInfo          C          O(1)
_getTag           C          O(1)
_setInfo          C          O(1)
_setWeight        C          O(1)
_setTag           C          O(1)
----------------------------------------------------------

Methods not Overriden:
----------------------
_toString

_getSource

_getDest
 
_JsonEdge (Class to Serialize and Deserialize) 


We are going to explain each method of EdgesData :
---------------------------------------------------

_getSrc      : Return the Source of the asked EdgeData.    
      
_getWeight   : Return the Weight of this EdgeData.

_getInfo     : Return the info of my EdgeData.

_setInfo     : To set the info of my EdgeData.

_getTag      : Return the tag of my EdgeData.

_setTag      : To set the Tag of my EdgesData.

_ToString    : To print my EdgeData.

_getLocation : Returns the location of this EdgeData.         

---------------------------------------------------------------------------------------------------------------------------------------




3)GeoLocation   
-------------

Constructor :
-------------

-x               :Int      (Type)
-y               :Int      (Type)
_z               :Int      (Type)
_distance        :String   (Type)

GeoLocation is an implementation class of geo_location interface which contains :
---------------------------------------------------------------------------------

_x               Complexity O(1)
_y               C          O(1)
_z               C          O(1)
_distance        C          O(1)
_toString        C          O(1)
_Hashcode        C          O(1)
_setTag          C          O(1)
-----------------------------------------------------------------

Methods not Overriden:
----------------------

_setDistance     C          O(1)

We are going to explain each method of GeoLocation :
---------------------------------------------------

_x           : Return the x of the asked NodeData.    
      
_y           : Return the y of this NodeData.

_z           : Return the z of my NodeData.

_distance    : Return the distance of my NodeData.

_toString    : Print Location.

_setTag     : Change the tag of my Location         

---------------------------------------------------------------------------------------------------------------------------------------

---------------------------------------------------------------------------------------------------------------------------------------

4)DWGraph_DS
------------

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

In the Copy Constructor I copy all the edges and all the neighbours I will call the Copy Constructor in the method "Copy" of  DWGraph_Algo

---------------------------------------------------------------------------------------------------------------------------------------



WGraph_DS is an implementation class of graph interface which contains :
-----------------------------------------------------------------------

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


We are going to explain each method of DWGraph_DS :
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

--------------------------------------------------------------------------------------------------------------------------------------------------------


5) DWGraph_Algo:
----------------

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

_Collection       : HashMap (Type)

WGraph_Algo is an implementation class of dw_graph_algorithms interface which contains :
----------------------------------------------------------------------------------------

_compare             Complexity    ----
_init                C             ----
_getGraph            C             ----
_copy                C             ----
_isConnected         C             ---- 
_shortestPath        C             ----
_shortestPathDist    C             ----
_save                C             ---- 
_load                C             ----



Methods not Overriden :
-----------------------

_BreadthFirstSearch  
_chek_Visit
_InitTag
_Dijkstra
_Dijkstra_graphNi



We are going to explain each method of DWGraph_Algo :
-----------------------------------------------------


_init                : In order to Init the graph on which this set of algorithms operates on.

_copy                : Creat a Deep copy of my graph , each Node and each neighbor of them
                     : Using the copy constructor of WGraphDS
                     : The copy constructor of WGraphDs copy all nodes one by one the pointer in the memory will not be the same that the nodes copied
                     : The copy constructor of WGraphDs copy all edges one by one the pointer in the memory will not be the same that the edge copied
                     

_isConnected         : Checks if the Graph is connected => If Each Node can be reach from any Node of My graph 
                     : Checking all the path possible using The BFS Algorithm 
                     : BFS Use Check_Visit In order to verify if all the node of my graph was Visited 
                     : Using the Tag of Each Node if there is a Node with a Tag (0) So the node wasn't visited and the graph is not    : connnected

_shortestPath        : In order to know the shortest Path between two Node (Using Dijkstra)  
  
  _Dijkstra          : Declaration of a priority Queue  (Using the Comparator)
                     : First Initialise all the Tag to Infinity using InitTag
                     : Clear the visited list
                     : Add source node to my PQueue 
                     : initialise distance from node to itself to 0
                     : Set Info of node to source
                     : While the PQueue is not empty
                     : Node u take the value of The node i removed from PQueue 
                     : Add u node to Visited List
                     : Calling Dijkstra_graphNi giving Node u , Pqueue , Graph
     
   _InitTag          : Initialize all tag to infinite       

   _Dijkstra_graphNi : Pass to all neighbor of u
                     : Take Value of the current node
                     : If the current node is not 'visited'
                     : Compare Distance and initialize Comparing Distance 
                     : Adding the current node into the visited List


_shortestPathDist    : Return the List of The Shortest path  (Calling Dijkstra)
                     : Using String (Split) To get all the value of my Shortest Path 

-save                : To Serialize the object  (Turn to octet the object)
 
_load                : To Copy My object Graph (Serialized) into a chosen File 


---------------------------------------------------------------------------------------------------------------------------------------


Parti 2)
----------------------------------------------------------------------------------------------------------------------------------------
The Goal of this part is to Run the Ex2 contain in the util of gameClient and for each scenario using the algorithm we implemented in the part 1

gameClient:  
-----------

Ex2        :  Calling LoginFrame if no argument are received.If ex2 Received args DijkstraAlgo is called.
    

                

Graphics :
----------

_DijkstraAlgo : Use ShortPathDist in order to calculate a ratio ,in the method wichBest each Pokemon are attribute to an Agent;
              : Run the Game Using gameclient.start
              
              
LoginFrame     :  Revived two parameter ID and Scenario if the button Start Game are Pressed the game start calling DijkstraAlgo                 




-----------------------------------------------------------------END README------------------------------------------------------------








