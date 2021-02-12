package api;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


class DWGraph_AlgoTest {

    @Test
    void copy() {

        directed_weighted_graph g = new DWGraph_DS();

        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(3));

        g.connect(1,2,4);
        g.connect(1,3,50);

        dw_graph_algorithms ag =  new DWGraph_Algo();
        ag.init(g);
        directed_weighted_graph k = ag.copy();

        assertEquals(g.toString(),k.toString());
        g.removeEdge(1,2);
        assertNotEquals(g.toString(),k.toString());
    }

    @Test
    void isConnected() {

        directed_weighted_graph g = new DWGraph_DS();

        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(3));

        dw_graph_algorithms ag =  new DWGraph_Algo();
        ag.init(g);

        g.connect(1,2,4); // 1 --> 2
        assertFalse(ag.isConnected());

        g.connect(2,3,15); // 2 --> 3
        assertFalse(ag.isConnected());

        g.connect(3,2,45);
        assertFalse(ag.isConnected());

        g.connect(3,2,166); // 3 --> 2
        assertFalse(ag.isConnected());

        g.connect(2,1,14); // 2 --> 1 (now strongly connected)
        assertTrue(ag.isConnected());

    }

    @Test
    void reverseGraph(){
        directed_weighted_graph g = new DWGraph_DS();

        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(3));

        g.connect(1,2,4);
        g.connect(1,3,5);
        g.connect(2,1,3);

        directed_weighted_graph ag = new DWGraph_DS();
        ag = ((DWGraph_DS) g).reverseGraph();

      //  System.out.println("reversed g.toString: "+ag.toString());

    }

    @Test
    void shortestPathDist() {
        directed_weighted_graph g = new DWGraph_DS();


        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(3));
        g.addNode(new NodeData(4));
        g.addNode(new NodeData(5));
        g.addNode(new NodeData(6));

        dw_graph_algorithms ag =  new DWGraph_Algo();
        ag.init(g);

        g.connect(1,6,3000);
        g.connect(1,2,30);
        g.connect(2,5,4);
        g.connect(5,6,38);

        Double d = ag.shortestPathDist(1,6);
        assertEquals(72,d);
    }

    @Test
    void shortestPath() {
        directed_weighted_graph g = new DWGraph_DS();

        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(3));
        g.addNode(new NodeData(4));
        g.addNode(new NodeData(5));
        g.addNode(new NodeData(6));

        dw_graph_algorithms ag =  new DWGraph_Algo();
        ag.init(g);

        g.connect(1,6,4000);
        g.connect(1,2,3000);
        g.connect(2,5,4);
        g.connect(5,6,38);
        g.connect(1,6,2);


        System.out.println("Distance de 1 a 6 = "+ag.shortestPathDist(1,6));


        List<node_data> sp = ag.shortestPath(1,6);

        int[] checkKey = {1,2,5,6};

        int i = 0;
        for(node_data n: sp) {
            System.out.print(n.getKey()+",");
            assertEquals(n.getKey(), checkKey[i]);
            i++;
        }

        g.connect(1,6,2);

        sp = ag.shortestPath(1,6);
        int[] checkKey2 = {1,6};

        i = 0;
        for(node_data n: sp) {
            assertEquals(n.getKey(), checkKey2[i]);
            i++;
        }



    }

    @Test
    void saveload() {

        directed_weighted_graph graph1 = new DWGraph_DS();

        graph1.addNode(new NodeData(0));
        graph1.addNode(new NodeData(1));
        graph1.addNode(new NodeData(2));

        graph1.connect(0,1,3000);
        graph1.connect(0,2,30);

        graph1.getNode(0).setLocation(new GeoLocation(1245,12165.5,454.6));
        graph1.getNode(1).setLocation(new GeoLocation(14.67,1.2,6.48));
        graph1.getNode(2).setLocation(new GeoLocation(45.4,777,1.023));


        dw_graph_algorithms first =  new DWGraph_Algo();
        first.init(graph1);
        first.save("test.json");

        ////////

        directed_weighted_graph graph2 = new DWGraph_DS();
        dw_graph_algorithms second     = new DWGraph_Algo(graph2);

        second.load("test.json");


       // System.out.println("\n\nfirst.getGraph = \n" +first.getGraph().toString());
       // System.out.println("\nsecond.getGraph = \n"+second.getGraph().toString());

        assertEquals(first.getGraph().toString(), second.getGraph().toString());
    }

}