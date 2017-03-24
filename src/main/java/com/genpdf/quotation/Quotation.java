package com.genpdf.quotation;

import java.util.ArrayList;

public class Quotation {
	
	private QuotationHeader header;
	private ArrayList<QuotationItem> itemList;
	private double totalAmount;
	
	public Quotation() {
		
		super();
		
		header = new QuotationHeader();
		itemList = new ArrayList<QuotationItem>();
		setTotalAmount(0);
	}

	public void setHeader(QuotationHeader header) {	
		this.header = header;
	}
	
	public QuotationHeader getHeader() {
		return this.header;
	}

	public ArrayList<QuotationItem> getItemList() {
		return this.itemList;
	}

	public void setItemList(ArrayList<QuotationItem> itemList) {
		this.itemList = itemList;
		calcTotalAmount();
	}
	
	public void addItem(QuotationItem item) {
		this.itemList.add(item);
		calcTotalAmount();
	}
	
	public void addItem(String division, String description, int quantity, String currency, double unitPrice, double totalAmount, String note) {
		
		QuotationItem item = new QuotationItem(division, description, quantity, currency, unitPrice, totalAmount, note);
		this.addItem(item);
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	private void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public void calcTotalAmount() {

		double totalAmount = 0;
		
		for(QuotationItem item : itemList) {
			totalAmount += item.getAmount();
		}
		
		setTotalAmount(totalAmount);
	}
}
