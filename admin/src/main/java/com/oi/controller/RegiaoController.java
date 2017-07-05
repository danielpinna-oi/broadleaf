package com.oi.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.broadleafcommerce.openadmin.web.controller.AdminAbstractController;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/" + RegiaoController.SECTION_KEY)
@Secured("PERMISSION_OTHER_DEFAULT")
public class RegiaoController extends AdminAbstractController {

    protected static final String SECTION_KEY = "test";

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String test(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        // This is expected by the modules/emptyContainer template, this is a custom template that gets included into the body
        model.addAttribute("customView", "views/test");

        // ensure navigation gets set up correctly
        setModelAttributes(model, SECTION_KEY);

        // gets the scaffolding set up to display the template from the customView attribute above
        return "modules/emptyContainer";
    }

}