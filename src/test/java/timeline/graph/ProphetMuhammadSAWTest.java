/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package timeline.graph;

import java.util.Map;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Affan Hasan
 */
public class ProphetMuhammadSAWTest {
    
    final GraphTraversalSource g;
    
    public ProphetMuhammadSAWTest() {
        final TimelineGraph timelineGraph = new StandardTimelineGraph();
        this.g = timelineGraph.getGraphTraversalSource();
    }
    
    @Test
    public void prophetMuhammadSAWBirthEventShouldExist() {
        // There should be only one event named Birth Of Prophet Muhammad S.A.W
        assertEquals(1L, this.getBirthEventVertex().count().next());
    }

    @Test
    public void WikipediaReferenceForBirthIn53BHShouldExist() {
        
        this.getBirthEventVertex().as("birthEvent") //
            .out("borned_in").count().next();
//            .has("source", "wikipedia").as("wikipediaReference") //
//            .outV().as("hijriYear") //
//            .select("birthEvent", "wikipediaReference", "hijriYear") //
//            .select("birthEvent", "wikipediaReference") //
//            .next();
        System.out.println(this.getBirthEventVertex()
            .out("borned_in").count().next());

//        
//        // Verify that one of the references is for wikipedia
//        assertEquals(1, this.getBirthEventVertex() //
//            .outE().hasLabel("borned_in").toList().stream().filter(e->
//            e.value("source").equals("wikipedia") &&
//            e.value("reference").equals("https://en.wikipedia.org/wiki/Muhammad")).count());
//        
//        // Verify that one of the references is for Al azami book "History Of Quranic Text"
//        assertEquals(1, this.getBirthEventVertex() //
//            .outE().hasLabel("borned_in").toList().stream().filter(e->
//            e.value("source").equals("The History Of Quranic Text From Revelation To Compilation By Mustafa Al Aazami") &&
//            e.value("reference").equals("Jan 2003 Page 23")).count());
    }
    
    @Test
    public void shouldAssertThatProphetDiedIn11AHWikipedia() {
        // There should be only one event named Birth Of Prophet Muhammad S.A.W
        assertEquals(1L, getDeathEventVertex().count().next());
       
        // Verify that there are only 2 references to birth currently
        assertEquals(2L, getDeathEventVertex() //
            .outE().hasLabel("died_in").count().next());
        
        // Verify that one of the references is for wikipedia
        assertEquals(1, 
                getDeathEventVertex() //
            .outE().hasLabel("died_in").toList().stream().filter(e->
            e.value("source").equals("wikipedia") &&
            e.value("reference").equals("https://en.wikipedia.org/wiki/Muhammad")).count());
        
        // Verify that one of the references is for Al azami book "History Of Quranic Text"
        assertEquals(1, getDeathEventVertex() //
            .outE().hasLabel("died_in").toList().stream().filter(e->
            e.value("source").equals("The History Of Quranic Text From Revelation To Compilation By Mustafa Al Aazami") &&
            e.value("reference").equals("Jan 2003 Page 23")).count());
    }
    
    private GraphTraversal<Vertex, Vertex> getBirthEventVertex() {
        return this.g.V().has("event", "name", "Birth Of Prophet Muhammad S.A.W");
    }
    
    private GraphTraversal<Vertex, Vertex> getDeathEventVertex() {
        return this.g.V().has("event", "name", "Death Of Prophet Muhammad S.A.W");
    }
}
