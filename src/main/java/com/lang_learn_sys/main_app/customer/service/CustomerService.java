package com.lang_learn_sys.main_app.customer.service;

import com.lang_learn_sys.main_app.customer.entity.Customer;
import com.lang_learn_sys.main_app.customer.repo.CustomerRepository;
import com.lang_learn_sys.main_app.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository theCustomerRepository;

    @Transactional
    public List<Customer> getAllCustomers(){
        return theCustomerRepository.findAll();
    }
    @Transactional
    public Customer getCutomerById(Long id){
        List<Customer> customers = getAllCustomers();
        for (Customer cust:customers) {
            if(cust.getUser_id()==id) return cust;
        }
        return null;
    }
    @Transactional
    public boolean deleteCustomerById(Long id){
        if(getCutomerById(id)==null)
            return false;
        theCustomerRepository.delete(getCutomerById(id));
        return true;
    }
    @Transactional
    public boolean addOrUpdateCustomer(Customer theCustomer, UserService theUserService){
        if(theUserService.findUserById((long)theCustomer.getUser_id())==null) return false;
        theCustomerRepository.save(theCustomer);
        return true;
    }
}
