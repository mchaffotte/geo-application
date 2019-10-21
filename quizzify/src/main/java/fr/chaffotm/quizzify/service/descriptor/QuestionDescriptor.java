package fr.chaffotm.quizzify.service.descriptor;

import fr.chaffotm.querify.criteria.QueryCriteria;
import fr.chaffotm.quizzify.service.ColumnType;

public interface QuestionDescriptor<T> {

    QueryCriteria<T> getQueryCriteria();

    String getQuestionType();

    ColumnType getAttributeColumnType();

    String getQuestionImage(T entity);

    String getQuestion(T entity);

    String getAttributeValue(T entity);

}
