package com.genpdf.quotation;

import com.genpdf.common.GenRequest;

public class QuotationRequest extends GenRequest {

	private Quotation quotation;

	public QuotationRequest() {
		super();
	}

	public QuotationRequest(String org, String docType, int seq, Quotation quotation) {
		super(org, docType, seq);
		this.quotation = quotation;
	}

	public Quotation getQuotation() {
		return quotation;
	}

	public void setQuotation(Quotation quotation) {
		this.quotation = quotation;
	}
}
