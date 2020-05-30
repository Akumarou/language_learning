package com.lang_learn_sys.main_app.accounting.income.service;

import com.lang_learn_sys.main_app.accounting.income.entity.Income;
import com.lang_learn_sys.main_app.accounting.income.repo.IncomeRepository;
import com.lang_learn_sys.main_app.accounting.product.entity.Product;
import com.lang_learn_sys.main_app.accounting.product.service.ProductService;
import com.lang_learn_sys.main_app.accounting.product_info.entity.ProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class IncomeService {
    @Autowired
    IncomeRepository theIncomeRepository;
    @Autowired
    ProductService theProductService;

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
    public void addProd(Income theIncome,ProductInfo prod,Long givenCount) {
        Set<Product> products = theIncome.getProducts();
        if(products==null)  products=new HashSet<>();


        Product product = null;
        for (Product tempProduct:products)
            if(tempProduct.getProdinfo().equals(prod)) {
                products.remove(product);product = tempProduct; }

        if(product==null) product = new Product(prod,givenCount);
        else product.setCount(product.getCount()+givenCount);
        products.add(theProductService.addProduct(product));
        theIncome.setProducts(products);
    }

    @Transactional
    public boolean addOrUpdateIncome(Income theIncome) {
        theIncomeRepository.save(theIncome);
        return true;
    }
}
