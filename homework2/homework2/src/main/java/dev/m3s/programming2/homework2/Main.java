package dev.m3s.programming2.homework2;

public class Main {
    public static void main(String[] args) {
    //     // 1. Create a student using the constructor with no parameters
    //     Student student = new Student();

    //     // 2. Create 11 Courses
    //     Course[] courses = new Course[]{
    //         new Course("Programming 1", 811104, 'P', 1, 1, 5.0, true),
    //         new Course("All kinds of basic studies", 112233, 'P', 1, 2, 45.0, true),
    //         new Course("More basic studies", 223344, 'a', 1, 1, 50.5, true),
    //         new Course("Even more basic studies", 556677, 'a', 0, 3, 50.0, true),
    //         new Course("Final basic studies", 123123, 'A', 1, 4, 50.5, true),
    //         new Course("Programming 2", 616161, 'A', 1, 3, 25.0, true),
    //         new Course("All kinds of master studies", 717171, 'P', 0, 2, 45.0, true),
    //         new Course("More master studies", 818181, 'A', 1, 1, 25.0, true),
    //         new Course("Even more master studies", 919191, 'S', 1, 3, 20.0, true),
    //         new Course("Extra master studies", 666666, 'S', 0, 5, 8.0, false),
    //         new Course("Final master studies", 888888, 'S', 1, 5, 18.0, false)
    //     };

    //     // 3. Create StudentCourses from the courses above
    //     StudentCourse[] studentCourses = new StudentCourse[]{
    //         new StudentCourse(courses[0], 1, 2013),
    //         new StudentCourse(courses[1], 1, 2014),
    //         new StudentCourse(courses[2], 1, 2015),
    //         new StudentCourse(courses[3], 4, 2016),
    //         new StudentCourse(courses[4], 5, 2017),
    //         new StudentCourse(courses[5], 1, 2018),
    //         new StudentCourse(courses[6], 1, 2019),
    //         new StudentCourse(courses[7], 2, 2020),
    //         new StudentCourse(courses[8], 0, 2021),
    //         new StudentCourse(courses[9], 'A', 2021),
    //         new StudentCourse(courses[10], 'f', 2022)
    //     };

    //     // Steps 4 & 5: Create arrays for bachelor and master StudentCourses
    //     StudentCourse[] bachelorCourses = new StudentCourse[]{studentCourses[0], studentCourses[1], studentCourses[2], studentCourses[3], studentCourses[4]};
    //     StudentCourse[] masterCourses = new StudentCourse[]{studentCourses[5], studentCourses[6], studentCourses[7], studentCourses[8], studentCourses[9], studentCourses[10]};

    //     // Steps 6-11: Setting details for the student
    //     student.setDegreeTitle(0, "Bachelor of Science");
    //     student.setDegreeTitle(1, "Master of Science");
    //     student.setTitleOfThesis(0, "Bachelor thesis title");
    //     student.setTitleOfThesis(1, "Masters thesis title");
    //     student.addCourses(0, bachelorCourses);
    //     student.addCourses(1, masterCourses);
    //     student.setStartYear(2001);
    //     student.setGraduationYear(2020);
    //     student.setFirstName("Donald");
    //     student.setLastName("Duck");

    //     // 16. Print the details of the student using toString method.
    //     System.out.println(student);

    //     // 17-18. Set birthdate and update thesis titles
    //     student.setBirthDate("230498-045T");
    //     student.setTitleOfThesis(0, "Christmas - The most wonderful time of the year");
    //     student.setTitleOfThesis(1, "Dreaming of a white Christmas");

    //     // 20. Print the degrees of the student
    //     student.printDegrees();

    //     // 21. Set the grade of the course 919191S to 3
    //     for (StudentCourse studentCourse : studentCourses) {
    //         if (studentCourse.getCourse().getCourseCode().equals("919191S")) {
    //             studentCourse.setGrade(3);
    //             break;
    //         }
    //     }

    //     // 22 & 23. Print the details and degrees of the student again
    //     System.out.println(student);
    //     student.printDegrees();

    //     // 24. Print the courses of the student
    //     student.printCourses();

    //     // 25. Set the grade of the course 888888S to ‘X’
    //     for (StudentCourse studentCourse : studentCourses) {
    //         if (studentCourse.getCourse().getCourseCode().equals("888888S")) {
    //             studentCourse.setGrade('X');
    //             break;
    //         }
    //     }

    //     // 26. Print the details of the StudentCourse 888888S.
    //     // Assuming `printCourseDetails` is a method in `Student` that prints details of a course
    //     student.printCourseDetails(888888, 'S');

    //     // 27-32. Adjusting grades and printing details as instructed
    //     student.setCourseGrade(888888, 'S', 'a');
    //     student.printCourseDetails(888888, 'S');
    //     student.setCourseGrade(811104, 'P', 6);
    //     student.printCourseDetails(811104, 'P');
    //     student.setCourseGrade(811104, 'P', 5);
    //     student.printCourseDetails(811104, 'P');
    }
}