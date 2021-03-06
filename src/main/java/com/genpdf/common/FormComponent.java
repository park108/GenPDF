package com.genpdf.common;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class FormComponent {

	private long id;

	private long formId;
	private String code;
	private String parentCode;

	private String description;

	private Boolean isHide;

	private String areaType;
	private String componentType;

	private float x;
	private String xUnit;
	private float y;
	private String yUnit;
	private float width;
	private String widthUnit;
	private float height;
	private String heightUnit;

	private char align;
	private Boolean isBold;
	private Boolean isItalic;
	private float fontSize;

	private int backgroundColorR;
	private int backgroundColorG;
	private int backgroundColorB;

	public FormComponent() {

		super();
	}

	public FormComponent(long id, long formId, String code, String parentCode, String description, Boolean isHide, String areaType, String componentType, float x, String xUnit, float y, String yUnit, float width, String widthUnit, float height, String heightUnit, char align, Boolean isBold, Boolean isItalic, float fontSize, int backgroundColorR, int backgroundColorG, int backgroundColorB) {
		this.id = id;
		this.formId = formId;
		this.code = code;
		this.parentCode = parentCode;
		this.description = description;
		this.isHide = isHide;
		this.areaType = areaType;
		this.componentType = componentType;
		this.x = x;
		this.xUnit = xUnit;
		this.y = y;
		this.yUnit = yUnit;
		this.width = width;
		this.widthUnit = widthUnit;
		this.height = height;
		this.heightUnit = heightUnit;
		this.align = align;
		this.isBold = isBold;
		this.isItalic = isItalic;
		this.fontSize = fontSize;
		this.backgroundColorR = backgroundColorR;
		this.backgroundColorG = backgroundColorG;
		this.backgroundColorB = backgroundColorB;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getFormId() {
		return formId;
	}

	public void setFormId(long formId) {
		this.formId = formId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getHide() {
		return isHide;
	}

	public void setHide(Boolean hide) {
		isHide = hide;
	}

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	public String getComponentType() {
		return componentType;
	}

	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public String getxUnit() {
		return xUnit;
	}

	public void setxUnit(String xUnit) {
		this.xUnit = xUnit;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public String getyUnit() {
		return yUnit;
	}

	public void setyUnit(String yUnit) {
		this.yUnit = yUnit;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public String getWidthUnit() {
		return widthUnit;
	}

	public void setWidthUnit(String widthUnit) {
		this.widthUnit = widthUnit;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public String getHeightUnit() {
		return heightUnit;
	}

	public void setHeightUnit(String heightUnit) {
		this.heightUnit = heightUnit;
	}

	public Boolean getBold() {
		return isBold;
	}

	public void setBold(Boolean bold) {
		isBold = bold;
	}

	public Boolean getItalic() {
		return isItalic;
	}

	public void setItalic(Boolean italic) {
		isItalic = italic;
	}

	public float getFontSize() {
		return fontSize;
	}

	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}

	public char getAlign() {
		return align;
	}

	public void setAlign(char align) {
		this.align = align;
	}

	public int getBackgroundColorR() {
		return backgroundColorR;
	}

	public void setBackgroundColorR(int backgroundColorR) {
		this.backgroundColorR = backgroundColorR;
	}

	public int getBackgroundColorG() {
		return backgroundColorG;
	}

	public void setBackgroundColorG(int backgroundColorG) {
		this.backgroundColorG = backgroundColorG;
	}

	public int getBackgroundColorB() {
		return backgroundColorB;
	}

	public void setBackgroundColorB(int backgroundColorB) {
		this.backgroundColorB = backgroundColorB;
	}
}
