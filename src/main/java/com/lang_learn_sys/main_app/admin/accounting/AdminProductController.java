package com.lang_learn_sys.main_app.admin.accounting;

import com.lang_learn_sys.main_app.accounting.product.entity.Product;
import com.lang_learn_sys.main_app.accounting.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class AdminProductController {
    @Autowired
    ProductService theProductService;


    @GetMapping("/admin/product/list")
    public String showProducts(
            @RequestParam(name = "action", required = false,defaultValue = "none")String givenAction,
            @RequestParam(name = "id", required = false,defaultValue = "0")String idProdStr,
            Model model){

        switch (givenAction) {
            case "add":
                model.addAttribute("theProduct",new Product());
                return "/admin/product/change";
            case "get":
                Long id = Long.parseLong(idProdStr);
                if(id<=0 || (theProductService.getProductById(id)==null)) {
                    model.addAttribute("hasErrors", true);
                    model.addAttribute("error", "Неверно задан продукт");
                    model.addAttribute("allProducts", theProductService.getAllProducts());
                    return "/admin/product/list";
                }
                model.addAttribute("theProduct", theProductService.getProductById(id));
                return "/admin/product/get";
            case "update":
                Long id_u = Long.parseLong(idProdStr);
                if(id_u<=0 || (theProductService.getProductById(id_u)==null)) {
                    model.addAttribute("hasErrors", true);
                    model.addAttribute("error", "Неверно задан продукт");
                    model.addAttribute("allProducts", theProductService.getAllProducts());
                    return "/admin/product/list";
                }
                model.addAttribute("theProduct", theProductService.getProductById(id_u));
                return "/admin/product/change";
            case "delete":
                Long id_d = Long.parseLong(idProdStr);
                if(id_d<=0 || !theProductService.deleteProductById(id_d)){
                    model.addAttribute("hasErrors", true);
                    model.addAttribute("error", "Неверно задан продукт");
                    model.addAttribute("allProducts", theProductService.getAllProducts());
                    return "/admin/product/list";
                }
                model.addAttribute("allProducts", theProductService.getAllProducts());
                return "/admin/product/list";
            default:
                model.addAttribute("allProducts", theProductService.getAllProducts());
                return "/admin/product/list";
        }
    }

    @PostMapping("/admin/product/list")
    public String addOrUpdateProduct(
            @ModelAttribute(name="theProduct")Product theProduct, Model model){
        if(!theProductService.addOrUpdateProduct(theProduct)){
            model.addAttribute("hasErrors", true);
            model.addAttribute("error", "Возникла ошибка при обновлении (добавлении) :(");
        }
        model.addAttribute("allProducts", theProductService.getAllProducts());
        return "/admin/product/list";
    }
}
