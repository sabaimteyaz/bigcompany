package com.big.company.processor;

import com.big.company.entity.Employee;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class EmployeeReportingAnalyserTest {

    private EmployeeReportingAnalyser analyser;
    private Employee employee1;
    private Employee employee2;
    private Employee employee3;
    private Employee employee4;
    private Employee employee5;

    @Before
    public void setUp() {
        analyser = new EmployeeReportingAnalyser();

        // Mocking Employee objects
        employee1 = Mockito.mock(Employee.class);
        employee2 = Mockito.mock(Employee.class);
        employee3 = Mockito.mock(Employee.class);
        employee4 = Mockito.mock(Employee.class);
        employee5 = Mockito.mock(Employee.class);

        // Setting up the hierarchy
        when(employee1.getId()).thenReturn("E1");
        when(employee2.getId()).thenReturn("E2");
        when(employee3.getId()).thenReturn("E3");
        when(employee4.getId()).thenReturn("E4");
        when(employee5.getId()).thenReturn("E5");

        when(employee1.getManager()).thenReturn(employee2);
        when(employee2.getManager()).thenReturn(employee3);
        when(employee3.getManager()).thenReturn(employee4);
        when(employee4.getManager()).thenReturn(employee5);
        when(employee5.getManager()).thenReturn(null);

        // Assigning the mocked employees to the employeeSet
        Set<Employee> employeeSet = new HashSet<>();
        employeeSet.add(employee1);
        employeeSet.add(employee2);
        employeeSet.add(employee3);
        employeeSet.add(employee4);
        employeeSet.add(employee5);

        EmployeeGenerator.employeeSet = employeeSet;
    }

    @Test
    public void testAnalyse() {
        // Redirect system output to a stream
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        analyser.analyse();

        // Verify output
        String expectedOutput = "Employees with long reporting: E1,";
        assertEquals(expectedOutput, outContent.toString().trim());
    }

    @Test
    public void testGetManagerHierarchyCount() {
        int count = analyser.getManagerHierarchyCount(employee1);
        assertEquals(4, count);

        count = analyser.getManagerHierarchyCount(employee2);
        assertEquals(3, count);

        count = analyser.getManagerHierarchyCount(employee3);
        assertEquals(2, count);

        count = analyser.getManagerHierarchyCount(employee4);
        assertEquals(1, count);

        count = analyser.getManagerHierarchyCount(employee5);
        assertEquals(0, count);
    }
}
