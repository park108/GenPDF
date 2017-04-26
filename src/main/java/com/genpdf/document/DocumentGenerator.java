package com.genpdf.document;

import com.genpdf.common.Code;
import com.genpdf.common.Form;
import com.genpdf.common.FormComponent;
import com.genpdf.common.Gen;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

public class DocumentGenerator extends Gen {

	public DocumentResponse generate(Form form, Map<String, FormComponent> componentMap, Code font, ArrayList<Code> componentTypes, DocumentRequest req) throws IOException {

		///////////////////////////////////////////////////////////////////////////////////////////////
		// Initialize
		///////////////////////////////////////////////////////////////////////////////////////////////

		// Document 생성
		PDDocument document = new PDDocument();
		
		// Document 정보 등록
		String author = (String) req.getData("SUP0");
		String title = (String) req.getData("TITL");
		String subject = (String) req.getData("SUBJ");

		setDocumentInfo(document, author, title, subject);

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

		float tablePosition = 0;

		// Header, Body 컨텐츠 추가
		PDPageContentStream contentStream = new PDPageContentStream(document, page);

		// Header, Body 컨텐츠 그리기
		for(FormComponent component : componentList) {

			Object data = req.getData(component.getCode());

			if(null != data && "HEAD".equals(component.getAreaType()) || "BODY".equals(component.getAreaType())) {

				if ("IMG".equals(component.getComponentType())) {

					// TODO: 이미지 경로를 component 속성으로 옮겨야 함
					drawImage(document, contentStream, form, component, form.getLogoImagePath());
				}
				else if ("TEXT".equals(component.getComponentType())) {
					drawText(contentStream, form, component, (String) req.getData(component.getCode()));
				}
				else if ("LIST".equals(component.getComponentType())) {
					drawTextList(contentStream, form, component, req.getList(component.getCode()));
				}
				else if ("LINE".equals(component.getComponentType())) {
					drawLine(contentStream, form, component);
				}
				else if ("TABL".equals(component.getComponentType())) {
					tablePosition = drawTable(document, document.getPage(0), form, component, componentList, req);
				}
			}
		}

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

			Object data = req.getData(component.getCode());

			if(null != data && "FOOT".equals(component.getAreaType())) {

				if ("IMG".equals(component.getComponentType())) {

					// TODO: 이미지 경로를 component 속성으로 옮겨야 함
					drawImage(document, footerStream, form, component, form.getSignImagePath());
				}
				else if ("TEXT".equals(component.getComponentType())) {
					drawText(footerStream, form, component, (String) data);
				}
				else if ("LIST".equals(component.getComponentType())) {
					drawTextList(footerStream, form, component, req.getList(component.getCode()));
				}
				else if ("LINE".equals(component.getComponentType())) {
					drawLine(footerStream, form, component);
				}
			}
		}

		// Footer 컨텐츠 닫기
		footerStream.close();


		///////////////////////////////////////////////////////////////////////////////////////////////
		// PDF 생성
		///////////////////////////////////////////////////////////////////////////////////////////////

		// Document 저장
		String resourceDocsDir = "/public/";
		String docName = "document.pdf";
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

		return new DocumentResponse(form.getFilePath(), "S", "파일을 정상적으로 생성했습니다");
	}
}
