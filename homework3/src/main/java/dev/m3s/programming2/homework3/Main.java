package dev.m3s.programming2.homework3;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Employee mickey = new ResponsibleTeacher("Mouse", "Mickey");
        //mickey.MonthlyPayment(756.85);
        // Employee goofy = new AssistanTeacher("The Dog", "Goofy");
        //goofy.HourBasedPayment(3.5, 11);

        Student student1 = new Student("Duck", "Donald");
        Course course1 = new Course("Programming 1", 811104, 'P', 1, 2, 5.0, true);
        Course course2 = new Course("All kinds of basic studies", 112233, 'P', 1, 2, 45.0, true);
        Course course3 = new Course("More basic studies", 223344, 'A', 1, 1, 50.5, true);
        Course course4 = new Course("Even more basic studies", 556677, 'A', 0, 3, 50.0, true);
        Course course5 = new Course("Final basic studies", 123123, 'A', 1, 4, 50.5, true);
        Course course6 = new Course("Programming 2", 616161, 'A', 1, 3, 25.0, true);
        Course course7 = new Course("All kinds of master studies", 717171, 'P', 0, 2, 45.0, true);
        Course course8 = new Course("More master studies", 818181, 'A', 1, 1, 25.0, true);
        Course course9 = new Course("Even more master studies ", 919191, 'S', 1, 3, 20.0, true);
        Course course10 = new Course("Extra master studies", 666666, 'S', 0, 5, 8.0, false);
        Course course11 = new Course("Final master studies", 888888, 'S', 1, 5, 18.0, false);

        // DesignatedCourse designatedCourse3 = new DesignatedCourse(course3, true, 2023);
        // DesignatedCourse designatedCourse4 = new DesignatedCourse(course4, false, 2023);
        // DesignatedCourse designatedCourse10 = new DesignatedCourse(course10, false, 2022);
        // DesignatedCourse designatedCourse11 = new DesignatedCourse(course11, true, 2023);

        // List<DesignatedCourses> designatedCourses = new ArrayList<>();

        // designatedCourses.add(designatedCourse3);
        // designatedCourses.add(designatedCourse4);
        // designatedCourses.add(designatedCourse10);
        // designatedCourses.add(designatedCourse11);

        // designatedCourse3
    }
}