package com.genpdf.quotation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.genpdf.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuotationController {

	@Autowired
	private CodeDao codeDao;

	@Autowired
	private FormDao formDao;

	@Autowired
	private FormComponentDao formComponentDao;

	private Quotation quotation;

	@RequestMapping(value = "/quotation/get")
    public ResponseEntity<Quotation> getQuotation() {

    	if(null == this.quotation) {
    		this.quotation = new Quotation();
    	}
    	
    	System.out.println("Get Quotation Info: "+ quotation.getHeader().getSubject() + ", " + quotation.getHeader().getQuotationNumber());
    	
    	return new ResponseEntity<Quotation>(this.quotation, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/quotation/create", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseEntity<QuotationResponse> createQuotation(@RequestBody QuotationRequest request) throws IOException {

		this.quotation = request.getQuotation();
    	this.quotation.calcTotalAmount();
        
    	System.out.println("Creating Quotation: "+ quotation.getHeader().getSubject() + ", " + quotation.getHeader().getQuotationNumber());

    	Form form = formDao.getForm(request.getId());
    	Map<String, FormComponent> componentMap = formComponentDao.getFormComponentMap(request.getId());
    	Code font = codeDao.getCode("F002", form.getFontCode());

	    QuotationResponse response;

	    if(null == form) {
		    response = new QuotationResponse("", "E", "등록되지 않은 Form 입니다: seq = " + request.getId());
	    }
	    else {
		    response = new Gen().generate(form, componentMap, font, quotation);
	    }

	    return new ResponseEntity<QuotationResponse>(response, HttpStatus.CREATED);
    }
}
