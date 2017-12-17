
import java.io.*;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessData {
    public static void main(String[] args) {
        processData(); //We call the method to process files: 'airports.dat' 'routes.dat'

    }

    private static void processData(){
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
                    //FIXME Still need to implement the node adding.
                }
            }

            in = new BufferedReader(new FileReader("src/routes.dat"));
            while ((read = in.readLine()) != null) {
                String[] splited = read.split(",");

                //Use the openflights ID value.
                System.out.println("Origin ID: "+splited[3]+", Arrival ID: "+splited[5]);
                //FIXME Still need to check if origin and arrival nodes exist and create the edge.
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

    private static void showAirports(Graph G){
        System.out.println("Airport");
        /*For each Vertex in Graph
        Print airportID/airportName/airportCountry;
        */
    }

    private static void showFlights(Graph G){
        System.out.println("Flights");
        /*For each Edge in Graph
        Print routeID/originID+Name/destinationID+Name
        */
    }

    private static void showReport(Graph G){
        /*For each Vertex in Graph
            current = 0;
            Vertex fartherWest = null;
            If v.getAltitude > current
                fartherWest = v;
                current = v.getAltitude;
            //Same with North;

            connections = 0;
            Vertex mostConnection = null;
            If v.getConnections > connections
                mostConnection = v;
                connections = v.getConnections;
        */
    }
}
