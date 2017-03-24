package com.genpdf.quotation;

public class QuotationItem {
	
	private String division;
	private String description;
	private int quantity;
	private String unit;

	private String currency;
	private double unitPrice;
	private double amount;
	private String note;
	
	public QuotationItem() {
		
		super();
		
		this.setDivision("");
		this.setDescription("");
		this.setQuantity(0);
		this.setUnit("");
		this.setCurrency("KRW");
		this.setUnitPrice(0);
		this.setAmount(0);
		this.setNote("");
	}
	
	public QuotationItem(String division, String description, int quantity, String currency, double unitPrice, double totalAmount, String note) {
		
		this.setDivision(division);
		this.setDescription(description);
		this.setQuantity(quantity);
		this.setUnit(unit);
		this.setCurrency(currency);
		this.setUnitPrice(unitPrice);
		this.setAmount(totalAmount);
		this.setNote(note);
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
