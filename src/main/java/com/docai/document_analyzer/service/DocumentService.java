package com.docai.document_analyzer.service;

import com.docai.document_analyzer.model.AnalysisResponse;
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

    public AnalysisResponse analyzeDocument(MultipartFile file) throws Exception {

        String filename = file.getOriginalFilename();
        String text;

        if (filename.toLowerCase().endsWith(".pdf")) {
            text = pdfUtil.extractText(file.getInputStream());
        } else if (filename.toLowerCase().endsWith(".docx")) {
            text = docxUtil.extractText(file.getInputStream());
        } else {
            throw new IllegalArgumentException("Only PDF/DOCX supported.");
        }

        AnalysisResponse res = openAIService.generateAnalysis(text);
        res.setFileName(filename);
        res.setFileSize(file.getSize());
        res.setDocumentText(text);

        return res;
    }
}
