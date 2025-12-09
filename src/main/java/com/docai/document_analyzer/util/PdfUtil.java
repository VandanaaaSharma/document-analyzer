package com.docai.document_analyzer.util;

import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import java.io.InputStream;

@Component
public class PdfUtil {

    private final Tika tika = new Tika();

    public String extractText(InputStream inputStream) throws Exception {
        return tika.parseToString(inputStream);
    }
}
