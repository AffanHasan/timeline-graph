package timeline.languages;

import io.cucumber.java.en.Then;

public class SystemLanguages {

    @Then("Verify that system supports: {String})")
    public void verify_that_system_supports_language(String languageCode) {
        /**
         * MATCH (:SystemLanguages)-[:HAS_LANGUAGE]->(:LanguageCode)
         */
    }
}
