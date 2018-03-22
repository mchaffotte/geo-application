package fr.chaffotm.geobase.service.quiz.generator;

import fr.chaffotm.geobase.domain.CountryEntity;
import fr.chaffotm.geobase.service.quiz.MultipleChoice;

import java.util.List;

public interface Generator {

    List<MultipleChoice> generate(List<CountryEntity> countries);

}
