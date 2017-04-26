package com.genpdf.common;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.HorizontalAlignment;
import be.quodlibet.boxable.Row;
import be.quodlibet.boxable.image.Image;
import com.genpdf.document.DocumentRequest;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.GregorianCalendar;

public class Gen {
	
	protected void setDocumentInfo(PDDocument document, String author, String title, String subject) {
		
		PDDocumentInformation info = document.getDocumentInformation();
		info.setAuthor(author);
		info.setTitle(title);
		info.setCreator("GenPDF");
		info.setSubject(subject);
		info.setCreationDate(new GregorianCalendar());
		info.setModificationDate(new GregorianCalendar());
	}

	private float getX(Form form, FormComponent component) {

		float x = 0;

		if(component.getxUnit().equals("pt")) {
			x = form.getMarginLeft() + component.getX();
		}
		else if(component.getxUnit().equals("%")) {
			float pageWidth = form.getPageWidth() - form.getMarginLeft() - form.getMarginRight();
			x = pageWidth * component.getX() / 100;
		}

		return x;
	}

	private float getY(Form form, FormComponent component) {

		float pageHeight = form.getPageHeight();
		float y = pageHeight;
		y -= form.getMarginTop();

		if(component.getyUnit().equals("pt")) {
			y -= component.getY();
		}
		else if(component.getyUnit().equals("%")) {
			y -= pageHeight * component.getY() / 100;
		}

		return y;
	}

	private float calcTextXbyAlign(float x, float width, float stringWidth, float fontSize, char align) {

		if('L' == align) {

		}
		else if('C' == align) {
			x += width / 2;
			x -= ((stringWidth / 1000.0f) * fontSize) / 2;
		}
		else if('R' == align) {
			x += width;
			x -= (stringWidth / 1000.0f) * fontSize;
		}

		return x;
	}

	private float getWidth(Form form, FormComponent component) {

		float width = 0;

		if(component.getWidthUnit().equals("pt")) {
			width = component.getWidth();
		}
		else if(component.getWidthUnit().equals("%")) {
			width = form.getPageWidth();
			width -= form.getMarginLeft();
			width -= form.getMarginRight();
			width = width * component.getWidth() / 100;
		}

		return width;
	}

	private float getHeight(Form form, FormComponent component) {

		float height = 0;

		if(component.getHeightUnit().equals("pt")) {
			height = component.getHeight();
		}
		else if(component.getWidthUnit().equals("%")) {
			height = form.getPageHeight();
			height -= form.getMarginTop();
			height -= form.getMarginBottom();
			height = height * component.getHeight() / 100;
		}

		return height;
	}

	protected void drawLine(PDPageContentStream stream, Form form, FormComponent component) throws IOException {

		float x = getX(form, component);
		float y = getY(form, component);

		float toX = x + getWidth(form, component);
		float toY = y + getHeight(form, component);

		if(!component.getHide()) {
			stream.moveTo(x, y);
			stream.lineTo(toX, toY);
			stream.stroke();
		}
	}

	protected void drawText(PDPageContentStream stream, Form form, FormComponent component, String text) throws IOException {

		PDType0Font font = component.getBold() ? form.getFontBold() : form.getFont();
		font.getFontDescriptor().setItalic(component.getItalic());
		font.getFontDescriptor().setItalicAngle(30);

		float fontSize = component.getFontSize();

		float x = getX(form, component);
		float y = getY(form, component);
		float width = getWidth(form, component);

		if(!component.getHide()) {

			x = calcTextXbyAlign(x, width, font.getStringWidth(text), fontSize, component.getAlign());

			stream.beginText();
			stream.setFont(font, fontSize);
			stream.newLineAtOffset(x, y);
			stream.showText(text);
			stream.endText();
		}
	}

	protected void drawTextList(PDPageContentStream stream, Form form, FormComponent component, ArrayList<String> texts) throws IOException {

		if(component.getHide()) return;

		int i = 0;

		PDType0Font font = component.getBold() ? form.getFontBold() : form.getFont();
		font.getFontDescriptor().setItalic(component.getItalic());
		font.getFontDescriptor().setItalicAngle(30);

		float fontSize = component.getFontSize();

		float addedHeight = component.getHeight();

		if(component.getHeightUnit().equals("%")) {
			addedHeight *= fontSize;
			addedHeight /= 100;
		}

		for(String text : texts) {

			float x = getX(form, component);
			float y = getY(form, component);
			float width = getWidth(form, component);

			x = calcTextXbyAlign(x, width, font.getStringWidth(text), fontSize, component.getAlign());

			stream.beginText();
			stream.setFont(font, fontSize);
			stream.newLineAtOffset(x, y - (addedHeight * i));
			stream.showText(text);
			stream.endText();

			++i;
		}
	}

	protected void drawImage(PDDocument document, PDPageContentStream stream, Form form, FormComponent component, String filePath) throws IOException {

		if(component.getHide()) return;

		float x = getX(form, component);
		float y = getY(form, component);
		float height = component.getHeight();
		float width = component.getWidth();

		URL url = getClass().getResource(filePath);
		System.out.println("Image path = " + filePath);
		Image image = new Image(ImageIO.read(new File(url.getPath())));

		if(image.getWidth() > width) {
			image = image.scaleByWidth(width);
		}
		if(image.getHeight() > height) {
			image = image.scaleByHeight(height);
		}

		image.draw(document, stream, x, y);
	}

	protected float drawTable(PDDocument document, PDPage page, Form form, FormComponent tableComponent, ArrayList<FormComponent> componentList, DocumentRequest req) throws IOException {


		float x = getX(form, tableComponent);
		float y = getY(form, tableComponent);
		float width = getWidth(form, tableComponent);

		if(tableComponent.getHide()) return y;

		// TODO: Footer Size 관리 및 등록
		BaseTable table = new BaseTable(y, form.getPageHeight() - form.getMarginTop() - 100
				, form.getMarginBottom(), width, x, document, page, true, true);

		// Header, Body, Footer Row Component 추출
		FormComponent headerRowComponent = null;
		FormComponent bodyRowComponent = null;
		FormComponent footerRowComponent = null;

		for(FormComponent row : componentList) {

			if("TRHD".equals(row.getComponentType()) && row.getParentCode().equals(tableComponent.getCode())) {
				headerRowComponent = row;
			}

			else if("TRBD".equals(row.getComponentType()) && row.getParentCode().equals(tableComponent.getCode())) {
				bodyRowComponent = row;
			}

			else if("TRFT".equals(row.getComponentType()) && row.getParentCode().equals(tableComponent.getCode())) {
				footerRowComponent = row;
			}
		}

		// Header, Body, Footer Column Component 추출
		ArrayList<FormComponent> headerColsComponentList = new ArrayList<>();
		ArrayList<FormComponent> bodyColsComponentList = new ArrayList<>();
		ArrayList<FormComponent> footerColsComponentList = new ArrayList<>();

		for(FormComponent column : componentList) {

			if("TCOL".equals(column.getComponentType())) {

				if (null != headerRowComponent && column.getParentCode().equals(headerRowComponent.getCode())) {
					headerColsComponentList.add(column);
				}

				else if (null != bodyRowComponent && column.getParentCode().equals(bodyRowComponent.getCode())) {
					bodyColsComponentList.add(column);
				}

				else if (null != footerRowComponent && column.getParentCode().equals(footerRowComponent.getCode())) {
					footerColsComponentList.add(column);
				}
			}
		}

		Ascending ascending = new Ascending();
		headerColsComponentList.sort(ascending);
		bodyColsComponentList.sort(ascending);
		footerColsComponentList.sort(ascending);

		// Header Row 생성
		if(null != headerRowComponent) {

			Row<PDPage> headerRow = table.createRow(headerRowComponent.getHeight());

			for (FormComponent headerCol : headerColsComponentList) {

				createCell(form, headerCol, headerRow, (String) req.getData(headerCol.getCode()));
			}

			table.addHeaderRow(headerRow);
		}

		// Body Row 생성
		if(null != bodyRowComponent) {

			int rowCount = Integer.parseInt((String) req.getData(bodyRowComponent.getCode()));

			for(int i = 0; i < rowCount; i++) {

				Row<PDPage> bodyRow = table.createRow(bodyRowComponent.getHeight());

				for (FormComponent bodyCol : bodyColsComponentList) {

					String cellText = "";

					try {
						cellText = (String) req.getList(bodyCol.getCode()).get(i);
					}
					catch(IndexOutOfBoundsException e) {
						System.out.println("Missing Data(row: " + i + ", col: " + bodyCol.getX() + ")");
					}

					createCell(form, bodyCol, bodyRow, cellText);
				}
			}
		}

		// Footer Row 생성
		if(null != footerRowComponent) {

			Row<PDPage> footerRow = table.createRow(footerRowComponent.getHeight());

			for(FormComponent footerCol : footerColsComponentList) {

				createCell(form, footerCol, footerRow, (String) req.getData(footerCol.getCode()));
			}
		}

		return table.draw();
	}

	private void createCell(Form form, FormComponent col, Row<PDPage> row, String text) {

		Cell<PDPage> cell = row.createCell(col.getWidth(), text);

		if('L' == col.getAlign()) {
			cell.setAlign(HorizontalAlignment.LEFT);
		}
		else if('C' == col.getAlign()) {
			cell.setAlign(HorizontalAlignment.CENTER);
		}
		else if('R' == col.getAlign()) {
			cell.setAlign(HorizontalAlignment.RIGHT);
		}

		cell.setFont(col.getBold() ? form.getFontBold() : form.getFont());
		cell.setFillColor(new Color(col.getBackgroundColorR(), col.getBackgroundColorG(), col.getBackgroundColorB()));
		cell.setFontSize(col.getFontSize());
	}

	// 내림차순
	class Descending implements Comparator<FormComponent> {

		@Override
		public int compare(FormComponent o1, FormComponent o2) {
			return Float.compare(o2.getX(), o1.getX());
		}
	}

	// 오름차순
	class Ascending implements Comparator<FormComponent> {

		@Override
		public int compare(FormComponent o1, FormComponent o2) {
			return Float.compare(o1.getX(), o2.getX());
		}
	}
}
