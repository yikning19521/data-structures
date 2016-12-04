package molecules;

import atoms.SinglyLinkedNode;

import java.util.*;

public class LinkedList<E> implements List<E> {

  private SinglyLinkedNode<E> _head;
  private SinglyLinkedNode<E> _tail;
  private int _length;


  public LinkedList() {
    _head = _tail = null;
    _length = 0;
  }

  public boolean add(E e) {
    SinglyLinkedNode<E> newElement = new SinglyLinkedNode<E>(e);
    _tail.setNext(newElement);
    _length += 1;
    if (_length == 1) {
      _head = _tail = newElement;
    }
    return true;
  }

  public void add(int index, E element) {
    checkIndex(index);
    SinglyLinkedNode<E> newElement = new SinglyLinkedNode<E>(element);
    if (_length == 0) {
      _head = _tail = newElement;
    } else if (index == 0) {
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
    _length += 1;
  }

  public boolean addAll(Collection<? extends E> c) {
    for (E e : c) {
      add(e);
    }
    return true;
  }

  public boolean addAll(int index, Collection<? extends E> c) {
    checkIndex(index);
    int collectionSize = c.size();
    if (collectionSize == 0) {
      return false;
    }
    if (index == _length) {
      addAll(c);
    }
    int i = index;
    for (E e : c) {
      add(i, e);
      i += 1;
    }
    return true;
  }

  public void clear() {
    _head = _tail = null;
    _length = 0;
  }

  public boolean contains(Object o) {
    for (E e : this) {
      if (e.equals(o)) {
        return true;
      }
    }
    return false;
  }

  public boolean containsAll(Collection<?> c) {
    for (Object o : c) {
      if (!contains(o)) {
        return false;
      }
    }
    return true;
  }

  public E get(int index) {
    checkIndex(index);
    return getNode(index).getValue();
  }

  public int hashCode() {
    int hashCode = 1;
    for (E e : this) {
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
    return new SinglyLinkedNodeIterator();
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
    return new SinglyLinkedNodeListIterator();
  }

  public ListIterator<E> listIterator(int index) {
    return new SinglyLinkedNodeListIterator(index);
  }

  public E remove(int index) {
    checkIndex(index);
    E retVal;

    if (index == 0) {
      retVal = _head.getValue();
      _head = _head.getNext();
      if (_length == 1) {
        _tail = null;
      }
    } else {
      SinglyLinkedNode<E> previous = getNode(index - 1);
      SinglyLinkedNode<E> retNode = previous.getNext();
      retVal = retNode.getValue();
      previous.setNext(retNode.getNext());
      if (index == _length - 1) {
        _tail = previous;
      }

    }
    _length -= 1;
    return retVal;
  }

  public boolean remove(Object o) {
    Iterator<E> iterator = iterator();
    while (iterator.hasNext()) {
      E elem = iterator.next();
      if (o == null ? elem == null : elem.equals(o)) {
        iterator.remove();
        return true;
      }
    }
    return false;
  }

  public boolean removeAll(Collection<?> c) {
    boolean elementRemoved = false;
    for (Object o : c) {
      elementRemoved = remove(o);
    }
    return elementRemoved;
  }

  public boolean retainAll(Collection<?> c) {
    Iterator<E> iterator = iterator();
    boolean elementRemoved = false;
    while (iterator.hasNext()) {
      E elem = iterator.next();
      if (!c.contains(elem)) {
        iterator.remove();
        elementRemoved = true;
      }
    }
    return elementRemoved;
  }

  public E set(int index, E elem) {
    SinglyLinkedNode<E> node = getNode(index);
    E retVal = node.getValue();
    node.setValue(elem);
    return retVal;
  }

  public int size() {
    return _length;
  }

  public List<E> subList(int fromIndex, int toIndex) {
    if (fromIndex < 0 || toIndex > _length || fromIndex > toIndex) {
      throw new IndexOutOfBoundsException();
    }

    LinkedList<E> retList = new LinkedList<E>();
    if (fromIndex == toIndex) {
      retList.add(getNode(fromIndex).getValue());
    } else {
      int index = fromIndex;
      do {
        retList.add(getNode(index).getValue());
        index += 1;
      } while (index < toIndex);
    }
    return retList;
  }

  public Object[] toArray() {
    Object[] retArray = new Object[_length];
    int i = 0;
    for (E e : this) {
      retArray[i] = e;
      i += 1;
    }
    return retArray;
  }

  public <T> T[] toArray(T[] a) {
    if (a.length < _length) {
      return (T[]) toArray();
    } else {
      int i = 0;
      for (E e : this) {
        a[i] = (T) e;
        i += 1;
      }

      if (a.length > _length) {
        a[_length] = null;
      }
      return a;
    }
  }

  private void checkIndex(int index) {
    if (index < 0 || index >= _length) {
      throw new IndexOutOfBoundsException("invalid index: " + index);
    }
  }

  private SinglyLinkedNode<E> getNode(int index, boolean throwException) {
    try {
      checkIndex(index);
    } catch (IndexOutOfBoundsException e) {
      if (throwException) {
        throw e;
      }
      return null;
    }
    int iterator = 0;
    SinglyLinkedNode<E> pointer = _head;
    while (iterator < index) {
      pointer = pointer.getNext();
      iterator += 1;
    }
    return pointer;
  }

  private SinglyLinkedNode<E> getNode(int index) {
    return getNode(index, true);
  }

  public class SinglyLinkedNodeIterator implements Iterator<E> {
    SinglyLinkedNodeListIterator _listIterator;

    public SinglyLinkedNodeIterator() {
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

  public class SinglyLinkedNodeListIterator implements ListIterator<E> {
    int _previousIndex;
    int _nextIndex;

    SinglyLinkedNode<E> _previous;
    SinglyLinkedNode<E> _next;
    IteratorCall _lastCall;


    public SinglyLinkedNodeListIterator() {
      this(0);

    }

    public SinglyLinkedNodeListIterator(int index) {
      checkIndex(index);
      _nextIndex = index;
      _previousIndex = index - 1;
      if (index == 0) {
        _previous = null;
        _next = _head;
      } else {
        _previous = _head;
        int i = 0;
        while (i < index - 1) {
          _previous = _previous.getNext();
          i += 1;
        }
        _next = _previous.getNext();
      }
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
        SinglyLinkedNode<E> _newPrevious = _head;
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
      SinglyLinkedNode<E> newNode = new SinglyLinkedNode<E>(e, _next);

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
          SinglyLinkedNode<E> prevPrevious = _head;
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
        SinglyLinkedNode<E> newNext = _next.getNext();
        _next = newNext;
        if (_previous == null) {
          _head = _next;
        } else {
          _previous.setNext(newNext);
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

  private enum IteratorCall {
    ADD, NEXT, PREVIOUS, REMOVE
  }

}
