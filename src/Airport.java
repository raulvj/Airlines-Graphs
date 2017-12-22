
/*********************************************************************
*
* Class Name: Airport
* Author/s name: Raul Valentin Jorge
* Release/Creation date: 18/12/2017
* Class description: contains the constructor and the methods(getters and setters) for Airport objects.
*
**********************************************************************
*/
public class Airport {
    private String id; //this is the IATA code of the airport.
    private String name;
    private String country;
    private String city;
    private double latitude;
    private double longitude;
    private double altitude;
    private int connections; //the number of routes that the airport object has.

    /*Constructor*/
    public Airport (String id, String name, String country, String city, double latitude, double longitude, double altitude){
        this.id = id;
        this.name = name;
        this.country = country;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.connections = 0;
    }

    /*Getters and Setters*/
    public String getID(){
        return this.id;
    }

    public void setID(String value){
        this.id = value;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String value){
        this.name = value;
    }

    public String getCountry(){
        return this.country;
    }

    public void setCountry(String value){
        this.country = value;
    }
    
    public String getCity() {
    	return this.city;
    }
    
    public void setCity(String value) {
    	this.city = value;
    }
    
    public double getLatitude(){
        return this.latitude;
    }

    public void setLatitude(double value){
        this.latitude = value;
    }

    public double getLongitude(){
        return this.longitude;
    }

    public void setLongitude(double value){
        this.longitude = value;
    }
    
    public double getAltitude() {
    	return this.altitude;
    }
    
    public void setAltitude(double value) {
    	this.altitude = value;
    }

    public int getConnections(){
        return this.connections;
    }

    public void setConnections(int value){
        this.connections = value;
    }
}

