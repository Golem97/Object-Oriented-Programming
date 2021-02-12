package api;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import org.json.JSONObject;
import org.w3c.dom.Node;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public class DWGraph_DS  implements directed_weighted_graph {

    //CONSTRUCTOR :
    private HashMap<Integer, node_data> hash_graph; // Hashmap of all nodes in graph
    private HashMap<Integer,HashMap<Integer,edge_data>>   hash_edges; // Hashmap of all edges in graph

    private int mc;
    private int edges;


    //DEFAULT CONSTRUCTOR
    public DWGraph_DS() {
        this.hash_graph = new HashMap<>();
        this.hash_edges = new HashMap<>();
        this.mc = 0;
        this.edges = 0;
    }

    //COPY CONSTRUCTOR
    public DWGraph_DS(DWGraph_DS g) {

        this.hash_graph = new HashMap<>();
        this.hash_edges = new HashMap<>();

        //deep copying hash_graph
        if (g.hash_graph != null) {

            //itr iterates over the Collection of hash_graph's nodes
            Iterator itr = g.hash_graph.values().iterator();

            while (itr.hasNext()) {
                NodeData copyNode = (NodeData) itr.next();
                this.hash_graph.put(copyNode.getKey(), new NodeData(copyNode));
            }
        }

        //deep copying hash_edges

        //deep copying edgeMap

        if (g.hash_edges != null) {

            Iterator itr2 = g.hash_edges.keySet().iterator();  //itr2 iterates over all the keys of the outer g's Hashmap
            while (itr2.hasNext()) {

                int src = (int) itr2.next();

                Iterator itr3 = g.hash_edges.get(src).keySet().iterator(); //itr3 iterates over all the keys of the inner g's Hashmap

                HashMap<Integer, edge_data> newInnerMap = new HashMap<>(); // create new innerMap

                while (itr3.hasNext()) {

                    int dest = (int) itr3.next();

                   edge_data ed = new EdgeData((EdgeData) g.hash_edges.get(src).get(dest));

                    newInnerMap.put(dest,ed);//put weight in new innerMap
                }
                this.hash_edges.put(src, newInnerMap);//put newInnerMap in edgeMap at outerIndex

            }
        }
        this.mc=g.getMC();
        this.edges=g.edgeSize();

    }

    @Override
    public node_data getNode (int key){

        if(this.hash_graph.containsKey(key)) {
            node_data r = this.hash_graph.get(key);
            return r;
        }
        //if there's no such node
        return null;
    }

    @Override
    public edge_data getEdge ( int src, int dest){
        if (hasEdge(src,dest)) {
            edge_data ed = this.hash_edges.get(src).get(dest);
            return ed;
        }


       else return null;
    }

    @Override
    public void addNode (node_data n) {


        //this.hash_graph.put(n.getKey(),new NodeData(n.getKey()));
   //     mc++;

        if(n.getKey() == -1) ((NodeData) n).setId(getFirstAvailableKey());

       HashMap<Integer, edge_data> tempmap = new HashMap<>();

        if (!this.hash_graph.containsKey(n.getKey())) {
            hash_graph.put(n.getKey(), n);
            hash_edges.put(n.getKey(), tempmap);
            mc++;
        }
    }

    //Test function to adapt NodeData() constructor
    public int getFirstAvailableKey(){

        for (int i=0; i < hash_graph.size(); i++){
            if(!hash_graph.containsKey(i)) return i;
        }
        return hash_graph.size();
    }


    @Override
    public void connect ( int src, int dest, double w) {

        //no negative weight
        if (w < 0) return;

        NodeData n1 = (NodeData) this.hash_graph.get(src);
        NodeData n2 = (NodeData) this.hash_graph.get(dest);

        //if no edges at all create the Hashmap
        if (this.hash_edges.isEmpty()) {
            this.hash_edges.put(src, new HashMap<>());
        }

        //if there's an edge from src to dest existing but w is different
        if (hasEdge(src, dest) && getEdge(src, dest).getWeight() != w) {
            ((EdgeData) getEdge(src, dest)).setWeight(w);                     // Updates weight of src --> dest edge
            mc++;
        }

        if (this.hash_graph.get(src) != null                                                     // if src node isn't null
                && this.hash_graph.get(dest) != null                                             // if dest node isn't null
                && src != dest                                                                  // Node 1 = Node2
                && !hasEdge(src, dest)                                                           // Already an edge exist
                && this.hash_graph.containsKey(src)                                             // The Graph contains the src node
                && this.hash_graph.containsKey(dest))                                           // The Graph contains the dest node
        {

            n1.hash_node.put(dest, n2);                                                          //Add n2 to n1's neighbour list

            if (!this.hash_edges.containsKey(src)) {
                this.hash_edges.put(src, new HashMap<>());
            }

          //  if (this.hash_edges.get(src) != null) {

                this.hash_edges.get(src).put(dest, new EdgeData());

                EdgeData ed = new EdgeData((EdgeData) getEdge(src, dest));
                ed.setSource(src);
                ed.setDest(dest);
                ed.setWeight(w);

                //Add to my List of Edge Node1  the edges between N1 and N2
                this.hash_edges.get(src).put(dest, ed);

                edges++;
                mc++;
            }
        }
  //  }

    // returns true if the directed edge from node1 --> node 2 exists
    public boolean hasEdge(int node1, int node2) {


        if(node1 != node2                               // a node can't be connected to itself
                && this.hash_graph.containsKey(node1)   // if node 1 exists in graph
                && this.hash_graph.containsKey(node2)   // if node 2 exists in graph
                && this.hash_edges.containsKey(node1)
                && this.hash_edges.get(node1).containsKey(node2)){

            return true;
        }
        else return false;
    }
    @Override
    public Collection<node_data> getV () {
        Collection<node_data> coln = this.hash_graph.values();
        return coln;
    }

    @Override
    public Collection<edge_data> getE (int node_id){

        if(!this.hash_edges.containsKey(node_id)) return null;

        Collection<edge_data> cole = this.hash_edges.get(node_id).values();
        return cole;
    }

    @Override
    public node_data removeNode (int key){

        if (this.hash_graph.containsKey(key)
                &&this.hash_graph.get(key)!=null){

            NodeData nd = (NodeData) this.hash_graph.get(key);
            //Remove all edges going out
            for (HashMap<Integer, edge_data> k : this.hash_edges.values()) {

                if (k.containsKey(key)){
                    k.remove(key);
                    edges--;
                }
            }

            edges-=this.hash_edges.get(key).size();   //Remove all the incoming edges (Decrement edges size)
            this.hash_edges.remove(key);               //Remove edges as source
            //Remove my node of each neighbour

            for (node_data ni: nd.hash_node.values()) {
                NodeData nz = (NodeData) ni;
                if (nz.hash_node.containsKey(key)){
                    nz.hash_node.remove(key);
                }
            }
            mc++;
            return hash_graph.remove(key);
        }
        else
            return null;
    }



    @Override
    public edge_data removeEdge (int src, int dest){

        if(this.hash_graph.containsKey(src) && !hasEdge(src,dest)) return null;

        if(hasEdge(src,dest)){
            mc++;
            edges--;
            return this.hash_edges.get(src).remove(dest);

        }

        else return null;
    }

    @Override
    public int nodeSize () {
        return this.hash_graph.size();
    }

    @Override
    public int edgeSize () {
        return edges;
    }

    @Override
    public int getMC () {
        return mc;
    }

    @Override
    public String toString() {

        return "{\"Edges\":" + convertEdgeMap(hash_edges) +
                "," +
                "\"Nodes\":"+ convertNodeMap(hash_graph) +
                "}";
    }

    public String convertEdgeMap(HashMap<Integer, HashMap<Integer,edge_data>> map) {

        StringBuilder mapAsString = new StringBuilder("[");

        for (Integer key : map.keySet()) {
            for (Integer key2: map.get(key).keySet()){

                mapAsString.append("{\"src\":"+map.get(key).get(key2).getSrc() + ",\"w\":"+map.get(key).get(key2).getWeight()+",\"dest\":"+map.get(key).get(key2).getDest());
                mapAsString.append("},");
            }
        }
        mapAsString.delete(mapAsString.length()-2, mapAsString.length()).append("}]");
        return mapAsString.toString();
    }

    public String convertNodeMap(HashMap<Integer, node_data> map) {

        StringBuilder mapAsString = new StringBuilder("[");

        for (Integer key : map.keySet()) {

            mapAsString.append("{\"pos\":\""+map.get(key).getLocation()+"\",\"id\":"+key);

                mapAsString.append("},");

        }
        mapAsString.delete(mapAsString.length()-2, mapAsString.length()).append("}]");
        return mapAsString.toString();
    }




    public HashMap<Integer, HashMap<Integer, edge_data>> getHash_edges() {
        return hash_edges;
    }

    public directed_weighted_graph reverseGraph() {
        directed_weighted_graph g = new DWGraph_DS();

        //we have this graph, we want to copy all its edges but reversed in g graph
        for (Integer src : this.getHash_edges().keySet()) {

            for (Integer dest : this.getHash_edges().get(src).keySet()) {

                if (g.getNode(src) == null)  g.addNode(new NodeData(src));

                if (g.getNode(dest) == null) g.addNode(new NodeData(dest));

                double weight = this.getHash_edges().get(src).get(dest).getWeight();

//                if (((DWGraph_DS)g).getHash_edges().get(dest) == null){
//
//                    ((DWGraph_DS)g).getHash_edges().get(dest).put(src,new EdgeData());
//                }
                g.connect(dest, src, weight);
            }
        }
        return g;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hash_graph, hash_edges, edges);
    }
}