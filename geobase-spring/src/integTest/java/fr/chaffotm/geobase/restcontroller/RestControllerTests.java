package fr.chaffotm.geobase.restcontroller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CountryRestControllerIT.class,
        QuizTypeRestControllerIT.class,
        QuizRestControllerIT.class,
        ExceptionRestControllerIT.class
})
public class RestControllerTests {

}


