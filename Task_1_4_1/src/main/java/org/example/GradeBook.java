package org.example;

import java.util.ArrayList;
import java.util.List;

public class GradeBook {
    private final List<Grade> grades;
    private final EducationType educationType;
    private final int currentSemester;
    private final Curriculum curriculum;

    public GradeBook(EducationType educationType, int currentSemester, Curriculum curriculum) {
        if (educationType == null) {
            throw new IllegalArgumentException("Форма обучения не может быть null");
        }
        if (currentSemester <= 0) {
            throw new IllegalArgumentException("Номер семестра должен быть положителен");
        }
        if (curriculum == null) {
            throw new IllegalArgumentException("Учебный план не может быть null");
        }

        this.educationType = educationType;
        this.currentSemester = currentSemester;
        this.curriculum = curriculum;
        this.grades = new ArrayList<>();
    }

    public void addGrade(Grade grade) {
        if (grade == null) {
            throw new IllegalArgumentException("Оценка не может быть null");
        }

        if (grade.getSemester() > currentSemester) {
            throw new IllegalArgumentException("Семестр оценки больше текущего семестра");
        }

        if (!isAssessmentPlanned(grade)) {
            throw new IllegalArgumentException("Аттестация отсутствует в учебном плане");
        }

        if (hasGradeForAssessment(grade)) {
            throw new IllegalArgumentException("Оценка за эту аттестацию уже добавлена");
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

    public boolean canTransferToBudget() {
        if (educationType == EducationType.BUDGET || currentSemester < 3) {
            return false;
        }

        boolean hasExamsInPreviousSemester = false;
        boolean hasExamsInSecondPreviousSemester = false;

        for (PlannedAssessment assessment : curriculum.getAssessments()) {
            if (assessment.getControlType() != ControlType.EXAM) {
                continue;
            }

            if (assessment.getSemester() == currentSemester - 1) {
                hasExamsInPreviousSemester = true;
            } else if (assessment.getSemester() == currentSemester - 2) {
                hasExamsInSecondPreviousSemester = true;
            } else {
                continue;
            }

            Grade grade = findGrade(assessment);

            if (grade == null) {
                return false;
            }

            if (grade.getGradeValue() == GradeValue.SATISFACTORY
                    || grade.getGradeValue() == GradeValue.UNSATISFACTORY) {
                return false;
            }
        }

        return hasExamsInPreviousSemester && hasExamsInSecondPreviousSemester;
    }

    private boolean isAssessmentPlanned(Grade grade) {
        for (PlannedAssessment assessment : curriculum.getAssessments()) {
            if (assessment.getSubjectName().equals(grade.getSubjectName())
                    && assessment.getSemester() == grade.getSemester()
                    && assessment.getControlType() == grade.getControlType()) {
                return true;
            }
        }

        return false;
    }

    private boolean hasGradeForAssessment(Grade grade) {
        for (Grade existing : grades) {
            if (existing.getSubjectName().equals(grade.getSubjectName())
                    && existing.getSemester() == grade.getSemester()
                    && existing.getControlType() == grade.getControlType()) {
                return true;
            }
        }

        return false;
    }

    private Grade findGrade(PlannedAssessment assessment) {
        for (Grade grade : grades) {
            if (grade.getSubjectName().equals(assessment.getSubjectName())
                    && grade.getSemester() == assessment.getSemester()
                    && grade.getControlType() == assessment.getControlType()) {
                return grade;
            }
        }

        return null;
    }

    private boolean isDiplomaAssessment(PlannedAssessment assessment) {
        ControlType assessmentControlType = assessment.getControlType();

        return assessmentControlType == ControlType.EXAM
                || assessmentControlType == ControlType.DIFFERENTIATED_CREDIT
                || assessmentControlType == ControlType.PRACTICE;
    }

    private boolean isLastDiplomaAssessment(PlannedAssessment assessment) {
        for (PlannedAssessment other : curriculum.getAssessments()) {
            if (other.getSubjectName().equals(assessment.getSubjectName())
                    && other.getSemester() > assessment.getSemester()
                    && isDiplomaAssessment(other)) {
                return false;
            }
        }

        return true;
    }

    private boolean canGetExcellentThesis() {
        for (PlannedAssessment assessment : curriculum.getAssessments()) {
            if (assessment.getControlType() == ControlType.THESIS) {
                Grade thesisGrade = findGrade(assessment);

                if (thesisGrade == null) {
                    return assessment.getSemester() >= currentSemester;
                }

                return thesisGrade.getGradeValue() == GradeValue.EXCELLENT;
            }
        }

        return false;
    }

    public boolean canGetRedDiploma() {
        int totalGrades = 0;
        int excellentGrades = 0;
        int remainingGrades = 0;

        for (PlannedAssessment assessment : curriculum.getAssessments()) {
            if (!isDiplomaAssessment(assessment) || !isLastDiplomaAssessment(assessment)) {
                continue;
            }

            totalGrades++;

            Grade grade = findGrade(assessment);

            if (grade == null) {
                if (assessment.getSemester() < currentSemester) {
                    return false;
                }

                remainingGrades++;
            } else {
                if (grade.getGradeValue() == GradeValue.EXCELLENT) {
                    excellentGrades++;
                } else if (grade.getGradeValue() != GradeValue.GOOD) {
                    return false;
                }
            }
        }

        return totalGrades > 0
                && (excellentGrades + remainingGrades) * 100 >= totalGrades * 75
                && canGetExcellentThesis();
    }
}
