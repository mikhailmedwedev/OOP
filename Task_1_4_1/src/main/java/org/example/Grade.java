package org.example;

public class Grade {
    private final String subjectName;
    private final int semester;
    private final GradeValue gradeValue;
    private final ControlType controlType;

    public Grade(String subjectName, int semester, GradeValue gradeValue, ControlType controlType) {
        if (subjectName == null || subjectName.isBlank()) {
            throw new IllegalArgumentException("Названия предмета не может быть пустым");
        }
        if (semester <= 0) {
            throw new IllegalArgumentException("Номер семестра должен быть положительным");
        }
        if (gradeValue == null) {
            throw new IllegalArgumentException("Значение оценки не может быть null");
        }
        if (controlType == null) {
            throw new IllegalArgumentException("Вид контроля не может быть null");
        }

        if (controlType == ControlType.CREDIT
                && gradeValue != GradeValue.PASSED
                && gradeValue != GradeValue.NOT_PASSED) {
            throw new IllegalArgumentException("Недопустимая оценка для зачета");
        }
        if (controlType != ControlType.CREDIT
                && (gradeValue == GradeValue.PASSED
                || gradeValue == GradeValue.NOT_PASSED)) {
            throw new IllegalArgumentException("Недопустимая оценка для данного вида контроля");
        }

        this.subjectName = subjectName;
        this.semester = semester;
        this.gradeValue = gradeValue;
        this.controlType = controlType;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getSemester() {
        return semester;
    }

    public GradeValue getGradeValue() {
        return gradeValue;
    }

    public ControlType getControlType() {
        return controlType;
    }
}
