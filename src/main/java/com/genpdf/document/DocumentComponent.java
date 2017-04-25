package com.genpdf.document;

import java.util.ArrayList;

public class DocumentComponent {

	String code;
	Object data;
	ArrayList<String> list;

	public DocumentComponent() {
		super();
		code = "";
		data = new Object();
		list = new ArrayList<String>();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ArrayList<String> getList() {
		return list;
	}

	public void setList(ArrayList<String> list) {
		this.list = list;
	}
}
