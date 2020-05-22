package com.lang_learn_sys.main_app.accounting.consumption.service;

import com.lang_learn_sys.main_app.accounting.consumption.entity.Consumption;
import com.lang_learn_sys.main_app.accounting.consumption.repo.ConsumptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ConsumptionService {
    @Autowired
    ConsumptionRepository theConsumptionRepository;

    @Transactional
    public List<Consumption> getAllConsumptions() {
        return theConsumptionRepository.findAll();
    }


    @Transactional
    public Consumption getConsumptionById(Long id) {
        return theConsumptionRepository.getById(id);
    }


    @Transactional
    public boolean deleteConsumptionById(Long id) {
        if (getConsumptionById(id) == null)
            return false;
        theConsumptionRepository.delete(getConsumptionById(id));
        return true;
    }


    @Transactional
    public boolean addOrUpdateConsumption(Consumption theConsumption) {
        theConsumptionRepository.save(theConsumption);
        return true;
    }
}
