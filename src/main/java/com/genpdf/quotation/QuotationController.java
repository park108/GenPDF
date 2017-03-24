package com.genpdf.quotation;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuotationController {
	
	Quotation quotation;

    @RequestMapping(value = "/quotation/get")
    public ResponseEntity<Quotation> getQuotation() {

    	if(null == this.quotation) {
    		this.quotation = new Quotation();
    	}
    	
    	System.out.println("Get Quotation Info: "+ quotation.getHeader().getSubject() + ", " + quotation.getHeader().getQuotationNumber());
    	
    	return new ResponseEntity<Quotation>(this.quotation, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/quotation/create", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> createQuotation(@RequestBody GenRequest request) throws IOException {

		this.quotation = request.getQuotation();
    	this.quotation.calcTotalAmount();
        
    	System.out.println("Creating Quotation: "+ quotation.getHeader().getSubject() + ", " + quotation.getHeader().getQuotationNumber());
    	
    	Gen gen = new Gen();
    	String filePath = gen.generate(request.getOrg(), request.getPresetCode(), quotation);
    	
    	return new ResponseEntity<String>("{\"path\":\"" + filePath + "\"}", HttpStatus.CREATED);
    }
}
