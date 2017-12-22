
import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Scanner;


/*********************************************************************
*
* Class Name:
* Author/s name:
* Release/Creation date:
* Class version:
* Class description: A brief description of what the class does
*
**********************************************************************
*/
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
        
        //Vertex<DecoratedElement<Airport>> origin = graph.getVertex("TFS");
        //Vertex<DecoratedElement<Airport>> dest = graph.getVertex("VLC");
        //ArrayList<Vertex<DecoratedElement<Airport>>> routes = new ArrayList<Vertex<DecoratedElement<Airport>>>();
        //depthFirstSearch(graph, origin, dest, routes);
        //breadthFirstSearch(graph, origin, dest);       
    }
    
    /*********************************************************************
    *
    * Method name:
    *
    * Name of the original author (if the module author is different
    * than the author of the file):
    *
    * Description of the Method: A description of what the method does.
    *
    * Calling arguments: A list of the calling arguments, their types, and
    * brief explanations of what they do.
    *
    * Return value: it type, and a brief explanation of what it does.
    *
    * Required Files: A list of required files needed by the method,
    * indicating if the method expects files to be already opened (only if
    * files are used)
    *
    * List of Checked Exceptions and an indication of when each exception
    * is thrown.
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
    * Method name:
    *
    * Name of the original author (if the module author is different
    * than the author of the file):
    *
    * Description of the Method: A description of what the method does.
    *
    * Calling arguments: A list of the calling arguments, their types, and
    * brief explanations of what they do.
    *
    * Return value: it type, and a brief explanation of what it does.
    *
    * Required Files: A list of required files needed by the method,
    * indicating if the method expects files to be already opened (only if
    * files are used)
    *
    * List of Checked Exceptions and an indication of when each exception
    * is thrown.
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
                	//System.out.println(String.format("\"%s\"", splited2[2]));                	
                	u = graph.getVertex(splited2[2]).getElement();
                    v = graph.getVertex(splited2[4]).getElement();

                    if((u != null) && (v != null) && !(graph.areAdjacent(graph.getVertex(splited2[2]), graph.getVertex(splited2[4])))){
                    	graph.insertEdge(u, v);
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
	
	/* DELETE WHEN FINAL VERSION */
    @SuppressWarnings({ "rawtypes", "unused" })
	private static void showAirports(Graph<DecoratedElement<Airport>, Edge> graph){
    	DecoratedElement<Airport> object;
    	System.out.println("\nAirport information:");
    	
        for (Iterator<Vertex<DecoratedElement<Airport>>> item = graph.getVertices(); item.hasNext();) {
        	object = item.next().getElement(); 
            System.out.printf("IATA: %s\t Country: %s\t Airport name: %s\n", object.getElement().getID(), object.getElement().getCountry(), object.getElement().getName());
        }
    }
    
    /* DELETE WHEN FINAL VERSION */
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
    
    /*********************************************************************
    *
    * Method name:
    *
    * Name of the original author (if the module author is different
    * than the author of the file):
    *
    * Description of the Method: A description of what the method does.
    *
    * Calling arguments: A list of the calling arguments, their types, and
    * brief explanations of what they do.
    *
    * Return value: it type, and a brief explanation of what it does.
    *
    * Required Files: A list of required files needed by the method,
    * indicating if the method expects files to be already opened (only if
    * files are used)
    *
    * List of Checked Exceptions and an indication of when each exception
    * is thrown.
    *
    *********************************************************************/
    @SuppressWarnings({ "rawtypes" })
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
    * Method name:
    *
    * Name of the original author (if the module author is different
    * than the author of the file):
    *
    * Description of the Method: A description of what the method does.
    *
    * Calling arguments: A list of the calling arguments, their types, and
    * brief explanations of what they do.
    *
    * Return value: it type, and a brief explanation of what it does.
    *
    * Required Files: A list of required files needed by the method,
    * indicating if the method expects files to be already opened (only if
    * files are used)
    *
    * List of Checked Exceptions and an indication of when each exception
    * is thrown.
    *
    *********************************************************************/
    private static void depthFirstSearch(Graph<DecoratedElement<Airport>, Edge> g, Vertex<DecoratedElement<Airport>> origin, Vertex<DecoratedElement<Airport>> dest, ArrayList<Vertex<DecoratedElement<Airport>>> routes){        
        
    	//ArrayList<Vertex<DecoratedElement<Airport>>> routes = new ArrayList<Vertex<DecoratedElement<Airport>>>();
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
        			System.out.println("The route that he has followed is: \n");

        			for(int i=0; i < routes.size(); i++) {
        				System.out.printf("/ %s", routes.get(i).getElement().getElement().getName());
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
    * Method name:
    *
    * Name of the original author (if the module author is different
    * than the author of the file):
    *
    * Description of the Method: A description of what the method does.
    *
    * Calling arguments: A list of the calling arguments, their types, and
    * brief explanations of what they do.
    *
    * Return value: it type, and a brief explanation of what it does.
    *
    * Required Files: A list of required files needed by the method,
    * indicating if the method expects files to be already opened (only if
    * files are used)
    *
    * List of Checked Exceptions and an indication of when each exception
    * is thrown.
    *
    *********************************************************************/
    private static void breadthFirstSearch(Graph<DecoratedElement<Airport>, Edge> g, Vertex<DecoratedElement<Airport>> origin, Vertex<DecoratedElement<Airport>> dest){
        
    	/*
         * Queue<Vertex<DecoratedElement<Airport>>> queue = new ArrayDeque();
	        queue.add(origin);
	        origin.getElement().setVisited(true);

	        while (! queue.isEmpty()){
	            Vertex<DecoratedElement<Airport>> aux = queue.remove();
	            aux.getElement().setVisited(true);
	            
	            //ESTE FOR ES EL QUE FALLA, SOLO HAY QUE COGER UN ITERATOR CON LOS VERTICES QUE SEAN ADYACENTES!!!!!!                        
	            for (Iterator<Vertex<DecoratedElement<Airport>>> it = g.getVertices(); it.hasNext(); ) {
	            	number++;
	                Vertex<DecoratedElement<Airport>> z = it.next();
	                if (g.areAdjacent(z, aux)) {
	                	if((! z.getElement().isVisited()) && (! queue.contains(z))) {
	                		z.getElement().setVisited(true);
	                		z.getElement().setParent(aux.getElement());
	                		queue.add(z);
	                	}
	                }                
	            }
	              
	            if(z.getElement().getElement().getID().equals(dest.getElement().getElement().getID())) {
	                for(int i = 0; i < queue.size(); i++) {
	                	System.out.println(queue.remove().getElement().getElement().getName());
	                }
	                break;
	            }               
	        }
        */
    	
    	//ALGORITHM SIMPLEPATH.    	
    	Queue<Vertex<DecoratedElement<Airport>>> queue = new LinkedList();
    	Vertex<DecoratedElement<Airport>> u, v;
    	DecoratedElement<Airport> dec;
    	Edge ed;
    	
    	int length;
    	boolean noEnd = true;
    	
    	Iterator<Edge<Edge>> it1;
    	Iterator<Vertex<DecoratedElement<Airport>>> it2 = g.getVertices();
    	
    	while(it2.hasNext()) {
    		dec = it2.next().getElement();
    		dec.setVisited(false);
    	}
    	
    	origin.getElement().setVisited(true);
    	queue.offer(origin);
    	
    	while(!queue.isEmpty() && noEnd) {
    		u = queue.peek();
    		it1 = g.incidentEdges(u);
    		
    		while(it1.hasNext() && noEnd) {
    			ed = it1.next();
    			v = g.opposite(u, ed);
    			
    			if(v.getElement().equals(dest.getElement())) {
    				queue.offer(v);
    				noEnd = false;
    			}else {
        			if(!v.getElement().isVisited()) {
        				v.getElement().setVisited(true);
        				queue.offer(v);
        				noEnd = !(v.getElement().equals(dest.getElement()));			
        			}
    			}
    		}
    	}
    	
    	if(!noEnd) {
    		length = queue.size();
    		System.out.println("\n\nThe route that the spy has followed is: \n");
    		
    		for(int i=0; i < length; i++) {
    			System.out.printf("/ %s\n", queue.remove().getElement().getElement().getName());
    		}    		    	
    	}
    }
    
    /*********************************************************************
    *
    * Method name:
    *
    * Name of the original author (if the module author is different
    * than the author of the file):
    *
    * Description of the Method: A description of what the method does.
    *
    * Calling arguments: A list of the calling arguments, their types, and
    * brief explanations of what they do.
    *
    * Return value: it type, and a brief explanation of what it does.
    *
    * Required Files: A list of required files needed by the method,
    * indicating if the method expects files to be already opened (only if
    * files are used)
    *
    * List of Checked Exceptions and an indication of when each exception
    * is thrown.
    *
    *********************************************************************/
    @SuppressWarnings("unused")
	private static void spyGame(Graph<DecoratedElement<Airport>, Edge> graph) {
        //Read by keyboard the airport Source and Destination.
    	Scanner keyboard = new Scanner(System.in);
    	
    	System.out.println("\nIntroduce the IATA code of the origin airport: ");
    	String origin = keyboard.next();
    	Vertex<DecoratedElement<Airport>> ori = graph.getVertex(origin);
    	
    	System.out.println("\nIntroduce the IATA code of the destination airport: ");
    	String destination = keyboard.next();
    	Vertex<DecoratedElement<Airport>> dest = graph.getVertex(destination);
    	
    	ArrayList<Vertex<DecoratedElement<Airport>>> routes = new ArrayList<Vertex<DecoratedElement<Airport>>>();
        depthFirstSearch(graph, ori, dest, routes);
        breadthFirstSearch(graph, ori, dest);
    	keyboard.close();
	}
}
