package com.lang_learn_sys.main_app.accounting.product.service;

import com.lang_learn_sys.main_app.accounting.product.entity.Product;
import com.lang_learn_sys.main_app.accounting.product.repo.ProductRepository;
import com.lang_learn_sys.main_app.accounting.product_info.entity.ProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
@Service
public class ProductService {
    @Autowired
    ProductRepository theProductRepository;

    @Transactional
    public List<Product> getAllProducts(){
        return theProductRepository.findAll();
    }


    @Transactional
    public Product getProductById(Long id){
        return theProductRepository.getById(id);
    }


    @Transactional
    public boolean deleteProductById(Long id){
        if(getProductById(id)==null)
            return false;
        theProductRepository.delete(getProductById(id));
        return true;
    }


    @Transactional
    public boolean addOrUpdateProduct(Product theProduct){
        theProductRepository.save(theProduct);
        return true;
    }

    @Transactional
    public Product addProduct(Product theProduct){
        addOrUpdateProduct(theProduct);
        return theProduct;
    }
}
