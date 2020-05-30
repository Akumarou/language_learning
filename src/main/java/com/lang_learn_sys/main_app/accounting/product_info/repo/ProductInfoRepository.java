package com.lang_learn_sys.main_app.accounting.product_info.repo;

import com.lang_learn_sys.main_app.accounting.product_info.entity.ProductInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInfoRepository extends CrudRepository<ProductInfo,Long> {
    List<ProductInfo> findAll();
    void deleteById(Long Id);
    ProductInfo getById(Long id);
}
