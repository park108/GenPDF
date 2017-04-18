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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;

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

	@GetMapping(value = "/admin/forms/create")
	public String formCreate(Model model) {

		Form newForm = new Form();
		// TODO: org 값을 사용자 정보(Session)에서 가져와야 함
		newForm.setOrg("SKCC");

		model.addAttribute("title", "Form");
		model.addAttribute("form", newForm);
		model.addAttribute("documentTypes", codeDao.getCodeList("F001"));
		model.addAttribute("fonts", codeDao.getCodeList("F002"));

		return "form_detail";
	}

	@PostMapping(value = "/admin/forms/create")
	public String formCreate(@Valid Form form, BindingResult bindingResult
			, @RequestParam("logoImage") MultipartFile logoImage
			, @RequestParam("signImage") MultipartFile signImage
			, final RedirectAttributes redirectAttributes) {

		// TODO: org 값을 사용자 정보(Session)에서 가져와야 함
		form.setOrg("SKCC");

		if(bindingResult.hasErrors()) {

			redirectAttributes.addFlashAttribute("result", "E");
			redirectAttributes.addFlashAttribute("message", "Input correctly!");

			return "form_detail";
		}

		long id = formDao.insertForm(form);
		form.setId(id);

		try {
			form.setLogoImagePath(uploadImageFile(id, "logo", logoImage));
			form.setSignImagePath(uploadImageFile(id, "sign", signImage));
		}
		catch(IOException e) {
			e.printStackTrace();

			redirectAttributes.addFlashAttribute("result", "E");
			redirectAttributes.addFlashAttribute("message", "File upload error!");

			return "redirect:/admin/forms/";
		}

		formDao.updateForm(form);

		redirectAttributes.addFlashAttribute("result", "S");
		redirectAttributes.addFlashAttribute("message", "Save Successfully!");

		return "redirect:/admin/forms/";
	}

	@GetMapping(value = "/admin/forms/{id}/edit")
	public String formEdit(@PathVariable long id, Model model) {

		Form selectedForm = formDao.getForm(id);

		model.addAttribute("title", "Form");
		model.addAttribute("form", selectedForm);
		model.addAttribute("documentTypes", codeDao.getCodeList("F001"));
		model.addAttribute("fonts", codeDao.getCodeList("F002"));

		return "form_detail";
	}

	@PostMapping(value = "/admin/forms/{id}/edit")
	public String formEdit(@Valid Form form, BindingResult bindingResult
			, @RequestParam("logoImage") MultipartFile logoImage
			, @RequestParam("signImage") MultipartFile signImage
			, final RedirectAttributes redirectAttributes) {

		// TODO: org 값을 사용자 정보(Session)에서 가져와야 함
		form.setOrg("SKCC");

		if(bindingResult.hasErrors()) {

			redirectAttributes.addFlashAttribute("result", "E");
			redirectAttributes.addFlashAttribute("message", "Input correctly!");

			return "form_detail";
		}

		try {
			form.setLogoImagePath(uploadImageFile(form.getId(), "logo", logoImage));
			form.setSignImagePath(uploadImageFile(form.getId(), "sign", signImage));
		}
		catch(IOException e) {
			e.printStackTrace();

			redirectAttributes.addFlashAttribute("result", "E");
			redirectAttributes.addFlashAttribute("message", "File upload error!");

			return "redirect:/admin/forms/";
		}

		formDao.updateForm(form);

		redirectAttributes.addFlashAttribute("result", "S");
		redirectAttributes.addFlashAttribute("message", "Save Successfully!");

		return "redirect:/admin/forms/";
	}

	@PostMapping(value = "/admin/forms/{id}/delete")
	public String formDelete(@PathVariable long id, final RedirectAttributes redirectAttributes) {

		int result = formDao.delForm(id);

		redirectAttributes.addFlashAttribute("result", "S");
		redirectAttributes.addFlashAttribute("message", "Delete Successfully!");

		return "redirect:/admin/forms/";
	}

	/******************************************************************
	 /* Functions
	 ******************************************************************/
	private String uploadImageFile(long id, String type, MultipartFile file) throws IOException {

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		String fileName = id + "_" + timestamp.getTime() + "_" + type + ".image";

		String realPath = getClass().getResource(FILE_DIR).getPath();
		String staticPath = getClass().getResource(STATIC_DIR).getPath();

		byte[] bytes;
		Path filePath;
		Path webPath;

		if(!file.isEmpty()) {

			bytes = file.getBytes();
			filePath = Paths.get(realPath + file);
			Files.write(filePath, bytes);
			webPath = Paths.get(staticPath + file);
			Files.write(webPath, bytes);
		}

		return FILE_DIR + fileName;
	}
}
