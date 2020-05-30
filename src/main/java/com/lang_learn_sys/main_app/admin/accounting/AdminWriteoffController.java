package com.lang_learn_sys.main_app.admin.accounting;

import com.lang_learn_sys.main_app.accounting.income.entity.Income;
import com.lang_learn_sys.main_app.accounting.product.service.ProductService;
import com.lang_learn_sys.main_app.accounting.product_info.entity.ProductInfo;
import com.lang_learn_sys.main_app.accounting.product_info.service.ProductInfoService;
import com.lang_learn_sys.main_app.accounting.writeoff.entity.Writeoff;
import com.lang_learn_sys.main_app.accounting.writeoff.service.WriteoffService;
import com.lang_learn_sys.main_app.employee.service.EmployeeService;
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
public class AdminWriteoffController {
    @Autowired
    WriteoffService theWriteoffService;
    @Autowired
    EmployeeService theEmployeeService;
    @Autowired
    ProductService theProductService;
    @Autowired
    ProductInfoService theProductInfoService;

    @GetMapping("/admin/writeoff/list")
    public String showWriteoffs(
            @RequestParam(name = "action", required = false, defaultValue = "none") String givenAction,
            @RequestParam(name = "id", required = false, defaultValue = "0") String idwroffStr,
            Model model) {

        switch (givenAction) {
            case "add":
                model.addAttribute("theWriteoff", new Writeoff());
                model.addAttribute("allEmployees", theEmployeeService.getAllEmployees());
                return "/admin/writeoff/add";
            case "get":
                Long id = Long.parseLong(idwroffStr);
                if (id <= 0 || (theWriteoffService.getWriteoffById(id) == null)) {
                    model.addAttribute("hasErrors", true);
                    model.addAttribute("error", "Неверно задана накладная");
                    model.addAttribute("allWriteoffs", theWriteoffService.getAllWriteoffs());
                    return "/admin/writeoff/list";
                }
                model.addAttribute("theWriteoff", theWriteoffService.getWriteoffById(id));
                return "/admin/writeoff/get";
            case "update":
                Long id_u = Long.parseLong(idwroffStr);
                if (id_u <= 0 || (theWriteoffService.getWriteoffById(id_u) == null)) {
                    model.addAttribute("hasErrors", true);
                    model.addAttribute("error", "Неверно задана накладная");
                    model.addAttribute("allWriteoffs", theWriteoffService.getAllWriteoffs());
                    return "/admin/writeoff/list";
                }
                model.addAttribute("allEmployees", theEmployeeService.getAllEmployees());
                model.addAttribute("theWriteoff", theWriteoffService.getWriteoffById(id_u));
                return "/admin/writeoff/change";
            case "delete":
                Long id_d = Long.parseLong(idwroffStr);
                if (id_d <= 0 || !theWriteoffService.deleteWriteoffById(id_d)) {
                    model.addAttribute("hasErrors", true);
                    model.addAttribute("error", "Неверно задана накладная");
                }
                model.addAttribute("allWriteoffs", theWriteoffService.getAllWriteoffs());
                return "/admin/writeoff/list";
            default:
                model.addAttribute("allWriteoffs", theWriteoffService.getAllWriteoffs());
                return "/admin/writeoff/list";
        }
    }

    @PostMapping("/admin/writeoff/addProp")
    public String addEmplOrProdToWriteoff(
            @RequestParam(name = "id", required = true) String id,
            @RequestParam(name = "item_id", required = true) String item_id,
            @RequestParam(name = "action", required = true) String action,
            @RequestParam(name = "countS", required = false,defaultValue = "1") String countS,
            Model model)  {
        Writeoff temp = theWriteoffService.getWriteoffById(Long.parseLong(id));
        if(temp==null){
            model.addAttribute("hasErrors", true);
            model.addAttribute("error", "Неверно задана накладная");
            model.addAttribute("allIncomes", theWriteoffService.getAllWriteoffs());
            return "/admin/writeoff/list";
        }
        if (action.equals("addEmpl")) {
            temp.addEmpl(theEmployeeService.getEmployeeById(Long.parseLong(item_id)));
        } else if (action.equals("addProd")) {
                long count = Long.parseLong(countS);
                ProductInfo tempProd = theProductInfoService.getProductInfoById(Long.parseLong(item_id));
                if(count<1||tempProd==null){
                    model.addAttribute("hasErrors", true);
                    model.addAttribute("error", "Возникла ошибка при обновлении (добавлении) :(");
                    model.addAttribute("theIncome", temp);
                    return "/admin/writeoff/change";
                }
                theWriteoffService.addProd(temp,tempProd,count);
        }
        if (!theWriteoffService.addOrUpdateWriteoff(temp)) {
            model.addAttribute("hasErrors", true);
            model.addAttribute("error", "Возникла ошибка при обновлении (добавлении) :(");
        }
        model.addAttribute("theWriteoff", temp);
        return "/admin/writeoff/change";
    }
    @GetMapping("/admin/writeoff/addProp")
    public String showFormForAddEmplOrProdToWriteoff(
            @RequestParam(name = "id", required = true) String id,
            @RequestParam(name = "action", required = true) String action,
            Model model) {
        model.addAttribute("action",action);
        if (action.equals("addEmpl")) {
            model.addAttribute("id", id);
            model.addAttribute("actionBool",true);
            model.addAttribute("allEmployees", theEmployeeService.getAllEmployees());
            return "/admin/writeoff/addProp";
        } else if (action.equals("addProd")) {
            model.addAttribute("id", id);
            model.addAttribute("actionBool",false);
            model.addAttribute("allProducts", theProductInfoService.getAllProductInfos());
            return "/admin/writeoff/addProp";
        }
        model.addAttribute("allWriteoffs", theWriteoffService.getAllWriteoffs());
        return "/admin/writeoff/list";
    }

    @PostMapping("/admin/writeoff/add")
    public String addWriteoff(
            @RequestParam(name = "firstEmployee", required = true) String firstEmpl,
            @ModelAttribute(name = "theWriteoff") Writeoff theWriteoff, Model model) {
        theWriteoff.setEmployees(Collections.singleton(theEmployeeService.getEmployeeById(Long.parseLong(firstEmpl))));
        if (!theWriteoffService.addOrUpdateWriteoff(theWriteoff)) {
            model.addAttribute("hasErrors", true);
            model.addAttribute("error", "Возникла ошибка при обновлении (добавлении) :(");
        }

        model.addAttribute("theWriteoff", theWriteoff);
        return "/admin/writeoff/change";
    }

    @PostMapping("/admin/writeoff/change")
    public String updateWriteoff(
            @RequestParam(name = "id", required = false, defaultValue = "0") String id,
            @RequestParam(name = "date", required = false, defaultValue = "") String date,
            @RequestParam(name = "open", required = false, defaultValue = "") String open,
            @RequestParam(name = "close", required = false, defaultValue = "") String close,
            @RequestParam(name = "emplId", required = false, defaultValue = "") String emplId,
            @RequestParam(name = "prodId", required = false, defaultValue = "") String prodId,
            Model model) {
        if (id.equals("0")) {
            model.addAttribute("allWriteoffs", theWriteoffService.getAllWriteoffs());
            return "/admin/writeoff/list";
        }
        Writeoff temp = theWriteoffService.getWriteoffById(Long.parseLong(id));
        if (!date.equals("")) {
            temp.setDate(Date.valueOf(date));
        } else if (!close.equals("")) {
            temp.setClosed(true);
        } else if (!open.equals("")) {
            temp.setClosed(false);
        } else if (!emplId.equals("")) {
            temp.deleteEmpl(theEmployeeService.getEmployeeById(Long.parseLong(emplId)));
        } else if (!prodId.equals("")) {
            temp.deleteProd(theProductService.getProductById(Long.parseLong(prodId)));
        }
        if (!theWriteoffService.addOrUpdateWriteoff(temp)) {
            model.addAttribute("hasErrors", true);
            model.addAttribute("error", "Возникла ошибка при обновлении (добавлении) :(");
        }
        model.addAttribute("allEmployees", theEmployeeService.getAllEmployees());
        model.addAttribute("theWriteoff", temp);
        return "/admin/writeoff/change";
    }
}