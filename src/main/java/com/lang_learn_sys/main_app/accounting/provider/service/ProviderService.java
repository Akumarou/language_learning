package com.lang_learn_sys.main_app.accounting.provider.service;

import com.lang_learn_sys.main_app.accounting.provider.entity.Provider;
import com.lang_learn_sys.main_app.accounting.provider.repo.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProviderService {
    @Autowired
    ProviderRepository theProviderRepository;

    @Transactional
    public List<Provider> getAllProviders(){
        return theProviderRepository.findAll();
    }


    @Transactional
    public Provider getProviderById(Long id){
        return theProviderRepository.getById(id);
    }


    @Transactional
    public boolean deleteCustomerById(Long id){
        if(getProviderById(id)==null)
            return false;
        theProviderRepository.delete(getProviderById(id));
        return true;
    }


    @Transactional
    public boolean addOrUpdateProvider(Provider theProvider){
        theProviderRepository.save(theProvider);
        return true;
    }
}

