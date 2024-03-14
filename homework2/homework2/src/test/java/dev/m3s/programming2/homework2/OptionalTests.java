package dev.m3s.programming2.homework2;
import static dev.m3s.programming2.homework2.H2.*;
import static dev.m3s.programming2.homework2.H2Matcher.*;

import java.time.Year;

import io.github.staffan325.automated_grading.Points;
import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@TestCategories({ @TestCategory("Optional tests") })
class OptionalTests {
    Object student;

    @BeforeEach
    public void setUp() {
        student = paramlessStudent.instantiate();
    }

    @Test
    @Points(category = "Optional tests", failDeduct = 0)
    @DisplayName("Dynamic startYear test")
    void dynamicYearTest() {

        // This is an optional test 
        // This test only works if current year is manually modified to differ from the real date
        // It is Used for checking whether the default value of the startYear variable is hardcoded or dynamic

        int modifiedYear = Year.now().getValue();

        System.out.println(String.format(
                "(Optional test)\n" + 
                "Setting date to %d in order to check if startYear is set dynamically to current year instead of being hardcoded", modifiedYear));

        int currentYear = startYear.read(student);

        assertEquals(currentYear, modifiedYear,
                explain("The current year should preferably be retrieved dynamically instead of being hardcoded."));

    }

}