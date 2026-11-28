package org.example;

/**
 * Демонстрирует пример работы зачётной книжки.
 */
public class Main {
    public static void main(String[] args) {
        Curriculum curriculum = new Curriculum();

        curriculum.addAssessment(new PlannedAssessment(
                "Математический анализ", 1, ControlType.EXAM));
        curriculum.addAssessment(new PlannedAssessment(
                "Программирование", 1, ControlType.EXAM));
        curriculum.addAssessment(new PlannedAssessment(
                "История", 1, ControlType.EXAM));
        curriculum.addAssessment(new PlannedAssessment(
                "Физическая культура", 1, ControlType.CREDIT));

        curriculum.addAssessment(new PlannedAssessment(
                "Математический анализ", 2, ControlType.EXAM));
        curriculum.addAssessment(new PlannedAssessment(
                "Программирование", 2, ControlType.EXAM));
        curriculum.addAssessment(new PlannedAssessment(
                "Дискретная математика", 2, ControlType.DIFFERENTIATED_CREDIT));
        curriculum.addAssessment(new PlannedAssessment(
                "Физика", 2, ControlType.EXAM));
        curriculum.addAssessment(new PlannedAssessment(
                "Физическая культура", 2, ControlType.CREDIT));

        curriculum.addAssessment(new PlannedAssessment(
                "Выпускная квалификационная работа", 8, ControlType.THESIS));

        EducationType educationType = EducationType.PAID;
        int currentSemester = 3;

        GradeBook gradeBook = new GradeBook(educationType, currentSemester, curriculum);

        gradeBook.addGrade(new Grade(
                "Математический анализ", 1, GradeValue.EXCELLENT, ControlType.EXAM));
        gradeBook.addGrade(new Grade(
                "Программирование", 1, GradeValue.GOOD, ControlType.EXAM));
        gradeBook.addGrade(new Grade(
                "История", 1, GradeValue.EXCELLENT, ControlType.EXAM));
        gradeBook.addGrade(new Grade(
                "Физическая культура", 1, GradeValue.PASSED, ControlType.CREDIT));

        gradeBook.addGrade(new Grade(
                "Математический анализ", 2, GradeValue.EXCELLENT, ControlType.EXAM));
        gradeBook.addGrade(new Grade(
                "Программирование", 2, GradeValue.EXCELLENT, ControlType.EXAM));
        gradeBook.addGrade(new Grade(
                "Дискретная математика", 2, GradeValue.EXCELLENT,
                ControlType.DIFFERENTIATED_CREDIT));
        gradeBook.addGrade(new Grade(
                "Физика", 2, GradeValue.GOOD, ControlType.EXAM));
        gradeBook.addGrade(new Grade(
                "Физическая культура", 2, GradeValue.PASSED, ControlType.CREDIT));

        System.out.println("========================================");
        System.out.println("          ЭЛЕКТРОННАЯ ЗАЧЁТНАЯ КНИЖКА");
        System.out.println("========================================");

        System.out.println("Форма обучения: " + educationType);
        System.out.println("Текущий семестр: " + currentSemester);

        System.out.printf("%nСредний балл: %.2f%n", gradeBook.getAverageGrade());

        System.out.println("Перевод на бюджет: "
                + (gradeBook.canTransferToBudget() ? "возможен" : "невозможен"));

        System.out.println("Красный диплом: "
                + (gradeBook.canGetRedDiploma() ? "возможен" : "пока невозможен"));

        System.out.println("========================================");
    }
}
