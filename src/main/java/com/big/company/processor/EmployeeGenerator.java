package com.big.company.processor;

import com.big.company.entity.Employee;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

public class EmployeeGenerator {

    public static Set<Employee> employeeSet = new HashSet<>();
    public static Map<String, Employee> employeeMap = new HashMap<>();
    public static Employee ceo;

    public void readEmployees() {
        String filePath = "src/main/resources/employees.csv";

        // Create a FileReader
        try (Reader reader = new FileReader(filePath)) {
            // Create CSVFormat and CSVParser
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(reader);

            // Iterate over the records
            for (CSVRecord record : records) {
                try {
                    String employeeId = record.get("Id");
                    Employee employee = employeeMap.containsKey(employeeId) ? employeeMap.get(employeeId) : new Employee();
                    employee.setId(record.get("Id"));
                    employee.setFirstName(record.get("firstName"));
                    employee.setLastName(record.get("lastName"));
                    employee.setSalary(Double.parseDouble(record.get("salary")));
                    Employee manager;
                    if (!record.isSet("managerId")) {
                        ceo = employee;
                    } else {
                        String managerId = record.get("managerId");
                        if (employeeMap.containsKey(managerId)) {
                            manager = employeeMap.get(managerId);
                        } else {
                            manager = new Employee();
                            manager.setId(managerId);
                            employeeMap.put(managerId, manager);
                        }
                        employee.setManager(manager);
                    }
                    employeeSet.add(employee);
                    employeeMap.put(employee.getId(), employee);
                } catch (Exception e) {
                    System.err.println("An error occurred while processing record: " + record.getRecordNumber() +" "+ e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the CSV file: " + e.getMessage());
        }
    }
}
