package com.genpdf.quotation;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import com.genpdf.common.FormDao;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class Gen {

	public QuotationResponse generate(Form form, Quotation quotation) throws IOException {
		
		// Document 생성
		PDDocument document = new PDDocument();
		
		// Document 정보 등록
		setDocumentInfo(document, quotation);

		// Document 에 페이지 추가
		PDPage page = new PDPage();
		document.addPage(page);

		// Document 컨텐츠 추가
		PDPageContentStream contentStream = new PDPageContentStream(document, page);

		form.initialize(document, page);
	    
	    float y = 0;
	    float textWidth = 0;
	    
        String text;
	    BaseTable table;
	    Row<PDPage> headerRow;
	    Row<PDPage> row;
	    Cell<PDPage> cell;
	    DecimalFormat decimalFormat = new DecimalFormat("#,##0");
		
	    // 행간 지정
		contentStream.setLeading(20f);

		// Logo 출력
		y = form.getPageHeight() - form.getMarginTop();

		String logoImagePath = form.getLogoImagePath();
		URL logoImageUrl = getClass().getResource(logoImagePath);
		System.out.println("Logo Image path = " + logoImageUrl.getPath());
		Image imageLogo = new Image(ImageIO.read(new File(logoImageUrl.getPath())));

		float logoHeight = 50;
		imageLogo = imageLogo.scaleByHeight(logoHeight);
		imageLogo.draw(document, contentStream, form.getMarginLeft(), y);
		
		// 타이틀 출력
	    float titleHeight = form.getPageHeight() - form.getMarginTop() - (logoHeight - (form.getFontSizeTitle() / 2));
		contentStream.beginText();
		contentStream.setFont(form.getFontBold(), form.getFontSizeTitle());
		text = quotation.getHeader().getTitle();
		textWidth = (form.getFontBold().getStringWidth(text) / 1000.0f) * form.getFontSizeTitle();
		contentStream.newLineAtOffset((form.getPageWidth() - textWidth) / 2, titleHeight);
		contentStream.showText(text);
		contentStream.endText();
		
		// 견적 번호 출력
	    y = y - logoHeight - 10;
	    		
		contentStream.beginText();
		contentStream.setFont(form.getFont(), form.getFontSizeBody());
		contentStream.newLineAtOffset(form.getMarginLeft(), y);
		text = "No. " + quotation.getHeader().getQuotationNumber();
		contentStream.showText(text);
		contentStream.endText();
		
		// 날짜 출력
		contentStream.beginText();
		contentStream.setFont(form.getFont(), form.getFontSizeBody());
		text = "" + quotation.getHeader().getDate();
		textWidth = (form.getFont().getStringWidth(text) / 1000.0f) * form.getFontSizeBody();
		
		contentStream.newLineAtOffset(form.getPageWidth() - textWidth - form.getMarginRight(), y);
		contentStream.showText(text);
		contentStream.endText();
		
		// 구분선 출력
		y -= 8;
		
		contentStream.moveTo(form.getMarginLeft(), y);
		contentStream.lineTo(form.getPageWidth() - form.getMarginRight(), y);
		contentStream.stroke();
		
		// 주제 출력
		y -= 15;
		
		contentStream.beginText();
		contentStream.setFont(form.getFontBold(), form.getFontSizeBody());
		contentStream.newLineAtOffset(form.getMarginLeft(), y);
		text = quotation.getHeader().getSubject();
		contentStream.showText(text);
		contentStream.endText();
		
		// 공급자 정보 출력
		y = y - 30;
		
		contentStream.beginText();
		contentStream.setFont(form.getFontBold(), form.getFontSizeBody());
		contentStream.newLineAtOffset(form.getMarginLeft(), y);
		text = "공 급 자";
		contentStream.showText(text);
		contentStream.endText();
		
		contentStream.beginText();
		contentStream.setFont(form.getFont(), form.getFontSizeBody());
		contentStream.newLineAtOffset(form.getMarginLeft(), y - 20);
		text = quotation.getHeader().getSupplier().getName();
		contentStream.showText(text);
		contentStream.newLine();
		text = quotation.getHeader().getSupplier().getAddress1();
		contentStream.showText(text);
		contentStream.newLine();
		text = quotation.getHeader().getSupplier().getAddress2();
		contentStream.showText(text);
		contentStream.newLine();
		text = "Phone: " + quotation.getHeader().getSupplier().getPhoneNumber();
		contentStream.showText(text);
		contentStream.newLine();
		text = "Fax: " + quotation.getHeader().getSupplier().getFaxNumber();
		contentStream.showText(text);
		contentStream.newLine();
		text = "Email: " + quotation.getHeader().getSupplier().getEmail();
		contentStream.showText(text);
		contentStream.endText();
		
		// 고객 정보 출력
		contentStream.beginText();
		contentStream.setFont(form.getFontBold(), form.getFontSizeBody());
		contentStream.newLineAtOffset(400, y);
		text = "고 객";
		contentStream.showText(text);
		contentStream.endText();
		
		contentStream.beginText();
		contentStream.setFont(form.getFont(), form.getFontSizeBody());
		contentStream.newLineAtOffset(400, y - 20);
		text = quotation.getHeader().getCustomer().getName();
		contentStream.showText(text);
		contentStream.newLine();
		text = quotation.getHeader().getCustomer().getAddress1();
		contentStream.showText(text);
		contentStream.newLine();
		text = quotation.getHeader().getCustomer().getAddress2();
		contentStream.showText(text);
		contentStream.newLine();
		text = "Phone: " + quotation.getHeader().getCustomer().getPhoneNumber();
		contentStream.showText(text);
		contentStream.newLine();
		text = "Fax: " + quotation.getHeader().getCustomer().getFaxNumber();
		contentStream.showText(text);
		contentStream.newLine();
		text = "Email: " + quotation.getHeader().getCustomer().getEmail();
		contentStream.showText(text);
		contentStream.endText();
		
		// 테이블 출력
		y = 480;
		
		contentStream.beginText();
		contentStream.setFont(form.getFontBold(), form.getFontSizeBody());
		contentStream.newLineAtOffset(form.getMarginLeft(), y);
		text = "품목 상세";
		contentStream.showText(text);
		contentStream.endText();
		
		contentStream.beginText();
		contentStream.setFont(form.getFont(), form.getFontSizeBody());
		text = "(단위: KRW)";
		textWidth = (form.getFont().getStringWidth(text) / 1000.0f) * form.getFontSizeBody();
		contentStream.newLineAtOffset(form.getPageWidth() - textWidth - form.getMarginRight(), y);
		contentStream.showText(text);
		contentStream.endText();
		
		y -= 10;
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
		String signImagePath = form.getSignImagePath();
		URL signImageUrl = getClass().getResource(signImagePath);
		System.out.println("Sign Image path = " + signImageUrl.getPath());
		Image imageSign = new Image(ImageIO.read(new File(signImageUrl.getPath())));

		float signHeight = 80;
		imageSign = imageSign.scaleByHeight(signHeight);
		y = 150 + (imageSign.getHeight() / 2);
		imageSign.draw(document, contentStream, form.getPageWidth() - imageSign.getWidth() - form.getMarginRight(), y);
	    
	    // 대표이사명 출력
	    y = 150;
	    
		contentStream.beginText();
		contentStream.setFont(form.getFontBold(), form.getFontSizeBody());
		text = quotation.getHeader().getManagerName();
		textWidth = (form.getFontBold().getStringWidth(text) / 1000.0f) * form.getFontSizeBody();
		contentStream.newLineAtOffset(form.getPageWidth() - textWidth - form.getMarginRight() - 100, y);
		contentStream.showText(text);
		contentStream.endText();
		
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
}
