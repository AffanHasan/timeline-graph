/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package timeline.graph;

import java.net.URL;
import static org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph.GREMLIN_TINKERGRAPH_GRAPH_LOCATION;
import static org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph.GREMLIN_TINKERGRAPH_GRAPH_FORMAT;
import org.apache.commons.configuration2.BaseConfiguration;
import org.apache.commons.configuration2.Configuration;
import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;

/**
 *
 * @author ahassan
 */
public class StandardTimelineGraph implements TimelineGraph {
    
    private final GraphTraversalSource g;
    
    public StandardTimelineGraph() {
        final Configuration configuration = new BaseConfiguration();
        final URL url = this.getClass().getClassLoader().getResource("time-line.xml");
        configuration.addProperty(GREMLIN_TINKERGRAPH_GRAPH_LOCATION, url.getPath());
        configuration.addProperty(GREMLIN_TINKERGRAPH_GRAPH_FORMAT, "graphml");
        final Graph graph = TinkerGraph.open(configuration);
        this.g = traversal().withEmbedded(graph);
    }

    @Override
    public GraphTraversalSource getGraphTraversalSource() {
        return this.g;
    }
    
}
