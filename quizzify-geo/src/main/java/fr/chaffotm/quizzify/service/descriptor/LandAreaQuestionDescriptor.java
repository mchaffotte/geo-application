package fr.chaffotm.quizzify.service.descriptor;

import fr.chaffotm.geodata.entity.CountryEntity;
import fr.chaffotm.querify.CriteriaRepository;
import fr.chaffotm.querify.criteria.FieldOrder;
import fr.chaffotm.querify.criteria.Filters;
import fr.chaffotm.querify.criteria.QueryCriteria;
import fr.chaffotm.quizzify.resource.Filter;
import fr.chaffotm.quizzify.resource.FilterType;
import fr.chaffotm.quizzify.service.ColumnType;

public class LandAreaQuestionDescriptor implements QuestionDescriptor<CountryEntity> {

    @Override
    public FilterType getFilterType(final CriteriaRepository repository) {
        return new FilterTypeFactory().build(repository);
    }

    @Override
    public QueryCriteria<CountryEntity> getQueryCriteria(Filter filter) {
        final QueryCriteria<CountryEntity> criteria = new QueryCriteria<>(CountryEntity.class);
        criteria.setJoin("area");
        if (filter != null && filter.getName().equals("region.id")) {
            criteria.setFilter(Filters.in("region.id", "getSubRegions", filter.getValue()));
        }
        criteria.addSort("area.land", FieldOrder.DESC);
        return criteria;
    }

    @Override
    public String getQuestionType() {
        return "LAND_AREA";
    }

    @Override
    public ColumnType getAttributeColumnType() {
        return ColumnType.NUMERIC;
    }

    @Override
    public String getQuestionImage(final CountryEntity entity) {
        return null;
    }

    @Override
    public String getQuestion(final CountryEntity entity) {
        return "Which country has the largest land area?";
    }

    @Override
    public String getAttributeValue(final CountryEntity entity) {
        return entity.getName();
    }

}
