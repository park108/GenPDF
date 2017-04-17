package com.genpdf.common;

public abstract class GenRequest {

	private long id;

	public GenRequest() {
		super();
	}

	public GenRequest(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
