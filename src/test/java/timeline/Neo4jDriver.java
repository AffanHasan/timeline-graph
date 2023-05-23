package timeline;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Config;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

import java.util.Base64;

public class Neo4jDriver implements AutoCloseable {
    private final Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "Passw0rd@TimelineGraph")); // TODO encrypt
    public Driver getDriver() {
        return driver;
    }
    @Override
    public void close() throws Exception {
        this.driver.close();
    }
}