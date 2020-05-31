package com.lang_learn_sys.main_app.controllers.accounting;

import com.lang_learn_sys.main_app.accounting.product_info.entity.ProductInfo;
import com.lang_learn_sys.main_app.accounting.product_info.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {
    @Autowired
    ProductInfoService theProductService;

    @GetMapping("/sales_dep/product/change")
    public String showProductssales(Model model) {
        model.addAttribute("theProduct", new ProductInfo());
        return "/sales_dep/product/change";

    }
    @PostMapping("/sales_dep/product/change")
    public String addOrUpdateProductsales(
            @ModelAttribute(name="theProduct") ProductInfo theProductInfo, Model model){
        if(!theProductService.addOrUpdateProductInfo(theProductInfo)){
            model.addAttribute("hasErrors", true);
            model.addAttribute("error", "Возникла ошибка при обновлении (добавлении) :(");
        }
        model.addAttribute("allProducts", theProductService.getAllProductInfos());
        return "index";
    }
    @GetMapping("/accountant/product/change")
    public String showProductsacc(Model model) {
        model.addAttribute("theProduct", new ProductInfo());
        return "/accountant/product/change";

    }
    @PostMapping("/accountant/product/change")
    public String addOrUpdateProductacc(
            @ModelAttribute(name="theProduct") ProductInfo theProductInfo, Model model){
        if(!theProductService.addOrUpdateProductInfo(theProductInfo)){
            model.addAttribute("hasErrors", true);
            model.addAttribute("error", "Возникла ошибка при обновлении (добавлении) :(");
        }
        model.addAttribute("allProducts", theProductService.getAllProductInfos());
        return "index";
    }
}
