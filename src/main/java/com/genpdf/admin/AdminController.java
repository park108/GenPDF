package com.genpdf.admin;

import com.genpdf.common.CodeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

	@Autowired
	private CodeDao codeDao;

    @RequestMapping(value = "/admin")
    public String main(Model model) {

	    return "admin";
    }

    @RequestMapping(value = "/admin/code")
	public String codeMain(Model model) {

	    model.addAttribute("codeSet", codeDao.getCodeSetList());

	    return "code";
    }

	@RequestMapping(value = "/admin/code/edit")
	public String codeEdit(@RequestParam(name = "codeSet", required = false) String codeSet, Model model) {

		model.addAttribute("codeSet", codeDao.getCodeList(codeSet));

		return "codeEdit";
	}
}
