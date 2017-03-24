package com.genpdf.quotation;

public class GenRequest {
	
	private String org;
	private String presetCode;
	private Quotation quotation;
	
	public GenRequest() {
		super();
	}

	public GenRequest(String org, String presetCode, Quotation quotation) {
		super();
		this.org = org;
		this.presetCode = presetCode;
		this.quotation = quotation;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getPresetCode() {
		return presetCode;
	}

	public void setPreset(String presetCode) {
		this.presetCode = presetCode;
	}

	public Quotation getQuotation() {
		return quotation;
	}

	public void setQuotation(Quotation quotation) {
		this.quotation = quotation;
	}
}
