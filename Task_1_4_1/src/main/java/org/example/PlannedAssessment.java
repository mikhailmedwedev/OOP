package org.example;

public class PlannedAssessment {
    private final String subjectName;
    private final int semester;
    private final ControlType controlType;

    public PlannedAssessment(String subjectName, int semester, ControlType controlType) {
        if (subjectName == null || subjectName.isBlank()) {
            throw new IllegalArgumentException("Названия предмета не может быть пустым");
        }
        if (semester <= 0) {
            throw new IllegalArgumentException("Номер семестра должен быть положительным");
        }
        if (controlType == null) {
            throw new IllegalArgumentException("Вид контроля не может быть null");
        }

        this.subjectName = subjectName;
        this.semester = semester;
        this.controlType = controlType;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getSemester() {
        return semester;
    }

    public ControlType getControlType() {
        return controlType;
    }
}
