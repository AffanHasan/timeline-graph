package timeline.informationsources.urdu.books;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en_scouse.An;
import org.neo4j.driver.Session;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Relationship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static timeline.BeforeAndAfterAllCucumberHook.getTimelineDatabaseSession;

public class RaheeqUlMakhtoom {

    private static final String BOOK_NAME = "الرحيق المختوم";
    private static final String AUTHOR_NAME = "صفی الرحمان مبارک پوری";
    @Given("the book الرحيق المختوم")
    public void the_book_الرحيق_المختوم() {
        try (final Session session = getTimelineDatabaseSession()) {
            final Node bookNode = session.executeRead(tx->
                tx.run(String.format("MATCH (book:InformationSource:Book {name:'%s'}) RETURN book", BOOK_NAME)) //
                .single().get("book").asNode());
            assertEquals(BOOK_NAME, bookNode.get("name").asString());
        } catch (final Exception e) {
            fail(e.getMessage());
        }
    }

    @And("The author of book الرحيق المختوم is صفی الرحمان مبارک پوری")
    public void author() {
        try (final Session session = getTimelineDatabaseSession()) {
            final Node authorNode = session.executeRead(tx->
                tx.run(String.format("MATCH (book:InformationSource:Book {name:'%s'})<-[:AUTHOR_OF]-(author:Author {name:'%s'}) RETURN author"
                        , BOOK_NAME, AUTHOR_NAME)) //
                        .single().get("author").asNode());
            assertEquals(AUTHOR_NAME, authorNode.get("name").asString());
        } catch (final Exception e) {
            fail(e.getMessage());
        }
    }

    @And("The Book الرحيق المختوم was originally written in Arabic")
    public void originally_written_in_arabic() {
        try (final Session session = getTimelineDatabaseSession()) {
            final Node arabicLanguageNide = session.executeRead(tx->
                    tx.run(String.format("MATCH (book:InformationSource:Book {name:'%s'})-[:LANGUAGE]->(language:Language {code:'%s'}) RETURN language"
                                    , BOOK_NAME, "Ar")) //
                            .single().get("language").asNode());
            assertEquals("Ar", arabicLanguageNide.get("code").asString());
        } catch (final Exception e) {
            fail(e.getMessage());
        }
    }

    @And("The Book الرحيق المختوم was translated in Urdu by صفی الرحمان مبارک پوری")
    public void translated_in_urdu_by_the_author() {
        try (final Session session = getTimelineDatabaseSession()) {
            session.executeRead(tx-> {
                final Node urduLanguageNode = tx.run(String.format("MATCH (book:InformationSource:Book {name:'%s'})-[:LANGUAGE]->(language:Language {code:'%s'}) RETURN language"
                                , BOOK_NAME, "Ur")) //
                        .single().get("language").asNode();
            assertEquals("Ur", urduLanguageNode.get("code").asString());
                final Relationship relation = tx.run(String.format("MATCH (book:InformationSource:Book {name:'%s'})<-[r:TRANSLATED {languageCode:'Ur'}]->(author:Author {name:'%s'}) RETURN r"
                                , BOOK_NAME, AUTHOR_NAME)) //
                        .single().get("r").asRelationship();
            assertEquals("Ur", relation.get("languageCode").asString());
                return null;
            });
        } catch (final Exception e) {
            fail(e.getMessage());
        }
    }

    @And("The Book الرحيق المختوم was published in Urdu by ال مكتبة السلفية لاهور in 2021 CE")
    public void urdu_publisher() {
        try (final Session session = getTimelineDatabaseSession()) {
            session.executeRead(tx-> {
                final Node publisher = tx.run(String.format("MATCH (book:InformationSource:Book {name:'%s'})<-[:PUBLISHED {year:2021, era:'CE'}]-(publisher:Publisher {name:'%s'}) RETURN publisher"
                        , BOOK_NAME, "ال مكتبة السلفية لاهور")) //
                .single().get("publisher").asNode();
                assertEquals("ال مكتبة السلفية لاهور", publisher.get("name").asString());
                return null;
            });
        } catch (final Exception e) {
            fail(e.getMessage());
        }
    }

// Page 86
    @Given("First reference in page 86 says that شق الصدر happened 3 years after birth in 50 BH")
    public void first_reference_for_shaq_us_sadr_in_3_years_after_birth_in_50_BH(){
        try (final Session session = getTimelineDatabaseSession()) {
            final Node event = session.executeRead(tx-> {
                final String query = """
                MATCH (book:InformationSource:Book {name:'%s'})-[:PAGE]->(:Page {number:86})-[:REFER_TO]->(event:Event {name:'شق الصدر'})
                -[:HAPPENED_IN {source:'Book', name:'الرحيق المختوم', page:86}]->
                (:Year {value:50})<-[:YEAR {type:'BH'}]-(:TimeLine {name:'Hijri'}) RETURN event
                """;
                return tx.run(String.format(query, BOOK_NAME)).single().get("event").asNode();
            });
            assertEquals("شق الصدر", event.get("name").asString());
        } catch (final Exception e) {
            fail(e.getMessage());
        }
    }

    @Given("Second reference in page 86 says that شق الصدر happened 4 years after birth in 49 BH")
    public void secvond_reference_page_86_says_happened_4_years_After_birth_in_49_BH(){
        try (final Session session = getTimelineDatabaseSession()) {
            final Node event = session.executeRead(tx-> {
                final String query = """
                MATCH (book:InformationSource:Book {name:'%s'})-[:PAGE]->(:Page {number:86})-[:REFER_TO]->(event:Event {name:'شق الصدر'})
                -[:HAPPENED_IN {source:'Book', name:'الرحيق المختوم', page:86}]->
                (:Year {value:49})<-[:YEAR {type:'BH'}]-(:TimeLine {name:'Hijri'}) RETURN event
                """;
                return tx.run(String.format(query, BOOK_NAME)).single().get("event").asNode();
            });
            assertEquals("شق الصدر", event.get("name").asString());
        } catch (final Exception e) {
            fail(e.getMessage());
        }
    }

    @Given("Third reference in page 86 says that شق الصدر happened 5 years after birth in 48 BH")
    public void secvond_reference_page_86_says_happened_5_years_After_birth_in_48_BH(){
        try (final Session session = getTimelineDatabaseSession()) {
            final Node event = session.executeRead(tx-> {
                final String query = """
                MATCH (book:InformationSource:Book {name:'%s'})-[:PAGE]->(:Page {number:86})-[:REFER_TO]->(event:Event {name:'شق الصدر'})
                -[:HAPPENED_IN {source:'Book', name:'الرحيق المختوم', page:86}]->
                (:Year {value:48})<-[:YEAR {type:'BH'}]-(:TimeLine {name:'Hijri'}) RETURN event
                """;
                return tx.run(String.format(query, BOOK_NAME)).single().get("event").asNode();
            });
            assertEquals("شق الصدر", event.get("name").asString());
        } catch (final Exception e) {
            fail(e.getMessage());
        }
    }

// Page 87
    @Given("Last Prophet traveled to Medina يثرب in 47 BH with his mother آمنة and grandfather عبد المطلب to meet his father's عبدالله maternal relatives")
    public void prophet_traveled_to_medina_with_his_mother_in_47_bh(){
        try (final Session session = getTimelineDatabaseSession()) {
            final Node event = session.executeRead(tx-> {
                final String query = """
MATCH (lastProphet:Arab:Man:Prophet:LastProphet {name:'محمد بن عبدالله'})-[:TRAVEL]->
(event:Event {name:'Journey Of The Last Prophet Towards مدينة With His Mother'})-[:TRAVELED_IN]->
(:Year {value:47})<-[:YEAR {type:'BH'}]-(:TimeLine {name:'Hijri'})
MATCH (event)-[:TRAVELED_TO {source:'Book', name:'الرحيق المختوم', page:87}]->(:City {name:'مدينة'})
MATCH (event)-[:TRAVELED_WITH {source:'Book', name:'الرحيق المختوم', page:87}]->
    (aamina:Arab:Woman:Mother {name:'آمنة بنت وهب'})-[:MOTHER_OF]->(lastProphet)
MATCH (event)-[:TRAVELED_WITH {source:'Book', name:'الرحيق المختوم', page:87}]->(:Arab:Woman {name:'ام ايمن'})-[:SLAVE_OF]->(aamina)
MATCH (event)-[:TRAVELED_WITH {source:'Book', name:'الرحيق المختوم', page:87}]->
    (:Arab:Man {name:'عبد المطلب'})-[:FATHER_OF]->(:Arab:Man {name:'عبدالله بن عبدالمطلب'})-[:FATHER_OF]->(lastProphet)
RETURN event
                """;
                return tx.run(String.format(query)).single().get("event").asNode();
            });
            assertEquals(	"Journey Of The Last Prophet Towards مدينة With His Mother", event.get("name").asString());
        } catch (final Exception e) {
            fail(e.getMessage());
        }
    }

    @Given("Last Prophet's mother آمنة died in 47 BH at a place called ابواء when he was six years old")
    public void last_prophets_mother_died_in_47BH_at_place_called_abwaa(){
        try (final Session session = getTimelineDatabaseSession()) {
            final Node deathEventNode = session.executeRead(tx-> {
                final String query = """
                MATCH (fourtySevenBH:Year {value:47})<-[:YEAR {type:'BH'}]-(:TimeLine {name:'Hijri'})
                MATCH (abwaa:Village {name:'الأبواء'})
                MATCH (event:Event {name:'Death Of آمنة بنت وهب'})
                MATCH (motherOfLastProphet:Arab:Woman:Mother {name:'آمنة بنت وهب'})
                MATCH (motherOfLastProphet)-[:DEATH]->(event)
                MATCH (event)-[:DIED_IN {source:'Book', name:'الرحيق المختوم',page:87}]->(fourtySevenBH)
                MATCH (event)-[:DEATH_PLACE {source:'Book', name:'الرحيق المختوم', page:87}]->(abwaa)
                RETURN event
                """;
                return tx.run(String.format(query)).single().get("event").asNode();
            });
            assertEquals(	"Death Of آمنة بنت وهب", deathEventNode.get("name").asString());
        } catch (final Exception e) {
            fail(e.getMessage());
        }
    }

    @Then("his grandfather عبد المطلب took him under his guardianship in 47 BH")
    public void his_grandfather_took_him_under_his_guardianship(){
        try (final Session session = getTimelineDatabaseSession()) {
            final Node event = session.executeRead(tx-> {
                final String query = """
            MATCH (book:Book {name:'الرحيق المختوم'})
            MATCH (fourtySevenBH:Year {value:47})<-[:YEAR {type:'BH'}]-(:TimeLine {name:'Hijri'})
            MATCH (grandFather:Arab:Man {name:'عبد المطلب'})
            MATCH (lastProphet:Arab:Man:LastProphet {name:'محمد بن عبدالله'})
            
            MATCH (event:Event {name:'Last Prophet Under Guardianship Of عبد المطلب'})<-[:TOOK_GUARDIAN_SHIP_OF_LAST_PROPHET]-(grandFather)
            MATCH (event)-[:HAPPENED_IN {source:'Book', name:'الرحيق المختوم', page:87}]->(fourtySevenBH)
            MATCH (event)<-[:REFER_TO]-(page87:Page {number:87})<-[:PAGE]-(book)
            RETURN event
                """;
                return tx.run(String.format(query)).single().get("event").asNode();
            });
            assertEquals(	"Last Prophet Under Guardianship Of عبد المطلب", event.get("name").asString());
        } catch (final Exception e) {
            fail(e.getMessage());
        }
    }

    @Given("Monastery of بحيرا was located in Syrian city بصرى in حوران region")
    public void monastery_of_bahira_was_in_basra_in_horan_region() {
        final String query = """
            MATCH (bahira:Man {name:'بحيرا'})-[:LIVED_IN]->(monasteryOfBahira:Monastery {name:'Monastery Of بحيرا'})
                -[:LOCATED_IN {source:'Book', name:'%s', page:88}]->(basra:City {name:'بصرى'})-[:LOCATED_IN]->(hauran:Region {name:'حوران'})
            MATCH (:Book {name:'%s'})-[:PAGE]->(:Page {number:88})-[:REFER_TO]->(monasteryOfBahira)
            MATCH (:Book {name:'%s'})-[:PAGE]->(:Page {number:88})-[:REFER_TO]->(bahira)
            RETURN monasteryOfBahira
                """;
        try (final Session session = getTimelineDatabaseSession()) {
            final Node monastryOfBahira = session.executeRead(tx-> {
                return tx.run(String.format(query, BOOK_NAME, BOOK_NAME, BOOK_NAME)).single().get("monasteryOfBahira").asNode();
            });
            assertEquals(	"Monastery Of بحيرا", monastryOfBahira.get("name").asString());
        } catch (final Exception e) {
            fail(e.getMessage());
        }
    }

    @Given("His uncle ابو طالب took him to Roman administered syria for a business trip at the age of 12 years in 41 BH")
    public void abu_talib_took_prophet_to_syrian_business_trip() {
        final String query = """
            MATCH (timeline:TimeLine {name:'Hijri'})
            MATCH (grandFatherOfProphet:Arab:Man {name:'عبد المطلب'})
            MATCH (lastProphet:Arab:Man:LastProphet {name:'محمد بن عبدالله'})
            MATCH (event:Event {name:'Syrian Business Trip With ابو طالب'})
            MATCH (syria:Country {name:'Syria'})
            MATCH (fourtyOneBH:Year {value:41})<-[:YEAR {type:'BH'}]-(timeline)
            MATCH (book:Book {name:'الرحيق المختوم'})
            
            MATCH (grandFatherOfProphet)-[:FATHER_OF]->(abuTalib:Arab:Man {name:'ابو طالب'})-[:UNCLE_OF]->(lastProphet)
            MATCH (event:Event {name:'Syrian Business Trip With ابو طالب'})
            MATCH (lastProphet)-[:TRAVEL]->(event)
            MATCH (event)-[:TRAVELED_IN {source:'Book', name:'الرحيق المختوم', page:89}]->(fourtyOneBH)
            MATCH (event)-[:TRAVELED_TO {source:'Book', name:'الرحيق المختوم', page:89}]->(syria)
            MATCH (event)-[:TRAVELED_WITH {source:'Book', name:'الرحيق المختوم', page:89}]->(abuTalib)
            
            MATCH (book)-[:PAGE]->(:Page {number:89})-[:REFER_TO]->(event)
            RETURN event
            """;
        try (final Session session = getTimelineDatabaseSession()) {
            final Node event = session.executeRead(tx-> {
                return tx.run(String.format(query)).single().get("event").asNode();
            });
            assertEquals(	"Syrian Business Trip With ابو طالب", event.get("name").asString());
        } catch (final Exception e) {
            fail(e.getMessage());
        }
    }

    @And("There was a Monastery of a priest named بحيرا in the city of بصرى")
    public void there_was_monstry_of_a_priest_bahira_in_basra() {
        try (final Session session = getTimelineDatabaseSession()) {
            final String query = """
            MATCH (monastery:Monastery {name:'Monastery Of بحيرا'})-[:LOCATED_IN {source:'Book', name:'الرحيق المختوم', page:88}]->
            (basraCity:City {name:'بصرى'})
            -[:LOCATED_IN {source:'Book', name:'الرحيق المختوم', page:88}]->(:Region {name:'حوران'})
            MATCH (bahira:Man {name:'بحيرا'})-[:LIVED_IN {source:'Book', name:'الرحيق المختوم', page:88}]->(monastery)
            RETURN bahira
            """;
            final Node bahira = session.executeRead(tx-> {
                return tx.run(String.format(query)).single().get("bahira").asNode();
            });
            assertEquals(	"بحيرا", bahira.get("name").asString());
        } catch (final Exception e) {
            fail(e.getMessage());
        }
    }

    @And("when they reached بصرى which was the central city of حوران a priest named بحيرا\\(جرجيس) came to visit them")
    public void when_they_reached_بصرى_which_was_the_central_city_of_حوران_a_priest_named_بحيرا_جرجيس_came_to_visit_them() {
        try (final Session session = getTimelineDatabaseSession()) {
            final String query = """
            MATCH (basra:City {name:'بصرى'})
            MATCH (bahira:Man {name:'بحيرا'})
            MATCH (lastProphet:Arab:Man {name:'محمد بن عبدالله'})
            MATCH (book:Book {name:'الرحيق المختوم'})-[:PAGE]->(page88:Page {number:88})
            MATCH (fourtyOneBH:Year {value:41})<-[:YEAR {type:'BH'}]-(:TimeLine {name:'Hijri'})
            MATCH (monastery:Monastery {name:'Monastery Of بحيرا'})
            MATCH (event:Event {name:'Meeting Of بحيرا With The Last Prophet'})
            MATCH (event)-[:HAPPENED_AT {source:'Book', name:'الرحيق المختوم', page:88}]->(monastery)
            MATCH (event)-[:HAPPENED_IN {source:'Book', name:'الرحيق المختوم', page:88}]->(fourtyOneBH)
            MATCH (bahira)-[:MEET  {source:'Book', name:'الرحيق المختوم', page:88}]->(event)<-[:MEET  {source:'Book', name:'الرحيق المختوم', page:88}]-(lastProphet)
            // BOOK Reference
            MATCH (page88)-[:REFER_TO]->(event)
            RETURN (event)
            """;
            final Node meeting = session.executeRead(tx-> {
                return tx.run(String.format(query, BOOK_NAME, BOOK_NAME, BOOK_NAME, BOOK_NAME,
                        BOOK_NAME)).single().get("event").asNode();
            });
            assertEquals(	"Meeting Of بحيرا With The Last Prophet", meeting.get("name").asString());
        } catch (final Exception e) {
            fail(e.getMessage());
        }
    }

    @And("priest testified that محمد is going to be the last prophet of Allah by positively identifying the signs of his prophecy")
    public void priest_testified_that_محمد_is_going_to_be_the_last_prophet_of_allah_by_positively_identifying_the_signs_of_his_prophecy() {
        try (final Session session = getTimelineDatabaseSession()) {
            final String query = """
            """;
            final Node meeting = session.executeRead(tx-> {
                return tx.run(String.format(query, BOOK_NAME, BOOK_NAME, BOOK_NAME, BOOK_NAME,
                        BOOK_NAME)).single().get("event").asNode();
            });
            assertEquals(	"Meeting Of بحيرا With The Last Prophet", meeting.get("name").asString());
        } catch (final Exception e) {
            fail(e.getMessage());
        }
    }


}