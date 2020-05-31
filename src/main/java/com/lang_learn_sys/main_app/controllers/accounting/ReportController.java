package com.lang_learn_sys.main_app.controllers.accounting;

import com.lang_learn_sys.main_app.accounting.consumption.service.ConsumptionService;
import com.lang_learn_sys.main_app.accounting.income.service.IncomeService;
import com.lang_learn_sys.main_app.accounting.writeoff.service.WriteoffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReportController {
    @Autowired
    WriteoffService theWriteoffService;
    @Autowired
    IncomeService theIncomeService;
    @Autowired
    ConsumptionService theConsumptionService;

    @RequestMapping("/accountant/makeReport")
    public String makeReport(Model model){
        model.addAttribute("incomes",theIncomeService.getAllIncomes());
        model.addAttribute("writeoffs",theWriteoffService.getAllWriteoffs());
        model.addAttribute("consumptions",theConsumptionService.getAllConsumptions());
        return "/accountant/makeReport";
    }

}
