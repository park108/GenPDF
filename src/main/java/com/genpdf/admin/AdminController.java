package com.genpdf.admin;

import com.genpdf.common.Code;
import com.genpdf.common.CodeDao;
import com.genpdf.common.Form;
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

    /******************************************************************
    /* CodeSet CRUD
     ******************************************************************/
    @GetMapping(value = "/admin/code/list")
	public String codeSetList(Model model) {

	    model.addAttribute("codeSet", codeDao.getCodeSetList());

	    return "code_set_list";
    }

    @GetMapping(value = "/admin/code/create")
    public String codeSetCreate(Model model) {

	    model.addAttribute("codeSet", new Code());

    	return "code_set_detail";
    }

	@GetMapping(value = "/admin/code/{codeSet}/edit")
	public String codeSetEdit(@PathVariable String codeSet, Model model) {

		Code selectedCodeSet = codeDao.getCodeSet(codeSet);

		model.addAttribute("codeSet", selectedCodeSet);

		return "code_set_detail";
	}

	@PostMapping(value = "/admin/code/{codeSet}/save")
	public String codeSetSave(@Valid Code codeSet, BindingResult bindingResult, final RedirectAttributes redirectAttributes) {

		if(bindingResult.hasErrors()) {

			redirectAttributes.addFlashAttribute("result", "E");
			redirectAttributes.addFlashAttribute("message", "Input correctly!");

			return "code_set_detail";
		}

		int result = codeDao.setCodeSet(codeSet);

		redirectAttributes.addFlashAttribute("result", "S");
		redirectAttributes.addFlashAttribute("message", "Save Successfully!");

		return "redirect:/admin/code/list/";
	}

	/******************************************************************
	 /* Code CRUD
	 ******************************************************************/
	@GetMapping(value = "/admin/code/{codeSet}/list")
	public String codeList(@PathVariable String codeSet, Model model) {

		Code selectedCodeSet = codeDao.getCodeSet(codeSet);

		model.addAttribute("codeSet", selectedCodeSet);
		model.addAttribute("codeList", codeDao.getCodeList(codeSet));

		return "code_list";
	}

	@GetMapping(value = "/admin/code/{codeSet}/create")
	public String codeCreate(@PathVariable String codeSet, Model model) {

		Code selectedCodeSet = codeDao.getCodeSet(codeSet);

		model.addAttribute("codeSet", selectedCodeSet);
		model.addAttribute("code", new Code());

		return "code_detail";
	}

	@GetMapping(value = "/admin/code/{codeSet}/{code}/edit")
	public String codeEdit(@PathVariable String codeSet, @PathVariable String code, Model model) {

		Code selectedCodeSet = codeDao.getCodeSet(codeSet);
		Code selectedCode = codeDao.getCode(codeSet, code);

		model.addAttribute("codeSet", selectedCodeSet);
		model.addAttribute("code", selectedCode);

		return "code_detail";
	}

	@PostMapping(value = "/admin/code/{codeSet}/{code}/save")
	public String codeSave(@Valid Code code, BindingResult bindingResult, final RedirectAttributes redirectAttributes) {

		if(bindingResult.hasErrors()) {

			redirectAttributes.addFlashAttribute("result", "E");
			redirectAttributes.addFlashAttribute("message", "Input correctly!");

			return "code_detail";
		}

		int result = codeDao.setCode(code);

		redirectAttributes.addFlashAttribute("result", result);
		redirectAttributes.addFlashAttribute("message", "Save Successfully!");

		return "redirect:/admin/code/" + code.getCodeSet() + "/list/";
	}

	/******************************************************************
	 /* Form CRUD
	 ******************************************************************/
	@GetMapping(value = "/admin/form/list")
	public String formMain(Model model) {

		// TODO: org 값을 사용자 정보(Session)에서 가져와야 함
		model.addAttribute("org", "SKCC");
    	model.addAttribute("formList", formDao.getFormList("SKCC"));

    	return "form_list";
	}

	@GetMapping(value = "/admin/form/{org}/create")
	public String formCreate(@PathVariable String org, Model model) {

		Form newForm = new Form();
		newForm.setOrg(org);

		model.addAttribute("form", newForm);

		return "form_detail";
	}

	@GetMapping(value = "/admin/form/{org}/{docType}/{seq}/edit")
	public String formEdit(@PathVariable String org, @PathVariable String docType, @PathVariable int seq, Model model) {

		Form selectedForm = formDao.getForm(org, docType, seq);

		model.addAttribute("form", selectedForm);

		return "form_detail";
	}

	@PostMapping(value = "/admin/form/{org}/{docType}/{seq}/save")
	public String codeSave(@Valid Form form, BindingResult bindingResult, final RedirectAttributes redirectAttributes) {

		if(bindingResult.hasErrors()) {

			redirectAttributes.addFlashAttribute("result", "E");
			redirectAttributes.addFlashAttribute("message", "Input correctly!");

			return "form_detail";
		}

		int result = formDao.setForm(form);

		redirectAttributes.addFlashAttribute("result", result);
		redirectAttributes.addFlashAttribute("message", "Save Successfully!");

		return "redirect:/admin/form/" + form.getOrg() + "/list/";
	}
}
