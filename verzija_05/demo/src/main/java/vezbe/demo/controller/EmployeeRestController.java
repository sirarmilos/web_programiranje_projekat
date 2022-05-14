/*package vezbe.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vezbe.demo.model.Employee;
import vezbe.demo.service.EmployeeService;

import java.util.List;

@RestController
public class EmployeeRestController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/api/")
    public String welcome(){
        return "Hello from api!";
    }

    @GetMapping("/api/employees")
    public List<Employee> getEmployees(){
        List<Employee> employeeList = employeeService.findAll();

        return employeeList;
    }

    @GetMapping("/api/employees/{id}")
    public Employee getEmployee(@PathVariable(name = "id") Long id){
        Employee employee = employeeService.findOne(id);
        return employee;
    }

    @PostMapping("/api/save-employee")
    public String saveEmployee(@RequestBody Employee employee) {
        this.employeeService.save(employee);
        return "Successfully saved an employee!";
    }
}
*/