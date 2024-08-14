package com.big.company.processor;

import com.big.company.entity.Employee;

import java.util.HashMap;
import java.util.Map;

public class EmployeeReportingAnalyser {
    Map<String, Integer> hierarchyCountMap = new HashMap<>();

    public void analyse() {
        System.out.print("Employees with long reporting: ");
        EmployeeGenerator.employeeSet.forEach(e -> {
            if(getManagerHierarchyCount(e) >= 4) {
                System.out.print(e.getId() + ", ");
            }
        });
    }

    public int getManagerHierarchyCount(Employee employee) {
        Employee manager = employee.getManager();
        if(manager == null) {
            return 0;
        }

        if(hierarchyCountMap.containsKey(employee.getId())) {
            return hierarchyCountMap.get(employee.getId());
        }

        int count = 1 + getManagerHierarchyCount(manager);

        hierarchyCountMap.put(employee.getId(), count);
        return count;
    }
}
