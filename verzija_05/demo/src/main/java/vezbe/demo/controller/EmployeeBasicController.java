/*package vezbe.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import vezbe.demo.model.Employee;
import vezbe.demo.service.EmployeeService;

import java.util.List;

@Controller
public class EmployeeBasicController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/")
    public String welcome(){
        return "index.html";
    }

    @GetMapping("/employees")
    public String getEmployees(Model model){
        List<Employee> employeeList = employeeService.findAll();

        model.addAttribute("employees", employeeList);
        return "employees.html";
    }

    @GetMapping("/employees/{id}")
    public String getEmployee(@PathVariable(name = "id") Long id, Model model){
        Employee employee = employeeService.findOne(id);

        model.addAttribute("employee", employee);
        return "employee.html";
    }

    @GetMapping("/add-employee")
    public String addEmployee(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "add-employee.html";
    }

    @PostMapping("/save-employee")
    public String saveEmployee(@ModelAttribute Employee employee) {
        this.employeeService.save(employee);
        return "redirect:/employees";
    }
}
*/