package org.example;

import java.util.ArrayList;
import java.util.List;

public class Curriculum {
    private final List<PlannedAssessment> assessments;

    public Curriculum() {
        this.assessments = new ArrayList<>();
    }

    public void addAssessment(PlannedAssessment assessment) {
        if (assessment == null) {
            throw new IllegalArgumentException("Аттестация не может быть null");
        }

        for (PlannedAssessment existing : assessments) {
            if (existing.getSubjectName().equals(assessment.getSubjectName())
                    && existing.getSemester() == assessment.getSemester()
                    && existing.getControlType() == assessment.getControlType()) {
                throw new IllegalArgumentException("Такая аттестация уже есть в учебном плане");
            }

            if (assessment.getControlType() == ControlType.THESIS
                    && existing.getControlType() == ControlType.THESIS) {
                throw new IllegalArgumentException("В учебном плане может быть только одна ВКР");
            }
        }

        assessments.add(assessment);
    }

    public List<PlannedAssessment> getAssessments() {
        return List.copyOf(assessments);
    }
}
