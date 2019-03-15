package fr.chaffotm.geoquiz.service;

import fr.chaffotm.geoquiz.service.descriptor.QuestionDescriptorServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        QuestionDescriptorServiceTest.class,
        QuizResponseCheckerTest.class
})
public class AllTests {

}
