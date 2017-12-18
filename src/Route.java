
public class Route {
    private String sourceAirport;
    private String destinationAirport;

    public Route(String sourceAirport, String destinationAirport){
        this.sourceAirport = sourceAirport;
        this.destinationAirport = destinationAirport;
    }

    public String getSourceAirport(){
        return this.sourceAirport;
    }

    public void setSourceAirport(String value){
        this.sourceAirport = value;
    }

    public String getDestinationAirport(){
        return this.destinationAirport;
    }

    public void setDestinationAirport(String value){
        this.destinationAirport = value;
    }
}
