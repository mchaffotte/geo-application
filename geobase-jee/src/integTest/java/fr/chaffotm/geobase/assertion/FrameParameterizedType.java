package fr.chaffotm.geobase.assertion;

import fr.chaffotm.geobase.web.domain.Frame;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class FrameParameterizedType implements ParameterizedType {

    private final Class resourceClass;

    FrameParameterizedType(Class resourceClass) {
        this.resourceClass = resourceClass;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return new Type[] { resourceClass };
    }

    @Override
    public Type getRawType() {
        return Frame.class;
    }

    @Override
    public Type getOwnerType() {
        return Frame.class;
    }

}
