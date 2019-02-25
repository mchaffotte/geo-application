package fr.chaffotm.geobase.assertion;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class ListParameterizedType implements ParameterizedType {

    private final Class resourceClass;

    ListParameterizedType(Class resourceClass) {
        this.resourceClass = resourceClass;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return new Type[] { resourceClass };
    }

    @Override
    public Type getRawType() {
        return List.class;
    }

    @Override
    public Type getOwnerType() {
        return List.class;
    }

}
