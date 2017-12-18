//package Airlines-Graphs

//import Element;


public class DecoratedElement<T> implements Element { //Remember that we can subtitute <T> for <Airport>
    private String id; //IATA
    private T element;
    private boolean visited;
    private DecoratedElement<T> parent;

    public DecoratedElement(String id, T element){
        this.id = id;
        this.element = element;
    }

    @Override
    public String getID(){
        return this.id;
    }

    public T getElement(){
        return this.element;
    }

    public boolean isVisited(){
        return this.visited;
    }

    public void setVisited(boolean value){
        this.visited = value;
    }

    public DecoratedElement<T> getParent() {
        return parent;
    }

    public void setParent(DecoratedElement<T> value){
        this.parent = value;
    }
}
