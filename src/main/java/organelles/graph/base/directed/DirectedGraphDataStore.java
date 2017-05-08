package organelles.graph.base.directed;

import organelles.graph.base.Edge;
import organelles.graph.base.GraphDataStore;
import organelles.graph.base.Vertex;
import organelles.graph.base.exception.EdgeAlreadyExistsException;
import organelles.graph.base.exception.EdgeMissingException;
import organelles.graph.base.exception.VertexAlreadyExistsException;
import organelles.graph.base.exception.VertexMissingException;

import java.util.*;

/**
 * Created by schan on 29/04/2017.
 */
public class DirectedGraphDataStore<V extends Vertex, E extends Edge> extends GraphDataStore<V, E> {


  private Collection<V> _vertices = new HashSet<V>();
  private Collection<E> _edges = new HashSet<E>();

  private Map<V, Map<E, V>> _adjacencyTable = new HashMap<V, Map<E, V>>();



  @Override
  public Collection<V> getVertices() {
    return _vertices;
  }

  @Override
  public Collection<E> getEdges() {
    return _edges;
  }

  @Override
  public void addVertex(V vertex) {
    checkIfVertexAlreadyExists(vertex, "Vertex (" + vertex.toString() + ") already exists");
    _vertices.add(vertex);
    _adjacencyTable.put(vertex, new HashMap<E, V>());
  }

  @Override
  public void addEdgeAndVertex(V srcVertex, E edge, V dstVertex) {

    checkIfVertexMissing(srcVertex, "Missing source vertex: " + srcVertex.toString());
    checkIfVertexAlreadyExists(
        dstVertex,
        "Destination vertex (" + dstVertex.toString() + ") already exists in graph"
    );
    checkIfGraphContainsEdge(edge);

    this.addVertex(dstVertex);
    this._adjacencyTable.get(srcVertex).put(edge, dstVertex);

  }

  @Override
  public void addEdge(V srcVertex, E edge, V dstVertex) {
    checkIfVertexMissing(srcVertex, "Missing source vertex: " + srcVertex.toString());
    checkIfVertexMissing(dstVertex, "Missing destination vertex: " + dstVertex.toString());

    checkIfGraphContainsEdge(edge);

    this._adjacencyTable.get(srcVertex).put(edge, dstVertex);
  }


  private void checkIfGraphContainsEdge(E edge) {
    if (_edges.contains(edge)) {
      throw new EdgeAlreadyExistsException("Edge already exists in graph: " + edge.toString());
    }
  }

  private void checkIfVertexMissing(V vertex, String msg) {
    if (!_vertices.contains(vertex)) {
      throw new VertexMissingException(msg);
    }
  }

  private void checkIfEdgeMissing(Edge edge, String msg) {
    if (!_edges.contains(edge)) {
      throw new EdgeMissingException(msg);
    }
  }

  private void checkIfVertexAlreadyExists(V vertex, String msg) {
    if (_vertices.contains(vertex)) {
      throw new VertexAlreadyExistsException(msg);
    }
  }

  @Override
  public Collection<V> getAdjacentVertices(V srcVertex) {
    checkIfVertexMissing(srcVertex, "Vertex (" + srcVertex.toString() + ") does not exist in this graph");
    return this._adjacencyTable.get(srcVertex).values();
  }

  @Override
  public V getAdjacentVertex(V srcVertex, E edge) {
    checkIfVertexMissing(srcVertex, "Source vertex (" + srcVertex.toString() + ") does not exist");
    checkIfEdgeMissing(edge, "Edge (" + edge.toString() + ") does not exist");

    Map<E, V> adjacent = this._adjacencyTable.get(srcVertex);
    if (!adjacent.containsKey(edge)) {
      throw new EdgeMissingException("No edge " + edge.toString() + " coming from vertex " + srcVertex.toString());
    }
    return adjacent.get(edge);
  }

  @Override
  public Collection<E> getAdjacentEdges(V srcVertex) {
    checkIfVertexMissing(srcVertex, "Source vertex (" + srcVertex.toString() + ") does not exist");
    return this._adjacencyTable.get(srcVertex).keySet();
  }

  @Override
  public Map<E, V> getAdjacentEdgeToVertexMap(V srcVertex) {
    checkIfVertexMissing(srcVertex, "Source vertex (" + srcVertex.toString() + ") does not exist");
    return this._adjacencyTable.get(srcVertex);
  }

}
