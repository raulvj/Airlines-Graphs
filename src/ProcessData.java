
import java.io.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessData {
    public static void main(String[] args) {

        Graph<DecoratedElement<Airport>,Edge> graph = new TreeMapGraph<DecoratedElement<Airport>,Edge>();

        processData(graph); //We call the method to process files: 'airports.dat' 'routes.dat'

        showAirports(graph);

        showFlights(graph);

        showReport(graph);
    }

    private static void processData(Graph<DecoratedElement<Airport>,Edge> graph){
        //String pattern = "\"(Spain|Italy|Germany|France)\"";
        //Pattern r = Pattern.compile(pattern);
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader("src/airports.dat"));
            String read = null;
            while ((read = in.readLine()) != null) {
                String[] splited = read.split(",");
                if (splited[3].equals("\"Spain\"")|| splited[3].equals("\"Italy\"") || splited[3].equals("\"France\"") || splited[3].equals("\"Germany\""))
                {
                    //Use the openflights ID value for the airport identifier.
                    System.out.println(splited[1]+" "+splited[0]);
                    //FIXME Check if works.
                    if(splited[4] != null){
                        Airport a = new Airport( splited[4],  splited[1],  splited[3],  Double.parseDouble(splited[6]),  Double.parseDouble(splited[7]));
                        DecoratedElement<Airport> decoratedElement = new DecoratedElement<Airport>(a.getID(),a);
                        graph.insertVertex(decoratedElement);
                    }
                }
            }

            in = new BufferedReader(new FileReader("src/routes.dat"));
            Vertex<DecoratedElement<Airport>> u; // splited[2];
            Vertex<DecoratedElement<Airport>> v; // splited[4];
            while ((read = in.readLine()) != null) {
                String[] splited = read.split(",");

                //Use the openflights ID value.
                System.out.println("Origin ID: "+splited[3]+", Arrival ID: "+splited[5]);
                //FIXME Check if works.
                u = graph.getVertex(splited[2]); // splited[2];
                v = graph.getVertex(splited[4]); // splited[4];

                if(u != null && v != null){
                    graph.insertEdge(u,v);
                }
            }
        } catch (IOException e) {
            System.out.println("There was a problem: " + e);
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void showAirports(Graph g){

        /*For each Vertex in Graph
        Print airportID/airportName/airportCountry;
        */

        for (Iterator<Vertex<Airport>> item = g.getVertices(); item.hasNext();) {
            System.out.printf("Airport ID: %s, Airport name: %s, Airport country: %s", item.next().getElement().getID(), item.next().getElement().getName(), item.next().getElement().getCountry());
        }
    }

    private static void showFlights(Graph g){

        /*For each Edge in Graph
        Print routeID/originID+Name/destinationID+Name
        */

        for (Iterator<Edge<Route>> item = g.getEdges(); item.hasNext();) {
            System.out.printf("Flights: OriginAirport - DestinationAirport\n%s - %s", item.next().getElement().getSourceAirport(), item.next().getElement().getDestinationAirport());
        }
    }

    private static void showReport(Graph g){

        /*For each Vertex in Graph
            Vertex fartherWest = null;
            Vertex fartherNorth = null;

            currentLong = 0;
            If ((v.getLongitude < 0) && (v.getLongitude < currentLong)){
                fartherWest = v;
                currentLong = v.getLongitude;
            }
            currentLat = 0;
            If ((v.getLatitude > 0) && (v.getLatitude > currentLat)){
                fartherNorth = v;
                currentLat = v.getLatitude;
            }

            connections = 0;
            Vertex mostConnections = null;
            If (v.getConnections > connections){
                mostConnection = v;
                connections = v.getConnections;
            }
        */
        Vertex<Airport> fartherWest = null;
        Vertex<Airport> fartherNorth = null;
        Vertex<Airport> mostconnected = null;
        double currentLong = 0;
        double currentLat = 0;
        int connections = 0;
        Vertex<DecoratedElement<Airport>> v = null;
        for (Iterator<Vertex<Airport>> item = g.getVertices(); item.hasNext(); ) {
            if((item.next().getElement().getLongitude() < 0) && (item.next().getElement().getLongitude() < currentLong)){
                fartherWest = item.next();
                currentLong = item.next().getElement().getLongitude();
            }

            if((item.next().getElement().getLatitude() > 0) && (item.next().getElement().getLatitude() > currentLat)){
                fartherNorth = item.next();
                currentLat = item.next().getElement().getLatitude();
            }

            if(item.next().getElement().getConnections() > connections){
                mostconnected = item.next();
                connections = item.next().getElement().getConnections();
            }
        }

        System.out.printf("FartherWest: %s", fartherWest.getElement().getID());
        System.out.printf("FartherNorth: %s", fartherNorth.getElement().getID());
        System.out.printf("Most connections: %s with %d connections", mostconnected.getElement().getID(),mostconnected.getElement().getConnections());
    }
}
