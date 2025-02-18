package com.huudan.springbootdemo.controller;

import com.huudan.springbootdemo.model.Employee;
import com.huudan.springbootdemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2/employees")
public class EmployeeV2Controller {


    private final EmployeeService employeeService;

    public EmployeeV2Controller( @Qualifier("employeeV2ServiceImpl")EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public Employee save(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable String id) {
        return employeeService.getEmployeeById(id);
    }

    @DeleteMapping(value = "/{id}")
    public String deleteEmployeeById(@PathVariable String id) {
        return employeeService.deleteEmployeeById(id);
    }
}
