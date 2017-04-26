package com.genpdf.document;

import com.genpdf.common.GenRequest;

import java.util.ArrayList;

public class DocumentRequest extends GenRequest {

	ArrayList<DocumentComponent> componentList;

	public DocumentRequest() {
		super();
		componentList = new ArrayList<DocumentComponent>();
	}

	public DocumentRequest(long id) {

		super(id);
	}

	public ArrayList<DocumentComponent> getComponentList() {
		return componentList;
	}

	public void setComponentList(ArrayList<DocumentComponent> componentList) {
		this.componentList = componentList;
	}

	public Object getData(String code) {

		Object data = null;

		for(DocumentComponent comp : componentList) {

			if(code.equals(comp.getCode())) {

				data = comp.getData();
			}
		}

		return data;
	}

	public ArrayList<String> getList(String code) {

		ArrayList<String> list = null;

		for(DocumentComponent comp : componentList) {

			if(code.equals(comp.getCode())) {

				list = comp.getList();
			}
		}

		if(null == list) {
			list = new ArrayList<String>();
		}

		return list;
	}
}
