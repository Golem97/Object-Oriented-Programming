package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.*;
import java.util.*;

public class DWGraph_Algo implements dw_graph_algorithms {

    private directed_weighted_graph DWgraph;
    private HashSet<node_data> visited_set = new HashSet<>(); // Hashset containing all the visited nodes
    private HashMap<Integer, Double> hd = new HashMap<>();

    //Constructor
    public DWGraph_Algo() {
        this.DWgraph = new DWGraph_DS();
        this.visited_set = new HashSet<>();
    }

    //Copy Constyructor
    public DWGraph_Algo(directed_weighted_graph g) {
        this.DWgraph = g;
    }


    Comparator<node_data> comparator = new Comparator<node_data>() {
        @Override
        public int compare(node_data node1, node_data node2) {
            return (int) (node1.getTag() - node2.getTag());
        }
    };

    @Override
    public void init(directed_weighted_graph g) {
        this.DWgraph = g;
    }

    @Override
    public directed_weighted_graph getGraph() {
        return this.DWgraph;
    }

    @Override
    public directed_weighted_graph copy() {
        directed_weighted_graph myCopy = new DWGraph_DS((DWGraph_DS) this.DWgraph);
        return myCopy;
    }

    @Override
    public boolean isConnected() {

        //if graph is empty
        if (this.DWgraph == null) {
            return false;
        }

        Collection<node_data> nodelist = this.DWgraph.getV();
        if (BFS(nodelist, this.DWgraph)) {

            directed_weighted_graph ag = ((DWGraph_DS) this.DWgraph).reverseGraph();

            Collection<node_data> reversedNodelist = ag.getV();
            return BFS(reversedNodelist,ag);

        }
        return false;
    }


    @Override
    public double shortestPathDist(int src, int dest) {
        node_data start = DWgraph.getNode(src);
        node_data end = DWgraph.getNode(dest);

        if (getGraph().getV().contains(start) && getGraph().getV().contains(end)) {// if src and dest node are both in graph
            Dijkstra(DWgraph, start);
            if (hd.get(dest) < Double.POSITIVE_INFINITY) {
                return hd.get(dest);
            }
        }
        return -1;
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {

        node_data start = DWgraph.getNode(src);
        node_data end = DWgraph.getNode(dest);

        if (shortestPathDist(src, dest) != -1) { // updates node's info with shortest paths and checks if there is a path

            String[] finalInfo = end.getInfo().split(",");
            //System.out.println("The info of the node is Avec le ARRAY  "+printArray(finalInfo));
            int[] arr = new int[finalInfo.length];

            for (int i = 0; i < arr.length; i++) {
                arr[i] = Integer.parseInt(finalInfo[i]);
            }

            List<node_data> nodePath = new LinkedList<>();

            for (int i = 0; i < arr.length; i++) {
                nodePath.add(DWgraph.getNode(arr[i]));
            }
            //System.out.println("The info of the node (CE QUE L'ON RETOURNE) "+nodePath);

            return nodePath;
        }
        return null; //no path found
    }


    @Override
    public boolean save(String file) {

    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(DWGraph_DS.class, new DWGraph_Serializer());
    Gson gson = gsonBuilder.create();

    try (FileWriter writer = new FileWriter(file)) {
        writer.write(gson.toJson(DWgraph));
        return true;
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}


    @Override
    public boolean load(String file) {

        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(DWGraph_DS.class, new DWGraph_Deserializer());
        Gson gson = gsonBuilder.create();

        try (FileReader reader = new FileReader(file)) {
            init(gson.fromJson(reader, DWGraph_DS.class));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Breath First Search algorithm (BFS):
     * Given a Collection of nodes_info from a given graph,
     * the BFS algorithm checks if the graph KACHIR.
     * First we initiate all tags to 0 by calling init_Tag() method.
     * BFS is a graph traversal algorithm that starts traversing the graph from a random node and explores
     * all the neighbours nodes, then do the same with all it's unexplored nearest nodes.
     * Each time we visit a node we set its tag to 1, as visited.
     * The algorithm follows the same process by using a queue stocking all explored nodes, until
     * the queue is empty which means all the neighbours are connected by an existing path
     * Then we use a method called checksTag() to check if all tags are marked as visited (==1).
     *
     * @param nodeList collections of node_data.
     * @param g        given directed_weighted_graph.
     * @return boolean , true: if all nodes have been visited, false if not;
     */
    static private boolean BFS(Collection<node_data> nodeList, directed_weighted_graph g) {

        Queue<node_data> q = new LinkedList<node_data>();
        if (nodeList.isEmpty()) {
            return false;
        }

        for(node_data node : nodeList){

            if(((DWGraph_DS) g).getHash_edges().get(node.getKey()).isEmpty()) return false; // if there is a node without any neighbour
        }

        initTag(g.getV()); //set tags of all nodes to 0 (unvisited);
        Iterator itr = nodeList.iterator();
        node_data n1 = (node_data) itr.next();
        n1.setTag(1); //sets the first node as visited.
        q.add(n1);    //adds it to the queue

            while (!q.isEmpty()) {

            node_data first_out = q.remove(); //dequeue the first in queue.

            Collection<edge_data> e = g.getE(first_out.getKey()); //getting all the neighbours.

            if (e.isEmpty()) {
                return false;
            } //return false if there are no edges coming out (shows that the graph cannot be connected)

            Iterator klm = e.iterator();

            //iterating over the node edges
            while (klm.hasNext()) {
                edge_data ed = (edge_data) klm.next();
                int nver = ed.getDest(); //gets the next neighbour.
                if (g.getNode(nver).getTag() == 0) {    //checks if tag == 0 (already visited)
                    g.getNode(nver).setTag(1);// if tag == 0 set it as 1 (set the tag as visited)
                    q.add(g.getNode(nver));    //add the node to the queue.
                }
            }
        }
        return check_all_visited(nodeList); //returns true if all nodes have been visited
    }

    /**
     * Checks if all nodes in nodeList are visited (tag == 1).
     *
     * @param nodeList
     * @return true: all tags equal 1 , false: if at least one node tag is 0
     */
    private static boolean check_all_visited(Collection<node_data> nodeList) {

        Iterator idc = nodeList.iterator();
        while (idc.hasNext()) {
            node_data check = (node_data) idc.next();
            if (check.getTag() == 0) return false;
        }
        return true;
    }

    /**
     * sets all tags of nodes in nodeList to 0 (unvisited);
     *
     * @param nodeList
     */
    static private void initTag(Collection<node_data> nodeList) {
        Iterator blu = nodeList.iterator();
        while (blu.hasNext()) {
            node_data check = (node_data) blu.next();
            check.setTag(0);
        }
    }

    //we will use the tag of a given node as the shortest path value to get there from a source node
    private void Dijkstra(directed_weighted_graph g, node_data source) {

        dw_graph_algorithms ag = new DWGraph_Algo();
        ag.init(g);

        hd.clear();

        visited_set.clear();       //Clears Hashset (visited nodes)

        //initializes all nodes info to null and all nodes tags to +INF
        Collection<node_data> nodelist = DWgraph.getV();
        for (node_data node : nodelist) {
            node.setInfo(null);
            //node.setTag(Double.POSITIVE_INFINITY);
            hd.put(node.getKey(), Double.POSITIVE_INFINITY);


        }

        hd.put(source.getKey(), 0.0); // initialize only the source tag to 0
        source.setInfo(source.getKey() + ""); //initialize Info

        PriorityQueue<node_data> pq = new PriorityQueue<>(200, comparator);
        pq.add(source);

        while (!pq.isEmpty()) {
            node_data u = pq.poll(); //dequeue from front of queue

            //if u hasn't been visited yet
            visited_set.add(u); // set him as visited

                /*visit the unvisited neighbours nodes, starting from
			    the nearest node(smallest shortestDistance)*/

           // NodeData nd = (NodeData) this.DWgraph.getNode(u.getKey());

          //  HashMap<Integer, node_data> hashni = nd.getNi();


            Collection<edge_data> voisins = g.getE(u.getKey());

            int[] arr = new int[voisins.size()];
            int i =0;
            for (edge_data ed: voisins){
                arr[i++] = ed.getDest();
            }


            // visited_set.clear();
            for (int e : arr) { //for each u neighbour

                if(hd.get(u.getKey()) < hd.get(e)) this.DWgraph.getNode(e).setInfo(u.getInfo());

                if (!visited_set.contains(this.DWgraph.getNode(e))) {

                    node_data v = this.DWgraph.getNode(e);
                    double weight = g.getEdge(u.getKey(),this.DWgraph.getNode(e).getKey()).getWeight();


                        double distanceFromU = hd.get(u.getKey()) + weight;

                        if (distanceFromU < hd.get(v.getKey())) { //if we found a shortest path from u to neighbour e

                            /*remove v from queue for updating
                            the shortestDistance value*/
                            pq.remove(v);
                             // v's hashmap key now stores the shortest path from u to v
                            hd.put(v.getKey(), (distanceFromU));
                            pq.add(v); // put it back in pq updated

                            this.DWgraph.getNode(e).setInfo(this.DWgraph.getNode(e).getInfo() + "," + v.getKey()); //update shortestpath in info
                            visited_set.add(this.DWgraph.getNode(e));
                        }

            } // if we get there there is no path
        }
    }
 }
}
