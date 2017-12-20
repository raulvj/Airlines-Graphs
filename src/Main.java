import java.io.*;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Graph<DecoratedElement<Airport>,Edge> graph = new TreeMapGraph<DecoratedElement<Airport>,Edge>();
        
        processData(graph); //We call the method to process files: 'airports.dat' 'routes.dat'
        
        System.out.printf("There is a total of %d airports between the 4 countries.\n", graph.getN());
        System.out.printf("The total amount of flights between them is %d.\n", graph.getM());
        
        //showAirports(graph);

        //showFlights(graph);

        showReport(graph);
        
        //spyGame(graph);
    }

    private static String[] removeDoubleQuotes(String[] splited) {
    	String[] result = new String[splited.length];
    	for(int i=0; i < splited.length; i++) {
    		result[i] = splited[i].replace("\"", "");
    	}
    	return result;
    }
    
	@SuppressWarnings("rawtypes")
	private static void processData(Graph<DecoratedElement<Airport>, Edge> graph){
        BufferedReader in = null, in2 = null;
        
        try {
            in = new BufferedReader(new FileReader("src/airports.dat"));
            String read;
            Airport airport;
            DecoratedElement<Airport> decoratedElement;            
            
            while ((read = in.readLine()) != null) {
                String[] splited = read.split(",");
                String[] withoutQuote;
                if (splited[3].equals("\"Spain\"") || splited[3].equals("\"Italy\"") || splited[3].equals("\"France\"") || splited[3].equals("\"Germany\"")){
                    //Use the IATA code for the airport identifier.
                	withoutQuote = removeDoubleQuotes(splited);
                    if(! splited[4].equals("\\N")){
                        airport = new Airport(withoutQuote[4], withoutQuote[1], withoutQuote[3], Double.parseDouble(withoutQuote[6]), Double.parseDouble(withoutQuote[7]), Double.parseDouble(withoutQuote[8]));
                        decoratedElement = new DecoratedElement<Airport>(airport.getID(),airport);
                        graph.insertVertex(decoratedElement);
                    }
                }
            }
            
            in2 = new BufferedReader(new FileReader("src/routes.dat"));
            DecoratedElement<Airport> u;
            DecoratedElement<Airport> v;
            
            while ((read = in2.readLine()) != null) {
                String[] splited2 = read.split(",");
                //Use the IATA code.
                //FIXME Check if works.
                
                try {
                	//System.out.println(String.format("\"%s\"", splited2[2]));
                	
                	u = graph.getVertex(splited2[2]).getElement();
                    v = graph.getVertex(splited2[4]).getElement();

                    if((u != null) && (v != null)){
                    	//Create edge (algorithm to avoid repeated edges!)
                        //System.out.println(graph.insertEdge(u,v).getID());
                        //System.out.println(graph.insertEdge(u, v).getID());
                    	
                        u.getElement().setConnections(u.getElement().getConnections() + 1);
                        v.getElement().setConnections(v.getElement().getConnections() + 1);
                    }
                }catch (NullPointerException e) {
                	//System.out.println(e.getMessage());
                	//e.printStackTrace();
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
    
    @SuppressWarnings({ "rawtypes", "unused" })
	private static void showAirports(Graph<DecoratedElement<Airport>, Edge> graph){
    	DecoratedElement<Airport> object;
    	System.out.println("\nAirport information:");
    	
        for (Iterator<Vertex<DecoratedElement<Airport>>> item = graph.getVertices(); item.hasNext();) {
        	object = item.next().getElement(); 
            System.out.printf("IATA: %s\t Country: %s\t Airport name: %s\n", object.getElement().getID(), object.getElement().getCountry(), object.getElement().getName());
        }
    }

    @SuppressWarnings({ "rawtypes", "unused" })
	private static void showFlights(Graph<DecoratedElement<Airport>, Edge> graph){
        Edge<Edge> object;
        Vertex<DecoratedElement<Airport>>[] vertices;
        System.out.println("\nFlights: Origin Airport - Destination Airport");
        
        for (Iterator<Edge<Edge>> item = graph.getEdges(); item.hasNext();) {
        	object = item.next();
        	vertices = graph.endVertices(object);
            System.out.printf("%s - %s\n", vertices[0].getElement().getElement().getName(), vertices[1].getElement().getElement().getName());
        }
    }

    @SuppressWarnings({ "rawtypes", "unused" })
	private static void showReport(Graph<DecoratedElement<Airport>, Edge> graph){ //Add AVG altitude.
        DecoratedElement<Airport> fartherWest = null;
        DecoratedElement<Airport> fartherNorth = null;
        DecoratedElement<Airport> mostconnected = null;
        DecoratedElement<Airport> test; //the current object of the iterator.
        
        double currentLong = 0;
        double currentLat = 0;
        int connections = 0;
        int avg_altitude = 0;

        try {
            for (Iterator<Vertex<DecoratedElement<Airport>>> item = graph.getVertices(); item.hasNext();) {
            	//Check if the item has appear before.
            	test = item.next().getElement();
                if((test.getElement().getLongitude() < 0) && (test.getElement().getLongitude() < currentLong)){
                    fartherWest = test;
                    currentLong = fartherWest.getElement().getLongitude();
                }

                if((test.getElement().getLatitude() > 0) && (test.getElement().getLatitude() > currentLat)){
                    fartherNorth = test;
                    currentLat = fartherNorth.getElement().getLatitude();
                }

                if(test.getElement().getConnections() > connections){
                    mostconnected = test;
                    connections = mostconnected.getElement().getConnections();
                }
                
                avg_altitude += test.getElement().getAltitude();
            }
            double value = (avg_altitude/(graph.getN()));
            System.out.printf("\nThe airport which is farther west is: %s in %s.", fartherWest.getElement().getName(), fartherWest.getElement().getCountry());
            System.out.printf("\nThe airport which is farther north is: %s in %s.", fartherNorth.getElement().getName(),fartherNorth.getElement().getCountry());
            System.out.printf("\nThe airport with the most connections is: %s with %d connections.", mostconnected.getElement().getName(), mostconnected.getElement().getConnections());
            System.out.printf("\nThe average of altitude of all the airports between the 4 countries selected is: %.2f feets.\n", value);
        }
        catch(NoSuchElementException e){
        	e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
	private static void depthFirstSearch(Graph<DecoratedElement<Airport>, Edge> g, Vertex<DecoratedElement<Airport>> origin){
        /*
        Input: graph G and a start vertex v of G
        Output: a labeling of the edges of G in the connected component of v as DISCOVERY edges and BACK edges

        //PreVisit(v); //perform some action at node v
        label v as VISITED
        for all edge e incident in v do
            if edge e is UNEXPLORED
                w <- G.opposite(v,e) //returns the endvertex of edge e
                                 //different from v (vertex adjacent to v)
                if vertex w is UNVISITED
                    label e as a DISCOVERY edge //it leads to an unvisited vertex //if needed
                    DFS(G,w)
                else
                    label e as a BACK edge
                end_if
            end_if
        end_for
        PostVisit(v) //perform some action at node v
        */
    }

    @SuppressWarnings("unused")
	private static void breadthFirstSearch(Graph<DecoratedElement<Airport>, Edge> g, Vertex<DecoratedElement<Airport>> origin){
        /*
        Input: a graph G with all its vertices marked as unvisited and a start vertex v of G
        Create an empty queue Q
        Enqueue v in Q
        set v as VISITED
        while Q not empty do
            u <- Apply a dequeue in Q
            Visit u
            for each vertex z adjacent to u do
                if z is UNVISITED and z is not in Q then
                    set z as VISITED
                    set u as node parent of z
                    Enqueue z in Q
                end_if
            end_for
        end_while
        */
    }
    
    @SuppressWarnings("unused")
	private static void spyGame(Graph<DecoratedElement<Airport>, Edge> graph) {
        //Read by keyboard the airport Source and Destination.
    	Scanner keyboard = new Scanner(System.in);
    	
    	System.out.println("Introduce the IATA code of the origin airport:");
    	String origin = keyboard.next();
    	
    	System.out.println("Introduce the IATA code of the destination airport:");
    	String destination = keyboard.next();
    	
    	
        //depthFirstSearch(graph, origin);
        //breadthFirstSearch(graph, origin);
    	keyboard.close();
	}
}
