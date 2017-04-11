package com.genpdf.common;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import jdk.nashorn.internal.ir.annotations.Ignore;
import jdk.nashorn.internal.objects.annotations.Property;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

public class Form{

	private String org;
	private String docType;
	private int seq;

	private String description;

	private String fontCode;

	private float fontSizeTitle;
	private float fontSizeBody;
	private float fontSizeTableHeader;
	private float fontSizeTableBody;
	private float fontSizeFooter;
	
	private float marginLeft;
	private float marginRight;
	private float marginBottom;
	private float marginTop;

	private String logoImagePath;
	private String signImagePath;

	PDType0Font font;
	PDType0Font fontBold;

	private float pageWidth;
	private float pageHeight;

	private String filePath;

	@Autowired
	private CodeDao codeDao;

	public Form() {
		super();
	}

	public Form(String org, String docType, int seq, String description, String fontCode, float fontSizeTitle, float fontSizeBody, float fontSizeTableHeader, float fontSizeTableBody, float fontSizeFooter, float marginLeft, float marginRight, float marginBottom, float marginTop, String logoImagePath, String signImagePath) {

		super();

		this.org = org;
		this.docType = docType;
		this.seq = seq;
		this.description = description;
		this.fontCode = fontCode;
		this.fontSizeTitle = fontSizeTitle;
		this.fontSizeBody = fontSizeBody;
		this.fontSizeTableHeader = fontSizeTableHeader;
		this.fontSizeTableBody = fontSizeTableBody;
		this.fontSizeFooter = fontSizeFooter;
		this.marginLeft = marginLeft;
		this.marginRight = marginRight;
		this.marginBottom = marginBottom;
		this.marginTop = marginTop;
		this.logoImagePath = logoImagePath;
		this.signImagePath = signImagePath;
	}

	public void initialize(PDDocument document, PDPage page, Code font) {

		String fontPath = "/fonts/" + font.getAttr1();
		String fontPathBold = font.getAttr2().length() > 0 ? "/fonts/" + font.getAttr2() : "/fonts/" + font.getAttr1();

		URL fontUrl = getClass().getResource(fontPath);
		URL fontBoldUrl = getClass().getResource(fontPathBold);

		System.out.println("Font(general) path = " + fontUrl.getPath());
		System.out.println("Font(bold) path = " + fontBoldUrl.getPath());

		File fontFile = new File(fontUrl.getPath());
		File fontFileBold = new File(fontBoldUrl.getPath());

	    try {
			setFont(PDType0Font.load(document, fontFile));
		    setFontBold(PDType0Font.load(document, fontFileBold));
		} catch (IOException e) {
			e.printStackTrace();
		}

		setPageWidth(page.getMediaBox().getWidth());
		setPageHeight(page.getMediaBox().getHeight());
	}

	public String getFontCode() {
		return fontCode;
	}

	public void setFontCode(String fontCode) {
		this.fontCode = fontCode;
	}
	
    public PDType0Font getFont() {
		return font;
	}

	public void setFont(PDType0Font font) {
		this.font = font;
	}

	public PDType0Font getFontBold() {
		return fontBold;
	}

	public void setFontBold(PDType0Font fontBold) {
		this.fontBold = fontBold;
	}

	public float getFontSizeTitle() {
		return fontSizeTitle;
	}

	public void setFontSizeTitle(float fontSizeTitle) {
		this.fontSizeTitle = fontSizeTitle;
	}

	public float getFontSizeBody() {
		return fontSizeBody;
	}

	public void setFontSizeBody(float fontSizeBody) {
		this.fontSizeBody = fontSizeBody;
	}

	public float getFontSizeTableHeader() {
		return fontSizeTableHeader;
	}

	public void setFontSizeTableHeader(float fontSizeTableHeader) {
		this.fontSizeTableHeader = fontSizeTableHeader;
	}

	public float getFontSizeTableBody() {
		return fontSizeTableBody;
	}

	public void setFontSizeTableBody(float fontSizeTableBody) {
		this.fontSizeTableBody = fontSizeTableBody;
	}

	public float getFontSizeFooter() {
		return fontSizeFooter;
	}

	public void setFontSizeFooter(float fontSizeFooter) {
		this.fontSizeFooter = fontSizeFooter;
	}

	public float getPageWidth() {
		return pageWidth;
	}

	public void setPageWidth(float pageWidth) {
		this.pageWidth = pageWidth;
	}

	public float getPageHeight() {
		return pageHeight;
	}

	public void setPageHeight(float pageHeight) {
		this.pageHeight = pageHeight;
	}

	public float getMarginLeft() {
		return marginLeft;
	}

	public void setMarginLeft(float marginLeft) {
		this.marginLeft = marginLeft;
	}

	public float getMarginRight() {
		return marginRight;
	}

	public void setMarginRight(float marginRight) {
		this.marginRight = marginRight;
	}

	public float getMarginBottom() {
		return marginBottom;
	}

	public void setMarginBottom(float marginBottom) {
		this.marginBottom = marginBottom;
	}

	public float getMarginTop() {
		return marginTop;
	}

	public void setMarginTop(float marginTop) {
		this.marginTop = marginTop;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getLogoImagePath() {
		return logoImagePath;
	}

	public void setLogoImagePath(String logoImagePath) {
		this.logoImagePath = logoImagePath;
	}

	public String getSignImagePath() {
		return signImagePath;
	}

	public void setSignImagePath(String signImagePath) {
		this.signImagePath = signImagePath;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
