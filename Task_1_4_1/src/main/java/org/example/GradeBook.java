package org.example;

import java.util.ArrayList;
import java.util.List;

public class GradeBook {
    private final List<Grade> grades;
    private EducationType educationType;
    private int currentSemester;

    public GradeBook(EducationType educationType, int currentSemester) {
        if (educationType == null) {
            throw new IllegalArgumentException("Форма обучения не может быть null");
        }
        if (currentSemester <= 0) {
            throw new IllegalArgumentException("Номер семестра должен быть положителен");
        }

        this.educationType = educationType;
        this.currentSemester = currentSemester;
        this.grades = new ArrayList<>();
    }

    public void addGrade(Grade grade) {
        if (grade == null) {
            throw new IllegalArgumentException("Оценка не может быть null");
        }
        if (grade.getSemester() > currentSemester) {
            throw new IllegalArgumentException("Семестр оценки больше текущего семестра");
        }

        grades.add(grade);
    }

    public double getAverageGrade() {
        double gradesSum = 0.0;
        int gradesCount = 0;

        for (Grade grade : grades) {
            if (grade.getGradeValue().getNumericValue() != null) {
                gradesSum += grade.getGradeValue().getNumericValue();
                gradesCount++;
            }
        }

        if (gradesCount == 0) {
            return 0.0;
        }

        return gradesSum / gradesCount;
    }

    public List<Grade> getGrades() {
        return List.copyOf(grades);
    }
}
