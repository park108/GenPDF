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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;

@Controller
public class AdminController {

	private static final String FILE_DIR = "/images/";
	private static final String STATIC_DIR = "/static/images/";

	@Autowired
	private CodeDao codeDao;

	@Autowired
	private FormDao formDao;

	@GetMapping(value = "/admin")
    public String main(Model model) {

	    model.addAttribute("title", "Home");

	    return "admin";
    }

    /******************************************************************
    /* CodeSet CRUD
     ******************************************************************/
    @GetMapping(value = "/admin/codes")
	public String codeSetList(Model model) {

    	model.addAttribute("title", "Code");
	    model.addAttribute("codeSet", codeDao.getCodeSetList());

	    return "code_set_list";
    }

    @GetMapping(value = "/admin/codes/create")
    public String codeSetCreate(Model model) {

	    model.addAttribute("title", "Code");
	    model.addAttribute("codeSet", new Code());

    	return "code_set_detail";
    }

	@GetMapping(value = "/admin/codes/{codeSet}/edit")
	public String codeSetEdit(@PathVariable String codeSet, Model model) {

		Code selectedCodeSet = codeDao.getCodeSet(codeSet);

		model.addAttribute("title", "Code");
		model.addAttribute("codeSet", selectedCodeSet);

		return "code_set_detail";
	}

	@PostMapping(value = "/admin/codes/{codeSet}/save")
	public String codeSetSave(@Valid Code codeSet, BindingResult bindingResult, final RedirectAttributes redirectAttributes) {

		if(bindingResult.hasErrors()) {

			redirectAttributes.addFlashAttribute("result", "E");
			redirectAttributes.addFlashAttribute("message", "Input correctly!");

			return "code_set_detail";
		}

		int result = codeDao.setCodeSet(codeSet);

		redirectAttributes.addFlashAttribute("result", "S");
		redirectAttributes.addFlashAttribute("message", "Save Successfully!");

		return "redirect:/admin/codes/";
	}

	@PostMapping(value = "/admin/codes/{codeSet}/delete")
	public String codeSetDelete(@PathVariable String codeSet, final RedirectAttributes redirectAttributes) {

		int result = codeDao.delCodeSet(codeSet);

		redirectAttributes.addFlashAttribute("result", "S");
		redirectAttributes.addFlashAttribute("message", "Delete Successfully!");

		return "redirect:/admin/codes/";
	}

	/******************************************************************
	 /* Code CRUD
	 ******************************************************************/
	@GetMapping(value = "/admin/codes/{codeSet}")
	public String codeList(@PathVariable String codeSet, Model model) {

		Code selectedCodeSet = codeDao.getCodeSet(codeSet);

		model.addAttribute("title", "Code");
		model.addAttribute("codeSet", selectedCodeSet);
		model.addAttribute("codeList", codeDao.getCodeList(codeSet));

		return "code_list";
	}

	@GetMapping(value = "/admin/codes/{codeSet}/create")
	public String codeCreate(@PathVariable String codeSet, Model model) {

		Code selectedCodeSet = codeDao.getCodeSet(codeSet);

		model.addAttribute("title", "Code");
		model.addAttribute("codeSet", selectedCodeSet);
		model.addAttribute("code", new Code());

		return "code_detail";
	}

	@GetMapping(value = "/admin/codes/{codeSet}/{code}/edit")
	public String codeEdit(@PathVariable String codeSet, @PathVariable String code, Model model) {

		Code selectedCodeSet = codeDao.getCodeSet(codeSet);
		Code selectedCode = codeDao.getCode(codeSet, code);

		model.addAttribute("title", "Code");
		model.addAttribute("codeSet", selectedCodeSet);
		model.addAttribute("code", selectedCode);

		return "code_detail";
	}

	@PostMapping(value = "/admin/codes/{codeSet}/{code}/save")
	public String codeSave(@Valid Code code, BindingResult bindingResult, final RedirectAttributes redirectAttributes) {

		if(bindingResult.hasErrors()) {

			redirectAttributes.addFlashAttribute("result", "E");
			redirectAttributes.addFlashAttribute("message", "Input correctly!");

			return "code_detail";
		}

		int result = codeDao.setCode(code);

		redirectAttributes.addFlashAttribute("result", "S");
		redirectAttributes.addFlashAttribute("message", "Save Successfully!");

		return "redirect:/admin/codes/" + code.getCodeSet();
	}

	@PostMapping(value = "/admin/codes/{codeSet}/{code}/delete")
	public String codeDelete(@PathVariable String codeSet, @PathVariable String code, final RedirectAttributes redirectAttributes) {

		int result = codeDao.delCode(codeSet, code);

		redirectAttributes.addFlashAttribute("result", "S");
		redirectAttributes.addFlashAttribute("message", "Delete Successfully!");

		return "redirect:/admin/codes/" + codeSet;
	}

	/******************************************************************
	 /* Form CRUD
	 ******************************************************************/
	@GetMapping(value = "/admin/forms")
	public String formMain(Model model) {

		// TODO: org 값을 사용자 정보(Session)에서 가져와야 함
		model.addAttribute("title", "Form");
		model.addAttribute("org", "SKCC");
    	model.addAttribute("formList", formDao.getFormList("SKCC"));
		model.addAttribute("documentTypes", codeDao.getCodeList("F001"));
		model.addAttribute("fonts", codeDao.getCodeList("F002"));

    	return "form_list";
	}

	@GetMapping(value = "/admin/forms/{org}/create")
	public String formCreate(@PathVariable String org, Model model) {

		Form newForm = new Form();
		newForm.setOrg(org);

		model.addAttribute("title", "Form");
		model.addAttribute("form", newForm);
		model.addAttribute("documentTypes", codeDao.getCodeList("F001"));
		model.addAttribute("fonts", codeDao.getCodeList("F002"));

		return "form_detail";
	}

	@GetMapping(value = "/admin/forms/{org}/{docType}/{seq}/edit")
	public String formEdit(@PathVariable String org, @PathVariable String docType, @PathVariable int seq, Model model) {

		Form selectedForm = formDao.getForm(org, docType, seq);

		model.addAttribute("title", "Form");
		model.addAttribute("form", selectedForm);
		model.addAttribute("documentTypes", codeDao.getCodeList("F001"));
		model.addAttribute("fonts", codeDao.getCodeList("F002"));

		return "form_detail";
	}

	@PostMapping(value = "/admin/forms/{org}/{docType}/{seq}/save")
	public String formSave(@Valid Form form, BindingResult bindingResult
			, @RequestParam("logoImage") MultipartFile logoImage
			, @RequestParam("signImage") MultipartFile signImage
			, final RedirectAttributes redirectAttributes) {

		if(bindingResult.hasErrors()) {

			redirectAttributes.addFlashAttribute("result", "E");
			redirectAttributes.addFlashAttribute("message", "Input correctly!");

			return "form_detail";
		}

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		try {
			String realPath = getClass().getResource(FILE_DIR).getPath();
			String staticPath = getClass().getResource(STATIC_DIR).getPath();

			byte[] bytes;
			Path filePath;
			Path webPath;

			if(!logoImage.isEmpty()) {

				String logoImageName = form.getOrg() + "_" + form.getDocType() + "_" + form.getSeq() + "_" + timestamp.getTime() + "_logo.image";
				form.setLogoImagePath(FILE_DIR + logoImageName);

				bytes = logoImage.getBytes();
				filePath = Paths.get(realPath + logoImageName);
				Files.write(filePath, bytes);
				webPath = Paths.get(staticPath + logoImageName);
				Files.write(webPath, bytes);
			}

			if(!signImage.isEmpty()) {

				String signImageName = form.getOrg() + "_" + form.getDocType() + "_" + form.getSeq() + "_" + timestamp.getTime() + "_sign.image";
				form.setSignImagePath(FILE_DIR + signImageName);

				bytes = signImage.getBytes();
				filePath = Paths.get(realPath + signImageName);
				Files.write(filePath, bytes);
				webPath = Paths.get(staticPath + signImageName);
				Files.write(webPath, bytes);
			}
		}
		catch(IOException e) {
			e.printStackTrace();

			redirectAttributes.addFlashAttribute("result", "E");
			redirectAttributes.addFlashAttribute("message", "File upload error!");

			return "redirect:/admin/forms/";
		}

		int result = formDao.setForm(form);

		redirectAttributes.addFlashAttribute("result", "S");
		redirectAttributes.addFlashAttribute("message", "Save Successfully!");

		return "redirect:/admin/forms/";
	}
}
