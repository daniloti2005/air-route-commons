package br.com.daniloti2005.air_route_commons.service.csv;

import br.com.daniloti2005.air_route_commons.interpreter.dijkstra.Route;
import br.com.daniloti2005.air_route_commons.singleton.route.RouteSingleton;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileService {
    private String path;
    private final String sep = File.separator;

    public FileService(){
        if (!path.trim().isEmpty()){
            setPath(path);
        }
    }

    public FileService(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String read() throws Exception {
        String ret = "";
        File file = new File(path);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line=br.readLine())!=null){
            ret += line;
        }
        br.close();
        fr.close();
        return ret;
    }

    public Route loadfromfile() throws Exception {
        Route ret = new Route();
        File file = new File(path);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line=br.readLine())!=null){
            String origin = line.split(",")[0];
            String destination = line.split(",")[1];
            String cost = line.split(",")[2];
            ret.makeRoute(origin, destination, Integer.valueOf(cost));
        }
        br.close();
        fr.close();
        return ret;
    }

    public void save() throws Exception {
        File file = new File(path);
        FileWriter fw = new FileWriter(file, StandardCharsets.UTF_8);
        BufferedWriter out = new BufferedWriter(fw);
        for (String route: RouteSingleton.getListRoutes()){
            out.write(route);
        }
        out.flush();
        out.close();
        fw.close();
    }

    public void save(String path) throws Exception {
        File file = new File(path);
        FileWriter fw = new FileWriter(file, StandardCharsets.UTF_8);
        BufferedWriter out = new BufferedWriter(fw);
        for (String route: RouteSingleton.getListRoutes()){
            out.write(route);
        }
        out.flush();
        out.close();
        fw.close();
    }
}
