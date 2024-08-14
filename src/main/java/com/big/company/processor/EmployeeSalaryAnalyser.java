package com.big.company.processor;

import java.util.*;

public class EmployeeSalaryAnalyser {

    public void analyse() {

        Map<String, List<Double>> managerSubrdinateSalaryMap = new HashMap<>();
        EmployeeGenerator.employeeSet.forEach(e -> {
            if (e.getManager() != null) {
                String managerId = e.getManager().getId();
                List<Double> salaryList;
                if (managerSubrdinateSalaryMap.containsKey(managerId)) {
                    salaryList = managerSubrdinateSalaryMap.get(managerId);
                    salaryList.add(e.getSalary());
                    managerSubrdinateSalaryMap.put(managerId, salaryList);
                } else {
                    salaryList = new ArrayList<>();
                    salaryList.add(e.getSalary());
                    managerSubrdinateSalaryMap.put(managerId, salaryList);
                }
            }
        });

        List<String> managersEarningLess = new ArrayList<>();
        List<String> managersEarningMore = new ArrayList<>();
        managerSubrdinateSalaryMap.entrySet().forEach(entry -> {
            if(!entry.getValue().isEmpty()) {
                double averageSubOrdinateSalary = entry.getValue().stream().mapToDouble(Double::doubleValue).average().getAsDouble();
                double salary = EmployeeGenerator.employeeMap.get(entry.getKey()).getSalary();
                double maxBound = averageSubOrdinateSalary*1.50;
                double minBound = averageSubOrdinateSalary*1.20;

                if(salary<minBound) {
                    managersEarningLess.add(entry.getKey());
                }

                if(salary> maxBound) {
                    managersEarningMore.add(entry.getKey());
                }
            }
        });

        System.out.print("Managers earning less: ");
        managersEarningLess.forEach(e-> System.out.print(e + ", "));
        System.out.println();
        System.out.print("Managers earning more: ");
        managersEarningMore.forEach(e-> System.out.print(e + ", "));
        System.out.println();
    }
}
