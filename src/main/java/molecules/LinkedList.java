package molecules;

import atoms.SinglyLinkedNode;

import java.util.*;

/**
 * Created by schan on 04/12/2016.
 */
public class LinkedList<E> implements List {

  private SinglyLinkedNode _head;
  private SinglyLinkedNode _tail;
  private int _length;

  public boolean add(E e) {
    _length += 1;
    SinglyLinkedNode newElement = new SinglyLinkedNode<E>(e);
    _tail.setNext(newElement);
    return true;
  }

  public void add(int index, E element) {
    checkIndex(index);

    _length += 1;
    SinglyLinkedNode newElement = new SinglyLinkedNode<E>(element);
    if (index == 0) {
      newElement.setNext(_head);
      _head = newElement;
    } else if (index == _length) {
      _tail.setNext(newElement);
      _tail = newElement;
    } else {
      SinglyLinkedNode previous = _head;
      int iterator = 1;
      while (iterator != index) {
        previous = previous.getNext();
        iterator += 1;
      }
      SinglyLinkedNode next = previous.getNext();
      previous.setNext(newElement);
      newElement.setNext(next);
    }
  }

  public boolean addAll(Collection<? extends E> c) {
    SinglyLinkedNode newElement;
    for (E e : c) {
      newElement = new SinglyLinkedNode<E>(e);
      _tail.setNext(newElement);
      _tail = newElement;
      _length += 1;
    }
    return true;
  }

  public boolean addAll(int index, Collection<? extends E> c) {
    checkIndex(index);
    int collectionSize = c.size();
    if (collectionSize == 0) {
      return true;
    }
    if (index == _length) {
      addAll(c);
    }

    if (index == 0) {
      E[] values = (E[]) c.toArray();
      SinglyLinkedNode start = new SinglyLinkedNode<E>(values[0]);
      SinglyLinkedNode end = start;
      int collectionIndex = 1;
      while (collectionIndex < collectionSize) {
        SinglyLinkedNode<E> newNode = new SinglyLinkedNode<E>(values[collectionIndex]);
        end.setNext(newNode);
        end = newNode;
        collectionIndex += 1;
      }

      end.setNext(_head);
      _head = start;

    } else {
      SinglyLinkedNode previousNode = getNode(index - 1);
      SinglyLinkedNode nextNode = previousNode.getNext();

      for (E e : c) {
        SinglyLinkedNode newNode = new SinglyLinkedNode<E>(e);
        newNode.setNext(nextNode);
        previousNode.setNext(newNode);
        previousNode = newNode;
      }
    }
    _length += collectionSize;
    return true;
  }

  public void clear() {
    _head = _tail = null;
    _length = 0;
  }

  public boolean contains(Object o) {
    Iterator<E> iterator = iterator();
    while (iterator.hasNext()) {
      E value = iterator.next();
      if (value.equals(o)) {
        return true;
      }
    }
    return false;
  }

  public boolean containsAll(Collection<?> c) {
    Object[] collectionValues = c.toArray();
    for (Object o : collectionValues) {
      Iterator<E> iterator = iterator();
      boolean containsValue = false;
      while (iterator.hasNext()) {
        E value = iterator.next();
        if (value.equals(o)) {
          containsValue = true;
          break;
        }
      }
      if (!containsValue) return false;
    }
    return true;
  }

  public E get(int index) {
    return (E) getNode(index).getValue();
  }

  public int hashCode() {
    int hashCode = 1;
    Iterator<E> iterator = iterator();
    while (iterator.hasNext()) {
      E e = iterator.next();
      hashCode = (31 * hashCode) + (e == null ? 0 : e.hashCode());
    }
    return hashCode;
  }

  public int indexOf(Object o) {
    Iterator<E> iterator = iterator();
    int index = 0;
    while (iterator.hasNext()) {
      E e = iterator.next();
      if (e.equals(o)) {
        return index;
      }
      index += 1;
    }
    return -1;
  }

  public boolean isEmpty() {
    return _length == 0;
  }

  public Iterator<E> iterator() {
    return new SinglyLinkedNodeIterator<E>(_head);
  }

  public int lastIndexOf(Object o) {
    Iterator<E> iterator = iterator();
    int index = 0;
    int foundIndex = -1;
    while (iterator.hasNext()) {
      E e = iterator.next();
      if (e.equals(o)) {
        foundIndex = index;
      }
      index += 1;
    }
    return foundIndex;
  }

  public ListIterator<E> listIterator() {
  
  }

  public ListIterator<E> listIterator(int index) {

  }

  public E remove(int index) {

  }

  public boolean remove(Object o) {
    return false;
  }

  public boolean removeAll(Collection<?> c) {

  }

  public boolean retainALL(Collection<?> c) {

  }

  public int size() {
    return _length;
  }

  public List<E> subList(int fromIndex, int toIndex) {

  }

  public Object[] toArray() {

  }

  public <T> T[] toArray(T[] a) {
    T[] y;
    return y;
  }

  private void checkIndex(int index) {
    if (index < 0 || index > _length) {
      throw new IndexOutOfBoundsException("invalid index: " + index);
    }
  }

  private SinglyLinkedNode<E> getNode(int index) {
    try {
      checkIndex(index);
      int iterator = 0;
      SinglyLinkedNode pointer = _head;
      while (iterator < index) {
        pointer = pointer.getNext();
        iterator += 1;
      }
      return pointer;
    } catch (IndexOutOfBoundsException e) {
      return null;
    }
  }

  public class SinglyLinkedNodeIterator<E> implements Iterator<E> {
    SinglyLinkedNodeListIterator<E> _listIterator;

    public SinglyLinkedNodeIterator(SinglyLinkedNode<E> head) {
      _listIterator = new SinglyLinkedNodeListIterator();
    }

    public E next() {
      return _listIterator.next();
    }

    public boolean hasNext() {
      return _listIterator.hasNext();
    }

    public void remove() {
      _listIterator.remove();
    }

  }

  public class SinglyLinkedNodeListIterator<E> implements ListIterator<E> {
    int _previousIndex;
    int _nextIndex;
    SinglyLinkedNode<E> _previous;
    SinglyLinkedNode<E> _next;
    IteratorCall _lastCall;


    public SinglyLinkedNodeListIterator() {
      _nextIndex = 0;
      _previousIndex = -1;
      _previous = null;
      _next = _head;
      _lastCall = null;
    }

    public E next() {
      if (this.hasNext()) {
        _lastCall = IteratorCall.NEXT;

        // Move cursor
        _previous = _next.getNext();
        _next = _previous.getNext();
        incrementIndices();

        return _previous.getValue();
      }
      throw new NoSuchElementException();
    }

    public boolean hasNext() {
      return _nextIndex != _length;
    }

    public int nextIndex() { return _nextIndex; }

    public E previous() {
      if (this.hasPrevious()) {
        _lastCall = IteratorCall.PREVIOUS;

        // Move cursor
        SinglyLinkedNode _newPrevious = _head;
        while (_newPrevious.getNext() != _previous) {
          _newPrevious = _newPrevious.getNext();
        }
        _previous = _newPrevious;
        _next = _previous.getNext();
        decrementIndices();

        return _next.getValue();
      }
      throw new NoSuchElementException();
    }

    public boolean hasPrevious() {
      return _previousIndex != -1;
    }

    public int previousIndex() { return _previousIndex; }

    public void add(E e) {
      SinglyLinkedNode newNode = new SinglyLinkedNode<E>(e, _next);

      if (_previousIndex == -1) {
        _head = newNode;
        if(_length == 0) {
          _tail = newNode;
        }
        _nextIndex = 1;
      } else if (!hasNext()) {
        _tail.setNext(newNode);
        _tail = newNode;
        _nextIndex += 1;
      } else {
        _previous.setNext(newNode);
      }
      _previous = newNode;
      _previousIndex += 1;
      _length += 1;
      _lastCall = IteratorCall.ADD;
    }

    public void remove() {
      checkLastCall();
      if (_lastCall == IteratorCall.NEXT) {
        if (_previousIndex == 0) {
          _head = _next;
          _previous = null;
        } else {
          SinglyLinkedNode prevPrevious = _head;
          while (prevPrevious.getNext() != _previous) {
            prevPrevious = prevPrevious.getNext();
          }
          _previous = prevPrevious;
          _previous.setNext(_next);
        }
        if (_nextIndex == _length) {
            _tail = _previous;
        }
        decrementIndices();
      } else {
        SinglyLinkedNode newNext = _next.getNext();
        _next = newNext;
        if (_previous == null) {
          _head = _next;
        } else {
          _previous.setNext(_next.getNext());
        }
        if (_next == null) {
          _tail = _previous;
        }
      }
      _length -= 1;
      _lastCall = IteratorCall.REMOVE;
    }

    public void set(E e) {
      checkLastCall();
      if (_lastCall == IteratorCall.NEXT) {
        _previous.setValue(e);
      } else {
        _next.setValue(e);
      }
    }

    private void decrementIndices() {
      _previousIndex -= 1;
      _nextIndex -= 1;
    }

    private void incrementIndices() {
      _previousIndex += 1;
      _nextIndex += 1;
    }

    private void checkLastCall() {
      if (!(_lastCall == IteratorCall.NEXT || _lastCall == IteratorCall.PREVIOUS)) {
        throw new IllegalStateException();
      }
    }

  }
}


  private enum IteratorCall {
    ADD, NEXT, PREVIOUS, REMOVE
  }

}
