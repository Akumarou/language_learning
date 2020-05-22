package com.lang_learn_sys.main_app.accounting.income.service;

import com.lang_learn_sys.main_app.accounting.income.entity.Income;
import com.lang_learn_sys.main_app.accounting.income.repo.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class IncomeService {
    @Autowired
    IncomeRepository theIncomeRepository;

    @Transactional
    public List<Income> getAllIncomes() {
        return theIncomeRepository.findAll();
    }


    @Transactional
    public Income getIncomeById(Long id) {
        return theIncomeRepository.getById(id);
    }


    @Transactional
    public boolean deleteIncomeById(Long id) {
        if (getIncomeById(id) == null)
            return false;
        theIncomeRepository.delete(getIncomeById(id));
        return true;
    }


    @Transactional
    public boolean addOrUpdateIncome(Income theIncome) {
        theIncomeRepository.save(theIncome);
        return true;
    }
}
