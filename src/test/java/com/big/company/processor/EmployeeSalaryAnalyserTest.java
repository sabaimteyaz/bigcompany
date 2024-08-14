package com.big.company.processor;

import com.big.company.entity.Employee;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class EmployeeSalaryAnalyserTest {

    private EmployeeSalaryAnalyser analyser;
    private Employee manager1;
    private Employee manager2;
    private Employee employee1;
    private Employee employee2;
    private Employee employee3;
    private Employee employee4;

    @Before
    public void setUp() {
        analyser = new EmployeeSalaryAnalyser();

        // Mocking Employee objects
        manager1 = Mockito.mock(Employee.class);
        manager2 = Mockito.mock(Employee.class);
        employee1 = Mockito.mock(Employee.class);
        employee2 = Mockito.mock(Employee.class);
        employee3 = Mockito.mock(Employee.class);
        employee4 = Mockito.mock(Employee.class);

        // Setting up managers
        when(manager1.getId()).thenReturn("M1");
        when(manager2.getId()).thenReturn("M2");
        when(manager1.getSalary()).thenReturn(10000.0);
        when(manager2.getSalary()).thenReturn(200000.0);

        // Setting up employees and their salaries
        when(employee1.getId()).thenReturn("E1");
        when(employee2.getId()).thenReturn("E2");
        when(employee3.getId()).thenReturn("E3");
        when(employee4.getId()).thenReturn("E4");

        when(employee1.getManager()).thenReturn(manager1);
        when(employee2.getManager()).thenReturn(manager1);
        when(employee3.getManager()).thenReturn(manager2);
        when(employee4.getManager()).thenReturn(manager2);

        when(employee1.getSalary()).thenReturn(60000.0);
        when(employee2.getSalary()).thenReturn(70000.0);
        when(employee3.getSalary()).thenReturn(120000.0);
        when(employee4.getSalary()).thenReturn(130000.0);

        // Setting up EmployeeGenerator's employeeSet and employeeMap
        Set<Employee> employeeSet = new HashSet<>(Arrays.asList(manager1, manager2, employee1, employee2, employee3, employee4));
        EmployeeGenerator.employeeSet = employeeSet;

        Map<String, Employee> employeeMap = new HashMap<>();
        employeeMap.put("M1", manager1);
        employeeMap.put("M2", manager2);
        employeeMap.put("E1", employee1);
        employeeMap.put("E2", employee2);
        employeeMap.put("E3", employee3);
        employeeMap.put("E4", employee4);
        EmployeeGenerator.employeeMap = employeeMap;
    }

    @Test
    public void testAnalyse() {
        // Redirect system output to a stream
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        analyser.analyse();

        // Expected output
        String expectedOutput = "Managers earning less: M1, \n" +
                "Managers earning more: M2, \n";

        // Verify output
        assertEquals(expectedOutput.trim(), outContent.toString().trim());
    }
}
