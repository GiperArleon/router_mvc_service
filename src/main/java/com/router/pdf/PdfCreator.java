package com.router.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import java.io.FileOutputStream;

@Slf4j
public class PdfCreator {
    public void createPdf(String pdfPath, String text) {
        Document document = null;
        try {
            document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
            document.open();

            BaseFont bf = BaseFont.createFont("./fonts/consola.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font = new Font(bf, 14, Font.NORMAL);

            String[] str = text.split("\n");

            for(String line: str) {
                Chunk chunk = new Chunk(line, font);
                document.add(chunk);
                document.add(new Paragraph(""));
            }
        } catch(Exception e) {
            log.error("Error {} ", e.toString());
        } finally {
            if(document != null) {
                document.close();
            }
        }
    }
}
