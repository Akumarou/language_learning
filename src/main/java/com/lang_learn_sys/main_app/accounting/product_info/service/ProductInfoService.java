package com.lang_learn_sys.main_app.accounting.product_info.service;

import com.lang_learn_sys.main_app.accounting.product_info.entity.ProductInfo;
import com.lang_learn_sys.main_app.accounting.product_info.repo.ProductInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductInfoService {
    @Autowired
    ProductInfoRepository theProductInfoRepository;

    @Transactional
    public List<ProductInfo> getAllProductInfos(){
        return theProductInfoRepository.findAll();
    }


    @Transactional
    public ProductInfo getProductInfoById(Long id){
        return theProductInfoRepository.getById(id);
    }


    @Transactional
    public boolean deleteProductInfoById(Long id){
        if(getProductInfoById(id)==null)
            return false;
        theProductInfoRepository.delete(getProductInfoById(id));
        return true;
    }


    @Transactional
    public boolean addOrUpdateProductInfo(ProductInfo theProductInfo){
        theProductInfoRepository.save(theProductInfo);
        return true;
    }
}
