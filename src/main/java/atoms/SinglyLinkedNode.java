package atoms;

/**
 * Created by schan on 04/12/2016.
 */
public class SinglyLinkedNode<V> {

  private V _value;
  private SinglyLinkedNode _next;

  public SinglyLinkedNode(V value) {
    this(value, null);
  }

  public SinglyLinkedNode(V value, SinglyLinkedNode next) {
    setValue(value);
    setNext(next);
  }

  public void setValue(V value) { _value = value; }

  public V getValue() { return _value; }

  public void setNext(SinglyLinkedNode next) { _next = next; }

  public SinglyLinkedNode getNext() { return _next; }

}
