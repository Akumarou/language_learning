package com.lang_learn_sys.main_app.admin.accounting;

import com.lang_learn_sys.main_app.accounting.income.entity.Income;
import com.lang_learn_sys.main_app.accounting.income.service.IncomeService;
import com.lang_learn_sys.main_app.accounting.product.service.ProductService;
import com.lang_learn_sys.main_app.accounting.writeoff.entity.Writeoff;
import com.lang_learn_sys.main_app.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.Collections;

@Controller
public class AdminIncomeController {

    @Autowired
    IncomeService theIncomeService;
    @Autowired
    CustomerService theCustomerService;
    @Autowired
    ProductService theProductService;

    @GetMapping("/admin/income/list")
    public String showIncomes(
            @RequestParam(name = "action", required = false, defaultValue = "none") String givenAction,
            @RequestParam(name = "id", required = false, defaultValue = "0") String idIncStr,
            Model model) {

        switch (givenAction) {
            case "add":
                model.addAttribute("theIncome", new Income());
                model.addAttribute("allCustomers", theCustomerService.getAllCustomers());
                return "/admin/income/add";
            case "get":
                Long id = Long.parseLong(idIncStr);
                if (id <= 0 || (theIncomeService.getIncomeById(id) == null)) {
                    model.addAttribute("hasErrors", true);
                    model.addAttribute("error", "Неверно задана накладная");
                    model.addAttribute("allIncomes", theIncomeService.getAllIncomes());
                    return "/admin/income/list";
                }
                model.addAttribute("theIncome", theIncomeService.getIncomeById(id));
                return "/admin/income/get";
            case "update":
                Long id_u = Long.parseLong(idIncStr);
                if (id_u <= 0 || (theIncomeService.getIncomeById(id_u) == null)) {
                    model.addAttribute("hasErrors", true);
                    model.addAttribute("error", "Неверно задана накладная");
                    model.addAttribute("allIncomes", theIncomeService.getAllIncomes());
                    return "/admin/income/list";
                }
                model.addAttribute("allCustomers",theCustomerService.getAllCustomers());
                model.addAttribute("theIncome", theIncomeService.getIncomeById(id_u));
                return "/admin/income/change";
            case "delete":
                Long id_d = Long.parseLong(idIncStr);
                if (id_d <= 0 || !theIncomeService.deleteIncomeById(id_d)) {
                    model.addAttribute("hasErrors", true);
                    model.addAttribute("error", "Неверно задана накладная");
                }
                model.addAttribute("allIncomes", theIncomeService.getAllIncomes());
                return "/admin/income/list";
            default:
                model.addAttribute("allIncomes", theIncomeService.getAllIncomes());
                return "/admin/income/list";
        }
    }

    @PostMapping("/admin/income/addProp")
    public String addProdToIncome(
            @RequestParam(name = "id", required = true) String id,
            @RequestParam(name = "item_id", required = true) String item_id,
            Model model) {
        Income temp = theIncomeService.getIncomeById(Long.parseLong(id));
        temp.addProd(theProductService.getProductById(Long.parseLong(item_id)));
        if (!theIncomeService.addOrUpdateIncome(temp)) {
            model.addAttribute("hasErrors", true);
            model.addAttribute("error", "Возникла ошибка при обновлении (добавлении) :(");
        }
        model.addAttribute("theIncome", temp);
        return "/admin/income/change";
    }

    @GetMapping("/admin/income/addProp")
    public String showFormForAddProdToIncome(
            @RequestParam(name = "id", required = true) String id,
            Model model) {
        model.addAttribute("id", id);
        model.addAttribute("allProducts", theProductService.getAllProducts());
        return "/admin/income/addProp";
    }

    @PostMapping("/admin/income/add")
    public String addNewIncome(
            @RequestParam(name = "cust", required = true) String custId,
            @ModelAttribute(name = "theIncome") Income theIncome, Model model) {
        theIncome.setCustomer(theCustomerService.getCutomerById(Long.parseLong(custId)));
        if (!theIncomeService.addOrUpdateIncome(theIncome)) {
            model.addAttribute("hasErrors", true);
            model.addAttribute("error", "Возникла ошибка при обновлении (добавлении) :(");
        }

        model.addAttribute("theIncome", theIncome);
        model.addAttribute("allCustomers",theCustomerService.getAllCustomers());
        return "/admin/income/change";
    }

    @PostMapping("/admin/income/change")
    public String updateIncome(
            @RequestParam(name = "id", required = false, defaultValue = "0") String id,
            @RequestParam(name = "date", required = false, defaultValue = "") String date,
            @RequestParam(name = "open", required = false, defaultValue = "") String open,
            @RequestParam(name = "close", required = false, defaultValue = "") String close,
            @RequestParam(name = "custId", required = false, defaultValue = "") String custId,
            @RequestParam(name = "prodId", required = false, defaultValue = "") String prodId,
            Model model) {
        if (id.equals("0")) {
            model.addAttribute("allIncomes", theIncomeService.getAllIncomes());
            return "/admin/income/list";
        }
        Income temp = theIncomeService.getIncomeById(Long.parseLong(id));
        if (!date.equals("")) {
            temp.setDate(Date.valueOf(date));
        } else if (!close.equals("")) {
            temp.setClosed(true);
        } else if (!open.equals("")) {
            temp.setClosed(false);
        } else if (!custId.equals("")) {
            temp.setCustomer(theCustomerService.getCutomerById(Long.parseLong(custId)));
        } else if (!prodId.equals("")) {
            temp.deleteProd(theProductService.getProductById(Long.parseLong(prodId)));
        }
        if (!theIncomeService.addOrUpdateIncome(temp)) {
            model.addAttribute("hasErrors", true);
            model.addAttribute("error", "Возникла ошибка при обновлении (добавлении) :(");
        }
        model.addAttribute("theIncome", temp);
        return "/admin/income/change";
    }
}