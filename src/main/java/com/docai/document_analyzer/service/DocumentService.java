package com.docai.document_analyzer.service;

import com.docai.document_analyzer.util.PdfUtil;
import com.docai.document_analyzer.util.DocxUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentService {

    private final PdfUtil pdfUtil;
    private final DocxUtil docxUtil;
    private final OpenAIService openAIService;

    public DocumentService(PdfUtil pdfUtil, DocxUtil docxUtil, OpenAIService openAIService) {
        this.pdfUtil = pdfUtil;
        this.docxUtil = docxUtil;
        this.openAIService = openAIService;
    }

    public String analyzeDocument(MultipartFile file) throws Exception {
        String filename = file.getOriginalFilename().toLowerCase();
        String text;

        if (filename.endsWith(".pdf")) {
            text = pdfUtil.extractText(file.getInputStream());
        } else if (filename.endsWith(".docx")) {
            text = docxUtil.extractText(file.getInputStream());
        } else {
            throw new IllegalArgumentException("Only PDF and DOCX supported");
        }

        return openAIService.generateAnalysis(text);
    }
}

