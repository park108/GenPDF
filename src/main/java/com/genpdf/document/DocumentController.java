package com.genpdf.document;

import com.genpdf.common.*;
import com.genpdf.quotation.QuotationGenerator;
import com.genpdf.quotation.Quotation;
import com.genpdf.quotation.QuotationRequest;
import com.genpdf.quotation.QuotationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@RestController
public class DocumentController {

	@Autowired
	private CodeDao codeDao;

	@Autowired
	private FormDao formDao;

	@Autowired
	private FormComponentDao formComponentDao;

    @PostMapping(value = "/document/create", produces = "application/json;charset=UTF-8")
	public ResponseEntity<DocumentResponse>  createDocument(@RequestBody DocumentRequest request) throws IOException {

	    Form form = formDao.getForm(request.getId());
	    Map<String, FormComponent> componentMap = formComponentDao.getFormComponentMap(request.getId());
	    Code font = codeDao.getCode("F002", form.getFontCode());
	    ArrayList<Code> componentTypes = codeDao.getCodeList("F003");

	    DocumentResponse response;

	    if(null == form) {
		    response = new DocumentResponse("", "E", "등록되지 않은 Form 입니다: seq = " + request.getId());
	    }
	    else {
		    response = new DocumentGenerator().generate(form, componentMap, font, componentTypes, request);
	    }

	    return new ResponseEntity<DocumentResponse>(response, HttpStatus.CREATED);
    }
}
