package dev.m3s.programming2.homework3;
import static dev.m3s.programming2.homework3.H3.*;
import static dev.m3s.programming2.homework3.H3Matcher.*;

import java.time.Year;

import io.github.staffan325.automated_grading.Points;
import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

@TestCategories({ @TestCategory("Optional tests") })
class OptionalTests {
    Object student;

    @BeforeEach
    public void setUp() {
    }

    @Test
    @Points(category = "Optional tests", failDeduct = 0)
    @DisplayName("Dynamic student startYear test")
    void dynamicStudentStartYearTest() {

        // This is an optional test 
        // This test only works if current year is manually modified to differ from the real date
        // It is Used for checking whether the default value of the startYear variable is hardcoded or dynamic

        String fName = randomString(6);
		String lName = randomString(8);
		Object student = twoParamStudent.create(lName, fName);

        int modifiedYear = Year.now().getValue();
        

        System.out.println(String.format(
                "(Optional test)\n" + 
                "Setting date to %d in order to check if startYear is set dynamically to current year instead of being hardcoded", modifiedYear));

        int defaultStartYear = startYear.read(student);

        assertEquals(defaultStartYear, modifiedYear,
                explain("The current year should preferably be retrieved dynamically instead of being hardcoded."));

    }

    @EnabledIfEnvironmentVariable(named = H3.ENV_VAR, matches = H3.V1)
    @Test
    @Points(category = "Optional tests", failDeduct = 0)
    @DisplayName("Dynamic employee startYear test")
    void dynamicEmployeeStartYearTest() {

        // This is an optional test 
        // This test only works if current year is manually modified to differ from the real date
        // It is Used for checking whether the default value of the startYear variable is hardcoded or dynamic

        String fName = randomString(6);
		String lName = randomString(8);

        Object employee  = twoParamEmployee.instantiate(lName, fName);

        int modifiedYear = Year.now().getValue();

        System.out.println(String.format(
                "(Optional test)\n" + 
                "Setting date to %d in order to check if startYear is set dynamically to current year instead of being hardcoded", modifiedYear));

        int defaultStartYear = empStartYear.read(employee);

        assertEquals(defaultStartYear, modifiedYear,
                explain("The current year should preferably be retrieved dynamically instead of being hardcoded."));

    } 


    @EnabledIfEnvironmentVariable(named = H3.ENV_VAR, matches = H3.V2)
    @Test
    @Points(category = "Optional tests", failDeduct = 0)
    @DisplayName("Dynamic employee startYear test")
    void V2dynamicEmployeeStartYearTest() {

        // This is an optional test 
        // This test only works if current year is manually modified to differ from the real date
        // It is Used for checking whether the default value of the startYear variable is hardcoded or dynamic

        String fName = randomString(6);
		String lName = randomString(8);

        Object employee = twoParamResponsibleTeacher.instantiate(lName, fName);

        int modifiedYear = Year.now().getValue();

        System.out.println(String.format(
                "(Optional test)\n" + 
                "Setting date to %d in order to check if startYear is set dynamically to current year instead of being hardcoded", modifiedYear));

        int defaultStartYear = empStartYear.read(employee);

        assertEquals(defaultStartYear, modifiedYear,
                explain("The current year should preferably be retrieved dynamically instead of being hardcoded."));

    }

}