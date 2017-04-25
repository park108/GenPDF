package com.genpdf.common;

import be.quodlibet.boxable.image.Image;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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

	protected float getX(Form form, FormComponent component) {

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

	protected float getY(Form form, FormComponent component) {

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

	protected float getWidth(Form form, FormComponent component) {

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

	protected float getHeight(Form form, FormComponent component) {

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

	protected float drawLine(PDPageContentStream stream, Form form, FormComponent component) throws IOException {

		float x = getX(form, component);
		float y = getY(form, component);

		float toX = x + getWidth(form, component);
		float toY = y + getHeight(form, component);

		if(!component.getHide()) {
			stream.moveTo(x, y);
			stream.lineTo(toX, toY);
			stream.stroke();
		}

		return y > toY ? y : toY;
	}

	protected float drawText(PDPageContentStream stream, Form form, FormComponent component, String text) throws IOException {

		PDType0Font font = component.getBold() ? form.getFontBold() : form.getFont();
		font.getFontDescriptor().setItalic(component.getItalic());
		font.getFontDescriptor().setItalicAngle(30);

		float fontSize = component.getFontSize();

		float x = getX(form, component);
		float y = getY(form, component);
		float width = getWidth(form, component);

		if(!component.getHide()) {

			if (component.getAlign() == 'C') {
				x += width / 2;
				x -= ((font.getStringWidth(text) / 1000.0f) * fontSize) / 2;
			} else if (component.getAlign() == 'R') {
				x += width;
				x -= (font.getStringWidth(text) / 1000.0f) * fontSize;
			}

			stream.beginText();
			stream.setFont(font, fontSize);
			stream.newLineAtOffset(x, y);
			stream.showText(text);
			stream.endText();
		}

		return y + fontSize;
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

			if(component.getAlign() == 'C') {
				x += width / 2;
				x -= ((font.getStringWidth(text) / 1000.0f) * fontSize) / 2;
			}
			else if(component.getAlign() == 'R') {
				x += width;
				x -= (font.getStringWidth(text) / 1000.0f) * fontSize;
			}

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
}
