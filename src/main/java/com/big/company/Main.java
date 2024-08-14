package com.big.company;

import com.big.company.processor.EmployeeGenerator;
import com.big.company.processor.EmployeeReportingAnalyser;
import com.big.company.processor.EmployeeSalaryAnalyser;

public class Main {
    public static void main(String[] args) {
        EmployeeGenerator employeeGenerator = new EmployeeGenerator();
        employeeGenerator.readEmployees();

        EmployeeSalaryAnalyser employeeSalaryAnalyser = new EmployeeSalaryAnalyser();
        employeeSalaryAnalyser.analyse();

        EmployeeReportingAnalyser employeeReportingAnalyser = new EmployeeReportingAnalyser();
        employeeReportingAnalyser.analyse();

    }
}