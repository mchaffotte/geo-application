package fr.chaffotm.geobase;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CountryRestControllerIT.class,
        QuizTypeRestControllerIT.class,
        QuizRestControllerIT.class
})
public class AllITests {

}
