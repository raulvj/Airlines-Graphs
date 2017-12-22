//package Airlines-Graphs

//import Element;

/*********************************************************************
*
* Class Name: DecoratedElement
* Author/s name: (From Unit5 slides)
* Release/Creation date: 08/12/2017
* Class description: the DecoratedElement objects applies the decorator pattern to a generic element. *Note: in this case, the generic element will be <Airport>
*
**********************************************************************
*/
public class DecoratedElement<T> implements Element {
  private String id; //IATA
  private T element;
  private boolean visited;
  private DecoratedElement<T> parent;

  /* Constructor */
  public DecoratedElement(String id, T element){
      this.id = id;
      this.element = element;
      this.visited = false;
  }

  /* Getters and Setters */
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
  
  @Override
  public boolean equals (Object n) {
	  return (id.equals(((DecoratedElement<Airport>) n).getID()) && element.equals(((DecoratedElement<Airport>) n).getElement()));
  }
}

