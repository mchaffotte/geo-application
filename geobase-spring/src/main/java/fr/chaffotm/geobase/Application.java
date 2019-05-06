package fr.chaffotm.geobase;

import fr.chaffotm.geobase.repository.CountryRepository;
import fr.chaffotm.geobase.service.CountryService;
import fr.chaffotm.geobase.web.exception.ExceptionHandlingController;
import fr.chaffotm.geobase.web.rest.CountryRestController;
import fr.chaffotm.geodata.entity.CountryEntity;
import fr.chaffotm.quizzify.entity.QuizEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackageClasses = {
        CountryService.class, CountryRestController.class, ExceptionHandlingController.class, CountryRepository.class
})
@EntityScan(basePackageClasses = {CountryEntity.class, QuizEntity.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
