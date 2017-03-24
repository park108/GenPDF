package com.genpdf;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

public class Form{

	private String fontCode;

    PDType0Font font;
	PDType0Font fontBold;

	private float fontSizeTitle;
	private float fontSizeBody;
	private float fontSizeTableHeader;
	private float fontSizeTableBody;
	private float fontSizeFooter;
	
	private float pageWidth;
	private float pageHeight;
	
	private float marginLeft;
	private float marginRight;
	private float marginBottom;
	private float marginTop;
	
	private String logoImagePath;
	private String signImagePath;
	private String filePath;
	
	public Form() {
		super();
	}
	
	/**
	 * Preset 을 통한 Form 생성
	 * @param org 회사코드
	 * @param presetCode Preset코드
	 * @param document PDFBox document object
	 * @param page PDFBox page object
	 */
	public Form(String org, String presetCode, PDDocument document, PDPage page) {
		super();
		
		if("1".equals(presetCode)) {
			
			setFontCode("2");

			String fontPath = getFontPath(false);
			String fontPathBold = getFontPath(true);

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

			setFontSizeTitle(30);
			setFontSizeBody(11);
			setFontSizeTableHeader(10);
			setFontSizeTableBody(10);
			setFontSizeFooter(9);
			
			setPageWidth(page.getMediaBox().getWidth());
			setPageHeight(page.getMediaBox().getHeight());
			
			setMarginLeft(30);
			setMarginRight(30);
			setMarginBottom(30);
			setMarginTop(30);

			setLogoImagePath("/images/SK_PNG.png");
			setSignImagePath("/images/stamp_sample.png");
		}
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

	private String getFontPath(Boolean isBold) {
		
		final String fontDir = "/fonts/";
		String selectedFont = getFontCode();
		
		String fontFileName = "";
		
		if("1".equals(selectedFont)) {
			if(!isBold) {
				fontFileName = "NanumGothic.ttf";
			}
			else {
				fontFileName = "NanumGothicBold.ttf";
			}
		}
		else if("2".equals(selectedFont)) {
			if(!isBold) {
				fontFileName = "NanumMyeongjo.ttf";
			}
			else {
				fontFileName = "NanumMyeongjoBold.ttf";
			}
		}
		else if("3".equals(selectedFont)) {
			fontFileName = "NanumBrush.ttf";
		}
		else if("4".equals(selectedFont)) {
			fontFileName = "NanumPen.ttf";
		}
		
		return fontDir + fontFileName;
	}
}
