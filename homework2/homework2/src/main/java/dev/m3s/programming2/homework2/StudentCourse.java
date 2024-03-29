package dev.m3s.programming2.homework2;

import java.time.Year;
import java.util.Locale;

public class StudentCourse {

    // ATTRIBUTES

    private Course course;
    private int gradeNum;
    private int yearCompleted = 0;

    // CONSTUCTORS

    public StudentCourse() {
    }

    public StudentCourse(Course course, final int gradeNum, final int yearCompleted) {
        if (yearCompleted >= 2000) {
            this.yearCompleted = yearCompleted;
        }
        setCourse(course);
        setGrade(gradeNum);
    }

    // METHODS

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        if (course != null) {
            this.course = course;
        }
    }

    public int getGradeNum() {
        return gradeNum;
    }

    protected void setGrade(int gradeNum) {
        if (checkGradeValidity(gradeNum)) {
            this.gradeNum = gradeNum;
            if (yearCompleted == 0) {
                this.yearCompleted = Year.now().getValue();
            }
        }
    }

    private boolean checkGradeValidity(final int gradeNum) {
        return (gradeNum >= ConstantValues.MIN_GRADE && gradeNum <= ConstantValues.MAX_GRADE)
                || gradeNum == ConstantValues.GRADE_ACCEPTED || gradeNum == ConstantValues.GRADE_FAILED;
    }

    public boolean isPassed() {
        if (course.isNumericGrade()) {
            return gradeNum > 0;
        } else {
            char gradeChar = (char) gradeNum;
            return gradeChar == 'A';
        }
    }

    public int getYear() {
        return yearCompleted;
    }

    public void setYear(int yearCompleted) {
        if (yearCompleted > 2000 && yearCompleted <= Year.now().getValue()) {
            this.yearCompleted = yearCompleted;
        }
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("[")
                .append(course.getCourseCode())
                .append(" (")
                .append(String.format(Locale.US, "%.2f", course.getCredits()))
                .append(" cr), \"")
                .append(course.getName())
                .append("\". ")
                .append(course.getCourseTypeString())
                .append(", period: ")
                .append(course.getPeriod())
                .append(".] Year: ")
                .append(yearCompleted)
                .append(", Grade: ");

        if (course.isNumericGrade()) {
            if (gradeNum == 0) {
                str.append("Not graded");
            } else {
                str.append(gradeNum);
            }
        } else {
            char grade = isPassed() ? ConstantValues.GRADE_ACCEPTED : ConstantValues.GRADE_FAILED;
            str.append(grade);
        }

        str.append(".]");
        return str.toString();
    }
}
