package fr.chaffotm.geobase.endpoint;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ExceptionEndpointIT.class,
        CountryEndpointIT.class,
        QuizTypeEndpointIT.class,
        QuizEndpointIT.class,
})
public class EndpointTests {

}
