package ex1.src;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

//-----------------------------------------------WGraph_DS------------------------------------------------------------//
public class WGraph_DS implements weighted_graph , Serializable {

   //CONSTRUCTOR :
    HashMap<Integer,node_info>                           hash_graph;
    private   HashMap<Integer,HashMap<Integer,Double>>   hash_edges;
    private int mc    ;
    private int edges ;


    //DEFAULT CONSTRUCOR
    public WGraph_DS(){
        this.hash_graph =new HashMap<>();
        this.hash_edges= new HashMap<>();
        this.mc = 0 ;
        this.edges = 0 ;
    }

    //COPY CONSTRUCTOR
    public  WGraph_DS (WGraph_DS g) {
        this.hash_graph = new HashMap<>();

//----------------------------------COPY EDGES :
        //To copy all Edges of the graph (Used in copy of WgraphAlgo):

        if (g.hash_edges!=null) {

            for (Integer key :g.hash_edges.keySet()) {

                HashMap <Integer,HashMap<Integer,Double>> hh =null;

                for (Integer key2 :g.hash_edges.get(key).keySet()){
                    HashMap<Integer,Double>hd = null;

                    Double  dd=g.hash_edges.get(key).get(key2);

                    hd.put(key2,dd);
                    hh.put(key,hd);
                }
                this.hash_edges = hh;

            }
            }


    //----------------------------------COPY Neighbour :

    //To copy all Neighbour of the graph (Used in copy of WgraphAlgo):

        if (g.hash_graph!=null) {
            for (Integer key :hash_graph.keySet()) {
                this.hash_graph.put(key,g.hash_graph.get(key));
            }
        }
        this.mc =g.getMC();
        this.edges =g.edgeSize();
        }


    //-------------------------Generated Equal to cheks equality of 2 nodes
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WGraph_DS wGraph_ds = (WGraph_DS) o;
        return edges == wGraph_ds.edges &&
                Objects.equals(hash_graph, wGraph_ds.hash_graph) &&
                Objects.equals(hash_edges, wGraph_ds.hash_edges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hash_graph, hash_edges, edges);
    }


    //------------------------------------------NODE INFO INNER CLASS-------------------------------------------------//
    private class NodeInfo implements node_info ,Serializable{

        //CONSTRUCTOR
        private int    key ;
        private String info;
        private double tag ;
        private HashMap <Integer,node_info> hash_node;


        //DEFAULT CONSTRUCTOR
        public NodeInfo(int k) {
            this.key  =   k ;
            this.info =  "" ;
            this.tag  =   -1;
            this.hash_node = new HashMap<>();
        }

        public void removeT(node_info node){
            if (this.hash_node.containsValue(node)){
                this.hash_node.remove(node.getKey());
            }
        }
        //-------------------To Get the Id Of a Node
        @Override
        public int getKey() {
            return this.key;
        }
        //-------------------To Get the Info Of a Node
        @Override
        public String getInfo() {
            return this.info;
        }
        //-------------------To Set the Info Of a Node
        @Override
        public void setInfo(String s) {
            this.info=s;
        }
        //-------------------To Get the Tag Of a Node
        @Override
        public double getTag() {
            return this.tag;
        }
        //-------------------To Set the Tag Of a Node
        @Override
        public void setTag(double t) {
            this.tag=t;
        }


        //--------------------------------To Checks if to nodes equals (Generated)
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            NodeInfo nodeInfo = (NodeInfo) o;
            return key == nodeInfo.key &&
                    Double.compare(nodeInfo.tag, tag) == 0 &&
                    Objects.equals(info, nodeInfo.info);
        }




    }
    //------------------------------------------INNER CLASS END-------------------------------------------------------//

    //---------------------------------To Get a node from his ID
    @Override
    public node_info getNode(int key) {
        return hash_graph.get(key); }


    //----------------------------------To check if there is an edges between two nodes
    @Override
    public boolean hasEdge(int node1, int node2) {

       if(node1!=node2&&this.hash_graph.containsKey(node1)
        &&this.hash_graph.containsKey(node2)
        &&this.hash_graph.get(node1)!=null
        &&this.hash_graph.get(node2)!=null){

        NodeInfo n1 = (NodeInfo) this.hash_graph.get(node1);
        NodeInfo n2 = (NodeInfo) this.hash_graph.get(node2);

       return n1.hash_node.containsKey(node2);
       }
        else return false;
      }



    //----------------------------------To get an edges between two nodes
    @Override
    public double getEdge(int node1, int node2) {

        NodeInfo n1 = (NodeInfo) this.hash_graph.get(node1);
        NodeInfo n2 = (NodeInfo) this.hash_graph.get(node2);

        if (node1==node2){
            return 0;
        }

       if (!hasEdge(node1,node2)){
            return -1;
        }

       else {
           return hash_edges.get(node1).get(node2);

       }
    }

    //----------------------------------To add a node into the Graph
    @Override
    public void addNode(int key) {
        this.hash_graph.put(key,  new NodeInfo(key));
        this.hash_edges.put(key,new HashMap<>());
        mc++;
    }

    //----------------------------------To creat an edges with  weight between two node
    @Override
    public void connect(int node1, int node2, double w) {


        NodeInfo n1=  (NodeInfo) this.hash_graph.get(node1);
        NodeInfo n2 = (NodeInfo) this.hash_graph.get(node2);

        if (hasEdge(node1,node2)&&getEdge(node1,node2) != w){
            this.hash_edges.get(node1).put(node2,w);                  //Add to my List of Edge Node1  the edges between N1 and N2
            this.hash_edges.get(node2).put(node1,w);
            mc++;

        }

        if(this.hash_graph.get(node1)!=null  &&  this.hash_graph.get(node2)!=null        //Null Node
        && node1!=node2                                                                  //Node 1 = Node2
        && !hasEdge(node1,node2)                                                         //Already an edge exist
        && this.hash_graph.containsKey(node1) && this.hash_graph.containsKey(node2))     //The Graph contain the 2 Nodes

        {

            n1.hash_node.put(node2,n2);                        //Add n2 to n1's neighbour list .
            n2.hash_node.put(node1,n1);                        //Add n1 to n2's neighbour list .

            this.hash_edges.get(node1).put(node2,w);           //Add to my List of Edge Node1  the edges between N1 and N2
            this.hash_edges.get(node2).put(node1,w);           //Add to my List of Edge Node2  the edges between N1 and N2



            edges++;
            mc++;
        }
    }

    //----------------------------------To get a collection of node in the graph
    @Override
    public Collection<node_info> getV() {

  //      if(!this.hash_graph.isEmpty()){
      return hash_graph.values();}

    //----------------------------------To get a collection of neighbour of a node with his ID
    @Override
    public Collection<node_info> getV(int node_id) {

        NodeInfo nid = (NodeInfo) this.hash_graph.get(node_id);  //To get the node of node_id

        Collection<node_info> col= nid.hash_node.values();       //To add the neighbours in a collection

        return col;                                             //To return the collection.
    }

    //----------------------------------To remove a node of the Graph using his Key
    //------------------Removing all edges connected to the node i removed .
    //-----------Return the node removed
    @Override
    public node_info removeNode(int key) {

        NodeInfo nd = (NodeInfo) this.hash_graph.get(key);

     if (this.hash_graph.containsKey(key)
         &&this.hash_graph.get(key)!=null){

         for (HashMap<Integer, Double> k : this.hash_edges.values()) {
            if (k.containsKey(key)){
                 k.remove(key);
                 edges--;
            }
         }
         hash_edges.remove(key);

         //Remove my node of each neighbour

         for (node_info ni: nd.hash_node.values()) {
             NodeInfo nz = (NodeInfo) ni;
             if (nz.hash_node.containsKey(key)){
             nz.hash_node.remove(key);
             }
         }

         return hash_graph.remove(key);
     }
     else
         return null;
    }

    //----------------------------------To remove an edge between two nodes
    @Override
    public void removeEdge(int node1, int node2) {
       NodeInfo n1= (NodeInfo) this.hash_graph.get(node1);
       NodeInfo n2 =(NodeInfo) this.hash_graph.get(node2);

        if (hasEdge(node1,node2)
            &&this.hash_graph.get(node1)!=null
            &&this.hash_graph.get(node2)!=null
            &&node1!=node2){

            this.hash_edges.get(node1).remove(node2);
            this.hash_edges.get(node2).remove(node1);

            edges--;
            mc++;

         n1.removeT(this.hash_graph.get(node2));
         n2.removeT(this.hash_graph.get(node1));

        }

    }



    //----------------------------------To get the number of node contain in the graph
    @Override
    public int nodeSize() {
        return  this.hash_graph.size();
    }

    //----------------------------------To get the number of edges contain in the graph
    @Override
    public int edgeSize() {
        return this.edges;
    }

    //----------------------------------To get the number of changed in the graph
    @Override
    public int getMC() {
        return this.mc;
    }




}
