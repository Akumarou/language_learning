package com.lang_learn_sys.main_app.accounting.writeoff.service;

import com.lang_learn_sys.main_app.accounting.product.entity.Product;
import com.lang_learn_sys.main_app.accounting.writeoff.entity.Writeoff;
import com.lang_learn_sys.main_app.accounting.writeoff.repo.WriteoffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class WriteoffService {
    @Autowired
    WriteoffRepository theWriteoffRepository;

    @Transactional
    public List<Writeoff> getAllWriteoffs() {
        return theWriteoffRepository.findAll();
    }


    @Transactional
    public Writeoff getWriteoffById(Long id) {
        return theWriteoffRepository.getById(id);
    }


    @Transactional
    public boolean deleteWriteoffById(Long id) {
        if (getWriteoffById(id) == null)
            return false;
        theWriteoffRepository.delete(getWriteoffById(id));
        return true;
    }


    @Transactional
    public boolean addOrUpdateWriteoff(Writeoff theWriteoff) {
        theWriteoffRepository.save(theWriteoff);
        return true;
    }
}
