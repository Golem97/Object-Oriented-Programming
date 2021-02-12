package api;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_DSTest {

    @org.junit.jupiter.api.Test
    void connect() {
       directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(3));

        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(1,3,3);
        g.connect(2,1,3);

        assertTrue(((DWGraph_DS) g).hasEdge(0,2)); //check if edge 0 --> 2 exists (created)
        assertFalse(((DWGraph_DS) g).hasEdge(2,0)); //check if edge 2 --> 0 exists (not created)

        g.connect(0,1,16.33); // checks if connect updates weight of existing edge
        assertEquals(g.getEdge(0,1).getWeight(),16.33);

        g.connect(0,1,-3); // checks if connect ignores negative weight input
        assertEquals(g.getEdge(0,1).getWeight(),16.33);

        g.connect(2,2,15); // check if connect ignores the process of creating an edge between a node and itself
        assertFalse(((DWGraph_DS) g).hasEdge(2,2));

        g.connect(0,12,5); // checks if connects ignores the process of creating an edge between an existing and a not existing node
        assertFalse(((DWGraph_DS) g).hasEdge(0,12));

        g.connect(0,1,1);
        g.connect(0,2,2);
        assertEquals(g.getEdge(0,1).getWeight(),1);
    }



    @org.junit.jupiter.api.Test
    void removeNode() {
        directed_weighted_graph g = new DWGraph_DS();

        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(3));

        g.connect(0,1,1);

        g.connect(0,2,2);
        assertEquals(g.getEdge(0,1).getWeight(),1);

        g.connect(0,3,3);

        assertTrue(g.getEdge(0,1) != null);

        g.removeNode(4);
        int e = g.edgeSize();
        assertEquals(3,e);

        g.removeNode(0);
        e = g.edgeSize();
        assertEquals(0,e);

        assertFalse(((DWGraph_DS) g).hasEdge(1,0));
        assertEquals(3,g.nodeSize());
}


    @org.junit.jupiter.api.Test
    void checkNeighbours() {
        directed_weighted_graph g = new DWGraph_DS();

        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.connect(1,2,1); // add node2 to node1 neighbour's map

        NodeData n1 = new NodeData((NodeData) g.getNode(1)); // checks if node2 in inside node1 neighbour's map (true)
        NodeData n2 = new NodeData((NodeData) g.getNode(2)); // checks if node2 in inside node1 neighbour's map (false)

        assertFalse(n2.getNi().containsKey(1));
        assertTrue(n1.getNi().containsKey(2));
    }

    @org.junit.jupiter.api.Test
    void removeEdge() {
        directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(3));

        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);


        g.removeEdge(0,3);
        assertEquals(null,g.getEdge(0,3));
    }




}
