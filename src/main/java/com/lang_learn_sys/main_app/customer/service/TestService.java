package com.lang_learn_sys.main_app.customer.service;

import com.lang_learn_sys.main_app.course.service.CourseService;
import com.lang_learn_sys.main_app.customer.entity.Customer;
import com.lang_learn_sys.main_app.customer.entity.TestResult;
import com.lang_learn_sys.main_app.customer.repo.TestResultRepository;
import com.lang_learn_sys.main_app.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TestService {
    @Autowired
    TestResultRepository theTestResultRepository;
    @Autowired
    CustomerService theCustomerService;
    @Autowired
    UserService theUserService;

    @Transactional
    public List<TestResult> getAllTestResults() {
        return theTestResultRepository.findAll();
    }

    @Transactional
    public TestResult getTestResultById(Long id) {
        return theTestResultRepository.getById(id);
    }

    @Transactional
    public boolean deleteTestResultById(Long id) {
        if (getTestResultById(id) == null)
            return false;
        theTestResultRepository.delete(getTestResultById(id));
        return true;
    }

    @Transactional
    public boolean addOrUpdateTestResult(TestResult theTest, Long user_id) {
        if(theTest.getId()!=null)
            theTestResultRepository.save(theTest);
        else {
            TestResult byId = theTestResultRepository.getById(theTest.getId());
            byId.setDate(theTest.getDate());
            byId.setResult(theTest.getResult());
            byId.setTopic_id(theTest.getTopic_id());
            byId.setCourse_id(theTest.getCourse_id());
            theTestResultRepository.save(byId);
        }

        for (TestResult temp : theTestResultRepository.findAll())
            if (theTest.getCourse_id().equals(temp.getCourse_id()) &&
                    theTest.getResult().equals(temp.getResult()) &&
                    theTest.getTopic_id().equals(temp.getTopic_id()) &&
                    theTest.getDate().equals(temp.getDate()))
                theTest = temp;
        Customer cutomerById = theCustomerService.getCutomerById(user_id);
        cutomerById.addSuccess(theTest);
        theCustomerService.addOrUpdateCustomer(cutomerById, theUserService);
        return true;
    }
}
