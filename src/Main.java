
import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingDeque;


/*********************************************************************
*
* Class Name: Main
* Author/s name: Raúl Valentín Jorge, Javier Romero Sánchez.
* Release/Creation date:22-12-2017
* Class version:3
* Class description: Contains the executable and the main methods.
*
***********************************************************************/
public class Main {
    public static void main(String[] args) {

        Graph<DecoratedElement<Airport>,Edge> graph = new TreeMapGraph<DecoratedElement<Airport>,Edge>();
        
        processData(graph); //We call the method to process files: 'airports.dat' 'routes.dat'
        
        System.out.printf("There is a total of %d airports between the 4 countries.\n", graph.getN());
        System.out.printf("The total amount of flights between them is %d.\n", graph.getM());
        
        showReport(graph);
        spyGame(graph);             
    }
    
    /*********************************************************************
    *
    * Method name: removeDoubleQuotes
    *
    * Name of the original author: Raúl Valentín Jorge
    *
    * Description of the Method: Remove the " of the name of the data.
    *
    * Calling arguments: @param splited
    *
    * Return value: @return result
    *
    *********************************************************************/
    private static String[] removeDoubleQuotes(String[] splited) {
    	String[] result = new String[splited.length];
    	
    	for(int i=0; i < splited.length; i++) {
    		result[i] = splited[i].replace("\"", "");
    	}
    	return result;
    }
    
    /*********************************************************************
    *
    * Method name: processData
    *
    * Name of the original author: Raúl Valentín Jorge, Javier Romero Sánchez.
    *
    * Description of the Method: Reads airports.dat and routes.dat and creates and the nodes and the edges of the graph.
    *
    * Calling arguments: @param graph
    *
    * Required Files: airports.dat routes.dat
    *
    * List of Checked Exceptions:
    * @exception IOException if the previous files are not found.
    * @exception NullPointerException if 
    *
    *********************************************************************/
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
                        airport = new Airport(withoutQuote[4], withoutQuote[1], withoutQuote[3],withoutQuote[2], Double.parseDouble(withoutQuote[6]), Double.parseDouble(withoutQuote[7]), Double.parseDouble(withoutQuote[8]));
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
                //Use the IATA code to identify the routes.
                
                try {               	
                	u = graph.getVertex(splited2[2]).getElement();
                    v = graph.getVertex(splited2[4]).getElement();

                    if((u != null) && (v != null) && !(graph.areAdjacent(graph.getVertex(splited2[2]), graph.getVertex(splited2[4])))){
                    	graph.insertEdge(u, v);
                        u.getElement().setConnections(u.getElement().getConnections() + 1);
                        v.getElement().setConnections(v.getElement().getConnections() + 1);
                    }
                }catch (NullPointerException e) {
                	//As there will be too many 'null' values, do nothing with them, just keep going.
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
    
    /*********************************************************************
    *
    * Method name: showReport
    *
    * Name of the original author : Raúl Valentín Jorge.
    *
    * Description of the Method: Goes through the whole graph and gets the number of airports, how many connections, the airport with most connections and the farthest west and farthest north one, calculates the average altitude.
    *
    * Calling arguments: @param graph
    *
    * List of Checked Exceptions:
    * 
    * @exception NoSuchElementException
    *
    *********************************************************************/
    @SuppressWarnings({ "rawtypes" })
	private static void showReport(Graph<DecoratedElement<Airport>, Edge> graph){
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
            System.out.printf("\nThe airport which is farther west is: %s in %s.", fartherWest.getElement().getName(), fartherWest.getElement().getCity());
            System.out.printf("\nThe airport which is farther north is: %s in %s.", fartherNorth.getElement().getName(),fartherNorth.getElement().getCity());
            System.out.printf("\nThe airport with the most connections is: %s with %d connections.", mostconnected.getElement().getName(), mostconnected.getElement().getConnections());
            System.out.printf("\nThe average of altitude of all the airports between the 4 countries selected is: %.2f feets.\n\n", value);
        }
        catch(NoSuchElementException e){
        	e.printStackTrace();
        }
    }
    
    /*********************************************************************
    *
    * Method name:depthFirstSearch()
    *
    * Name of the original author: Raúl Valentín Jorge, Javier Romero Sánchez.
    *
    * Description of the Method: Goes through the whole graph and creates a route to the destination. 
    *
    * Calling arguments:@param g, @param origin, @param dest, @param routes.
    *
    *********************************************************************/
    private static void depthFirstSearch(Graph<DecoratedElement<Airport>, Edge> g, Vertex<DecoratedElement<Airport>> origin, Vertex<DecoratedElement<Airport>> dest, ArrayList<Vertex<DecoratedElement<Airport>>> routes){                    	
        Vertex<DecoratedElement<Airport>> aux = null; //The node we are going to use as auxiliary.
        Edge ed = null;       
        origin.getElement().setVisited(true);
        Iterator<Edge<Edge>> it = g.incidentEdges(origin);
        routes.add(origin);
        
        while(it.hasNext()) {
        	ed = it.next();
        	aux = g.opposite(origin, ed);
        	if(! aux.getElement().isVisited()) {
        		aux.getElement().setVisited(true);
        		aux.getElement().setParent(origin.getElement());
        		
        		//END CONDITION
        		if(aux.getElement().getElement().getID().equals(dest.getElement().getElement().getID())) {
        			routes.add(dest);
        			System.out.println("\nThe route that he has followed is: ");

        			for(int i=0; i < routes.size(); i++) {
        				System.out.printf("/ %s", routes.get(i).getElement().getElement().getID());
        			}
        			break;
        		}else {
        			depthFirstSearch(g, aux, dest, routes);
        		}        		
        	}
        }             
    }
    
    /*********************************************************************
    *
    * Method name: breadthFirstSearch
    *
    * Name of the original author : Raúl Valentín Jorge, Javier Romero Sánchez.
    *
    * Description of the Method: Goes through the whole graph and gets the most optimal route to the destination.
    *
    * Calling arguments: @param g, @param origin, @param dest.
    *
    * List of Checked Exceptions:
    * 
    * NullPointerException: In the case that the node has no parent.
    *
    *********************************************************************/
    private static void breadthFirstSearch(Graph<DecoratedElement<Airport>, Edge> g, Vertex<DecoratedElement<Airport>> origin, Vertex<DecoratedElement<Airport>> dest){    	
    	Queue<Vertex<DecoratedElement<Airport>>> queue = new LinkedList();
    	Vertex<DecoratedElement<Airport>> u, v = null;
    	DecoratedElement<Airport> dec;
    	Edge ed;
    	
    	int length;
    	boolean noEnd = true;
    	
    	Iterator<Edge<Edge>> it1;
    	Iterator<Vertex<DecoratedElement<Airport>>> it2 = g.getVertices();
    	
    	while(it2.hasNext()) {
    		dec = it2.next().getElement();
    		dec.setVisited(false);
    		dec.setParent(null);
    	}   	

    	try {
        	origin.getElement().setVisited(true);
        	queue.offer(origin);
        	while(!queue.isEmpty() && noEnd) {
        		u = queue.poll();
        		it1 = g.incidentEdges(u);
        		
        		while(it1.hasNext() && noEnd) {
        			ed = it1.next();
        			v = g.opposite(u, ed);
        			
        			if(v.getElement().getParent() == null) {
        				v.getElement().setParent(u.getElement());
        			}
        			queue.offer(v);
        			
        			if(v.getElement().equals(dest.getElement())) {
        				noEnd = false;        				
        			}
        		}       		       		
        	}
        	       	
        	System.out.println("\nThe route that the spy has followed is: ");

        	Deque<DecoratedElement<Airport>> end = new LinkedBlockingDeque();
        	end.add(v.getElement());
        	DecoratedElement<Airport> aux = v.getElement().getParent();
        		
        	while(!aux.getElement().getID().equals(origin.getElement().getElement().getID())) {        			
        		end.add(aux);
        		aux = aux.getParent();      			
        	}
        		
        	if(aux.getElement().getID().equals(origin.getElement().getElement().getID())) {
        		end.add(origin.getElement());
        	}        		
        		
        	while(! end.isEmpty()) {
        		System.out.printf("/ %s ", end.removeLast().getElement().getID());
        	}
        	
    	}catch(NullPointerException e) {
    		//As we did before, keep working.
    	}
    }
    
    /*********************************************************************
    *
    * Method name:spyGame
    *
    * Name of the original author : Raúl Valentín Jorge, Javier Romero Sánchez
    *
    * Description of the Method: Recreates the scenario of someone going from point A to B through one of the possible routes(depthFirstSearch.). 
    * Meanwhile, a spy has to go to the same point B by using the shortest route (breadthFirstSearch.). Request in the command line the ID of the 2
    * airports and prints the route each one has used.
    *
    * Calling arguments: @param graph
    *
    *********************************************************************/
    private static void spyGame(Graph<DecoratedElement<Airport>, Edge> graph) {
        //Read by keyboard the airport Source and Destination.
    	Scanner keyboard = new Scanner(System.in);
    	
    	System.out.print("\nIntroduce the IATA code of the origin airport: ");
    	String origin = keyboard.next();
    	Vertex<DecoratedElement<Airport>> ori = graph.getVertex(origin);
    	
    	System.out.print("\nIntroduce the IATA code of the destination airport: ");
    	String destination = keyboard.next();
    	Vertex<DecoratedElement<Airport>> dest = graph.getVertex(destination);
    	
    	ArrayList<Vertex<DecoratedElement<Airport>>> routes = new ArrayList<Vertex<DecoratedElement<Airport>>>();
        depthFirstSearch(graph, ori, dest, routes); //with this algorithm we will calculate a random route.
        breadthFirstSearch(graph, ori, dest); //with this algorithm we calculate the shortest path.
        
    	keyboard.close();
	}
}