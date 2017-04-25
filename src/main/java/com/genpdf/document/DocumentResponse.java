package com.genpdf.document;

import com.genpdf.common.GenResponse;

public class DocumentResponse extends GenResponse {

	public DocumentResponse() {
		super();
	}

	public DocumentResponse(String docUrl, String resultCode, String resultMessage) {
		super(docUrl, resultCode, resultMessage);
	}
}
