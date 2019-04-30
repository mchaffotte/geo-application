package fr.chaffotm.querify.jpa;

class FieldAccessor {

    private String fieldName;

    private String subFieldName;

    FieldAccessor(final String fieldPath) {
        final String[] fields = fieldPath == null ? new String[]{null} : fieldPath.split("\\.");
        if (fields.length > 0) {
            this.fieldName = fields[0];
        }
        if (fields.length > 1) {
            this.subFieldName = fields[1];
        }
    }

    String getFieldName() {
        return fieldName;
    }

    String getSubFieldName() {
        return subFieldName;
    }

    boolean hasField() {
        return fieldName != null;
    }

    boolean hasSubField() {
        return subFieldName != null;
    }

}
