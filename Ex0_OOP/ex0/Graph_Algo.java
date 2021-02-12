
package ex0;

import java.util.*;

public class Graph_Algo implements graph_algorithms {
    private graph my_graph;

                                                                  //CONSTRUCTOR
    public Graph_Algo(){
    this.my_graph=new Graph_DS();
    }

                                                                  //INIT GRAPH
    @Override
    public void init(graph g) {
        this.my_graph = g;
    }
                                                                   //DEEP COPY
    @Override
    public graph copy() {
        return new Graph_DS((Graph_DS) this.my_graph);
    }


    @Override
                                                              //To check if the Graph is connected or not using the BFS algorithm.
    public boolean isConnected() {
        if (this.my_graph.nodeSize()==1){
            return true;
        }
        if ( this.my_graph.edgeSize()==0&&this.my_graph.nodeSize()==0){
            return true;
        }

        if ( this.my_graph.edgeSize()==0&&this.my_graph!=null){
            return false;
        }
        if (this.my_graph==null ){                             //If the graph is Empty so is not connected
            return false;
        }
        if (this.my_graph.getV().size()==0){
            return true;
        }

        Collection<node_data> molly= this.my_graph.getV();                             //Create a new collection and get my graphs values in

        //Application of BFS on my collection and my graph
        return BreadthFirstSearch(molly, this.my_graph);                                      //If the Bfs return true all the nodes are connected
    }



    public boolean BreadthFirstSearch(Collection<node_data> coll,graph g){    //BFS RECEIVE LIST AND GRAPH
        Queue<node_data> myQueue = new LinkedList<>();                         //The Bfs work with a Queue
        if (coll.isEmpty()){                                                  //if my graph is empty
            return false;                                                       //So not connected
        }
                                                                               // Initialisation all my tag->0
        for (node_data n : this.my_graph.getV()) {                             // All node are "unvisited"
            n.setTag(0);
        }

       // System.out.println("All Tags Set to zero");                         //Checks success
        Iterator inter = coll.iterator();                                    //New Iterator to Stocks value of my collection
        node_data f = (node_data) inter.next();                                //New node f take the next node
        f.setTag(1);                                                          //set f to visited
        myQueue.add(f);                                                       //Add f to my queue
        while (!myQueue.isEmpty()) {                                        //while my queue is empty continue
            node_data deq = myQueue.remove();                                 // new node to dequeue
            Collection<node_data> col =g.getV(deq.getKey());
                                                                     //CHECKS IF THERE IS NEIGHBOUR
            if (col == null) {
                return false;                                       //IF THERE IS NO NEIGHBOUR SO THE GRAPH CANT BE CONNECTED.
            }
            //NEW ITERATOR FOR NEIGHBOUR

            for (node_data nn : col) {                        //KEEP NEIGHBOURS NODE IN ITERATOR
                int nn_key = nn.getKey();                       //WANT THE KEY OF NN

                if (g.getNode(nn_key).getTag() == 0 && g.getV().contains(nn_key)) {                         //CHECK IF VISITED
                    g.getNode(nn_key).setTag(1);                               //TURN  TAG TO VISITED
                    myQueue.add(g.getNode(nn_key));                            //ADD TO THE QUEUE

                }
            }
        }
        return chek_Visit(myQueue);   //Checks if all the nodes are visited

        }


    private boolean chek_Visit(Collection<node_data> nodeList) {

        for (node_data n :nodeList) { //For each node of my graph
          if (n.getTag()==0){                     //If there is one Node that is not visited
              return false;                       //th graph cant be connected
          }
        }
        return true;
    }





////////////////////////////           /////////////////////////////////             ///////////////////////////////////



    @Override
    public List<node_data> shortestPath(int src, int dest){

        node_data start = this.my_graph.getNode(src);             //Get The Node1 From is Key
        node_data end   = this.my_graph.getNode(dest);            //Get The Node2 frome is Key


        LinkedList<node_data> list_n = new LinkedList<>();        //Creat a new LinkedList
        HashMap<node_data, node_data> prev = new HashMap<>();     //New HashMap all  previous

        List path_dir = new LinkedList();                         //LinkedList Direction

        Queue q = new LinkedList();                               //Creat a Queue in order to remove all the node i visited

        node_data current = start;                               //the Node whos gone to visite all node take value of the start
        q.add(current);                                          //Add my current Node in my Queue

        list_n.add(current);                                     // And in my list too
        current.setTag(1);                                       //I visited the node so i set his tag to 1

        while(!q.isEmpty()){                                     // while my queue is not empty i continue
            current = (node_data) q.remove();                    //I remove my current Node of my queue
            if (current.equals(end)){                            //If current is equal to end i arrived at destination so break
                break;
            }else{
                for(node_data node : current.getNi()){           //for each neighbour of each Node
                    if(!list_n.contains(node)){                  //if my list of node do not contain the node
                        q.add(node);                             //Adding it to the queue
                        list_n.add(node);                        //Adding My node in the List
                        list_n.getLast().setTag(1);              //Set it to 1 because i visited it
                        prev.put(node, current);
                    }
                }
            }
        }
        if (!current.equals(end)){                                     //if current node never equal to my destination there is no path possible
            System.out.println("No Path Possible");
        }
        for(node_data node = end; node != null; node = prev.get(node)) {
            path_dir.add(node);                                        //Adding the node in my final path
        }

        return path_dir;                                               //Return the path
    }




    @Override
    public int shortestPathDist(int src, int dest) {
        node_data start = this.my_graph.getNode(src);                              //Get my start Node from his Key
        node_data end   = this.my_graph.getNode(dest);                             //Get my end   Node from his Key

        if (!this.my_graph.getV().contains(start)||!this.my_graph.getV().contains(end)){
            return -1;                        //If the Graph not Contain one of my node return -1
        }

        if(src==dest){                        //If the start is the end the shortest path is 0
            return 0;
        }

        if (end.getNi().isEmpty()||start.getNi().isEmpty()) {    //if there is no Neighbour in end and Start No path possible return -1
            return -1;
        }
        if (start.hasNi(dest)){                                  //If the start Node have end node as neighbour the path is 1
            return 1;
        }

        List<node_data> ret= shortestPath(src,dest);           //Using ShortestPath to return the size of the ShortestPath i printed in my method

return ret.size()-1;                                          //return the size
    }


}
