package com.genpdf.admin;

import com.genpdf.common.Code;
import com.genpdf.common.CodeDao;
import com.genpdf.common.FormDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AdminController {

	@Autowired
	private CodeDao codeDao;

	@Autowired
	private FormDao formDao;

    @GetMapping(value = "/admin")
    public String main(Model model) {

	    return "admin";
    }

    @GetMapping(value = "/admin/code")
	public String codeMain(Model model) {

	    model.addAttribute("codeSet", codeDao.getCodeSetList());

	    return "code";
    }

	@GetMapping(value = "/admin/code/{codeSet}")
	public String codeDetail(@PathVariable String codeSet, Model model) {

    	model.addAttribute("codeSet", codeSet);
		model.addAttribute("codeSetName", codeDao.getCodeSet(codeSet).getCodeSetName());
		model.addAttribute("codeList", codeDao.getCodeList(codeSet));

		return "code_list";
	}

	@PostMapping(value = "/admin/code/{codeSet}/{code}")
	public String codeSave(@Valid Code code, BindingResult bindingResult, final RedirectAttributes redirectAttributes) {

		if(bindingResult.hasErrors()) {
			return "code_detail";
		}

		int result = codeDao.setCode(code);

		redirectAttributes.addFlashAttribute("result", result);
		redirectAttributes.addFlashAttribute("message", "Save Successfully!");

		return "redirect:/admin/code/" + code.getCodeSet();
	}

	@GetMapping(value = "/admin/code/{codeSet}/{code}")
	public String codeEdit(@PathVariable String codeSet, @PathVariable String code, Model model) {

    	Code selectedCode = codeDao.getCode(codeSet, code);

		model.addAttribute("code", selectedCode);

		return "code_detail";
	}

	@GetMapping(value = "/admin/form")
	public String formMain(Model model) {

    	model.addAttribute("formList", formDao.getFormList("SKCC"));

    	return "form";
	}
}
