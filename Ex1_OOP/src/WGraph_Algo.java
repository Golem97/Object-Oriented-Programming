package ex1.src;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;


//-----------------------------------------WGraph_Algo----------------------------------------------------------------//
//---------------Implement weighted_graph:

public class WGraph_Algo implements weighted_graph_algorithms , Serializable {

    private weighted_graph myWgraph;
    private HashSet<node_info> visited = new HashSet<>();


    //-------------------------------Default Constructor :
    public WGraph_Algo() {
        this.myWgraph = new WGraph_DS();
        this.visited = new HashSet<>();


    }


    //------------------------------------Constructor :
    public WGraph_Algo(WGraph_Algo g) {
        this.myWgraph = g.myWgraph;
        this.visited = g.visited;

    }


//-------------------------------------Comparator:

    Comparator<node_info> comp = new Comparator<node_info>() {
        @Override
        public int compare(node_info o1, node_info o2) {

            if (o1.getTag() < o2.getTag()) {
                return -1;
            }

            if (o1.getTag() == o2.getTag()) {
                return 0;
            } else
                return 1;
        }
    };
//----------------------------------------------INITIALISATION -------------------------------------------------------//
//--------------Init     :
//--------------getGraph :
//--------------Copy     :

    @Override
    public void init(weighted_graph g) {
        this.myWgraph = g;
    }

    @Override
    public weighted_graph getGraph() {
        return this.myWgraph;
    }

    @Override
    public weighted_graph copy() {
        return new WGraph_DS((WGraph_DS) this.myWgraph);
    }


//----------------------------------------------IS CONNECTED PART ----------------------------------------------------//
//-------------------To check if the Graph is connected or not using the BFS algorithm--------------------------------//

//-----------Is Connected Use :

//-----------BreadthFirstSearch (BFS Algorithm).

//-----------Chek_Visit.


    @Override
    public boolean isConnected() {
        if (this.myWgraph.nodeSize()==1){
            return true;
        }

        if ( this.myWgraph.edgeSize()==0&&this.myWgraph.nodeSize()==0){
            return true;
        }

        if ( this.myWgraph.edgeSize()==0&&this.myWgraph!=null){
            return false;
        }
        if (this.myWgraph==null ){                             //If the graph is Empty so is not connected
            return false;
        }
        if (this.myWgraph.getV().size()==0){
            return true;
        }
        Collection<node_info> molly= this.myWgraph.getV();


        return BreadthFirstSearch(molly, this.myWgraph);
    }

    public boolean BreadthFirstSearch(Collection<node_info> coll, weighted_graph g){    //BFS RECEIVE LIST AND GRAPH
        Queue<node_info> myQueue = new LinkedList<>();                                  //The Bfs work with a Queue
        if (coll.isEmpty()){                                                            //if my graph is empty
            return false;                                                               //So not connected
        }

        // Initialisation all my tag->0
        for (node_info n : this.myWgraph.getV()) {                                      // All node are "unvisited"
            n.setTag(0);
        }

        // System.out.println("All Tags Set to zero");                   //Checks success
        Iterator inter = coll.iterator();                                //New Iterator to Stocks value of my collection
        node_info f = (node_info) inter.next();                          //New node f take the next node
        f.setTag(1);                                                     //set f to visited
        myQueue.add(f);                                                  //Add f to my queue
        while (!myQueue.isEmpty()) {                                     //while my queue is empty continue
            node_info deq = myQueue.remove();                            // new node to dequeue
            Collection<node_info> col =g.getV(deq.getKey());
            //CHECKS IF THERE IS NEIGHBOUR
            if (col == null) {
                return false;                                 //IF THERE IS NO NEIGHBOUR SO THE GRAPH CANT BE CONNECTED.
            }
            //NEW ITERATOR FOR NEIGHBOUR

            for (node_info nn : col) {                                                 //KEEP NEIGHBOURS NODE IN ITERATOR
                int nn_key = nn.getKey();                                              //WANT THE KEY OF NN

                if (g.getNode(nn_key).getTag() == 0 && g.getV().contains(nn_key)) {     //CHECK IF VISITED
                    g.getNode(nn_key).setTag(1);                                        //TURN  TAG TO VISITED
                    myQueue.add(g.getNode(nn_key));                                      //ADD TO THE QUEUE

                }
            }
        }
        return chek_Visit(myQueue);                                     //Checks if all the nodes are visited

    }

    private boolean chek_Visit(Collection<node_info> nodeList) {

        for (node_info n :nodeList) { //For each node of my graph
            if (n.getTag()==0){                     //If there is one Node that is not visited
                return false;                       //th graph cant be connected
            }
        }
        return true;
    }


//----------------------------------------------Shortest Path & Dist--------------------------------------------------//
    @Override
    public double shortestPathDist(int src, int dest) {
        node_info source = myWgraph.getNode(src);
        node_info destination =myWgraph.getNode(dest);

   if(!myWgraph.getV().isEmpty()&&getGraph().getV().contains(destination)&&getGraph().getV().contains(source)){

       Dijkstra((WGraph_DS) myWgraph,src);

       if (destination.getTag()<Double.POSITIVE_INFINITY){
                 return destination.getTag();
             }
        }
        return -1;
    }

    @Override
    public List<node_info> shortestPath(int src, int dest) {

        node_info source = myWgraph.getNode(src);
        node_info destination =myWgraph.getNode(dest);
        LinkedList<node_info> final_path =new LinkedList<>();

        if(!myWgraph.getV().isEmpty()&&getGraph().getV().contains(destination)&&getGraph().getV().contains(source)){

            Dijkstra((WGraph_DS) myWgraph,src);

            String s="";
            for (node_info node:this.myWgraph.getV()) {
              //  System.out.println("key: "+node.getKey()+" final_tag: "+node.getTag()+" final_info: "+node.getInfo());
             s=node.getInfo();
            }
            String[] splited =s.split("-");

            for (int i=0;i<splited.length;i++){
                final_path.add(this.myWgraph.getNode(Integer.parseInt(splited[i])));
            }
        }

        return final_path;
    }

//--------------------------------------------------Shortest Path Helpers---------------------------------------------//

    //------------------------------Initialize All Tag To inifinit:
    public void InitTag(WGraph_DS g){

        for (node_info ni: g.getV()) {

                ni.setTag(Double.POSITIVE_INFINITY);

        }
    }

//------------------------------------------Dijkstra Algorithm :

     public void Dijkstra(WGraph_DS g, int src) {

     PriorityQueue<node_info> pqueue =new PriorityQueue<>(3,comp);

        int V =this.myWgraph.nodeSize();

   //------------------------Clear visited List;
         visited.clear();

   //------------------------First Step : Init All Tag To Infinity Value :

    InitTag(g);

   //------------------------Add source node to  my PriorityQueue :

    pqueue.add( g.getNode(src));

   //------------------------Distance from node to itself is 0

    g.getNode(src).setTag(0);

   //------------------------Set Info of node to source

    g.getNode(src).setInfo(src+"");



    while (!pqueue.isEmpty()) {
        //----------------------------------u removed from priorityQueue
        node_info u = pqueue.remove();
        //---------------------------------- add node to finalized list (visited)
        visited.add(u);
        Djikstra_graphNi(u,pqueue,g);
    }



}

//------------------------Dijkstra Helper :

    private void Djikstra_graphNi(node_info u,PriorityQueue pq,WGraph_DS g)   {

        double edgDist  = -1;
        double newDist  = -1;

        //------------------------------------------ Pass to all neighbour of u
        for (node_info ni:g.getV(u.getKey())) {
            //---------------------------------------------Take Value of the current node
            ni.setInfo(u.getInfo());
            node_info v = ni;

            //-------------------------------if the current node is not 'visited'
            if (!visited.contains(ni)) {

                edgDist = g.getEdge(v.getKey(),u.getKey());

                newDist = u.getTag() + edgDist;

                // Compare distances
                if (newDist < v.getTag()) {
                    pq.remove(v);
                    v.setTag(newDist);
                    pq.add(v);

                    ni.setInfo(ni.getInfo()+"-"+v.getKey());


                }

                // Add the current vertex to the PriorityQueue

                visited.add(ni);
            }
        }


    }



//---------------------------------------------------Save & Load------------------------------------------------------//

    @Override
    public boolean save(String file) {

        try {
            File f =new File(file);
            FileOutputStream newfile  = new FileOutputStream(f);
            ObjectOutputStream out = new ObjectOutputStream(newfile);

            out.writeObject(this.myWgraph);

            out.close();
            newfile.close();

            return true;
        }

        catch (IOException e ){
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean load(String file) {

        try { FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            weighted_graph mz =(weighted_graph)in.readObject();
            this.myWgraph=mz;
            in.close();
            fileIn.close();
            return true; }

        catch (IOException | ClassNotFoundException i)
        { i.printStackTrace(); }
        return false;
}
}

//----------------------------------------------------END CODE-----------------------------------------------------------//
