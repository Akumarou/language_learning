package com.lang_learn_sys.main_app.security.service;

import com.lang_learn_sys.main_app.customer.entity.Customer;
import com.lang_learn_sys.main_app.customer.service.CustomerService;
import com.lang_learn_sys.main_app.employee.service.EmployeeService;
import com.lang_learn_sys.main_app.security.entity.Role;
import com.lang_learn_sys.main_app.security.entity.User;
import com.lang_learn_sys.main_app.security.repo.RoleRepository;
import com.lang_learn_sys.main_app.security.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    CustomerService theCustomerService;
    @Autowired
    EmployeeService theEmployeeService;
    @Autowired
    BCryptPasswordEncoder theBCryptPasswordEncoder;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(theBCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        Customer tempCust = new Customer();
        tempCust.setUser_id(user.getId());
        tempCust.setFirstName(user.getUsername());
        theCustomerService.addOrUpdateCustomer(tempCust,this);
        return true;
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            theCustomerService.deleteCustomerById(userId);
            theEmployeeService.deleteEmployeeById(userId);
            return true;
        }
        return false;
    }

    public List<User> usergtList(Long idMin) {
        return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
                .setParameter("paramId", idMin).getResultList();
    }
}
