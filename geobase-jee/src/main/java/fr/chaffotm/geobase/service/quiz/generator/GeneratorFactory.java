package fr.chaffotm.geobase.service.quiz.generator;

import fr.chaffotm.geobase.service.quiz.ColumnType;

public class GeneratorFactory {

    public Generator getGenerator(final ColumnType columnType) {
        final Generator generator;
        if(ColumnType.VARCHAR.equals(columnType)) {
            generator = new VarcharRandomGenerator();
        } else if (ColumnType.NUMERIC.equals(columnType)) {
            generator = new SuperiorityRandomGenerator();
        } else {
            throw new IllegalArgumentException("Unknown column type:" + columnType);
        }
        return generator;
    }

}
