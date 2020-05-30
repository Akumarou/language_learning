package com.lang_learn_sys.main_app.admin.accounting;

import com.lang_learn_sys.main_app.accounting.consumption.entity.Consumption;
import com.lang_learn_sys.main_app.accounting.consumption.service.ConsumptionService;
import com.lang_learn_sys.main_app.accounting.income.entity.Income;
import com.lang_learn_sys.main_app.accounting.product.service.ProductService;
import com.lang_learn_sys.main_app.accounting.product_info.entity.ProductInfo;
import com.lang_learn_sys.main_app.accounting.product_info.service.ProductInfoService;
import com.lang_learn_sys.main_app.accounting.provider.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;

@Controller
public class AdminConsumptionController {

    @Autowired
    ConsumptionService theConsumptionService;
    @Autowired
    ProviderService theProviderService;
    @Autowired
    ProductService theProductService;

    @Autowired
    ProductInfoService theProductInfoService;

    @GetMapping("/admin/consumption/list")
    public String showConsumptions(
            @RequestParam(name = "action", required = false, defaultValue = "none") String givenAction,
            @RequestParam(name = "id", required = false, defaultValue = "0") String idIncStr,
            Model model) {

        switch (givenAction) {
            case "add":
                model.addAttribute("theConsumption", new Income());
                model.addAttribute("allProviders", theProviderService.getAllProviders());
                return "/admin/consumption/add";
            case "get":
                Long id = Long.parseLong(idIncStr);
                if (id <= 0 || (theConsumptionService.getConsumptionById(id) == null)) {
                    model.addAttribute("hasErrors", true);
                    model.addAttribute("error", "Неверно задана накладная");
                    model.addAttribute("allConsumptions", theConsumptionService.getAllConsumptions());
                    return "/admin/consumption/list";
                }
                model.addAttribute("theConsumption", theConsumptionService.getConsumptionById(id));
                return "/admin/consumption/get";
            case "update":
                Long id_u = Long.parseLong(idIncStr);
                if (id_u <= 0 || (theConsumptionService.getConsumptionById(id_u) == null)) {
                    model.addAttribute("hasErrors", true);
                    model.addAttribute("error", "Неверно задана накладная");
                    model.addAttribute("allConsumptions", theConsumptionService.getAllConsumptions());
                    return "/admin/consumption/list";
                }
                model.addAttribute("allProviders",theProviderService.getAllProviders());
                model.addAttribute("theConsumption", theConsumptionService.getConsumptionById(id_u));
                return "/admin/consumption/change";
            case "delete":
                Long id_d = Long.parseLong(idIncStr);
                if (id_d <= 0 || !theConsumptionService.deleteConsumptionById(id_d)) {
                    model.addAttribute("hasErrors", true);
                    model.addAttribute("error", "Неверно задана накладная");
                }
                model.addAttribute("allConsumptions", theConsumptionService.getAllConsumptions());
                return "/admin/consumption/list";
            default:
                model.addAttribute("allConsumptions", theConsumptionService.getAllConsumptions());
                return "/admin/consumption/list";
        }
    }

    @PostMapping("/admin/consumption/addProp")
    public String addProdToConsumption(
            @RequestParam(name = "id", required = true) String id,
            @RequestParam(name = "item_id", required = true) String item_id,
            @RequestParam(name = "countS", required = false,defaultValue = "1") String countS,
            Model model) {
        long count = Long.parseLong(countS);
        Consumption temp = theConsumptionService.getConsumptionById(Long.parseLong(id));
        if(temp==null){
            model.addAttribute("hasErrors", true);
            model.addAttribute("error", "Неверно задана накладная");
            model.addAttribute("allConsumptions", theConsumptionService.getAllConsumptions());
            return "/admin/consumption/list";
        }
        ProductInfo tempProd = theProductInfoService.getProductInfoById(Long.parseLong(item_id));
        if(count<1||tempProd==null){
            model.addAttribute("hasErrors", true);
            model.addAttribute("error", "Возникла ошибка при обновлении (добавлении) :(");
            model.addAttribute("theConsumption", temp);
            return "/admin/consumption/change";
        }
        theConsumptionService.addProd(temp,tempProd,count);
        if (!theConsumptionService.addOrUpdateConsumption(temp)) {
            model.addAttribute("hasErrors", true);
            model.addAttribute("error", "Возникла ошибка при обновлении (добавлении) :(");
        }
        model.addAttribute("theConsumption", temp);
        return "/admin/consumption/change";
    }

    @GetMapping("/admin/consumption/addProp")
    public String showFormForAddProdToConsumption(
            @RequestParam(name = "id", required = true) String id,
            Model model) {
        model.addAttribute("id", id);
        model.addAttribute("allProducts", theProductInfoService.getAllProductInfos());
        return "/admin/consumption/addProp";
    }

    @PostMapping("/admin/consumption/add")
    public String addNewConsumption(
            @RequestParam(name = "cust", required = true) String custId,
            @ModelAttribute(name = "theConsumption") Consumption theConsumption, Model model) {
        theConsumption.setProvider(theProviderService.getProviderById(Long.parseLong(custId)));
        if (!theConsumptionService.addOrUpdateConsumption(theConsumption)) {
            model.addAttribute("hasErrors", true);
            model.addAttribute("error", "Возникла ошибка при обновлении (добавлении) :(");
        }

        model.addAttribute("theConsumption", theConsumption);
        model.addAttribute("allProviders",theProviderService.getAllProviders());
        return "/admin/consumption/change";
    }

    @PostMapping("/admin/consumption/change")
    public String updateConsumption(
            @RequestParam(name = "id", required = false, defaultValue = "0") String id,
            @RequestParam(name = "date", required = false, defaultValue = "") String date,
            @RequestParam(name = "open", required = false, defaultValue = "") String open,
            @RequestParam(name = "close", required = false, defaultValue = "") String close,
            @RequestParam(name = "custId", required = false, defaultValue = "") String custId,
            @RequestParam(name = "prodId", required = false, defaultValue = "") String prodId,
            Model model) {
        if (id.equals("0")) {
            model.addAttribute("allConsumptions", theConsumptionService.getAllConsumptions());
            return "/admin/consumption/list";
        }
        Consumption temp = theConsumptionService.getConsumptionById(Long.parseLong(id));
        if (!date.equals("")) {
            temp.setDate(Date.valueOf(date));
        } else if (!close.equals("")) {
            temp.setClosed(true);
        } else if (!open.equals("")) {
            temp.setClosed(false);
        } else if (!custId.equals("")) {
            temp.setProvider(theProviderService.getProviderById(Long.parseLong(custId)));
        } else if (!prodId.equals("")) {
            temp.deleteProd(theProductService.getProductById(Long.parseLong(prodId)));
        }
        if (!theConsumptionService.addOrUpdateConsumption(temp)) {
            model.addAttribute("hasErrors", true);
            model.addAttribute("error", "Возникла ошибка при обновлении (добавлении) :(");
        }
        model.addAttribute("theConsumption", temp);
        return "/admin/consumption/change";
    }
}
