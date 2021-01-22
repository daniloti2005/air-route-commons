package br.com.daniloti2005.air_route_commons.interpreter.dijkstra;

import java.util.*;

class Main {

    final static String NODE_A = "A";
    final static String NODE_B = "B";
    final static String NODE_C = "C";
    final static String NODE_D = "D";
    final static String NODE_E = "E";

    public static void main(String [] args) throws Exception {
        /**
         *
         * To construct the Node classes properly:
         *  1. Give it a static identifier at the top to make it easier to refer to
         *  2. Initial distance from origin is MAX_VALUE for all Nodes
         *  3. All Nodes' previousNode values should be initialized to null
         */
//        Node BRC = new Node(NODE_A, Integer.MAX_VALUE, null);
//        Node CDG = new Node(NODE_B, Integer.MAX_VALUE, null);
//        Node ORL = new Node(NODE_C, Integer.MAX_VALUE, null);
//        Node GRU = new Node(NODE_D, Integer.MAX_VALUE, null);
//        Node SCL = new Node(NODE_E, Integer.MAX_VALUE, null);

        Node BRC = new Node("BRC");
        Node CDG = new Node("CDG");
        Node ORL = new Node("ORL");
        Node GRU = new Node("GRU");
        Node SCL = new Node("SCL");

        String csvIn = """
            GRU,BRC,10
            BRC,SCL,5
            GRU,CDG,75
            GRU,SCL,20
            GRU,ORL,56
            ORL,CDG,5
            SCL,ORL,20
                """;
        Set<String> setNodes = new HashSet<>();

        for (String line : csvIn.split("\n")){
            String[] arrItems = line.split(",");
            setNodes.add(arrItems[0]);
            setNodes.add(arrItems[1]);
        }

        System.out.println(setNodes);


        System.out.println(csvIn);



        /**
         * To construct the Edges properly:
         *  1. For each Node, list the edges in an array, then set them
         *  2. The format for a new Edge is a reference to the Node at the end of the edge and the edge's length
         */

        Edge[] brcEdges = {new Edge(SCL, 5)};
        Edge[] cdgEdges = {};
        Edge[] orlEdges = {new Edge(CDG, 5)};
        Edge[] gruEdges = {new Edge(BRC, 10),
                           new Edge(CDG, 75),
                           new Edge(SCL, 20),
                           new Edge(ORL, 56)};
        Edge[] sclEdges = {new Edge(ORL, 20)};

        BRC.setEdges(Arrays.asList(brcEdges.clone()));
        CDG.setEdges(Arrays.asList(cdgEdges.clone()));
        ORL.setEdges(Arrays.asList(orlEdges.clone()));
        GRU.setEdges(Arrays.asList(gruEdges.clone()));
        SCL.setEdges(Arrays.asList(sclEdges.clone()));

        Route route = new Route();

        route.addNode(BRC);
        route.addNode(CDG);
        route.addNode(ORL);
        route.addNode(GRU);
        route.addNode(SCL);

        //Graph graph = new Graph(Arrays.asList(nodes.clone()));
        Graph graph = new Graph(route.getNodes());

        // The paths below differ because the graph is bidirectional and B->D=25 while D->B=2
        //Results
        System.out.println("results =>");

        // The paths below differ because the graph is bidirectional and B->D=25 while D->B=2
        List<Node> retorno =
                graph.dijkstra(route.getNodeFromMap("GRU"),
                route.getNodeFromMap("CDG"));

        try {
            List<Node> solution = graph.dijkstra(GRU, CDG);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Node item : retorno){
            System.out.println(item.getName()+" - "+item.getDistanceFromOrigin()) ;
        }


    }
}
