package br.com.daniloti2005.air_route_commons.interpreter.dijkstra;

import java.util.Arrays;

class Main {

    final static String NODE_A = "A";
    final static String NODE_B = "B";
    final static String NODE_C = "C";
    final static String NODE_D = "D";

    public static void main(String [] args)
    {
        /**
         *
         * To construct the Node classes properly:
         *  1. Give it a static identifier at the top to make it easier to refer to
         *  2. Initial distance from origin is MAX_VALUE for all Nodes
         *  3. All Nodes' previousNode values should be initialized to null
         */
//        Node a = new Node(NODE_A, Integer.MAX_VALUE, null);
//        Node b = new Node(NODE_B, Integer.MAX_VALUE, null);
//        Node c = new Node(NODE_C, Integer.MAX_VALUE, null);
//        Node d = new Node(NODE_D, Integer.MAX_VALUE, null);

        Node a = new Node(NODE_A);
        Node b = new Node(NODE_B);
        Node c = new Node(NODE_C);
        Node d = new Node(NODE_D);

        /**
         * To construct the Edges properly:
         *  1. For each Node, list the edges in an array, then set them
         *  2. The format for a new Edge is a reference to the Node at the end of the edge and the edge's length
         */
        Edge[] aEdges = {new Edge(b, 5), new Edge(c, 4)};
        Edge[] bEdges = {new Edge(d, 25), new Edge(a, 5)};
        Edge[] cEdges = {new Edge(d, 4), new Edge(a, 4)};
        Edge[] dEdges = {new Edge(b, 2), new Edge(c, 4)};

        a.setEdges(Arrays.asList(aEdges.clone()));
        b.setEdges(Arrays.asList(bEdges.clone()));
        c.setEdges(Arrays.asList(cEdges.clone()));
        d.setEdges(Arrays.asList(dEdges.clone()));

        Route route = new Route();

        route.addNode(a);
        route.addNode(b);
        route.addNode(c);
        route.addNode(d);


        //Graph graph = new Graph(Arrays.asList(nodes.clone()));
        Graph graph = new Graph(Node.getLstNode());

        // The paths below differ because the graph is bidirectional and B->D=25 while D->B=2
        System.out.println(graph.dijkstra(NODE_A, NODE_D));
        System.out.println(graph.dijkstra(NODE_D, NODE_A));
    }
}
