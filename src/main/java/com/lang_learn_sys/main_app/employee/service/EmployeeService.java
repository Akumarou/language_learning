package com.lang_learn_sys.main_app.employee.service;

import com.lang_learn_sys.main_app.customer.entity.Customer;
import com.lang_learn_sys.main_app.customer.service.CustomerService;
import com.lang_learn_sys.main_app.employee.entity.Employee;
import com.lang_learn_sys.main_app.employee.repo.EmployeeRepository;
import com.lang_learn_sys.main_app.security.entity.Role;
import com.lang_learn_sys.main_app.security.service.RoleService;
import com.lang_learn_sys.main_app.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository theEmployeeRepository;
    @Autowired
    CustomerService theCustomerService;
    @Autowired
    UserService theUserService;
    @Autowired
    RoleService theRoleService;

    @Transactional
    public List<Employee> getAllEmployeesByRole(String role){
        List<Employee> temp = theEmployeeRepository.findAll();
        temp.removeIf(empl -> !theUserService.findUserById(empl.getUser_id()).getRoles().contains(theRoleService.getRoleByName(role)));
        return temp;
    }

    @Transactional
    public List<Employee> getAllEmployees(){
        return theEmployeeRepository.findAll();
    }
    @Transactional
    public Employee getEmployeeById(Long id){
        List<Employee> employees = getAllEmployees();
        for (Employee empl:employees) {
            if(empl.getUser_id()==id) return empl;
        }
        return null;
    }
    @Transactional
    public boolean deleteEmployeeById(Long id){
        if(getEmployeeById(id)==null)
            return false;
        theEmployeeRepository.delete(getEmployeeById(id));
        return true;
    }
    @Transactional
    public boolean addOrUpdateEmployee(Employee theEmployee, UserService theUserService){
        if(theUserService.findUserById((long)theEmployee.getUser_id())==null) return false;
        theEmployeeRepository.save(theEmployee);
        Customer cutomerById = theCustomerService.getCutomerById(theEmployee.getUser_id());
        cutomerById.setFirstName(theEmployee.getFirstName());
        cutomerById.setLastName(theEmployee.getLastName());
        cutomerById.setEmail(theEmployee.getEmail());
        theCustomerService.addOrUpdateCustomer(cutomerById,theUserService);
        return true;
    }
}
