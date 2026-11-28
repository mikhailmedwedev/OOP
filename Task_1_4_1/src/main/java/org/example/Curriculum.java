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

        assessments.add(assessment);
    }

    public List<PlannedAssessment> getAssessments() {
        return List.copyOf(assessments);
    }
}
