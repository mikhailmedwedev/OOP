package org.example;

public enum GradeValue {
    EXCELLENT(5),
    GOOD(4),
    SATISFACTORY(3),
    UNSATISFACTORY(2),
    PASSED(null),
    NOT_PASSED(null);

    private final Integer numericValue;

    GradeValue(Integer numericValue) {
        this.numericValue = numericValue;
    }

    public Integer getNumericValue() {
        return numericValue;
    }
}
