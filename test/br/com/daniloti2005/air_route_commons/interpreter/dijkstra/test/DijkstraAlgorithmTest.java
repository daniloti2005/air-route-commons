package br.com.daniloti2005.air_route_commons.interpreter.dijkstra.test;

import br.com.daniloti2005.air_route_commons.DijkstraAlgorithm;
import br.com.daniloti2005.air_route_commons.interpreter.dijkstra.Node;
import br.com.daniloti2005.air_route_commons.interpreter.dijkstra.Route;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DijkstraAlgorithmTest {

    @Test
    void perform() {
        Route route = new Route();
        route.makeRoute("GRU","BRC",10);
        route.makeRoute("BRC","SCL",5);
        route.makeRoute("GRU","CDG",75);
        route.makeRoute("GRU","SCL",20);
        route.makeRoute("GRU","ORL",56);
        route.makeRoute("ORL","CDG",5);
        route.makeRoute("SCL","ORL",20);

        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(route.getNodeFromMap("GRU"),
                route.getNodeFromMap("CDG"),
                route);

        List<Node> retorno = dijkstraAlgorithm.perform();

        for (Node item : retorno){
            System.out.println(item.getName()+ " - "+item.getDistanceFromPrevious());
        }
        System.out.println("Total Cost"+retorno.get(retorno.size()).getDistanceFromOrigin());
    }
}