package com.lang_learn_sys.main_app.admin.accounting;

import com.lang_learn_sys.main_app.accounting.provider.entity.Provider;
import com.lang_learn_sys.main_app.accounting.provider.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminProviderController {
    @Autowired
    ProviderService theProviderService;


    @GetMapping("/admin/provider/list")
    public String showProviders(
            @RequestParam(name = "action", required = false,defaultValue = "none")String givenAction,
            @RequestParam(name = "id", required = false,defaultValue = "0")String idProvStr,
            Model model){

        switch (givenAction) {
            case "add":
                model.addAttribute("theProvider",new Provider());
                return "/admin/provider/change";
            case "get":
                Long id = Long.parseLong(idProvStr);
                if(id<=0 || (theProviderService.getProviderById(id)==null)) {
                    model.addAttribute("hasErrors", true);
                    model.addAttribute("error", "Неверно задан поставщик");
                    model.addAttribute("allProviders", theProviderService.getAllProviders());
                    return "/admin/provider/list";
                }
                model.addAttribute("theProvider", theProviderService.getProviderById(id));
                return "/admin/provider/get";
            case "update":
                Long id_u = Long.parseLong(idProvStr);
                if(id_u<=0 || (theProviderService.getProviderById(id_u)==null)) {
                    model.addAttribute("hasErrors", true);
                    model.addAttribute("error", "Неверно задан поставщик");
                    model.addAttribute("allProviders", theProviderService.getAllProviders());
                    return "/admin/provider/list";
                }
                model.addAttribute("theProvider", theProviderService.getProviderById(id_u));
                return "/admin/provider/change";
            case "delete":
                Long id_d = Long.parseLong(idProvStr);
                if(id_d<=0 || !theProviderService.deleteCustomerById(id_d)){
                    model.addAttribute("hasErrors", true);
                    model.addAttribute("error", "Неверно задан поставщик");
                    model.addAttribute("allProviders", theProviderService.getAllProviders());
                    return "/admin/provider/list";
                }
                model.addAttribute("allProviders", theProviderService.getAllProviders());
                return "/admin/provider/list";
            default:
                model.addAttribute("allProviders", theProviderService.getAllProviders());
                return "/admin/provider/list";
        }
    }

    @PostMapping("/admin/provider/list")
    public String addOrUpdateProvider(
            @ModelAttribute(name="theProvider")Provider theProvider, Model model){
        if(!theProviderService.addOrUpdateProvider(theProvider)){
            model.addAttribute("hasErrors", true);
            model.addAttribute("error", "Возникла ошибка при обновлении (добавлении) :(");
        }
        model.addAttribute("allProviders", theProviderService.getAllProviders());
        return "/admin/provider/list";
    }
}
