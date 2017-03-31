package com.genpdf.common;

public abstract class GenRequest {

	private String org;
	private String docType;
	private int seq;

	public GenRequest() {
		super();
	}

	public GenRequest(String org, String docType, int seq) {
		this.org = org;
		this.docType = docType;
		this.seq = seq;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}
}
