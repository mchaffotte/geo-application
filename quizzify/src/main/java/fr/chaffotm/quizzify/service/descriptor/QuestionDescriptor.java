package fr.chaffotm.quizzify.service.descriptor;

import fr.chaffotm.querify.CriteriaRepository;
import fr.chaffotm.querify.criteria.QueryCriteria;
import fr.chaffotm.quizzify.resource.Filter;
import fr.chaffotm.quizzify.resource.FilterType;
import fr.chaffotm.quizzify.service.ColumnType;

public interface QuestionDescriptor<T> {

    FilterType getFilterType(CriteriaRepository repository);

    QueryCriteria<T> getQueryCriteria(Filter filter);

    String getQuestionType();

    ColumnType getAttributeColumnType();

    String getQuestionImage(T entity);

    String getQuestion(T entity);

    String getAttributeValue(T entity);

}
