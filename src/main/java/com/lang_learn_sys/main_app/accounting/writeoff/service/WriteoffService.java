package com.lang_learn_sys.main_app.accounting.writeoff.service;

import com.lang_learn_sys.main_app.accounting.income.entity.Income;
import com.lang_learn_sys.main_app.accounting.product.entity.Product;
import com.lang_learn_sys.main_app.accounting.product.service.ProductService;
import com.lang_learn_sys.main_app.accounting.product_info.entity.ProductInfo;
import com.lang_learn_sys.main_app.accounting.writeoff.entity.Writeoff;
import com.lang_learn_sys.main_app.accounting.writeoff.repo.WriteoffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class WriteoffService {
    @Autowired
    WriteoffRepository theWriteoffRepository;

    @Autowired
    ProductService theProductService;

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

    @Transactional
    public void addProd(Writeoff theWriteoff, ProductInfo prod, long givenCount) {
        Set<Product> products = theWriteoff.getProducts();
        if(products==null)  products=new HashSet<>();
        Product product = null;
        for (Product tempProduct:products)
            if(tempProduct.getProdinfo().equals(prod)) {
                products.remove(product);product = tempProduct; }

        if(product==null) product = new Product(prod,givenCount);
        else product.setCount(product.getCount()+givenCount);
        products.add(theProductService.addProduct(product));
        theWriteoff.setProducts(products);
    }
}
