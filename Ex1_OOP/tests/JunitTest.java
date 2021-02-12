package ex1.tests;

import ex1.src.WGraph_Algo;
import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class JunitTest {


    //----------------Created 1 000 000 Millions Nodes & 10 000 000 Running time ==> (47 s Maximum)-------------------//
    //---------------------------------Using Randomly Graph Creator:
    private static Random _rnd = null;

    @Test
    void Million_node() {

        weighted_graph g1 = graph_creator(1000000, 10000000, 1);
        assertEquals(10000000, g1.edgeSize());
        WGraph_Algo ag1 = new WGraph_Algo();
        ag1.init(g1);
        assertTrue(ag1.isConnected());

    }

    @Test
    void HasEdges() {

        weighted_graph g1 = graph_creator(3, 3, 1);
        assertEquals(3, g1.edgeSize());
        WGraph_Algo ag1 = new WGraph_Algo();
        ag1.init(g1);
        assertTrue(ag1.isConnected());
        g1.removeNode(1);
        assertFalse(g1.hasEdge(1,3));
        assertTrue(ag1.isConnected());
    }

    ///////////////////////////////////
    /**
     * Generate a random graph with v_size nodes and e_size edges
     * @param v_size
     * @param e_size
     * @param seed
     * @return
     */
    public static weighted_graph graph_creator(int v_size, int e_size, int seed) {
        weighted_graph g = new WGraph_DS();
        _rnd = new Random(seed);
        for(int i=0;i<v_size;i++) {
            g.addNode(i);
        }
        // Iterator<node_data> itr = V.iterator(); // Iterator is a more elegant and generic way, but KIS is more important
        int[] nodes = nodes(g);
        while(g.edgeSize() < e_size) {
            int a = nextRnd(0,v_size);
            int b = nextRnd(0,v_size);
            int i = nodes[a];
            int j = nodes[b];
            double w = _rnd.nextDouble();
            g.connect(i,j, w);
        }
        return g;
    }
    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0+min, (double)max);
        int ans = (int)v;
        return ans;
    }
    private static double nextRnd(double min, double max) {
        double d = _rnd.nextDouble();
        double dx = max-min;
        double ans = d*dx+min;
        return ans;
    }
    /**
     * Simple method for returning an array with all the node_data of the graph,
     * Note: this should be using an Iterator<node_edge> to be fixed in Ex1
     * @param g
     * @return
     */
    private static int[] nodes(weighted_graph g) {
        int size = g.nodeSize();
        Collection<node_info> V = g.getV();
        node_info[] nodes = new node_info[size];
        V.toArray(nodes); // O(n) operation
        int[] ans = new int[size];
        for(int i=0;i<size;i++) {ans[i] = nodes[i].getKey();}
        Arrays.sort(ans);
        return ans;
    }
}


