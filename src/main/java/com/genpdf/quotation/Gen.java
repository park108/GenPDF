package com.genpdf.quotation;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import com.genpdf.common.Code;
import com.genpdf.common.FormComponent;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import com.genpdf.common.Form;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.HorizontalAlignment;
import be.quodlibet.boxable.Row;
import be.quodlibet.boxable.image.Image;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class Gen {

	public QuotationResponse generate(Form form, Map<String, FormComponent> componentMap, Code font, Quotation quotation) throws IOException {
		
		// Document 생성
		PDDocument document = new PDDocument();
		
		// Document 정보 등록
		setDocumentInfo(document, quotation);

		// Document 에 페이지 추가
		PDPage page = new PDPage();
		document.addPage(page);

		// Document 컨텐츠 추가
		PDPageContentStream contentStream = new PDPageContentStream(document, page);

		form.initialize(document, page, font);

		float x = 0;
	    float y = 0;
	    float width = 0;
		float height = 0;
	    float textWidth = 0;
	    
        String text;
	    BaseTable table;
	    Row<PDPage> headerRow;
	    Row<PDPage> row;
	    Cell<PDPage> cell;
	    DecimalFormat decimalFormat = new DecimalFormat("#,##0");
		
	    // 행간 지정
		contentStream.setLeading(20f);

		// Logo 이미지 출력
		drawImage(document, contentStream, form, componentMap.get("LOGO"), form.getLogoImagePath());

		// 타이틀 출력
		drawText(contentStream, form, componentMap.get("TITL"), quotation.getHeader().getTitle(), form.getFontBold(), form.getFontSizeTitle());
		
		// 견적 번호 출력
		drawText(contentStream, form, componentMap.get("QTNO"), quotation.getHeader().getQuotationNumber(), form.getFont(), form.getFontSizeBody());
		
		// 날짜 출력
		drawText(contentStream, form, componentMap.get("DATE"), quotation.getHeader().getDate(), form.getFont(), form.getFontSizeBody());

		// 주제 출력
		drawText(contentStream, form, componentMap.get("SUBJ"), quotation.getHeader().getSubject(), form.getFontBold(), form.getFontSizeBody());

		// 구분선 출력
		y = form.getPageHeight() - form.getMarginTop() - 73;
		
		contentStream.moveTo(form.getMarginLeft(), y);
		contentStream.lineTo(form.getPageWidth() - form.getMarginRight(), y);
		contentStream.stroke();

		// 공급자 정보 출력
		drawText(contentStream, form, componentMap.get("SUP0"), "공 급 자", form.getFontBold(), form.getFontSizeBody());
		drawText(contentStream, form, componentMap.get("SUP1"), quotation.getHeader().getSupplier().getName(), form.getFont(), form.getFontSizeBody());
		drawText(contentStream, form, componentMap.get("SUP2"), quotation.getHeader().getSupplier().getAddress1(), form.getFont(), form.getFontSizeBody());
		drawText(contentStream, form, componentMap.get("SUP3"), quotation.getHeader().getSupplier().getAddress2(), form.getFont(), form.getFontSizeBody());
		drawText(contentStream, form, componentMap.get("SUP4"), quotation.getHeader().getSupplier().getPhoneNumber(), form.getFont(), form.getFontSizeBody());
		drawText(contentStream, form, componentMap.get("SUP5"), quotation.getHeader().getSupplier().getFaxNumber(), form.getFont(), form.getFontSizeBody());
		drawText(contentStream, form, componentMap.get("SUP6"), quotation.getHeader().getSupplier().getEmail(), form.getFont(), form.getFontSizeBody());

		// 고객 정보 출력
		drawText(contentStream, form, componentMap.get("CUS0"), "고 객", form.getFontBold(), form.getFontSizeBody());
		drawText(contentStream, form, componentMap.get("CUS1"), quotation.getHeader().getCustomer().getName(), form.getFont(), form.getFontSizeBody());
		drawText(contentStream, form, componentMap.get("CUS2"), quotation.getHeader().getCustomer().getAddress1(), form.getFont(), form.getFontSizeBody());
		drawText(contentStream, form, componentMap.get("CUS3"), quotation.getHeader().getCustomer().getAddress2(), form.getFont(), form.getFontSizeBody());
		drawText(contentStream, form, componentMap.get("CUS4"), quotation.getHeader().getCustomer().getPhoneNumber(), form.getFont(), form.getFontSizeBody());
		drawText(contentStream, form, componentMap.get("CUS5"), quotation.getHeader().getCustomer().getFaxNumber(), form.getFont(), form.getFontSizeBody());
		drawText(contentStream, form, componentMap.get("CUS6"), quotation.getHeader().getCustomer().getEmail(), form.getFont(), form.getFontSizeBody());
		
		// 품목 상세 타이틀
		drawText(contentStream, form, componentMap.get("TTTL"), "품목 상세", form.getFontBold(), form.getFontSizeBody());

		// 품목 단위
		drawText(contentStream, form, componentMap.get("TUNT"), "(단위: KRW)", form.getFont(), form.getFontSizeBody());

		// 테이블 출력
		y = 470;
	    float tableWidth = form.getPageWidth() - form.getMarginLeft() - form.getMarginRight();
	    float colWidth[] = {15, 40, 12, 15, 18};
	    table = new BaseTable(y, 100, form.getMarginBottom(), tableWidth, form.getMarginLeft(), document, page, true, true);

	    headerRow = table.createRow(15f);
	    
	    cell = headerRow.createCell(colWidth[0], "구분");
	    cell.setFont(form.getFont());
	    cell.setFontSize(form.getFontSizeTableHeader());
	    cell.setFillColor(new Color(200, 200, 200));
	    
	    cell = headerRow.createCell(colWidth[1], "품목");
	    cell.setFont(form.getFont());
	    cell.setFontSize(form.getFontSizeTableHeader());
	    cell.setFillColor(new Color(200, 200, 200));
	    
	    cell = headerRow.createCell(colWidth[2], "수량");
	    cell.setFont(form.getFont());
	    cell.setFontSize(form.getFontSizeTableHeader());
	    cell.setFillColor(new Color(200, 200, 200));
	    cell.setAlign(HorizontalAlignment.RIGHT);
	    
	    cell = headerRow.createCell(colWidth[3], "단가");
	    cell.setFont(form.getFont());
	    cell.setFontSize(form.getFontSizeTableHeader());
	    cell.setFillColor(new Color(200, 200, 200));
	    cell.setAlign(HorizontalAlignment.RIGHT);
	    
	    cell = headerRow.createCell(colWidth[4], "금액");
	    cell.setFont(form.getFont());
	    cell.setFontSize(form.getFontSizeTableHeader());
	    cell.setFillColor(new Color(200, 200, 200));
	    cell.setAlign(HorizontalAlignment.RIGHT);
	    
	    table.addHeaderRow(headerRow);
	    
	    for(QuotationItem i : quotation.getItemList()) {
	    	
		    row = table.createRow(13f);
		    cell = row.createCell(colWidth[0],i.getDivision());
		    cell.setFont(form.getFont());
		    cell.setFontSize(form.getFontSizeTableBody());
	    	
		    cell = row.createCell(colWidth[1], i.getDescription());
		    cell.setFont(form.getFont());
		    cell.setFontSize(form.getFontSizeTableBody());
		    
		    String unit = i.getUnit();
		    cell = row.createCell(colWidth[2], decimalFormat.format(i.getQuantity()) + " " + unit);
		    cell.setFont(form.getFont());
		    cell.setFontSize(form.getFontSizeTableBody());
		    cell.setAlign(HorizontalAlignment.RIGHT);
		    
		    cell = row.createCell(colWidth[3], decimalFormat.format(i.getUnitPrice()));
		    cell.setFont(form.getFont());
		    cell.setFontSize(form.getFontSizeTableBody());
		    cell.setAlign(HorizontalAlignment.RIGHT);
		    
		    cell = row.createCell(colWidth[4], decimalFormat.format(i.getAmount()));
		    cell.setFont(form.getFont());
		    cell.setFontSize(form.getFontSizeTableBody());
		    cell.setAlign(HorizontalAlignment.RIGHT);
	    }
    	
	    row = table.createRow(15f);
	    cell = row.createCell(100 - colWidth[4], "계");
	    cell.setFont(form.getFont());
	    cell.setFontSize(form.getFontSizeTableBody());
	    cell.setAlign(HorizontalAlignment.CENTER);
	    
	    cell = row.createCell(colWidth[4], decimalFormat.format(quotation.getTotalAmount()));
	    cell.setFont(form.getFont());
	    cell.setFontSize(form.getFontSizeTableBody());
	    cell.setAlign(HorizontalAlignment.RIGHT);
	    cell.setFillColor(new Color(255, 255, 200));

	    table.draw();
		
		// 서명 이미지 출력
		drawImage(document, contentStream, form, componentMap.get("SIGN"), form.getSignImagePath());

	    // 대표이사명 출력
		drawText(contentStream, form, componentMap.get("APRV"), quotation.getHeader().getManagerName(), form.getFontBold(), form.getFontSizeBody());

	    // 구분선 출력
	    y = 120;
	    
		contentStream.moveTo(form.getMarginLeft(), y);
		contentStream.lineTo(form.getPageWidth() - form.getMarginRight(), y);
		contentStream.stroke();

		// 비고 출력
		y -= 20;
		
		contentStream.beginText();
		contentStream.setFont(form.getFont(), form.getFontSizeFooter());
		contentStream.newLineAtOffset(form.getMarginLeft(), y);
		
		for(String note : quotation.getHeader().getNotes()) {
			contentStream.showText(note);
			contentStream.newLine();
		}
		
		contentStream.endText();

		contentStream.close();
		
		// Document 저장
		String resourceDocsDir = "/public/";
		String docName = "quotation_sheet.pdf";
		String realPath = getClass().getResource(resourceDocsDir).getPath();
		System.out.println("Resource Path = " + realPath);
		document.save(realPath + docName);
		
		// Document 닫기
		document.close();

		// 파일 경로 세팅
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();

		String url = request.getRequestURL().toString();
		System.out.println("URL = " + url);

		String uri = request.getRequestURI().toString();
		System.out.println("URI = " + uri);

		String hostName = url.substring(0, url.length() - uri.length());
		String filePath = hostName + "/" + docName;
		System.out.println("Document FilePath = " + filePath);

		form.setFilePath(filePath);

		return new QuotationResponse(form.getFilePath(), "S", "파일을 정상적으로 생성했습니다");
	}
	
	private void setDocumentInfo(PDDocument document, Quotation quotation) {
		
		PDDocumentInformation info = document.getDocumentInformation();
		info.setAuthor(quotation.getHeader().getSupplier().getName());
		info.setTitle(quotation.getHeader().getTitle());
		info.setCreator("GenPDF");
		info.setSubject(quotation.getHeader().getSubject());
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

	private void drawText(PDPageContentStream stream, Form form, FormComponent component, String text, PDType0Font font, float fontSize) throws IOException {

		if(component.getHide()) return;

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
		stream.newLineAtOffset(x, y);
		stream.showText(text);
		stream.endText();
	}

	private void drawImage(PDDocument document, PDPageContentStream stream, Form form, FormComponent component, String filePath) throws IOException {

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
