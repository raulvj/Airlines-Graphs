
public class Airport {
    private String id;
    private String name;
    private String country;
    private double latitude;
    private double longitude;
    private double altitude;
    private int connections;

    public Airport (String id, String name, String country, double latitude, double longitude, double altitude){
        this.id = id; //IATA
        this.name = name;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.connections = 0;
    }

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

