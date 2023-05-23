package timeline.languages;

import io.cucumber.java.en.Then;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static timeline.BeforeAndAfterAllCucumberHook.getTimelineDatabaseSession;

public class SystemLanguages {

    @Then("Verify that system supports: {string}")
    public void verify_that_system_supports_language(String languageCode) {
        try(final Session session = getTimelineDatabaseSession()) {
            session.executeRead(tx -> {
                    final Result result = tx.run("MATCH (language:Language {code:'" + languageCode + "'}) RETURN language;");
                    assertEquals(languageCode, result.single().get("language").asNode().get("code").asString());
                    return null;
                }
            );
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
