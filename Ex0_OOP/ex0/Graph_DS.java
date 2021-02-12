package ex0;

import java.util.Collection;
import java.util.HashMap;


public class Graph_DS implements graph{

    private HashMap <Integer ,node_data> hash_coll;
    private int count;
    private int edges;

    //CONSTRUCTOR
    public Graph_DS(){
        this.hash_coll= new HashMap<>();
        this.count=0;
        this.edges=0;
    }

    //COPY CONSTRUCTOR
    public  Graph_DS (Graph_DS g){
        this.hash_coll= new HashMap<>();
        if (g!=null) {

            for (ex0.node_data node_data : g.hash_coll.values()) {
                NodeData nd = (NodeData) node_data;
                this.hash_coll.put(nd.getKey(), new NodeData(nd));


            }

        //    for (node_data n : g.hash_coll.values()) {
            //       this.hash_coll.put(n.getKey(), new NodeData((NodeData) n));
            //}
        }
        this.count=g.getMC();
        this.edges=g.edgeSize();
    }



    @Override
                                                    //To get a node from his Key
    public node_data getNode(int key) {
        //Ih the node is in the graph:
        return hash_coll.getOrDefault(key, null);          //Get it
        

    }

    @Override
    public boolean hasEdge(int node1, int node2) {
        return (hash_coll.get(node1).hasNi(node2));  //Checks if there is an edge between 2 nodes

    }
    @Override
    public void addNode(node_data n) {                //To add a new node on my graph.
                                                      // If the node already in the graph no need to
            count++;                                  //Increment new change.
           hash_coll.put(n.getKey(),n);               //Add my new node

    }


    @Override
    public void connect(int node1, int node2) {      //received 2 Node checks connections between.
        if ( hash_coll.get(node1)!=null && hash_coll.get(node2)!=null&& hash_coll.containsKey(node1)&&hash_coll.containsKey(node2)
                &&!hash_coll.get(node1).hasNi(node2)&& !hash_coll.get(node1).getNi().contains(node2) &&!hash_coll.get(node2).getNi().contains(node1)&&node1!=node2) {    //If theres is already a edge between them we don't have to connect //TO Checks if my nodes are contains in the graph

            hash_coll.get(node1).addNi(hash_coll.get(node2)); //To add n2 on the list of n1 neighbours
            hash_coll.get(node2).addNi(hash_coll.get(node1)); //To add n1 in the list of n2 neighbours
          //  System.out.println(node1+" and "+node2+" Connected."); //To print that the connection worked successfully
            count++;                                    //Increment the change of my graph.

                edges++;                               // A new connection was created so new edge between my new node.
          //  }

        }
    }

    @Override
                                                        //To get all the values of my Hashmap.
    public Collection<node_data> getV() {
        return this.hash_coll.values();
    }

    @Override
                                                         //Get the collection of neighbour of node with his ID
    public Collection<node_data> getV(int node_id) {
        return hash_coll.get(node_id).getNi();
    }


    @Override
    //To remove a node of the graph
    public node_data removeNode(int key) {              //Receive a Key
        if (hash_coll.get(key)!=null){                  //If the node is not null do this :
            count++;                                    //I can increment change because i know i will remove
            for (node_data Nd:this.getV()){             //For each node of my graph
              if (this.hasEdge(Nd.getKey(),key)){       //If there is an edge between the current and my node :
                 removeEdge(Nd.getKey(),key);           //Remove it
                 }
            }
        }
        else{
        System.out.println("Not this node here ");

            } //The node is not on the graph.
        return hash_coll.remove(key);                     //return the removed node .
    }



    @Override
    public void removeEdge(int node1, int node2) {

        if (this.hasEdge(node1,node2)){                                  //If there is an edge between the two node:
            count++;                                                     //Increment the change
            hash_coll.get(node1).getNi().remove(hash_coll.get(node2));   //Remove the node1 of neighbour list of node2
           // System.out.println("Edge removed between "+node1+" and "+node2);// Removed Successfully

            hash_coll.get(node2).getNi().remove(hash_coll.get(node1));       //Remove the node2 of neighbour lis of node1
           // System.out.println("Edge removed between "+node2+" and "+node1); //Removed Successfully
            edges--;                                                         //Remove the edge

        }

    }


    @Override
    public int nodeSize() {                                  //Get the number of node in the graph
        return hash_coll.size();
    }

    @Override
    public int edgeSize() {                                  //Get the number of edges in the graph
        return edges;
    }

    @Override
    public int getMC() {                                     //Get the numbers of change
        return count;
    }

                                                            //To String
    public String ToString() {
        StringBuilder s= new StringBuilder(" ");

        for (node_data Current:  this.getV()) {
            s.append(Current.ToString()).append(" ; ");

        }
        System.out.println(s);
        return s.toString();
    }


}
