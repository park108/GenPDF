package com.genpdf.quotation;

import com.genpdf.common.GenResponse;

public class QuotationResponse extends GenResponse {

	public QuotationResponse() {
		super();
	}

	public QuotationResponse(String docUrl, String resultCode, String resultMessage) {
		super(docUrl, resultCode, resultMessage);
	}
}
