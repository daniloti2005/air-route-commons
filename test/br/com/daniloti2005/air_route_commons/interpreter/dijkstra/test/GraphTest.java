package br.com.daniloti2005.air_route_commons.interpreter.dijkstra.test;

import br.com.daniloti2005.air_route_commons.DijkstraAlgorithm;
import br.com.daniloti2005.air_route_commons.interpreter.dijkstra.Graph;
import br.com.daniloti2005.air_route_commons.interpreter.dijkstra.Node;
import br.com.daniloti2005.air_route_commons.interpreter.dijkstra.Route;

import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    @org.junit.jupiter.api.Test
    void dijkstra() throws Exception {
        Route route = new Route();
        route.makeRoute("GRU","BRC",10);
        route.makeRoute("BRC","SCL",5);
        route.makeRoute("GRU","CDG",75);
        route.makeRoute("GRU","SCL",20);
        route.makeRoute("GRU","ORL",56);
        route.makeRoute("ORL","CDG",5);
        route.makeRoute("SCL","ORL",20);

        //Graph graph = new Graph(Arrays.asList(nodes.clone()));
        Graph graph = new Graph(route.getNodes());

        // The paths below differ because the graph is bidirectional and B->D=25 while D->B=2
        List<Node> retorno = graph.dijkstra(route.getNodeFromMap("GRU"), route.getNodeFromMap("CDG"));

        for (Node item : retorno){
            System.out.println(item.getName()+" - "+item.getDistanceFromOrigin()) ;
        }

        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(route.getNodeFromMap("GRU"),
                                                                    route.getNodeFromMap("CDG"),
                                                                    route);



        List<Node> returnNewAlgorithms = dijkstraAlgorithm.perform();

        for (Node item : retorno){
            System.out.println(item.getName()+ " - "+item.getDistanceFromPrevious());
        }
        System.out.println("Total Cost"+retorno.get(retorno.size()).getDistanceFromOrigin());
    }
}