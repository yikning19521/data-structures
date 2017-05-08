package organelles.graph.base;


import java.util.Collection;
import java.util.Map;

/**
 * Created by schan on 28/01/2017.
 */
public class Graph<V extends Vertex, E extends Edge> {

  private GraphDataStore<V, E> _graphDataStore;

  public Graph(GraphDataStore graphDataStore) {
    _graphDataStore = graphDataStore;
  }

  public void addVertex(V vertex){
    _graphDataStore.addVertex(vertex);
  }

  public void addVertex(V srcVertex, E edge, V dstVertex) {
    _graphDataStore.addEdgeAndVertex(srcVertex, edge, dstVertex);
  }

  public void addEdge(V srcVertex, E edge, V dstVertex) {
    _graphDataStore.addEdge(srcVertex, edge, dstVertex);
  }

  public Collection<V> getAdjacentVertices(V srcVertex) {
    return _graphDataStore.getAdjacentVertices(srcVertex);
  }

  public V getAdjacentVertex(V srcVertex, E edge) {
    return _graphDataStore.getAdjacentVertex(srcVertex, edge);
  }

  public Collection<E> getAdjacentEdges(V srcVertex) {
    return _graphDataStore.getAdjacentEdges(srcVertex);
  }

  public Map<E, V> getAdjacentEdgeToVertexMap(V vertex) {
    return _graphDataStore.getAdjacentEdgeToVertexMap(vertex);
  }

}
