package com.genpdf.quotation;

import java.util.ArrayList;

public class QuotationHeader {
	
	private String title;
	private String subject;
	
	private String date;
	private String quotationNumber;

	private CompanyInfo supplier;
	private CompanyInfo customer;
	
	private ArrayList<String> notes;
	
	private String managerName;
	
	public QuotationHeader() {
		
		super();
		
		this.setTitle("");
		this.setSubject("");
		this.setDate("");
		this.setQuotationNumber("");
		this.setSupplier(new CompanyInfo());
		this.setCustomer(new CompanyInfo());
		this.setNotes(new ArrayList<String>());
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<String> getNotes() {
		return notes;
	}

	public void setNotes(ArrayList<String> notes) {
		this.notes = notes;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getQuotationNumber() {
		return quotationNumber;
	}

	public void setQuotationNumber(String quotationNumber) {
		this.quotationNumber = quotationNumber;
	}

	public CompanyInfo getSupplier() {
		return supplier;
	}

	public void setSupplier(CompanyInfo supplier) {
		this.supplier = supplier;
	}

	public CompanyInfo getCustomer() {
		return customer;
	}

	public void setCustomer(CompanyInfo customer) {
		this.customer = customer;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
}