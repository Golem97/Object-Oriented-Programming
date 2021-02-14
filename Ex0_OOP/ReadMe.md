Nahanaël Benicho
---------------- 
Tz     342769130
----------------



Ex.0 Contain 3 Implementation :
---------------------------------------------------------------------------------------------------------------------------------------

1)NodeData   : Implementation of node_data.

2)Graph_Ds   : Implementation of Graph_DS.

3)Graph_Algo : Implementation of graph_algorithm

---------------------------------------------------------------------------------------------------------------------------------------


1)NodeData
-----------

Constructor :
-------------
-Key
_Tag
_Information
_Collection        : HashMap (Type)
_Counter           : In order to generat NodeData in Loop

Nodedata is an implement class of node_data who's contain :
-----------------------------------------------------------

_getKey           Complexity O(1)
_getNi            C          O(1)
_hasNi            C          O(1)
_addNi            C          O(1)
_removeNode       C          O(1)
_getInfo          C          O(1)
_setInfo          C          O(1)
_getTag           C          O(1)
_setTag           C          O(1)
_ToString         C          O(1)


We are going to explains each methodes of NodeData :
---------------------------------------------------

_getKey     : Return the key of the asked node.    

_getNi      : Return the collections of Neighbour of my node using a methode of collection known.

_hasNi      : Verify if a given Node has Neighbour or Not using an auto generated Method of Collection

_AddNi      : To Add a new neighbours on the Collection using the methode put.

_removeNode : Remove a node after checks if the node is contain or not on the Collection.

_getInfo    : Return the info of my NodeData.

_setInfo    : To set the info of my NodeDate.

_getTag     : Return the tag of my NodeData.

_setTag     : To set the Tag of my NodeDate.

_ToString   : To print my NodeData  

---------------------------------------------------------------------------------------------------------------------------------------


2)Graph_DS
-----------

Constructor :
-------------

_Collection        : HashMap (Type)
_Counter           : In order to know the number of changes i opperated in my graph.
_Edeges            : In order to know how many edges i have in my graph.



Graph_DS is an implement class of graph who's contain :
-------------------------------------------------------

_getNode        Complexity O(1)
_hasEdge        C          O(1)   
_addNode        C          O(1)
_connect        C          O(1)
_getV           C          O(1)
_getV (int)     C          O(1)
_removeNode     C          O(n)
_removeEdge     C          O(1)
_nodeSize       C          O(1)
_edgeSize       C          O(1)
_getMC          C          O(1)


We are going to explains each methodes of Graph_DS :
---------------------------------------------------

_getNode            : In order to get a node from his Key

_hasEdge            : In order to checks if there is an edge between 2 nodes (Using hasNi frome NodeData)

_addNode            : In order to add a new node in my graph.(Checks if the node already exist & Increment the new change)

_connect            : In order to connect two nodes (Checks for the Node 1/(2): Existe /Is null /Have already Node 2/(1) has Neighbour)

_getV               : In order to get all the values of my Hashmap.

_getV (int)         : In order to get all the Neighours of a Node with is ID (Using).
 
_removeNode         : In order to remove a Node of my graph (Checks if the node is contain in my graph).  
                    : For each neigbours of the node removed  we remove the node of the Neigbour list. (Using removeEdge)
                    : Also Decrement the Integer edges for all edges i removed 

_removeEdge         : In order to remove the connection between two Node.
                    : Checks if the Collection Actually exist.
                    : Remove Node1 of the list of neighbour of Node2 and the same frome Node2 to Node1.
                    : Decrement the edge i removed.
                    : Increment the change.

_nodeSize           : In order to know how many Node in the graph Return the size of my Collection.

_edgeSize           : In order to know how many connections there is in my graph (Retur all the edges i incremented).

_getMC              : In order to know how many changes i did in my graph (Just return the counter i incremented after all changes).

---------------------------------------------------------------------------------------------------------------------------------------

3)Graph_Algo
-----------

Constructor :
-------------
-Key
_Tag
_Information
_Collection        : HashMap (Type)
_Counter           : In order to generat NodeData in Loop

Graph_Algo is an implement class of graph_algorithm who's contain :
-------------------------------------------------------------------

_init                Complexity    ----
_copy                C             ----
_isConnected         C             ---- 
_shortestPath        C             ----
_shortestPathDist    C             ----



We are going to explains each methodes of Graph_Algo :
---------------------------------------------------
_init                : In order to Init the graph on which this set of algorithms operates on.

_copy                : Creat a Deep copy of my graph ,each Node and each neighbour of thems
                     : Using the copy constructor of GraphDS
                     : Using the copy constructot of NodeData 

_isConnected         : Checks if the Graph is connected => If Each Node can be reach from any Node of My graph 
                     : Checking all the path possible using The BFS Algorithm 
                     : BFS Use Check_Visite In order to verify if all the node of my graph was Visited 
                     : Using the Tag of Each Node if there is a Node with a Tag (0) So the node was'nt visited and the graph is not    : connnected

_shortestPath        : In order to know the shortest Path between two Node (Using BFS)

_shortestPathDist    : In order to know the size of the shortest Path between two Node (Using shortestPath).

---------------------------------------------------------------------------------------------------------------------------------------














