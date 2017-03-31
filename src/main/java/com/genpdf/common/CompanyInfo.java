package com.genpdf.common;

public class CompanyInfo {
	
	private String name;
	private String address1;
	private String address2;
	private String zipCode;
	private String phoneNumber;
	private String faxNumber;
	private String email;
	
	public CompanyInfo() {
		super();
	}
	
	public CompanyInfo(String name, String address1, String address2, String zipCode, String phoneNumber,
			String faxNumber, String email) {
		super();
		this.name = name;
		this.address1 = address1;
		this.address2 = address2;
		this.zipCode = zipCode;
		this.phoneNumber = phoneNumber;
		this.faxNumber = faxNumber;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
