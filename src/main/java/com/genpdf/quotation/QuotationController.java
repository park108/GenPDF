package com.genpdf.quotation;

import java.io.IOException;

import com.genpdf.common.Code;
import com.genpdf.common.CodeDao;
import com.genpdf.common.Form;
import com.genpdf.common.FormDao;
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

    	Form form = formDao.getForm(request.getOrg(), request.getDocType(), request.getSeq());
    	Code font = codeDao.getCode("F002", form.getFontCode());

	    QuotationResponse response;

	    if(null == form) {
		    response = new QuotationResponse("", "E", "등록되지 않은 Form 입니다: seq = " + request.getSeq());
	    }
	    else {
		    response = new Gen().generate(form, font, quotation);
	    }

	    return new ResponseEntity<QuotationResponse>(response, HttpStatus.CREATED);
    }
}
