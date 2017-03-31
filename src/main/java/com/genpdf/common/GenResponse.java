package com.genpdf.common;

public abstract class GenResponse {

	private String docUrl;
	private String resultCode;
	private String resultMessage;

	public GenResponse() {
		super();
	}

	public GenResponse(String docUrl, String resultCode, String resultMessage) {
		this.docUrl = docUrl;
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
	}

	public String getDocUrl() {
		return docUrl;
	}

	public void setDocUrl(String docUrl) {
		this.docUrl = docUrl;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
}
