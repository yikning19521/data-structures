package organelles.graph.base;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;
import java.util.Map;

/**
 * Created by schan on 29/04/2017.
 */
public class GraphDataStore<V extends Vertex, E extends Edge> {

  public Collection<V> getVertices() {
    throw new NotImplementedException();
  }

  public Collection<E> getEdges() {
    throw new NotImplementedException();
  }

  public void addVertex(V vertex) {
    throw new NotImplementedException();
  }

  public void addEdgeAndVertex(V srcVertex, E edge, V dstVertex) {
    throw new NotImplementedException();
  }

  public void addEdge(V srcVertex, E edge, V dstVertex) {
    throw new NotImplementedException();
  }


  public Collection<V> getAdjacentVertices(V srcVertex) {
    throw new NotImplementedException();
  }

  public V getAdjacentVertex(V srcVertex, E edge) {
    throw new NotImplementedException();
  }

  public Collection<E> getAdjacentEdges(V srcVertex) {
    throw new NotImplementedException();
  }

  public Map<E, V> getAdjacentEdgeToVertexMap(V vertex) {
    throw new NotImplementedException();
  }


}
