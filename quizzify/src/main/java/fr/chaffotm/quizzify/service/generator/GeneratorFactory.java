package fr.chaffotm.quizzify.service.generator;

import fr.chaffotm.quizzify.service.ColumnType;

public class GeneratorFactory {

    public Generator getGenerator(final ColumnType columnType) {
        final Generator generator;
        if(ColumnType.VARCHAR == columnType) {
            generator = new VarcharRandomGenerator();
        } else if (ColumnType.NUMERIC == columnType) {
            generator = new SuperiorityRandomGenerator();
        } else {
            throw new IllegalArgumentException("Unknown column type:" + columnType);
        }
        return generator;
    }

}
