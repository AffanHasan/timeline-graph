package timeline;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;

public class BeforeAndAfterAllCucumberHook {

    private static Neo4jDriver neo4jDriver;
    @BeforeAll
    public static void before_all_scenarios() {
        System.out.println("--- --- --- Initiating Neo4j Driver");
        neo4jDriver = new Neo4jDriver();
    }

    @AfterAll
    public static void after_all_scenarios() throws Exception {
        System.out.println("--- --- --- Disposing Neo4j Driver");
        neo4jDriver.close();
    }
    public static Session getTimelineDatabaseSession() {
        return neo4jDriver.getDriver().session(SessionConfig.builder().withDatabase("timeline").build());
    }
}
