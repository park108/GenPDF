package com.genpdf.quotation;

import java.awt.Color;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.genpdf.common.Code;
import com.genpdf.common.FormComponent;
import com.genpdf.common.Gen;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import com.genpdf.common.Form;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.HorizontalAlignment;
import be.quodlibet.boxable.Row;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class QuotationGenerator extends Gen {

	public QuotationResponse generate(Form form, Map<String, FormComponent> componentMap, Code font, ArrayList<Code> componentTypes, Quotation quotation) throws IOException {

		///////////////////////////////////////////////////////////////////////////////////////////////
		// Initialize
		///////////////////////////////////////////////////////////////////////////////////////////////

		// Document 생성
		PDDocument document = new PDDocument();
		
		// Document 정보 등록
		setDocumentInfo(document, quotation.getHeader().getSupplier().getName(), quotation.getHeader().getTitle(), quotation.getHeader().getSubject());

		// Document 에 페이지 추가
		PDPage page = new PDPage();
		document.addPage(page);

		// Form 초기화
		form.initialize(document, page, font);

	    // 숫자 양식 생성
	    DecimalFormat decimalFormat = new DecimalFormat("#,##0");

	    // 컴포넌트 맵을 리스트로 전환
		ArrayList<FormComponent> componentList = new ArrayList<FormComponent>(componentMap.values());


		///////////////////////////////////////////////////////////////////////////////////////////////
		// Header, Body 컨텐츠 생성
		///////////////////////////////////////////////////////////////////////////////////////////////

		// Header, Body 컨텐츠 추가
		PDPageContentStream contentStream = new PDPageContentStream(document, page);

		// Header, Body 컨텐츠 그리기
		for(FormComponent component : componentList) {

			if("HEAD".equals(component.getAreaType()) || "BODY".equals(component.getAreaType())) {

				if("IMG".equals(component.getComponentType())) {

					// TODO: 이미지 경로를 component 속성으로 옮겨야 함
					drawImage(document, contentStream, form, component, form.getLogoImagePath());
				}

				else if("TEXT".equals(component.getComponentType())) {

					// TODO: JSON 으로 받는 데이터에 Code 값을 같이 받아야 함
//					drawText(contentStream, form, component, quotation.getHeader().getTitle(), form.getFontBold(), form.getFontSizeTitle());
				}

				else if("LINE".equals(component.getComponentType())) {

					drawLine(contentStream, form, component);
				}
			}
		}

		// Logo 이미지 출력
//		drawImage(document, contentStream, form, componentMap.get("LOGO"), form.getLogoImagePath());

		// 타이틀 출력
		drawText(contentStream, form, componentMap.get("TITL"), quotation.getHeader().getTitle());
		
		// 견적 번호 출력
		drawText(contentStream, form, componentMap.get("QTNO"), quotation.getHeader().getQuotationNumber());
		
		// 날짜 출력
		drawText(contentStream, form, componentMap.get("DATE"), quotation.getHeader().getDate());

		// 주제 출력
		drawText(contentStream, form, componentMap.get("SUBJ"), quotation.getHeader().getSubject());

		// 공급자 정보 출력
		drawText(contentStream, form, componentMap.get("SUP0"), "공 급 자");
		drawText(contentStream, form, componentMap.get("SUP1"), quotation.getHeader().getSupplier().getName());
		drawText(contentStream, form, componentMap.get("SUP2"), quotation.getHeader().getSupplier().getAddress1());
		drawText(contentStream, form, componentMap.get("SUP3"), quotation.getHeader().getSupplier().getAddress2());
		drawText(contentStream, form, componentMap.get("SUP4"), quotation.getHeader().getSupplier().getPhoneNumber());
		drawText(contentStream, form, componentMap.get("SUP5"), quotation.getHeader().getSupplier().getFaxNumber());
		drawText(contentStream, form, componentMap.get("SUP6"), quotation.getHeader().getSupplier().getEmail());

		// 고객 정보 출력
		drawText(contentStream, form, componentMap.get("CUS0"), "고 객");
		drawText(contentStream, form, componentMap.get("CUS1"), quotation.getHeader().getCustomer().getName());
		drawText(contentStream, form, componentMap.get("CUS2"), quotation.getHeader().getCustomer().getAddress1());
		drawText(contentStream, form, componentMap.get("CUS3"), quotation.getHeader().getCustomer().getAddress2());
		drawText(contentStream, form, componentMap.get("CUS4"), quotation.getHeader().getCustomer().getPhoneNumber());
		drawText(contentStream, form, componentMap.get("CUS5"), quotation.getHeader().getCustomer().getFaxNumber());
		drawText(contentStream, form, componentMap.get("CUS6"), quotation.getHeader().getCustomer().getEmail());
		
		// 품목 상세 타이틀
		drawText(contentStream, form, componentMap.get("TTTL"), "품목 상세");

		// 품목 단위
		drawText(contentStream, form, componentMap.get("TUNT"), "(단위: KRW)");

		// 테이블 출력
		float y = 470;
	    float tableWidth = form.getPageWidth() - form.getMarginLeft() - form.getMarginRight();
	    float colWidth[] = {15, 40, 12, 15, 18};

		BaseTable table = new BaseTable(y, form.getPageHeight() - form.getMarginTop() - 100
			    , form.getMarginBottom(), tableWidth, form.getMarginLeft(), document, page, true, true);

		Row<PDPage> headerRow = table.createRow(15);

		Cell<PDPage> cell;
	    cell = headerRow.createCell(colWidth[0], "구분");
	    cell.setFont(form.getFont());
	    cell.setFontSize(10);
	    cell.setFillColor(new Color(200, 200, 200));
	    
	    cell = headerRow.createCell(colWidth[1], "품목");
	    cell.setFont(form.getFont());
	    cell.setFontSize(10);
	    cell.setFillColor(new Color(200, 200, 200));
	    
	    cell = headerRow.createCell(colWidth[2], "수량");
	    cell.setFont(form.getFont());
	    cell.setFontSize(10);
	    cell.setFillColor(new Color(200, 200, 200));
	    cell.setAlign(HorizontalAlignment.RIGHT);
	    
	    cell = headerRow.createCell(colWidth[3], "단가");
	    cell.setFont(form.getFont());
	    cell.setFontSize(10);
	    cell.setFillColor(new Color(200, 200, 200));
	    cell.setAlign(HorizontalAlignment.RIGHT);
	    
	    cell = headerRow.createCell(colWidth[4], "금액");
	    cell.setFont(form.getFont());
	    cell.setFontSize(10);
	    cell.setFillColor(new Color(200, 200, 200));
	    cell.setAlign(HorizontalAlignment.RIGHT);
	    
	    table.addHeaderRow(headerRow);

		Row<PDPage> row;
	    for(QuotationItem i : quotation.getItemList()) {
	    	
		    row = table.createRow(13);
		    cell = row.createCell(colWidth[0],i.getDivision());
		    cell.setFont(form.getFont());
		    cell.setFontSize(10);
	    	
		    cell = row.createCell(colWidth[1], i.getDescription());
		    cell.setFont(form.getFont());
		    cell.setFontSize(10);
		    
		    String unit = i.getUnit();
		    cell = row.createCell(colWidth[2], decimalFormat.format(i.getQuantity()) + " " + unit);
		    cell.setFont(form.getFont());
		    cell.setFontSize(10);
		    cell.setAlign(HorizontalAlignment.RIGHT);
		    
		    cell = row.createCell(colWidth[3], decimalFormat.format(i.getUnitPrice()));
		    cell.setFont(form.getFont());
		    cell.setFontSize(10);
		    cell.setAlign(HorizontalAlignment.RIGHT);
		    
		    cell = row.createCell(colWidth[4], decimalFormat.format(i.getAmount()));
		    cell.setFont(form.getFont());
		    cell.setFontSize(10);
		    cell.setAlign(HorizontalAlignment.RIGHT);
	    }
    	
	    row = table.createRow(15);
	    cell = row.createCell(100 - colWidth[4], "계");
	    cell.setFont(form.getFont());
	    cell.setFontSize(10);
	    cell.setAlign(HorizontalAlignment.CENTER);
	    
	    cell = row.createCell(colWidth[4], decimalFormat.format(quotation.getTotalAmount()));
	    cell.setFont(form.getFont());
	    cell.setFontSize(10);
	    cell.setAlign(HorizontalAlignment.RIGHT);
	    cell.setFillColor(new Color(255, 255, 200));

	    float tablePosition = table.draw();

	    // Header, Body 컨텐츠 닫기
		contentStream.close();


		///////////////////////////////////////////////////////////////////////////////////////////////
	    // Footer 컨텐츠 생성
		///////////////////////////////////////////////////////////////////////////////////////////////

		// Footer 영역이 부족할 경우 Page Breaking
		float footerHeight = form.getPageHeight() - form.getMarginTop() - form.getMarginBottom() - 550;
		float remainHeight = tablePosition - footerHeight;

		PDPage footerPage;

		if(0 > remainHeight) {
			footerPage = new PDPage();
			document.addPage(footerPage);
		}
		else {
			footerPage = document.getPage(document.getNumberOfPages() - 1);
		}

		// Footer 컨텐츠 생성
		PDPageContentStream footerStream = new PDPageContentStream(document, footerPage, PDPageContentStream.AppendMode.APPEND, false);


		// Footer 컨텐츠 그리기
		for(FormComponent component : componentList) {

			if("FOOT".equals(component.getAreaType())) {

				if("IMG".equals(component.getComponentType())) {

					// TODO: 이미지 경로를 component 속성으로 옮겨야 함
					drawImage(document, footerStream, form, component, form.getSignImagePath());
				}

				else if("TEXT".equals(component.getComponentType())) {

					// TODO: JSON 으로 받는 데이터에 Code 값을 같이 받아야 함
					drawText(footerStream, form, component, quotation.getHeader().getManagerName());
				}

				else if("LIST".equals(component.getComponentType())) {
					drawTextList(footerStream, form, component, quotation.getHeader().getNotes());
				}

				else if("LINE".equals(component.getComponentType())) {

					drawLine(footerStream, form, component);
				}
			}
		}

		// 서명 이미지 출력
//		drawImage(document, footerStream, form, componentMap.get("SIGN"), form.getSignImagePath());

	    // 대표이사명 출력
//		drawText(footerStream, form, componentMap.get("APRV"), quotation.getHeader().getManagerName(), form.getFontBold(), form.getFontSizeBody());

		// 비고 출력
//		drawTextList(footerStream, form, componentMap.get("NOTE"), quotation.getHeader().getNotes(), form.getFont(), form.getFontSizeFooter());

		// Footer 컨텐츠 닫기
		footerStream.close();


		///////////////////////////////////////////////////////////////////////////////////////////////
		// PDF 생성
		///////////////////////////////////////////////////////////////////////////////////////////////

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
}
