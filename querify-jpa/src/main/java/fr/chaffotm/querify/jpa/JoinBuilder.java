package fr.chaffotm.querify.jpa;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.Map;

public class JoinBuilder {

    private final Map<String, Join<Object, Object>> joinAliases;

    private final Root<?> mainEntity;

    JoinBuilder(final Root<?> mainEntity) {
        this.mainEntity = mainEntity;
        joinAliases = new HashMap<>();
    }

    <P> Path<P> getPath(final String fieldPath) {
        final String fieldName;
        final Path<?> selection;
        final FieldAccessor accessor = new FieldAccessor(fieldPath);
        if (accessor.hasSubField()) {
            fieldName = accessor.getSubFieldName();
            selection = addJoin(accessor.getFieldName());
        } else {
            fieldName = accessor.getFieldName();
            selection = mainEntity;
        }
        return selection.get(fieldName);
    }

    Join<Object, Object> addJoin(final String fieldName) {
        return joinAliases.computeIfAbsent(fieldName, join -> mainEntity.join(fieldName));
    }

}
