package com.lang_learn_sys.main_app.admin.users;

import com.lang_learn_sys.main_app.security.entity.Role;
import com.lang_learn_sys.main_app.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminRoleController {
    @Autowired
    RoleService theRoleService;


    @GetMapping("admin/role/list")
    public String getRoles(
            @RequestParam(name = "action", required = false, defaultValue = "none") String givenAction,
            @RequestParam(name = "id", required = false, defaultValue = "0") String idRoleStr,
            Model model) {
        switch (givenAction) {
            case "add":
                return "admin/role/add";
            case "delete":
                theRoleService.deleteRoleById(Long.parseLong(idRoleStr));
                model.addAttribute("allRoles", theRoleService.getAllRoles());
                return "admin/role/list";
            default:
                model.addAttribute("allRoles", theRoleService.getAllRoles());
                return "admin/role/list";
        }
    }

    @PostMapping("admin/role/list")
    public String updateRole(
            @ModelAttribute(name = "theRole") Role theRole, Model model) {
        if (theRole.getId() != 1L)
            theRoleService.addOrUpdateRole(theRole);
        model.addAttribute("allRoles", theRoleService.getAllRoles());
        return "admin/role/list";
    }

    @PostMapping("admin/role/list/add")
    public String addRole(
            @RequestParam(name = "name", required = true) String name,
            Model model) {
        Role tempRole = new Role();
        tempRole.setName("ROLE_" + name);
        theRoleService.addOrUpdateRole(tempRole);
        model.addAttribute("allRoles", theRoleService.getAllRoles());
        return "admin/role/list";
    }
//    @PostMapping("admin/role/list")
//    public String addOrUpdateRole(
//            @RequestParam(name="id", required = true) String id,
//            @RequestParam(name="nam", required = true) String name,
//            Model model){
//
//        model.addAttribute("allRoles",theRoleService.getAllRoles());
//        return "admin/role/list";
//    }
}
