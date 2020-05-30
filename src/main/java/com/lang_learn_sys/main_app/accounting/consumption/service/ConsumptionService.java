package com.lang_learn_sys.main_app.accounting.consumption.service;

import com.lang_learn_sys.main_app.accounting.consumption.entity.Consumption;
import com.lang_learn_sys.main_app.accounting.consumption.repo.ConsumptionRepository;
import com.lang_learn_sys.main_app.accounting.income.entity.Income;
import com.lang_learn_sys.main_app.accounting.product.entity.Product;
import com.lang_learn_sys.main_app.accounting.product.service.ProductService;
import com.lang_learn_sys.main_app.accounting.product_info.entity.ProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ConsumptionService {
    @Autowired
    ConsumptionRepository theConsumptionRepository;

    @Autowired
    ProductService theProductService;

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
    @Transactional
    public void addProd(Consumption theConsumption, ProductInfo prod, Long givenCount) {
        Set<Product> products = theConsumption.getProducts();
        if(products==null)  products=new HashSet<>();
        Product product = null;
        for (Product tempProduct:products)
            if(tempProduct.getProdinfo().equals(prod)) {
                products.remove(product);product = tempProduct; }

        if(product==null) product = new Product(prod,givenCount);
        else product.setCount(product.getCount()+givenCount);
        products.add(theProductService.addProduct(product));
        theConsumption.setProducts(products);
    }
}
