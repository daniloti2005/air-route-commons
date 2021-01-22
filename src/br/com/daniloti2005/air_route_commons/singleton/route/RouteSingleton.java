package br.com.daniloti2005.air_route_commons.singleton.route;


import br.com.daniloti2005.air_route_commons.interpreter.dijkstra.Edge;
import br.com.daniloti2005.air_route_commons.interpreter.dijkstra.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/***
 * Route Pool
 */
public final class RouteSingleton {
    
    private static RouteSingleton INSTANCE;
    private static String info = "This class is responsible to create a Route´s Pool";
    private static List<String> lstRoute;
    private static Map<String, Node> mapRoute;

    /***
     * Constructor doing nothing :)
     */
    private RouteSingleton() {

    }

    /***
     * Return a single INSTANCE of this Pool Class.
     * @return INSTANCE
     * @throws Exception
     */
    public static RouteSingleton getInstance() throws Exception {
        if (INSTANCE == null) {
            INSTANCE = new RouteSingleton();
        }

        return INSTANCE;
    }

    public static void setMapRoute(Map<String, Node> mapRouteParam) {
        mapRoute = mapRouteParam;
    }

    public static Map<String, Node> getMapRoute() {
        return mapRoute;
    }

    /***
     * set a Route to this Singleton pool.
     */
    public static void setRoute(String route){
        if (lstRoute == null) {
            lstRoute = new ArrayList<>();
            try {
                lstRoute.add(route.toString());
            } catch (Exception exception) {
                throw exception;
            }
            
        }
    }

    /***
     * get a list of Routes.
     * @return
     */
    public static List<String> getListRoutes() {
        return lstRoute;
    }


    /***
     * Reset Route's pool.
     */
    public static void reset() {
        lstRoute.clear();
    }

    /***
     *  Returns Information concerns to class responsibilities.
     * @return info
     */
    public static String getInfo() {
        return info;
    }
    
}
